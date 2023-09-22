package pages;

import database.Movie;
import utils.PageType;

import java.util.ArrayList;
import java.util.Arrays;

public class MoviesPage extends Page {
    private ArrayList<Movie> movies;

    /* Constructor */
    public MoviesPage() {
        super();
        movies = new ArrayList<>();
        setType(PageType.MOVIES);

        getNextPages().addAll(Arrays.asList(PageType.AUTHENTICATED, PageType.SEE_DETAILS,
                                                PageType.UNAUTHENTICATED, PageType.MOVIES));
    }

    /* Getters and Setters */
    public ArrayList<Movie> getMovies() {
        return movies;
    }
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
