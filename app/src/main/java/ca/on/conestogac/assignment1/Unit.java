package ca.on.conestogac.assignment1;

/**
 * Created by spark1435 on 10/16/2017.
 */

public class Unit {

    private int id;
    private String name;

    public Unit() {}
    public Unit(int Id, String Name) {
        id = Id;
        name = Name;
    }
    public void setId(int Id){
        id = Id;
    }
    public int getId(){
        return id;
    }
    public void setName(String Name){
        name = Name;
    }
    public String getName(){
        return name;
    }
}
