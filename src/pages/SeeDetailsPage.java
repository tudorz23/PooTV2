package pages;

import database.Movie;
import utils.PageType;

import java.util.Arrays;

public class SeeDetailsPage extends Page {
    private Movie movie;

    /* Constructor */
    public SeeDetailsPage() {
        super();
        setType(PageType.SEE_DETAILS);

        getNextPages().addAll(Arrays.asList(PageType.AUTHENTICATED, PageType.MOVIES,
                                PageType.UPGRADES, PageType.UNAUTHENTICATED));
    }

    /* Getters and Setters */
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
