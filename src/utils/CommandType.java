package utils;

public enum CommandType {
    CHANGE_PAGE("change page"),
    ON_PAGE("on page"),
    REGISTER("register"),
    LOGIN("login"),
    SEARCH("search"),
    FILTER("filter"),
    BUY_TOKENS("buy tokens"),
    BUY_PREMIUM_ACCOUNT("buy premium account"),
    PURCHASE("purchase"),
    WATCH("watch"),
    LIKE("like"),
    RATE("rate"),
    LOGOUT("logout"),
    BACK("back");

    private final String label;

    CommandType(String label) {
        this.label = label;
    }

    /**
     * Gets an enum Command type from the label String.
     * @param text String that will be compared to the label.
     * @return CommandType enum corresponding to the label.
     */
    public static CommandType fromString(String text) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.label.equals(text)) {
                return commandType;
            }
        }
        return null;
    }
}
