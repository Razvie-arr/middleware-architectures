package cz.cvut.fit.payments.client;

import https.courses_fit_cvut_cz.ni_am1.hw._04.CreditCard;
import https.courses_fit_cvut_cz.ni_am1.hw.web_services.ValidateCardRequest;
import https.courses_fit_cvut_cz.ni_am1.hw.web_services.ValidateCardResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class WebServiceClient extends WebServiceGatewaySupport {

    public ValidateCardResponse validateCard(CreditCard card) {
        ValidateCardRequest request = new ValidateCardRequest();
        request.setCardNumber(card.getNumber());
        request.setCardOwner(card.getOwner());

        return (ValidateCardResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
    }
}
