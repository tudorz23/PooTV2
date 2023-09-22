package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileInput.ActionInput;
import utils.CommandType;

public class CommandFactory {
    private Session session;
    private ArrayNode output;

    /* Constructor */
    public CommandFactory(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    /**
     * Factory method that creates ICommand instances, based on the actionInput.
     * @param actionInput key that decides the type of instance that is created.
     * @return ICommand object.
     * @throws IllegalArgumentException if command is not supported.
     */
    public ICommand getCommand(ActionInput actionInput) throws IllegalArgumentException {
        // If it is a "change page" command.
        if (actionInput.getType().equals("change page")) {
            return new ChangePageCommand(session, actionInput, output);
        }

        // Now, it surely is an "on page" command.
        CommandType commandType = CommandType.fromString(actionInput.getFeature());

        if (commandType == null) {
            throw new IllegalArgumentException("Command " + actionInput.getFeature()
                        + " is not supported.");
        }

        switch (commandType) {
            case LOGIN -> {
                return new LoginCommand(session, actionInput, output);
            }
            case REGISTER -> {
                return new RegisterCommand(session, actionInput, output);
            }
            case LOGOUT -> {
                return new LogoutCommand(session, output);
            }
            case SEARCH -> {
                return new SearchCommand(session, actionInput, output);
            }
            case FILTER -> {
                return new FilterCommand(session, actionInput, output);
            }
            case BUY_TOKENS -> {
                return new BuyTokensCommand(session, actionInput, output);
            }
            case BUY_PREMIUM_ACCOUNT -> {
                return new BuyPremiumAccountCommand(session, output);
            }
            case PURCHASE -> {
                return new PurchaseCommand(session, output);
            }
            case WATCH -> {
                return new WatchCommand(session, output);
            }
            case LIKE -> {
                return new LikeCommand(session, output);
            }
            case RATE -> {
                return new RateCommand(session, actionInput, output);
            }
            default -> throw new IllegalArgumentException("Command is not supported.");
        }
    }
}
