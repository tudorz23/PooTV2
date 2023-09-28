package commands.changePageStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.User;
import fileOutput.PrinterJson;
import pages.MoviesPage;
import pages.Page;
import utils.PageType;
import pages.PageFactory;

public class ChangeToMoviesStrategy implements IChangePageStrategy {
    private Session session;
    private ArrayNode output;
    private Page newPage;

    /* Constructor */
    public ChangeToMoviesStrategy(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    @Override
    public void changePage() {
        if (!testChangePageValidity()) {
            return;
        }

        PageFactory pageFactory = new PageFactory();
        newPage = pageFactory.createPage(PageType.MOVIES);

        // Push previous page on the page stack.
        Page previousPage = session.getCurrPage();
        session.pushPageStack(previousPage);

        session.setCurrPage(newPage);
        copyMovies();

        PrinterJson successPrinter = new PrinterJson();
        successPrinter.printSuccess(session.getCurrMovieList(),
                                    session.getCurrUser(), output);
    }

    /**
     * Checks if the changePage command is valid.
     * @return true if it is valid, false otherwise.
     */
    private boolean testChangePageValidity() {
        if (!session.getCurrPage().getNextPages().contains(PageType.MOVIES)) {
            PrinterJson errorPrinter = new PrinterJson();
            errorPrinter.printError(output);
            return false;
        }
        return true;
    }

    /**
     * Selects the movies from the Database that are not banned in current user's country
     * and copies them to the new page and to the session's list.
     */
    private void copyMovies() {
        User user = session.getCurrUser();

        for (Movie movie : session.getDatabase().getAvailableMovies()) {
            if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                // Add to current page's Movie list.
                ((MoviesPage) newPage).getMovies().add(movie);
            }
        }

        // Copy the movie list to the session's movie list.
        session.resetCurrMovieList();
        for (Movie movie : ((MoviesPage) newPage).getMovies()) {
            session.getCurrMovieList().add(movie);
        }
    }

    @Override
    public void back() {
        PageFactory pageFactory = new PageFactory();
        newPage = pageFactory.createPage(PageType.MOVIES);
        session.setCurrPage(newPage);
        copyMovies();

        PrinterJson successPrinter = new PrinterJson();
        successPrinter.printSuccess(session.getCurrMovieList(),
                session.getCurrUser(), output);
    }
}
