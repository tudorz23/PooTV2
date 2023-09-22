package utils;

public enum PageType {
    UNAUTHENTICATED("logout"),
    LOGIN("login"),
    REGISTER("register"),
    AUTHENTICATED("authenticated"),
    MOVIES("movies"),
    SEE_DETAILS("see details"),
    UPGRADES("upgrades");

    private final String label;

    PageType(String label) {
        this.label = label;
    }

    /**
     * Gets an enum page type from the label String.
     * @param text String that will be compared to the label.
     * @return PageType enum corresponding to the label.
     */
    public static PageType fromString(String text) {
        for (PageType pageType : PageType.values()) {
            if (pageType.label.equals(text)) {
                return pageType;
            }
        }
        return null;
    }
}
