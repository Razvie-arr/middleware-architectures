package cz.cvut.fit.hw07.service;

import cz.cvut.fit.hw07.dto.Tour;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourService {

    private final List<Tour> tours = new ArrayList<>();

    public Tour getTourById(String id) {
        return tours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public void addTour(Tour tour) {
        tours.add(tour);
    }

    public boolean deleteTour(String id) {
        return tours.removeIf(c -> c.getId().equals(id));
    }

    public List<Tour> getTours() { return tours; }

    public boolean updateTour(Tour newTour) {
        for (int i = 0; i < tours.size(); i++) {
            if (tours.get(i).getId().equals(newTour.getId())) {
                tours.set(i, newTour);
                return true;
            }
        }
        return false;
    }
}
