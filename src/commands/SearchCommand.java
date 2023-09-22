package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import fileInput.ActionInput;
import fileOutput.PrinterJson;
import pages.MoviesPage;
import utils.PageType;

public class SearchCommand implements ICommand {
    private Session session;
    private ActionInput actionInput;
    private ArrayNode output;

    /* Constructor */
    public SearchCommand(Session session, ActionInput actionInput, ArrayNode output) {
        this.session = session;
        this.actionInput = actionInput;
        this.output = output;
    }

    @Override
    public void execute() {
        PrinterJson printerJson = new PrinterJson();

        if (session.getCurrPage().getType() != PageType.MOVIES) {
            printerJson.printError(output);
            return;
        }

        // Clear the currently displayed movie list.
        session.resetCurrMovieList();

        // Search on the Page's movie list for films that start with that prefix.
        String prefix = actionInput.getStartsWith();
        for (Movie movie : ((MoviesPage) session.getCurrPage()).getMovies()) {
            if (movie.getName().startsWith(prefix)) {
                session.getCurrMovieList().add(movie);
            }
        }

        printerJson.printSuccess(session.getCurrMovieList(), session.getCurrUser(), output);
    }

}
