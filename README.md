*Designed by Marius-Tudor Zaharia, September 2023*

# PooTV - stage 2

---

## Table of contents
1. [What is PooTv?](#what-is-pootv)
2. [General platform structure](#general-platform-structure)
3. [New functionalities](#new-functionalities)
    * [Subscribe action](#subscribe-action)
    * [Database actions](#database-actions)
    * [Back page action](#back-page-action)
    * [Premium User recommendations](#premium-user-recommendations)
4. [Implementation details](#implementation-details)

---

## What is PooTV?
* PooTV is a TV series and movie streaming service backend written in the Java
language.
* This is the second stage of the main project, extending the first part, so 
the present will only cover the new functionalities, while details on the first stage
and the run procedure can be found [here](https://github.com/tudorz23/PooTV1.git).

---

## General platform structure
* The platform structure mainly remains the same as in the first stage, so
it still works by a file system's principle.
* The I/O details are almost unchanged, the exception being the addition of
a new field for the user to the output JSON file, *notifications*.

---

## New functionalities
### Subscribe action
* The currently logged-in user will be able to subscribe to a specific movie genre.
* This action can only be performed from the *See details* page.
* The user can only subscribe to one of the genres of the displayed movie.

### Database actions
* Through these, the database could be altered by the administrator.
* Could be executed regardless of the current page.
* *Database add* - to add a movie to the database.
* *Database delete* - to remove a movie from the database.
* For *add*, all the users that are subscribed to one of the added movie's genres
shall be notified of the addition.
* For *delete*, all the users that had purchased the movie will be notified and 
will be given a refund.

### Back page action
* Now, the platform supports back navigation between the pages.

### Premium User recommendations
* If there is still a logged-in premium user at the end of all input actions,
he will get a movie recommendation based on the following algorithm:
  * determine a ranking of the liked genres for the user
    * number of likes for a genre is equivalent to the number of movies liked
    by the user which belong to that genre.
    * a genre must have at least one like to be considered.
    * in case of a tie, the second sorting criteria will be ascending lexicographical.
  * sort all the movies **visible to the user** in descending order by the total
  number of likes.
  * find the most liked movie that had not been watched by the user and that belongs
  to the favorite genre of the user.
  * if there is no unwatched movie in that genre, go to next genre.
  * repeat until either a movie is found or there are no genres left.
  * send a notification, either recommending a movie or stating that there is no
  appropriate recommendation.

## Implementation details
* The 3 design patterns used in the first stage (Command, Factory, Strategy)
are kept for the second one.

### Back
* The visited pages are stored using a stack.
* When a *back* action is encountered, the stack is pop-ed and the **ChangePageStrategyFactory**
decides what changePageStrategy is necessary.
* Each changePageStrategy has a new method, `back()`, which mainly works as
`changePage()`, but does not restrict the change by the logical linking of the pages.

### Database actions
* Here, the Observer Design Pattern is used, as follows:
  * There are 2 interfaces, **IObserver**, with the `update()` method, and
  **IObservable**, with the `addObserver()`, `removeObserver()` and `notifyObservers()` methods.
  * **Database** class is the *observable*, as its changes trigger a response from the
  *observer*, which is the **User** class.
  * **Database** stores a list of the *observers* and, when a change occurs, it notifies
  all of them.
  * **User** makes internal changes according to the `update()` method when notified of
  a change in the *observable*.
* The database command is split in 2 **strategies**, for *add* and *delete*, using the
**IDatabaseStrategy** interface. Each of these strategies implement the `modifyDatabase()`
method.
* A factory method is used to determine the strategy and then the polymorphism is used
by calling the `modifyDatabase()` method on the **IDatabaseStrategy** object.

### Recommendation
* A **TreeMap** is used to rank the genres, with the *key* being the genre's name,
and the *value*, the like count. Thus, the genres will already be sorted lexicographically.
* A *comparator* is used to sort the TreeMap by the values, so the *genres* by the *like count*.
