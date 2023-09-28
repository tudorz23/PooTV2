package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.User;
import fileInput.ActionInput;
import fileOutput.PrinterJson;
import pages.SeeDetailsPage;
import utils.PageType;

public class SubscribeCommand implements ICommand {
    private Session session;
    private ActionInput actionInput;
    private ArrayNode output;

    /* Constructor */
    public SubscribeCommand(Session session, ActionInput actionInput, ArrayNode output) {
        this.session = session;
        this.actionInput = actionInput;
        this.output = output;
    }

    @Override
    public void execute() {
        String subscribeGenre = actionInput.getSubscribedGenre();

        if (!testSubscribeValidity(subscribeGenre)) {
            PrinterJson printerJson = new PrinterJson();
            printerJson.printError(output);
            return;
        }

        session.getCurrUser().getSubscribedGenres().add(subscribeGenre);
    }

    /**
     * Checks if the subscribe operation is valid.
     * @param subscribeGenre genre to subscribe to
     * @return true if action is valid, false otherwise.
     */
    private boolean testSubscribeValidity(String subscribeGenre) {
        // Page should be SeeDetails.
        if (session.getCurrPage().getType() != PageType.SEE_DETAILS) {
            return false;
        }

        User currUser = session.getCurrUser();

        // Check if the user is already subscribed to that genre.
        if (currUser.getSubscribedGenres().contains(subscribeGenre)) {
            return false;
        }

        // Check if currently displayed movie contains the wanted genre.
        Movie currDispMovie = ((SeeDetailsPage) session.getCurrPage()).getMovie();
        if (!currDispMovie.getGenres().contains(subscribeGenre)) {
            return false;
        }

        return true;
    }
}
