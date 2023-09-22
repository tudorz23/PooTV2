package pages;

import utils.PageType;

import java.util.ArrayList;

public class Page {
    private final ArrayList<PageType> nextPages;
    private PageType type;

    /* Constructor */
    public Page() {
        nextPages = new ArrayList<>();
    }

    /* Getters and Setters */
    public ArrayList<PageType> getNextPages() {
        return nextPages;
    }
    public PageType getType() {
        return type;
    }
    public void setType(PageType type) {
        this.type = type;
    }
}
