package cz.cvut.fit.payments;

import https.courses_fit_cvut_cz.ni_am1.hw._04.CreditCard;
import https.courses_fit_cvut_cz.ni_am1.hw._04.Payment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebServiceRepository {

    private static final List<Payment> payments = new ArrayList<>();

    @PostConstruct
    public void initRepo() {
        Payment payment1 = new Payment();
        payment1.setId(0);
        CreditCard card1 = new CreditCard();
        card1.setNumber("1234-1234-1234-1234");
        card1.setOwner("CardOwner");
        payment1.setCreditCard(card1);
        payments.add(payment1);
    }

    public List<Payment> getPayments(){
        return payments;
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }
}
