package pages;

import utils.PageType;

import java.util.Arrays;

public class RegisterPage extends Page {
    /* Constructor */
    public RegisterPage() {
        super();
        setType(PageType.REGISTER);

        getNextPages().addAll(Arrays.asList(PageType.UNAUTHENTICATED,
                                                PageType.AUTHENTICATED));
    }
}
