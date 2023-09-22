package commands.changePageStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileOutput.PrinterJson;
import pages.Page;
import pages.PageFactory;
import utils.PageType;

public class ChangeToAuthenticatedStrategy implements IChangePageStrategy {
    private Session session;
    private ArrayNode output;

    /* Constructor */
    public ChangeToAuthenticatedStrategy(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    @Override
    public void changePage() {
        if (!testValidity()) {
            return;
        }

        PageFactory pageFactory = new PageFactory();
        Page newPage = pageFactory.createPage(PageType.AUTHENTICATED);
        session.setCurrPage(newPage);

        session.getCurrMovieList().clear();
    }

    /**
     * Checks if the changePage command is valid.
     * @return true if it is valid, false otherwise.
     */
    private boolean testValidity() {
        if (!session.getCurrPage().getNextPages().contains(PageType.AUTHENTICATED)) {
            PrinterJson errorPrinter = new PrinterJson();
            errorPrinter.printError(output);
            return false;
        }
        return true;
    }
}
