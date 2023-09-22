package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileInput.ActionInput;
import fileOutput.PrinterJson;
import utils.PageType;

public class BuyTokensCommand implements ICommand {
    private Session session;
    private ActionInput actionInput;
    private int count;
    private ArrayNode output;

    /* Constructor */
    public BuyTokensCommand(Session session, ActionInput actionInput, ArrayNode output) {
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

        if (!session.getCurrUser().buyTokens(count)) {
            printerJson.printError(output);
        }
    }

    /**
     * Tests if the Buy Tokens action is valid.
     * @return true if valid, false otherwise.
     */
    private boolean testValidity() {
        // Current page should be UpgradesPage.
        if (session.getCurrPage().getType() != PageType.UPGRADES) {
            return false;
        }

        if (actionInput.getCount() == null) {
            return false;
        }

        try {
            this.count = Integer.parseInt(actionInput.getCount());
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }
}
