package commands.changePageStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileOutput.PrinterJson;
import pages.Page;
import pages.PageFactory;
import utils.PageType;

public class ChangeToUpgradesStrategy implements IChangePageStrategy {
    private Session session;
    private ArrayNode output;
    private Page newPage;

    /* Constructor */
    public ChangeToUpgradesStrategy(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    @Override
    public void changePage() {
        if (!testChangePageValidity()) {
            return;
        }

        PageFactory pageFactory = new PageFactory();
        newPage = pageFactory.createPage(PageType.UPGRADES);

        // Push previous page on the page stack.
        Page previousPage = session.getCurrPage();
        session.pushPageStack(previousPage);

        session.setCurrPage(newPage);
    }

    /**
     * Checks if the changePage command is valid.
     * @return true if it is valid, false otherwise.
     */
    private boolean testChangePageValidity() {
        if (!session.getCurrPage().getNextPages().contains(PageType.UPGRADES)) {
            PrinterJson errorPrinter = new PrinterJson();
            errorPrinter.printError(output);
            return false;
        }
        return true;
    }

    @Override
    public void back() {
        PageFactory pageFactory = new PageFactory();
        newPage = pageFactory.createPage(PageType.UPGRADES);
        session.setCurrPage(newPage);
    }
}
