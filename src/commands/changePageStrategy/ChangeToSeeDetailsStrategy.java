package commands.changePageStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import fileOutput.PrinterJson;
import pages.Page;
import pages.PageFactory;
import pages.SeeDetailsPage;
import utils.PageType;

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

        if (!testValidity()) {
            printerJson.printError(output);
            return;
        }

        PageFactory pageFactory = new PageFactory();
        newPage = pageFactory.createPage(PageType.SEE_DETAILS);

        if (!copyMovie()) {
            printerJson.printError(output);
            return;
        }

        session.setCurrPage(newPage);
        printerJson.printSuccess(session.getCurrMovieList(),
                                    session.getCurrUser(), output);
    }

    /**
     * Checks if the changePage command is valid.
     * @return true if it is valid, false otherwise.
     */
    private boolean testValidity() {
        // SeeDetailsPage can only be accessed from MoviesPage.
        if (session.getCurrPage().getType() != PageType.MOVIES) {
            return false;
        }
        return true;
    }

    /**
     * Selects the movie with the respective title from the currently displayed movie list
     * and sets it as new page's movie and session's current movie.
     * @return true if the movie is found, false otherwise.
     */
    private boolean copyMovie() {
        for (Movie movie : session.getCurrMovieList()) {
            if (movie.getName().equals(movieName)) {
                ((SeeDetailsPage) newPage).setMovie(movie);
                break;
            }
        }

        // Check if the movie is found.
        if (((SeeDetailsPage) newPage).getMovie() == null) {
            return false;
        }

        session.resetCurrMovieList();
        session.getCurrMovieList().add(((SeeDetailsPage) newPage).getMovie());

        return true;
    }
}
