package commands.changePageStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileOutput.PrinterJson;
import pages.Page;
import pages.PageFactory;
import utils.PageType;

public class ChangeToUnauthenticatedStrategy implements IChangePageStrategy {
    private Session session;
    private ArrayNode output;
    private Page newPage;

    /* Constructor */
    public ChangeToUnauthenticatedStrategy(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    @Override
    public void changePage() {
        if (!testValidity()) {
            return;
        }

        session.setCurrUser(null);
        session.getCurrMovieList().clear();
        PageFactory pageFactory = new PageFactory();
        newPage = pageFactory.createPage(PageType.UNAUTHENTICATED);
        session.setCurrPage(newPage);

        // Reset the page stack after logout.
        session.resetPageStack();
    }

    /**
     * Checks if the changePage command is valid.
     * @return true if it is valid, false otherwise.
     */
    private boolean testValidity() {
        if (!session.getCurrPage().getNextPages().contains(PageType.UNAUTHENTICATED)) {
            PrinterJson errorPrinter = new PrinterJson();
            errorPrinter.printError(output);
            return false;
        }
        return true;
    }

    @Override
    public void back() {
        // Cannot go back to Unauthenticated Homepage while there is still a logged-in user.
        PrinterJson printerJson = new PrinterJson();
        printerJson.printError(output);
    }
}
