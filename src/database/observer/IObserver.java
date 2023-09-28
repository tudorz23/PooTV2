package database.observer;

import fileInput.ActionInput;

public interface IObserver {
    /**
     * Update method for the Observer design pattern.
     */
    void update(ActionInput news);
}
