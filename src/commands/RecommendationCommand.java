package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.Notification;
import database.User;
import fileOutput.PrinterJson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RecommendationCommand implements ICommand {
    private Session session;
    private ArrayNode output;
    private Map<String, Integer> genresTop; // <genre, likeCount>
    private ArrayList<Movie> moviesTop;
    
    /* Constructor */
    public RecommendationCommand(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
        genresTop = new TreeMap<>();
        moviesTop = new ArrayList<>();
    }
    
    @Override
    public void execute() {
        if (!checkForPremiumUser()) {
            return;
        }

        getGenresTop();
        getMoviesTop();
        
        for (String genre : genresTop.keySet()) {
            for (Movie movie : moviesTop) {
                if (movie.getGenres().contains(genre)) {
                    if (!session.getCurrUser().getWatchedMovies().contains(movie)) {
                        getRecommendation(movie.getName());
                        return;
                    }
                }
            }
        }
        
        getRecommendation("No recommendation");
    }

    /**
     * Checks if there is a currently logged-in user and if he is premium.
     * @return true, if there is a premium logged-in user, false otherwise.
     */
    private boolean checkForPremiumUser() {
        if (session.getCurrUser() == null) {
            return false;
        }

        return session.getCurrUser().getCredentials().getAccountType().equals("premium");
    }

    /**
     * Makes a top of genres for the current user.
     */
    private void getGenresTop() {
        User currUser = session.getCurrUser();
        
        for (Movie movie : currUser.getLikedMovies()) {
            for (String genre : movie.getGenres()) {
                int likeCount = genresTop.getOrDefault(genre, 0);
                genresTop.put(genre, likeCount + 1);
            }
        }
        
        sortTreeMapByValue();
    }

    /**
     * Sorts the genres top descending by the number of likes,
     * and lexicographical by the name.
     */
    private void sortTreeMapByValue() {
        Comparator<Map.Entry<String, Integer>> valueComparator = (e1, e2) -> {
            // Compare values in descending order.
            int valueCompare = e2.getValue().compareTo(e1.getValue());
            if (valueCompare != 0) {
                return valueCompare;
            } else {
                // Compare keys in lexicographic order.
                return e1.getKey().compareTo(e2.getKey());
            }
        };
        
        genresTop = genresTop.entrySet().stream()
                .sorted(valueComparator)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        TreeMap::new
                ));
    }

    /**
     * Makes a top of available movies to the user by the number of likes. 
     */
    private void getMoviesTop() {
        String currUserCountry = session.getCurrUser().getCredentials().getCountry();
        for (Movie movie : session.getDatabase().getAvailableMovies()) {
            if (!movie.getCountriesBanned().contains(currUserCountry)) {
                moviesTop.add(movie);
            }
        }
        
        // Sort the movies in descending order by the number of likes.
        moviesTop.sort(Comparator.comparingInt(Movie::getNumLikes).reversed());
    }

    /**
     * Appends the recommendation to the user's notification list.
     * @param movieName movie name to recommend.
     */
    private void getRecommendation(String movieName) {
        Notification notification = new Notification(movieName, "Recommendation");
        session.getCurrUser().getNotifications().add(notification);

        PrinterJson printerJson = new PrinterJson();
        printerJson.printRecommendation(session.getCurrUser(), output);
    }
}
