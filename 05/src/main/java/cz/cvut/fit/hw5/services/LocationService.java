package cz.cvut.fit.hw5.services;

import cz.cvut.fit.hw5.dto.Location;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    private final List<Location> locations = new ArrayList<>();

    public List<Location> getLocations() {
        return locations;
    }

    public Location getLocationById(String id) {
        return locations.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public Location updateLocation(String id, Location newLocation) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getId().equals(id)) {
                locations.set(i, newLocation);
                return locations.get(i);
            }
        }
        return null;
    }

    public List<Location> getLocationsInCountry(String countryId) {
        return locations.stream()
                .filter(location -> location.getCountry().getId().equals(countryId))
                .collect(Collectors.toList());
    }

    public boolean deleteLocation(String id) {
        return locations.removeIf(c -> c.getId().equals(id));
    }
}
