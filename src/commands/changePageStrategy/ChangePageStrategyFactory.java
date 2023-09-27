package commands.changePageStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileInput.ActionInput;
import utils.PageType;

public class ChangePageStrategyFactory {
    private Session session;
    private ArrayNode output;
    private String movieName;

    /* Constructor */
    public ChangePageStrategyFactory(Session session, ArrayNode output, String movieName) {
        this.session = session;
        this.output = output;
        this.movieName = movieName;
    }

    /**
     * Factory method to get the appropriate change page strategy.
     * @return strategy of type IChangePageStrategy.
     * @throws IllegalArgumentException if the changePage argument is invalid.
     */
    public IChangePageStrategy getChangePageStrategy(PageType changePageType) throws IllegalArgumentException {
        if (changePageType == null) {
            throw new IllegalArgumentException("Page not supported.");
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
                        movieName);
            }
            case UPGRADES -> {
                return new ChangeToUpgradesStrategy(session, output);
            }
            case AUTHENTICATED -> {
                return new ChangeToAuthenticatedStrategy(session, output);
            }
            default -> {
                throw new IllegalArgumentException("Page not supported.");
            }
        }
    }
}
