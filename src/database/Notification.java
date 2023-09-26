package database;

public class Notification {
    private String movieName;
    private String message;

    /* Constructor */
    public Notification(String movieName, String message) {
        this.movieName = movieName;
        this.message = message;
    }

    /* Getters and Setters */
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
