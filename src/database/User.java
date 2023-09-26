package database;

import java.util.ArrayList;
import java.util.Objects;
import static utils.Constants.INITIAL_FREE_PREMIUM_MOVIES;
import static utils.Constants.PREMIUM_ACCOUNT_PRICE;

public final class User {
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
    public void setSubscribedGenres(ArrayList<String> subscribedGenres) {
        this.subscribedGenres = subscribedGenres;
    }
}
