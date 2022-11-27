package cz.cvut.fit.hw6.service;

import cz.cvut.fit.hw6.dto.Confirmation;
import cz.cvut.fit.hw6.dto.Operation;
import cz.cvut.fit.hw6.dto.Status;
import cz.cvut.fit.hw6.dto.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourService {
    @Autowired
    ConfirmationService confirmationService;

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

    public void addTourConfirmation(String id, Operation operation) {
        confirmationService.addConfirmation(new Confirmation(id, operation));
    }

    public Status getTourConfirmationStatus(String id, Operation operation) {
        if (confirmationService.getConfirmationById(id).operation() == operation) {
            return confirmationService.getConfirmationStatusById(id);
        }
        return null;
    }
}
