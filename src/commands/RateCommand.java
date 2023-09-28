package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.User;
import fileInput.ActionInput;
import fileOutput.PrinterJson;
import pages.SeeDetailsPage;
import utils.PageType;

import static utils.Constants.MAX_RATING;
import static utils.Constants.MIN_RATING;

public class RateCommand implements ICommand {
    private Session session;
    private ActionInput actionInput;
    private ArrayNode output;
    private User currUser;
    private Movie movie;

    /* Constructor */
    public RateCommand(Session session, ActionInput actionInput, ArrayNode output) {
        this.session = session;
        this.actionInput = actionInput;
        this.output = output;
    }

    @Override
    public void execute() {
        PrinterJson printerJson = new PrinterJson();

        if (!testValidity()) {
            printerJson.printError(output);
            return;
        }

        int newRating = actionInput.getRate();
        if (newRating < MIN_RATING || newRating > MAX_RATING) {
            printerJson.printError(output);
            return;
        }

        if (!currUser.getRatedMovies().contains(movie)) {
            currUser.getRatedMovies().add(movie);

            int currNumRatings = movie.getNumRatings();
            currNumRatings++;
            movie.setNumRatings(currNumRatings);
        }

        // Register the new rating.
        movie.getRatingsMap().put(currUser, newRating);

        movie.computeRating();

        printerJson.printSuccess(session.getCurrMovieList(), session.getCurrUser(), output);
    }

    /**
     * Tests if the Rate action is valid.
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
