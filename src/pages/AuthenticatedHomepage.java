package pages;

import utils.PageType;

import java.util.Arrays;

public class AuthenticatedHomepage extends Page {
    /* Constructor */
    public AuthenticatedHomepage() {
        super();
        setType(PageType.AUTHENTICATED);

        getNextPages().addAll(Arrays.asList(PageType.MOVIES, PageType.UPGRADES,
                PageType.UNAUTHENTICATED));
    }
}
