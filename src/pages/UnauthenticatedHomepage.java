package pages;

import utils.PageType;

import java.util.Arrays;

public class UnauthenticatedHomepage extends Page {
    /* Constructor */
    public UnauthenticatedHomepage() {
        super();
        setType(PageType.UNAUTHENTICATED);

        getNextPages().addAll(Arrays.asList(PageType.LOGIN, PageType.REGISTER));
    }
}
