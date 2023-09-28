package commands.databaseStrategy;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import fileInput.ActionInput;
import fileOutput.PrinterJson;

public class DatabaseAddStrategy implements IDatabaseStrategy {
    private Session session;
    private ActionInput actionInput;
    private ArrayNode output;

    /* Constructor */
    public DatabaseAddStrategy(Session session, ActionInput actionInput, ArrayNode output) {
        this.session = session;
        this.actionInput = actionInput;
        this.output = output;
    }

    @Override
    public void modifyDatabase() {
        Movie newMovie = new Movie(actionInput.getAddedMovie());

        if (checkMovieExistenceInDatabase(newMovie)) {
            PrinterJson printerJson = new PrinterJson();
            printerJson.printError(output);
            return;
        }

        session.getDatabase().addMovie(newMovie);

        session.getDatabase().notifyObservers(actionInput);
    }

    /**
     * Checks if the new movie is already registered in the database.
     * @return true if the movie is already in the database, false otherwise.
     */
    private boolean checkMovieExistenceInDatabase(Movie newMovie) {
        for (Movie movie : session.getDatabase().getAvailableMovies()) {
            if (movie.getName().equals(newMovie.getName())) {
                return true;
            }
        }
        return false;
    }
}
