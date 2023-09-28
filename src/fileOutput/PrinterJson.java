package fileOutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Movie;
import database.Credentials;
import database.Notification;
import database.User;

import java.util.ArrayList;

public class PrinterJson {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Used for printing to JSON file in case of a successful action.
     * @param currentMoviesList list of movies visible to the user.
     * @param user current logged-in user.
     * @param output ArrayNode for JSON printing.
     */
    public void printSuccess(ArrayList<Movie> currentMoviesList, User user, ArrayNode output) {
        ObjectNode message = mapper.createObjectNode();

        message.put("error", (String) null);

        ArrayNode currentMoviesNode = getMovieArrayNode(currentMoviesList);
        message.set("currentMoviesList", currentMoviesNode);

        ObjectNode currentUser = getUserNode(user);
        message.set("currentUser", currentUser);

        output.add(message);
    }

    /**
     * Used for printing to JSON file in case of a failed action.
     * @param output ArrayNode for JSON printing.
     */
    public void printError(ArrayNode output) {
        ObjectNode errorMessage = mapper.createObjectNode();
        errorMessage.put("error", "Error");

        ArrayNode currentMoviesList = mapper.createArrayNode();
        errorMessage.set("currentMoviesList", currentMoviesList);

        errorMessage.set("currentUser", null);

        output.add(errorMessage);
    }

    // Test for test 8
    public void printRecommendation(User user, ArrayNode output) {
        ObjectNode message = mapper.createObjectNode();

        message.put("error", (String) null);
        message.put("currentMoviesList", (String) null);

        ObjectNode currentUser = getUserNode(user);
        message.set("currentUser", currentUser);

        output.add(message);
    }

    /**
     * Converts a user object to an ObjectNode for JSON printing.
     */
    private ObjectNode getUserNode(User user) {
        ObjectNode userNode = mapper.createObjectNode();

        ObjectNode credentialsNode = mapper.createObjectNode();
        Credentials credentials = user.getCredentials();

        credentialsNode.put("name", credentials.getName());
        credentialsNode.put("password", credentials.getPassword());
        credentialsNode.put("accountType", credentials.getAccountType());
        credentialsNode.put("country", credentials.getCountry());
        credentialsNode.put("balance", String.valueOf(credentials.getIntBalance()));

        userNode.set("credentials", credentialsNode);

        userNode.put("tokensCount", user.getTokensCount());
        userNode.put("numFreePremiumMovies", user.getNumFreePremiumMovies());

        ArrayNode purchasedMoviesArrayNode = getMovieArrayNode(user.getPurchasedMovies());
        userNode.set("purchasedMovies", purchasedMoviesArrayNode);

        ArrayNode watchedMoviesArrayNode = getMovieArrayNode(user.getWatchedMovies());
        userNode.set("watchedMovies", watchedMoviesArrayNode);

        ArrayNode likedMoviesArrayNode = getMovieArrayNode(user.getLikedMovies());
        userNode.set("likedMovies", likedMoviesArrayNode);

        ArrayNode ratedMoviesArrayNode = getMovieArrayNode(user.getRatedMovies());
        userNode.set("ratedMovies", ratedMoviesArrayNode);

        ArrayNode notificationsArrayNode = getNotificationArrayNode(user.getNotifications());
        userNode.set("notifications", notificationsArrayNode);

        return userNode;
    }

    /**
     * Converts an ArrayList of users to an ArrayNode for JSON printing.
     */
    private ArrayNode getUserArrayNode(ArrayList<User> users) {
        ArrayNode userArrayNode = mapper.createArrayNode();

        for (User user : users) {
            ObjectNode userNode = getUserNode(user);
            userArrayNode.add(userNode);
        }

        return userArrayNode;
    }

    /**
     * Converts a movie object to an ObjectNode for JSON printing.
     */
    private ObjectNode getMovieNode(Movie movie) {
        ObjectNode movieNode = mapper.createObjectNode();

        movieNode.put("name", movie.getName());
        movieNode.put("year", String.valueOf(movie.getYear()));
        movieNode.put("duration", movie.getDuration());

        // Add the genres as an ArrayNode.
        ArrayNode genresNode = mapper.createArrayNode();
        for (String genre : movie.getGenres()) {
            genresNode.add(genre);
        }
        movieNode.set("genres", genresNode);

        // Add the actors as an ArrayNode.
        ArrayNode actorsNode = mapper.createArrayNode();
        for (String actor : movie.getActors()) {
            actorsNode.add(actor);
        }
        movieNode.set("actors", actorsNode);

        // Add the banned countries as an ArrayNode
        ArrayNode countriesBannedNode = mapper.createArrayNode();
        for (String countryBanned : movie.getCountriesBanned()) {
            countriesBannedNode.add(countryBanned);
        }
        movieNode.set("countriesBanned", countriesBannedNode);

        movieNode.put("numLikes", movie.getNumLikes());
        movieNode.put("rating", movie.getRating());
        movieNode.put("numRatings", movie.getNumRatings());

        return movieNode;
    }

    /**
     * Converts an ArrayList of movies to an ArrayNode for JSON printing.
     */
    private ArrayNode getMovieArrayNode(ArrayList<Movie> movies) {
        ArrayNode movieArrayNode = mapper.createArrayNode();

        for (Movie movie : movies) {
            ObjectNode movieNode = getMovieNode(movie);
            movieArrayNode.add(movieNode);
        }

        return movieArrayNode;
    }

    /**
     * Converts a notification object to an ObjectNode for JSON printing.
     */
    private ObjectNode getNotificationNode(Notification notification) {
        ObjectNode notificationNode = mapper.createObjectNode();

        notificationNode.put("movieName", notification.getMovieName());
        notificationNode.put("message", notification.getMessage());

        return notificationNode;
    }

    /**
     * Converts an ArrayList of notifications to an ArrayNode for JSON printing.
     */
    private ArrayNode getNotificationArrayNode(ArrayList<Notification> notifications) {
        ArrayNode notificationArrayNode = mapper.createArrayNode();

        for (Notification notification : notifications) {
            ObjectNode notificationNode = getNotificationNode(notification);
            notificationArrayNode.add(notificationNode);
        }

        return notificationArrayNode;
    }
}
