package commands.filterStrategy;

import client.Session;

import java.util.ArrayList;

public class FilterContainsActorStrategy implements IFilterStrategy {
    private Session session;
    private ArrayList<String> actors;

    /* Constructor */
    public FilterContainsActorStrategy(Session session, ArrayList<String> actors) {
        this.session = session;
        this.actors = actors;
    }

    @Override
    public void filter() {
        for (String actor : actors) {
            session.getCurrMovieList().removeIf(movie -> !movie.getActors().contains(actor));
        }
    }
}
