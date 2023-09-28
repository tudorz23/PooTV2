package commands.changePageStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileOutput.PrinterJson;
import pages.Page;
import pages.PageFactory;
import utils.PageType;

public class ChangeToLoginStrategy implements IChangePageStrategy {
    private Session session;
    private ArrayNode output;
    private Page newPage;

    /* Constructor */
    public ChangeToLoginStrategy(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    @Override
    public void changePage() {
        if (!testChangePageValidity()) {
            return;
        }

        session.setCurrUser(null);
        PageFactory pageFactory = new PageFactory();
        newPage = pageFactory.createPage(PageType.LOGIN);
        session.setCurrPage(newPage);
    }

    /**
     * Checks if the changePage command is valid.
     * @return true if it is valid, false otherwise.
     */
    private boolean testChangePageValidity() {
        if (!session.getCurrPage().getNextPages().contains(PageType.LOGIN)) {
            PrinterJson errorPrinter = new PrinterJson();
            errorPrinter.printError(output);
            return false;
        }
        return true;
    }

    @Override
    public void back() {
        // Cannot go back to Login Page while there is still a logged-in user.
        PrinterJson printerJson = new PrinterJson();
        printerJson.printError(output);
    }
}
