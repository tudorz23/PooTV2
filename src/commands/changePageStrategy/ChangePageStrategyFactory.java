package commands.changePageStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileInput.ActionInput;
import utils.PageType;

public class ChangePageStrategyFactory {
    private Session session;
    private ArrayNode output;

    /* Constructor */
    public ChangePageStrategyFactory(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    /**
     * Factory method to get the appropriate change page strategy.
     * @return strategy of type IChangePageStrategy.
     * @throws IllegalArgumentException if the changePage argument is invalid.
     */
    public IChangePageStrategy getChangePageStrategy(ActionInput actionInput) throws IllegalArgumentException {
        String stringPageName = actionInput.getPage();

        PageType changePageType = PageType.fromString(stringPageName);

        if (changePageType == null) {
            throw new IllegalArgumentException("Page " + stringPageName + " is not supported.");
        }

        switch (changePageType) {
            case UNAUTHENTICATED -> {
                return new ChangeToUnauthenticatedStrategy(session, output);
            }
            case LOGIN -> {
                return new ChangeToLoginStrategy(session, output);
            }
            case REGISTER -> {
                return new ChangeToRegisterStrategy(session, output);
            }
            case MOVIES -> {
                return new ChangeToMoviesStrategy(session, output);
            }
            case SEE_DETAILS -> {
                return new ChangeToSeeDetailsStrategy(session, output,
                        actionInput.getMovie());
            }
            case UPGRADES -> {
                return new ChangeToUpgradesStrategy(session, output);
            }
            case AUTHENTICATED -> {
                return new ChangeToAuthenticatedStrategy(session, output);
            }
            default -> {
                throw new IllegalArgumentException("Page " + stringPageName + " is not supported.");
            }
        }
    }
}
