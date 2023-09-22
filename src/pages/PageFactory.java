package pages;

import utils.PageType;

public class PageFactory {
    /**
     * Factory method that creates Page instances based on the PageType enum input.
     * @param type PageType enum.
     * @return Page object corresponding to the type input.
     */
    public Page createPage(PageType type) {
        switch (type) {
            case UNAUTHENTICATED -> {
                return new UnauthenticatedHomepage();
            }
            case LOGIN -> {
                return new LoginPage();
            }
            case REGISTER -> {
                return new RegisterPage();
            }
            case AUTHENTICATED -> {
                return new AuthenticatedHomepage();
            }
            case MOVIES -> {
                return new MoviesPage();
            }

            case SEE_DETAILS -> {
                return new SeeDetailsPage();
            }
            case UPGRADES -> {
                return new UpgradesPage();
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
