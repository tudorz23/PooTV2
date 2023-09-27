package client;

import database.Database;
import database.Movie;
import pages.Page;
import pages.PageFactory;
import database.User;
import utils.PageType;

import java.util.ArrayList;
import java.util.LinkedList;

public final class Session {
    private User currUser;
    private Page currPage;
    private ArrayList<Movie> currMovieList;
    private Database database;
    private LinkedList<Page> pageStack;

    public Session(Database database) {
        currUser = null;
        currMovieList = new ArrayList<>();
        PageFactory pageFactory = new PageFactory();
        currPage = pageFactory.createPage(PageType.UNAUTHENTICATED);
        this.database = database;
        pageStack = new LinkedList<>();
    }

    /**
     * Clears the currently displayed movie list.
     * clear() is not used because it is necessary that the list is sometimes empty
     * (and not populated with null elements, as clear() would do).
     */
    public void resetCurrMovieList() {
        currMovieList = new ArrayList<>();
    }

    /**
     * Pushes the given page on the page stack.
     * @param page page to push.
     */
    public void pushPageStack(Page page) {
        pageStack.push(page);
    }

    /**
     * Pops a page from the page stack.
     * @return popped page.
     * @throws java.util.NoSuchElementException if the stack is empty.
     */
    public Page popPageStack() {
        return pageStack.pop();
    }

    /**
     * Resets the page stack.
     */
    public void resetPageStack() {
        pageStack = new LinkedList<>();
    }

    /* Getters and Setters */
    public User getCurrUser() {
        return currUser;
    }
    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }
    public Page getCurrPage() {
        return currPage;
    }
    public void setCurrPage(Page currPage) {
        this.currPage = currPage;
    }
    public ArrayList<Movie> getCurrMovieList() {
        return currMovieList;
    }
    public void setCurrMovieList(ArrayList<Movie> currMovieList) {
        this.currMovieList = currMovieList;
    }

    public Database getDatabase() {
        return database;
    }
}
