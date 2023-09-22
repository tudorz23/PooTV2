package pages;

import utils.PageType;

import java.util.Arrays;

public class LoginPage extends Page {
    /* Constructor */
    public LoginPage() {
        super();
        setType(PageType.LOGIN);

        getNextPages().addAll(Arrays.asList(PageType.UNAUTHENTICATED, PageType.AUTHENTICATED));
    }
}
