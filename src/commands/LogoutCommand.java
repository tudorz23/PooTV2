package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.changePageStrategy.ChangeToUnauthenticatedStrategy;

public class LogoutCommand implements ICommand {
    private Session session;
    private ArrayNode output;

    /* Constructor */
    public LogoutCommand(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    @Override
    public void execute() {
        ChangeToUnauthenticatedStrategy changeToUnauthenticatedStrategy
                = new ChangeToUnauthenticatedStrategy(session, output);
        changeToUnauthenticatedStrategy.changePage();
    }
}
