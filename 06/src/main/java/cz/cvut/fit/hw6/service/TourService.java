package cz.cvut.fit.hw6.service;

import cz.cvut.fit.hw6.dto.Status;
import cz.cvut.fit.hw6.dto.Tour;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TourService {
    Map<String, Status> deleteStatuses = new HashMap<>();

    private final List<Tour> tours = new ArrayList<>();

    public Tour getTourById(String id) {
        return tours.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public void addTour(Tour tour) {
        tours.add(tour);
    }

    @Async
    public void asyncDeleteTour(String id) throws InterruptedException {
        deleteStatuses.put(id, Status.IN_PROGRESS);

        //simulate long operation, 30 sec delay
        Thread.sleep(30 * 1000);
        if (tours.removeIf(c -> c.getId().equals(id))) {
            System.out.println("async success");
            deleteStatuses.put(id, Status.SUCCESS);
        } else {
            deleteStatuses.put(id, Status.FAILED);
        }
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

    public Status getDeleteStatusById(String id) {
        return deleteStatuses.get(id);
    }
}
