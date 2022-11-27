package cz.cvut.fit.hw6.dto;

import org.springframework.hateoas.RepresentationModel;

public class Location extends RepresentationModel<Location> {

    String id;
    String name;
    private Country country;

    public Location(String id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
