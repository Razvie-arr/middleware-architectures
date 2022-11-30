package cz.cvut.fit.hw07;

import cz.cvut.fit.hw07.dto.Customer;
import cz.cvut.fit.hw07.dto.Tour;
import cz.cvut.fit.hw07.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestApplicationRepository {
    @Autowired
    TourService tourService;

    @PostConstruct
    public void initRepo() {
        List<Customer> customers1 = new ArrayList<>();
        List<Customer> customers2 = new ArrayList<>();
        customers1.add(new Customer(1, "Vlad"));
        customers1.add(new Customer(2, "Nafanya"));
        customers1.add(new Customer(3, "Kuzya"));
        customers2.add(new Customer(1, "Vlk"));
        customers2.add(new Customer(1, "Prasatko 1"));
        customers2.add(new Customer(1, "Prasatko 2"));
        customers2.add(new Customer(1, "Prasatko 3"));
        tourService.addTour(new Tour(1, "Tour to Baba Jaga", customers1));
        tourService.addTour(new Tour(2, "Odstrelime strechu", customers2));
        tourService.updateLastModified();
    }
}
