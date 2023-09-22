package commands.filterStrategy;

import client.Session;

import java.util.ArrayList;

public class FilterContainsGenreStrategy implements IFilterStrategy {
    private Session session;
    private ArrayList<String> genres;

    /* Constructor */
    public FilterContainsGenreStrategy(Session session, ArrayList<String> genres) {
        this.session = session;
        this.genres = genres;
    }

    @Override
    public void filter() {
        for (String genre : genres) {
            session.getCurrMovieList().removeIf(movie -> !movie.getGenres().contains(genre));
        }
    }
}
