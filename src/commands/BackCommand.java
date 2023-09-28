package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.changePageStrategy.ChangePageStrategyFactory;
import commands.changePageStrategy.IChangePageStrategy;
import fileOutput.PrinterJson;
import pages.Page;
import pages.SeeDetailsPage;
import utils.PageType;

import java.util.NoSuchElementException;

public class BackCommand implements ICommand {
    private Session session;
    private ArrayNode output;

    /* Constructor */
    public BackCommand(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    @Override
    public void execute() {
        Page previousPage;

        try {
            previousPage = session.popPageStack();
        } catch (NoSuchElementException noSuchElementException) {
            PrinterJson printerJson = new PrinterJson();
            printerJson.printError(output);
            return;
        }

        // Pass the movie name if page type is "See Details", else pass null.
        ChangePageStrategyFactory factory = new ChangePageStrategyFactory(session, output,
                previousPage.getType() == PageType.SEE_DETAILS
                        ? ((SeeDetailsPage) previousPage).getMovie().getName()
                        : null);

        IChangePageStrategy changeBackStrategy;

        try {
            changeBackStrategy = factory.getChangePageStrategy(previousPage.getType());
        } catch (IllegalArgumentException illegalArgumentException) {
            PrinterJson printerJson = new PrinterJson();
            printerJson.printError(output);
            return;
        }

        changeBackStrategy.back();
    }
}
