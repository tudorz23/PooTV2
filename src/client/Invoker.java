package client;

import commands.ICommand;
import java.util.LinkedList;

/**
 * Invoker for the Command pattern.
 */
public class Invoker {
    private LinkedList<ICommand> commandList;

    /* Constructor */
    public Invoker() {
        commandList = new LinkedList<>();
    }

    public void reset() {
        commandList.clear();
    }

    public void execute(ICommand command) {
        command.execute();
        commandList.add(command);
    }
}
