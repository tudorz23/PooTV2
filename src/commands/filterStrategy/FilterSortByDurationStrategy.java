package commands.filterStrategy;

import client.Session;
import database.Movie;
import java.util.Comparator;

public class FilterSortByDurationStrategy implements IFilterStrategy {
    private Session session;
    private String order;

    /* Constructor */
    public FilterSortByDurationStrategy(Session session, String order) {
        this.session = session;
        this.order = order;
    }

    @Override
    public void filter() {
        if (order.equals("increasing")) {
            session.getCurrMovieList().sort(
                    Comparator.comparingDouble(Movie::getDuration));
        } else if (order.equals("decreasing")) {
            session.getCurrMovieList().sort(
                    Comparator.comparingDouble(Movie::getDuration).reversed());
        }
    }
}
