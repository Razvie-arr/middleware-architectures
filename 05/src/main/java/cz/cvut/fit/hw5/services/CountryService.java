package cz.cvut.fit.hw5.services;

import cz.cvut.fit.hw5.dto.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    private final List<Country> countries = new ArrayList<>();

    public Country getCountryById(String id) {
        return countries.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public boolean updateCountry(Country newCountry) {
        for (int i = 0; i < countries.size(); i++) {
            if (countries.get(i).getId().equals(newCountry.getId())) {
                countries.set(i, newCountry);
                return true;
            }
        }
        return false;
    }
}
