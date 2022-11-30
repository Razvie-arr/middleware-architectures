package cz.cvut.fit.hw07.service;

import cz.cvut.fit.hw07.dto.Customer;
import cz.cvut.fit.hw07.dto.Tour;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TourService {

    private final List<Tour> tours = new ArrayList<>();
    private Long lastModified;

    public void addTour(Tour tour) {
        tours.add(tour);
        updateLastModified();
    }

    public List<Tour> getTours() { return tours; }

    public String generateStrongETag() throws NoSuchAlgorithmException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (baos; ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            for (Tour tour : tours) {
                oos.writeObject(tour.getId());
                oos.writeObject(tour.getName());
                oos.writeObject(tour.getCustomers());
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(baos.toByteArray());
            return DatatypeConverter.printHexBinary(thedigest);
        }
    }

    public String generateWeakETag() throws NoSuchAlgorithmException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (baos; ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            for (Tour tour : tours) {
                oos.writeObject(tour.getId());
                oos.writeObject(tour.getName());
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(baos.toByteArray());
            return "W/" + DatatypeConverter.printHexBinary(thedigest);
        }
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void updateLastModified() {
        this.lastModified = System.currentTimeMillis();
    }
}
