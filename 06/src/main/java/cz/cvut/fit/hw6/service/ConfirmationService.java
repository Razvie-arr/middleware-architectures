package cz.cvut.fit.hw6.service;

import cz.cvut.fit.hw6.dto.Confirmation;
import cz.cvut.fit.hw6.dto.Status;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfirmationService {
    Map<Confirmation, Status> confirmations = new HashMap<>();

    public void addConfirmation(Confirmation confirmation) {
        confirmations.put(confirmation, Status.UNCONFIRMED);
    }

    public Map<Confirmation, Status> getAllConfirmations() {
        return confirmations;
    }

    public void confirmConfirmation(String id) {
        Confirmation confirmation = getConfirmationById(id);
        confirmations.replace(confirmation, Status.CONFIRMED);
    }

    public Confirmation getConfirmationById(String id) {
        for (Map.Entry<Confirmation, Status> entry : confirmations.entrySet()) {
            if (entry.getKey().id().equals(id)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Status getConfirmationStatusById(String id) {
        return confirmations.get(getConfirmationById(id));
    }
}
