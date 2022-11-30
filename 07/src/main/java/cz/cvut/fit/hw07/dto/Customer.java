package cz.cvut.fit.hw07.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

public class Customer implements Serializable {
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
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
}
