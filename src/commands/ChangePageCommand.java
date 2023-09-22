package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.changePageStrategy.*;
import fileInput.ActionInput;
import fileOutput.PrinterJson;

public class ChangePageCommand implements ICommand {
    private Session session;
    private ActionInput actionInput;
    private ArrayNode output;

    /* Constructor */
    public ChangePageCommand(Session session, ActionInput actionInput, ArrayNode output) {
        this.session = session;
        this.actionInput = actionInput;
        this.output = output;
    }

    /**
     * Executes the changePage() method of IChangePageStrategy.
     */
    @Override
    public void execute() {
        ChangePageStrategyFactory factory = new ChangePageStrategyFactory(session, output);
        IChangePageStrategy changePageStrategy;

        try {
            changePageStrategy = factory.getChangePageStrategy(actionInput);
        } catch (IllegalArgumentException iae) {
            PrinterJson printerJson = new PrinterJson();
            printerJson.printError(output);
            return;
        }

        changePageStrategy.changePage();
    }
}
