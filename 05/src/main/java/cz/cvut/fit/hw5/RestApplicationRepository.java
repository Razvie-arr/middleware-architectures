package cz.cvut.fit.hw5;

import cz.cvut.fit.hw5.dto.Country;
import cz.cvut.fit.hw5.dto.Customer;
import cz.cvut.fit.hw5.dto.Location;
import cz.cvut.fit.hw5.dto.Tour;
import cz.cvut.fit.hw5.services.CountryService;
import cz.cvut.fit.hw5.services.CustomerService;
import cz.cvut.fit.hw5.services.LocationService;
import cz.cvut.fit.hw5.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestApplicationRepository {
    @Autowired
    CountryService countryService;
    @Autowired
    LocationService locationService;
    @Autowired
    TourService tourService;
    @Autowired
    CustomerService customerService;


    @PostConstruct
    public void initRepo() {
        countryService.addCountry(new Country("cz", "Czech Republic"));
        countryService.addCountry(new Country("de", "Deutschland"));
        locationService.addLocation(new Location("prg", "Prague", countryService.getCountryById("cz")));
        locationService.addLocation(new Location("brn", "Brno", countryService.getCountryById("cz")));
        locationService.addLocation(new Location("ck", "Cesky Krumlov", countryService.getCountryById("cz")));
        locationService.addLocation(new Location("brl", "Berlin", countryService.getCountryById("de")));
        tourService.addTour(new Tour("wntr", "Beautiful Winter Prague", locationService.getLocationById("prg")));
        tourService.addTour(new Tour("cstl", "Castles in Cesky Krumlov", locationService.getLocationById("ck")));
        tourService.addTour(new Tour("und", "Underground culture in Berlin", locationService.getLocationById("brl")));

        List<Tour> vladTours = new ArrayList<>();
        vladTours.add(tourService.getTourById("wntr"));
        vladTours.add(tourService.getTourById("cstl"));
        vladTours.add(tourService.getTourById("und"));
        customerService.addCustomer(new Customer("vb", "Vlad", vladTours));
    }
}
