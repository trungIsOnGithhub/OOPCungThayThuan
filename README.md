## Restaurant Order Management Tool

* [Reference - CMU Course](https://www.cs.cmu.edu/~charlie/courses/15-214/2013-spring/)
  
> New Added Multi User Text Editor App - exercise in Computer Network Course

> Using [Java Oracle JDK 17.0.8](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - [NetBean IDE 16](https://github.com/apache/netbeans/releases/tag/16)

*:golf: Coursework project for "Object-Oriented Programming" course **|** Semester 2021-2 **|** HCMUT*

>- *This tools is made solely for purpose of learning and practicing, as there may still exists severals bugs and flaws that I am working on :grinning:*
>- *As you may have seen, the idea off stuffing many code architecture design in a one simple tools application is unusual and resulting in unnecessary complexity. But at least, i had something to practice :sunglasses:*

>*Have not done working with the project yet, so there may be some unfinished parts, I am striving to put an end to it :running:*

##### :paperclip: To go into details:

* **Singleton Design Pattern**
```java
public class DBController {
    //Singleton, One and Only Object
    private static DBController currentSession;
    
    private Date sessionInitTime;
    private DBFunctionalHelper.ConfigDBData configData;

    public static DBController getCurrentDBSession() {
        if( currentSession == null ) {
            currentSession = new DBController(new Date());
        }
        return currentSession;
    }

    private DBController(Date sessionInitTime) {
        this.sessionInitTime = sessionInitTime;// dependency injection
        this.configData = DBFunctionalHelper.getDBConfigData();
    }
```
Served as a layer between data and application, so only one instance is allowed to keep the access to data clearly, sequentially enough.
* **Factory Design Pattern**
```java
abstract class AbstractFileFactory {
    public static AbstractFile getFile(String type, String fileName) {
        if( type.equalsIgnoreCase("history") )
            return new HistoryFile(fileName);
        if( type.equalsIgnoreCase("customers") )
            return new CustomersFile(fileName);
        if( type.equalsIgnoreCase("accounts") )
            return new AccountsFile(fileName);
        if( type.equalsIgnoreCase("menu") )
            return new MenuFile(fileName);
        return null;
    }
}
```
There will be further modification in the creation of ohjects, so things should be generalized into using Factory for better structure.
It also faclitate **Flyweight Pattern**, paving way for a more efficent way of handling and providing objects to client code.
* **Decorator Design Pattern**
```java
class PhoneNumberDecorator extends CustomerOrder {
    private String phoneNumber;

    public PhoneNumberDecorator(CustomerOrder toBeCopied, String phoneNumber) {
        super(toBeCopied);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public String serializeIntoString() {
        return super.serializeIntoString().concat( " " ).concat( this.phoneNumber );
    }
}
```
When an object need to be extended with more attribute later as a bussiness requirement, Decorater pattern come into use as a clean way to write object that is extendable.
* **Iterator Design Pattern**
```java
abstract class MainMenuIterator implements Iterator {
    private int currentPosition;
    private List<MenuItem> cached;

    MainMenuIterator(List<MenuItem> currentMenu) {
        this.currentPosition = 0;
        this.cached = currentMenu;
    }

    public final boolean hasNext() {
        return this.currentPosition < this.cached.size();
    }
}
class PriceIterator implements MainMenuIterator {
    public String getNext() {
        if( this.hasMoreItem() ) {
            return this.cached.get(this.currentPosition++).price;
        }
        return null;
    }
}
```
A high-level list of item can have many type of iteration to retrieve packaged-only attribute of member. Iterators also help to abstract the implementation of the list, so we can use other data structure for the list without changing the code doing iteration on it.

-------------------------------------------------------
#### Tasks Checklist
- [x] upload original code
- [x] implement Singleton
- [x] implement Factory
- [x] implement Wrapper
- [x] implement Iterator
- [ ] re-organize database code
- [x] add configure option
