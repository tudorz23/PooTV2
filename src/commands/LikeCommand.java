package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.User;
import fileOutput.PrinterJson;
import pages.SeeDetailsPage;
import utils.PageType;

public class LikeCommand implements ICommand {
    private Session session;
    private ArrayNode output;
    private User currUser;
    private Movie movie;

    /* Constructor */
    public LikeCommand(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    @Override
    public void execute() {
        PrinterJson printerJson = new PrinterJson();

        if (!testValidity()) {
            printerJson.printError(output);
            return;
        }

        if (currUser.getLikedMovies().contains(movie)) {
            printerJson.printError(output);
        }

        currUser.getLikedMovies().add(movie);

        int currNumLikes = movie.getNumLikes();
        currNumLikes++;
        movie.setNumLikes(currNumLikes);

        printerJson.printSuccess(session.getCurrMovieList(), session.getCurrUser(), output);
    }

    /**
     * Tests if the Like action is valid.
     * @return true if valid, false otherwise.
     */
    private boolean testValidity() {
        // Current page should be SeeDetailsPage.
        if (session.getCurrPage().getType() != PageType.SEE_DETAILS) {
            return false;
        }

        currUser = session.getCurrUser();
        movie = ((SeeDetailsPage) session.getCurrPage()).getMovie();

        // Check if the movie was purchased by the user.
        if (!currUser.getPurchasedMovies().contains(movie)) {
            return false;
        }

        // Check if the movie was watched by the user.
        if (!currUser.getWatchedMovies().contains(movie)) {
            return false;
        }

        return true;
    }
}
