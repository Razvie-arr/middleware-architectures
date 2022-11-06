package cz.cvut.fit.payments;

import cz.cvut.fit.payments.client.WebServiceClient;
import https.courses_fit_cvut_cz.ni_am1.hw._04.Payment;
import https.courses_fit_cvut_cz.ni_am1.hw._04.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private WebServiceRepository repository;
    @Autowired
    private WebServiceClient wsClient;
    private ResponseType response;

    public void addPayment(Payment payment) {
        if (wsClient.validateCard(payment.getCreditCard()).isResult()) {
            repository.addPayment(payment);
            response = ResponseType.OPERATION_FINISHED_SUCCESSFULLY;
        } else {
            response = ResponseType.CARD_NOT_VALID;
        }
    }

    public ResponseType getResponse() {
        return response;
    }
}
