package commands.filterStrategy;

import client.Session;
import database.Movie;
import java.util.Comparator;

public class FilterSortByDurationAndRatingStrategy implements IFilterStrategy {
    private Session session;
    private String durationOrder;
    private String ratingOrder;

    /* Constructor */
    public FilterSortByDurationAndRatingStrategy(Session session, String durationOrder,
                                                 String ratingOrder) {
        this.session = session;
        this.durationOrder = durationOrder;
        this.ratingOrder = ratingOrder;
    }

    @Override
    public void filter() {
        if (durationOrder.equals("increasing") && ratingOrder.equals("increasing")) {
            session.getCurrMovieList().sort(Comparator.comparingInt(Movie::getDuration)
                    .thenComparingDouble(Movie::getRating));
        } else if (durationOrder.equals("increasing") && ratingOrder.equals("decreasing")) {
            session.getCurrMovieList().sort(Comparator.comparingInt(Movie::getDuration)
                    .thenComparingDouble(Movie::getRating).reversed());
        } else if (durationOrder.equals("decreasing") && ratingOrder.equals("increasing")) {
            session.getCurrMovieList().sort(Comparator.comparingInt(Movie::getDuration)
                    .reversed().thenComparingDouble(Movie::getRating));
        } else if (durationOrder.equals("decreasing") && ratingOrder.equals("decreasing")) {
            session.getCurrMovieList().sort(Comparator.comparingInt(Movie::getDuration)
                    .reversed().thenComparingDouble(Movie::getRating).reversed());
        }
    }
}
