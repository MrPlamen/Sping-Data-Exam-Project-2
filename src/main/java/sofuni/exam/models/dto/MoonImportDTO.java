package sofuni.exam.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import sofuni.exam.util.LocalDateAdapter;

import java.time.LocalDate;

@XmlRootElement(name = "moon")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoonImportDTO {

    @XmlElement
    @Size(min = 2, max = 10)
    private String name;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate discovered;
    @XmlElement(name = "distance_from_planet")
    @Min(1)
    private int distanceFromPlanet;
    @XmlElement(name = "radius")
    @Min(1)
    private double radius;
    @XmlElement(name = "discoverer_id")
    private int discovererId;
    @XmlElement(name = "planet_id")
    private int planetId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDiscovered() {
        return discovered;
    }

    public void setDiscovered(LocalDate discovered) {
        this.discovered = discovered;
    }

    public int getDistanceFromPlanet() {
        return distanceFromPlanet;
    }

    public void setDistanceFromPlanet(int distanceFromPlanet) {
        this.distanceFromPlanet = distanceFromPlanet;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getDiscovererId() {
        return discovererId;
    }

    public void setDiscovererId(int discovererId) {
        this.discovererId = discovererId;
    }

    public int getPlanetId() {
        return planetId;
    }

    public void setPlanetId(int planetId) {
        this.planetId = planetId;
    }
}
