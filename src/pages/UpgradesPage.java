package pages;

import utils.PageType;

import java.util.Arrays;

public class UpgradesPage extends Page {
    /* Constructor */
    public UpgradesPage() {
        super();
        setType(PageType.UPGRADES);

        getNextPages().addAll(Arrays.asList(PageType.AUTHENTICATED, PageType.MOVIES,
                                PageType.UNAUTHENTICATED));
    }
}
