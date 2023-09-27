package commands.changePageStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import fileOutput.PrinterJson;
import pages.Page;
import pages.PageFactory;
import pages.SeeDetailsPage;
import utils.PageType;

import java.util.ArrayList;

public class ChangeToSeeDetailsStrategy implements IChangePageStrategy {
    private Session session;
    private ArrayNode output;
    private String movieName;
    private Page newPage;

    /* Constructor */
    public ChangeToSeeDetailsStrategy(Session session, ArrayNode output, String movieName) {
        this.session = session;
        this.output = output;
        this.movieName = movieName;
    }

    @Override
    public void changePage() {
        PrinterJson printerJson = new PrinterJson();

        if (!testChangePageValidity()) {
            printerJson.printError(output);
            return;
        }

        PageFactory pageFactory = new PageFactory();
        newPage = pageFactory.createPage(PageType.SEE_DETAILS);

        // Search in the currently displayed movie list for the wanted movie.
        Movie wantedMovie = findMovieInList(session.getCurrMovieList());

        if (wantedMovie == null) {
            printerJson.printError(output);
            return;
        }

        // Push previous page on the page stack.
        Page previousPage = session.getCurrPage();
        session.pushPageStack(previousPage);

        setWantedMovie(wantedMovie);
    }

    /**
     * Checks if the changePage command is valid.
     * @return true if it is valid, false otherwise.
     */
    private boolean testChangePageValidity() {
        // SeeDetailsPage can only be accessed from MoviesPage.
        return session.getCurrPage().getType() == PageType.MOVIES;
    }

    @Override
    public void back() {
        PrinterJson printerJson = new PrinterJson();

        PageFactory pageFactory = new PageFactory();
        newPage = pageFactory.createPage(PageType.SEE_DETAILS);

        // The wanted movie might have since been deleted from the database,
        // but, if it is still there, it surely still is visible to the current user.
        Movie wantedMovie = findMovieInList(session.getDatabase().getAvailableMovies());

        if (wantedMovie == null) {
            printerJson.printError(output);
            return;
        }

        setWantedMovie(wantedMovie);
    }

    /**
     * Selects the movie with the respective title from the passed list param
     * and sets it as new page's movie and session's current movie.
     * @param movieList list to search for the movie.
     * @return the wanted Movie object if it is found, null otherwise.
     */
    private Movie findMovieInList(ArrayList<Movie> movieList) {
        for (Movie movie : movieList) {
            if (movie.getName().equals(movieName)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Sets the Page's movie and the session's currently displayed movie  to the given movie.
     * @param wantedMovie movie to set to.
     */
    private void setWantedMovie(Movie wantedMovie) {
        ((SeeDetailsPage) newPage).setMovie(wantedMovie);
        session.resetCurrMovieList();
        session.getCurrMovieList().add(wantedMovie);

        session.setCurrPage(newPage);

        PrinterJson printerJson = new PrinterJson();
        printerJson.printSuccess(session.getCurrMovieList(),
                                    session.getCurrUser(), output);
    }
}
