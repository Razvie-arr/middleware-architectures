package cz.cvut.fit.booking;

import https.courses_fit_cvut_cz.ni_am1.tutorials.web_services.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebServiceRepository {

    private static final List<Booking> bookings = new ArrayList<>();
    private int id;

    @PostConstruct
    public void initRepo() throws DatatypeConfigurationException {
        Booking b1 = new Booking();
        PassengerInformation passengerInformation = new PassengerInformation();
        passengerInformation.setName("Vlad");
        passengerInformation.setLastName("Biastinov");
        b1.setPassengerInformation(passengerInformation);

        FlightData departure = new FlightData();
        FlightData arrival = new FlightData();

        XMLGregorianCalendar departureDateTime = DatatypeFactory.newInstance()
                                                .newXMLGregorianCalendar("2022-11-10T18:30:00");

        departure.setDateTime(departureDateTime);
        departure.setLocation("Prague");
        b1.setDeparture(departure);


        XMLGregorianCalendar arrivalDateTime = DatatypeFactory.newInstance()
                                               .newXMLGregorianCalendar("2022-11-10T20:30:00");

        arrival.setDateTime(arrivalDateTime);
        arrival.setLocation("Belgrade");
        b1.setArrival(arrival);

        Booking b2 = new Booking();
        b2.setArrival(arrival);
        b2.setDeparture(departure);

        PassengerInformation passengerInformation2 = new PassengerInformation();
        passengerInformation2.setName("Elon");
        passengerInformation2.setLastName("Mask");
        b2.setPassengerInformation(passengerInformation2);


        addBooking(b1);
        addBooking(b2);
    }

    public List<Booking> getBookings(){
        return bookings;
    }

    public void addBooking(Booking booking) {
        booking.setId(id);
        bookings.add(booking);
        id++;
    }

    public void deleteBooking(int bookingId) { bookings.remove(bookingId);}
}
