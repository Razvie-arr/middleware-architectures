package cz.cvut.fit.hw07.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class Customer extends RepresentationModel<Customer> {
    private String id;
    private String name;
    private List<Tour> tours;

    public Customer(String id, String name, List<Tour> tours) {
        this.id = id;
        this.name = name;
        this.tours = tours;
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

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }
}
