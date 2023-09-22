package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.changePageStrategy.ChangeToAuthenticatedStrategy;
import commands.changePageStrategy.ChangeToUnauthenticatedStrategy;
import database.User;
import fileInput.ActionInput;
import fileInput.CredentialsInput;
import fileOutput.PrinterJson;
import utils.PageType;

public class LoginCommand implements ICommand {
    private Session session;
    private ActionInput actionInput;
    private ArrayNode output;

    /* Constructor */
    public LoginCommand(Session session, ActionInput actionInput, ArrayNode output) {
        this.session = session;
        this.actionInput = actionInput;
        this.output = output;
    }

    @Override
    public void execute() {
        PrinterJson printerJson = new PrinterJson();

        // Check if we are on login page.
        if (session.getCurrPage().getType() != PageType.LOGIN) {
            printerJson.printError(output);

            if (session.getCurrPage().getType() == PageType.REGISTER) {
                moveToUnauthenticatedHomepage();
            }
            return;
        }

        if (!testValidity()) {
            printerJson.printError(output);
            moveToUnauthenticatedHomepage();
            return;
        }

        // Change to Authenticated Homepage.
        moveToAuthenticatedHomepage();
        printerJson.printSuccess(session.getCurrMovieList(), session.getCurrUser(), output);
    }

    /**
     * Checks if the introduced credentials correspond to an already registered user.
     * Also, if a registered user is found, it validates the login.
     * @return true if there is a user with those credentials in the database, false otherwise.
     */
    private boolean testValidity() {
        CredentialsInput credentialsInput = actionInput.getCredentials();
        String nameInput = credentialsInput.getName();
        String passwordInput = credentialsInput.getPassword();

        for (User user : session.getDatabase().getRegisteredUsers()) {
            if (nameInput.equals(user.getCredentials().getName())
                && passwordInput.equals(user.getCredentials().getPassword())) {
                validateLogin(user);
                return true;
            }
        }
        return false;
    }

    private void validateLogin(User user) {
        session.setCurrUser(user);
    }

    /**
     * Shortcut to move to Unauthenticated Homepage.
     */
    private void moveToUnauthenticatedHomepage() {
        ChangeToUnauthenticatedStrategy failedLoginStrategy
                    = new ChangeToUnauthenticatedStrategy(session, output);
        failedLoginStrategy.changePage();
    }

    /**
     * Shortcut to move to Authenticated Homepage.
     */
    private void moveToAuthenticatedHomepage() {
        ChangeToAuthenticatedStrategy succeededLoginStrategy
                = new ChangeToAuthenticatedStrategy(session, output);
        succeededLoginStrategy.changePage();
    }
}
