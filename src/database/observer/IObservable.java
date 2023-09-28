package database.observer;

import fileInput.ActionInput;

public interface IObservable {
    void addObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers(ActionInput news);
}
