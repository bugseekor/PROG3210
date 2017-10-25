package ca.on.conestogac.assignment1;

/**
 * Created by spark1435 on 10/20/2017.
 */

public class Car {

    private int id;
    private String name;
    private String year;
    private String engine;

    public Car() {
    }

    public Car(int id, String Name, String Year, String Engine) {
        this.id = id;
        this.name = Name;
        this.year = Year;
        this.engine = Engine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
