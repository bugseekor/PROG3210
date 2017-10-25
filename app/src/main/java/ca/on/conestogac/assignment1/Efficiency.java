package ca.on.conestogac.assignment1;

/**
 * Created by spark1435 on 10/19/2017.
 */

public class Efficiency {
    private int id;
    private int unitType;
    private Float fe;

    public Efficiency() {}
    public Efficiency(int Id, int UnitType, Float Fe) {
        id = Id;
        unitType = UnitType;
        fe = Fe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public Float getFe() {
        return fe;
    }

    public void setFe(Float fe) {
        this.fe = fe;
    }
}
