package cz.cvut.fit.payments;

import https.courses_fit_cvut_cz.ni_am1.hw._04.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @Autowired
    private WebServiceRepository repository;
    @Autowired
    private PaymentService paymentService;

    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/hw/04/", localPart = "getPaymentsRequest")
    @ResponsePayload
    public GetPaymentsResponse getPayments(@RequestPayload GetPaymentsRequest request) {
        GetPaymentsResponse response = new GetPaymentsResponse();
        response.getPayments().addAll(repository.getPayments());
        return response;
    }

    @PayloadRoot(namespace = "https://courses.fit.cvut.cz/NI-AM1/hw/04/", localPart = "addPaymentRequest")
    @ResponsePayload
    public AddPaymentResponse addPayment(@RequestPayload AddPaymentRequest request) {
        paymentService.addPayment(request.getPayment());
        AddPaymentResponse response = new AddPaymentResponse();
        response.setResponse(paymentService.getResponse());
        return response;
    }
}
