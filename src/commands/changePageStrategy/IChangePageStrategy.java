package commands.changePageStrategy;

public interface IChangePageStrategy {

    /**
     * Changes the current page to this page type.
     */
    void changePage();

    /**
     * Changes the page back to the previous one.
     */
    void back();
}
