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

    public Country updateCountry(String id, Country newCountry) {
        for (int i = 0; i < countries.size(); i++) {
            if (countries.get(i).getId().equals(id)) {
                countries.set(i, newCountry);
                return countries.get(i);
            }
        }
        return null;
    }

    public boolean deleteCountry(String id) {
        return countries.removeIf(c -> c.getId().equals(id));
    }
}
