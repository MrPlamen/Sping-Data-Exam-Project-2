package sofuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import sofuni.exam.models.enums.Type;

public class PlanetImportDTO {

    @Expose
    @Size(min = 3, max = 20)
    private String name;

    @Expose
    @Min(value = 1)
    private int diameter;

    @Expose
    @Min(value = 1)
    private long distanceFromSun;

    @Expose
    @Min(value = 1)
    private double orbitalPeriod;

    @Expose
    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public long getDistanceFromSun() {
        return distanceFromSun;
    }

    public void setDistanceFromSun(long distanceFromSun) {
        this.distanceFromSun = distanceFromSun;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
