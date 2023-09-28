package database;

import database.observer.IObserver;
import fileInput.ActionInput;

import java.util.ArrayList;
import java.util.Objects;

import static utils.Constants.*;

public final class User implements IObserver {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<Movie> purchasedMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> likedMovies;
    private ArrayList<Movie> ratedMovies;
    private ArrayList<Notification> notifications;
    private ArrayList<String> subscribedGenres;

    /* Constructor */
    public User(Credentials credentials) {
        this.credentials = credentials;
        tokensCount = 0;
        numFreePremiumMovies = INITIAL_FREE_PREMIUM_MOVIES;
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
        notifications = new ArrayList<>();
        subscribedGenres = new ArrayList<>();
    }

    /**
     * User uses balance to buy tokens.
     * @param count number of tokens bought.
     * @return true if operation can be done, false otherwise.
     */
    public boolean buyTokens(int count) {
        int currBalance = this.credentials.getIntBalance();

        if (count > currBalance) {
            return false;
        }

        this.tokensCount += count;

        currBalance -= count;
        this.getCredentials().setIntBalance(currBalance);

        return true;
    }

    /**
     * User uses tokens to buy premium account.
     * @return true if operation can be done, false otherwise.
     */
    public boolean buyPremiumAccount() {
        if (this.getCredentials().getAccountType().equals("premium")) {
            return false;
        }

        if (PREMIUM_ACCOUNT_PRICE > this.tokensCount)  {
            return false;
        }

        this.tokensCount -= PREMIUM_ACCOUNT_PRICE;
        this.getCredentials().setAccountType("premium");

        return true;
    }

    @Override
    public void update(ActionInput news) {
        if (news.getFeature().equals("add")) {
            updateAddMovie(new Movie(news.getAddedMovie()));
        } else if (news.getFeature().equals("delete")) {
            updateDeleteMovie(news.getDeletedMovie());
        }
    }

    /**
     * Helper update method for when a new movie is added.
     * @param newMovie added movie.
     */
    private void updateAddMovie(Movie newMovie) {
        if ((!checkSubscribeStatus(newMovie)) || checkCountryBanStatus(newMovie)) {
            return;
        }

        Notification notification = new Notification(newMovie.getName(), "ADD");
        this.getNotifications().add(notification);
    }

    /**
     * Checks if this user is subscribed to at least one of the new movie's genres.
     * @return true if he is, false otherwise.
     */
    private boolean checkSubscribeStatus(Movie newMovie) {
        for (String genre : newMovie.getGenres()) {
            if (this.getSubscribedGenres().contains(genre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the new movie is banned in current user's country.
     * @return true if it is banned, false otherwise.
     */
    private boolean checkCountryBanStatus(Movie newMovie) {
        return newMovie.getCountriesBanned().contains(this.getCredentials().getCountry());
    }

    /**
     * Helper update method for when a movie is deleted.
     * @param deletedMovie added movie.
     */
    private void updateDeleteMovie(String deletedMovie) {
       if (!checkIfMovieIsPurchased(deletedMovie)) {
           return;
       }

       returnMovieValue();

       this.getPurchasedMovies().removeIf(movie -> movie.getName().equals(deletedMovie));
       this.getWatchedMovies().removeIf(movie -> movie.getName().equals(deletedMovie));
       this.getLikedMovies().removeIf(movie -> movie.getName().equals(deletedMovie));
       this.getRatedMovies().removeIf(movie -> movie.getName().equals(deletedMovie));

       Notification notification = new Notification(deletedMovie, "DELETE");
       this.getNotifications().add(notification);
    }

    /**
     * Checks if this user has purchased the movie.
     * @return true if he has, false otherwise.
     */
    private boolean checkIfMovieIsPurchased(String movieName) {
        return this.getPurchasedMovies().stream()
                .anyMatch(movie -> movie.getName().equals(movieName));
    }

    /**
     * Gives the user compensations for the deleted movie.
     */
    private void returnMovieValue() {
        if (this.getCredentials().getAccountType().equals("premium")) {
            int currNumFreePremiumMovies = this.getNumFreePremiumMovies();
            currNumFreePremiumMovies++;
            this.setNumFreePremiumMovies(currNumFreePremiumMovies);
            return;
        }

        // If the user is standard.
        int currTokensCnt = this.getTokensCount();
        currTokensCnt += MOVIE_PRICE;
        this.setTokensCount(currTokensCnt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        return getCredentials().getName().equals(user.getCredentials().getName())
                && getCredentials().getPassword().equals(user.getCredentials().getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCredentials().getName(), getCredentials().getPassword());
    }

    /* Getters and Setters */
    public Credentials getCredentials() {
        return credentials;
    }
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
    public int getTokensCount() {
        return tokensCount;
    }
    public void setTokensCount(int tokensCount) {
        this.tokensCount = tokensCount;
    }
    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }
    public void setNumFreePremiumMovies(int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }
    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }
    public void setPurchasedMovies(ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }
    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }
    public void setWatchedMovies(ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }
    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }
    public void setLikedMovies(ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }
    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }
    public void setRatedMovies(ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }
}
