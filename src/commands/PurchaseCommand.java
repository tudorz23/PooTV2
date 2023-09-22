package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.User;
import fileOutput.PrinterJson;
import pages.SeeDetailsPage;
import utils.PageType;

import static utils.Constants.MOVIE_PRICE;

public class PurchaseCommand implements ICommand {
    private Session session;
    private ArrayNode output;
    private User currUser;
    private Movie movie;

    /* Constructor */
    public PurchaseCommand(Session session, ArrayNode output) {
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

        if (currUser.getCredentials().getAccountType().equals("standard")) {
            if (!purchaseFromStandardAccount()) {
                printerJson.printError(output);
                return;
            }
            printerJson.printSuccess(session.getCurrMovieList(), session.getCurrUser(), output);
        } else if (currUser.getCredentials().getAccountType().equals("premium")) {
            if (!purchaseFromPremiumAccount()) {
                printerJson.printError(output);
                return;
            }
            printerJson.printSuccess(session.getCurrMovieList(), session.getCurrUser(), output);
        }
    }

    /**
     * Tests if the Purchase action is valid.
     * @return true if valid, false otherwise.
     */
    private boolean testValidity() {
        // Current page should be SeeDetailsPage.
        if (session.getCurrPage().getType() != PageType.SEE_DETAILS) {
            return false;
        }

        currUser = session.getCurrUser();
        movie = ((SeeDetailsPage) session.getCurrPage()).getMovie();

        if (currUser.getPurchasedMovies().contains(movie)) {
            return false;
        }

        return true;
    }

    /**
     * Executes the purchasing of a movie for a user with standard account.
     * @return true if the purchasing is done successfully, false otherwise.
     */
    private boolean purchaseFromStandardAccount() {
        int currTokensCount = currUser.getTokensCount();
        if (currTokensCount < MOVIE_PRICE) {
            return false;
        }

        currTokensCount -= MOVIE_PRICE;
        currUser.setTokensCount(currTokensCount);
        currUser.getPurchasedMovies().add(movie);

        return true;
    }

    /**
     * Executes the purchasing of a movie for a user with premium account.
     * @return true if the purchasing is done successfully, false otherwise.
     */
    private boolean purchaseFromPremiumAccount() {
        int currNumFreeMovies = currUser.getNumFreePremiumMovies();

        if (currNumFreeMovies > 0) {
            currNumFreeMovies--;
            currUser.setNumFreePremiumMovies(currNumFreeMovies);
            currUser.getPurchasedMovies().add(movie);
            return true;
        }

        // If the user has no free movies left, he is basically a standard user.
        return purchaseFromStandardAccount();
    }
}
