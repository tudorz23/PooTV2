package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.databaseStrategy.DatabaseAddStrategy;
import commands.databaseStrategy.DatabaseDeleteStrategy;
import commands.databaseStrategy.IDatabaseStrategy;
import fileInput.ActionInput;
import fileOutput.PrinterJson;

public class DatabaseCommand implements ICommand {
    private Session session;
    private ActionInput actionInput;
    private ArrayNode output;

    /* Constructor */
    public DatabaseCommand(Session session, ActionInput actionInput, ArrayNode output) {
        this.session = session;
        this.actionInput = actionInput;
        this.output = output;
    }

    @Override
    public void execute() {
        IDatabaseStrategy databaseStrategy;

        try {
            databaseStrategy = getDatabaseStrategy();
        } catch (IllegalArgumentException illegalArgumentException) {
            PrinterJson printerJson = new PrinterJson();
            printerJson.printError(output);
            return;
        }

        databaseStrategy.modifyDatabase();
    }

    /**
     * Factory Method to create IDatabaseStrategy objects.
     * @return IDatabaseStrategy object.
     * @throws IllegalArgumentException if "feature" argument is not supported.
     */
    private IDatabaseStrategy getDatabaseStrategy() throws IllegalArgumentException {
        if (actionInput.getFeature().equals("add")) {
            return new DatabaseAddStrategy(session, actionInput, output);
        } else if (actionInput.getFeature().equals("delete")) {
            return new DatabaseDeleteStrategy(session, actionInput, output);
        } else {
            throw new IllegalArgumentException("Feature " + actionInput.getFeature()
                        + " is not supported on Database.");
        }
    }
}
