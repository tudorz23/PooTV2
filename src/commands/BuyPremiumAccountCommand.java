package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileOutput.PrinterJson;
import utils.PageType;

public class BuyPremiumAccountCommand implements ICommand {
    private Session session;
    private ArrayNode output;

    /* Constructor */
    public BuyPremiumAccountCommand(Session session, ArrayNode output) {
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

        if (!session.getCurrUser().buyPremiumAccount()) {
            printerJson.printError(output);
        }
    }

    /**
     * Tests if the Buy Premium Account action is valid.
     * @return true if valid, false otherwise.
     */
    private boolean testValidity() {
        // Current page should be UpgradesPage.
        if (session.getCurrPage().getType() != PageType.UPGRADES) {
            return false;
        }
        return true;
    }
}
