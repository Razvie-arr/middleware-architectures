package cz.cvut.fit.booking;

import https.courses_fit_cvut_cz.ni_am1.tutorials.web_services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;

    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/tutorials/web-services/", localPart = "getBookingsRequest")
    @ResponsePayload
    public GetBookingsResponse getBookings(@RequestPayload GetBookingsRequest request) {
        GetBookingsResponse response = new GetBookingsResponse();
        response.getBooking().addAll(repository.getBookings());
        return response;
    }
}
