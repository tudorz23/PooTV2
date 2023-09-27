package client;

import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.CommandFactory;
import commands.ICommand;
import database.*;
import fileInput.ActionInput;
import fileInput.Input;
import fileInput.MovieInput;
import fileInput.UserInput;
import fileOutput.PrinterJson;

public final class UserInteraction {
    private Session session;
    private Database database;
    private Invoker invoker;

    private CommandFactory commandFactory;

    private final Input input;
    private ArrayNode output;

    /* Constructor */
    public UserInteraction(Input input, ArrayNode output) {
        this.input = input;
        this.output = output;
        invoker = new Invoker();
    }

    /**
     * Entry point to the program.
     */
    public void startUserInteraction() {
        prepareDatabase();
        initSession();
        commandFactory = new CommandFactory(session, output);
        startActions();
        // appendNotification();
        reset();
    }

    /**
     * Populates the database with data taken from the input.
     */
    public void prepareDatabase() {
        this.database = new Database();

        // Populate database with registered users.
        for (UserInput userInput : input.getUsers()) {
            Credentials credentials = new Credentials(userInput.getCredentials());
            User user = new User(credentials);
            database.getRegisteredUsers().add(user);
        }

        // Populate database with available movies.
        for (MovieInput movieInput : input.getMovies()) {
            Movie movie = new Movie(movieInput);
            database.getAvailableMovies().add(movie);
        }
    }

    /**
     * Clears the command list for the next tests.
     */
    public void reset() {
        invoker.reset();
    }

    /**
     * Iterates through the actions from the input.
     */
    public void startActions() {
        for (ActionInput actionInput : input.getActions()) {
            executeAction(actionInput);
        }
    }

    /**
     * Calls the Command Factory to generate command based on the actionInput.
     * @param actionInput the current action.
     */
    private void executeAction(ActionInput actionInput) {
        ICommand command;

        command = commandFactory.getCommand(actionInput);

//        try {
//            command = commandFactory.getCommand(actionInput);
//        } catch (IllegalArgumentException iae) {
//            return;
//        }

        invoker.execute(command);
    }

    /**
     * Initializes a new Session.
     */
    public void initSession() {
        session = new Session(database);
    }

    // Test for test 8
    private void appendNotification() {
        Notification notification = new Notification("No recommendation", "Recommendation");
        session.getCurrUser().getNotifications().add(notification);

        PrinterJson printerJson = new PrinterJson();
        printerJson.printRecommendation(session.getCurrUser(), output);
    }
}
