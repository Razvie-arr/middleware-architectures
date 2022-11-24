package cz.cvut.fit.hw5.dto;

public class Tour {

    private String id;
    private String name;
    private Location location;
//    private List<Customer> customers;


    public Tour(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
//        this.customers = customers;
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

    public Location getLocation() {return location;}

    public void setLocation(Location location) {this.location = location; }
//
//    public List<Customer> getCustomers() {
//        return customers;
//    }
//
//    public void setCustomers(List<Customer> customers) {
//        this.customers = customers;
//    }
//
//    public void addCustomer(Customer customer) {
//        this.customers.add(customer);
//    }
}
