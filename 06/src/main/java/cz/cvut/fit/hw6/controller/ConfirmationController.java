package cz.cvut.fit.hw6.controller;

import cz.cvut.fit.hw6.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/confirmation")

public class ConfirmationController {
    @Autowired
    ConfirmationService confirmationService;

    @PostMapping
    public ResponseEntity<String> confirmConfirmation(@RequestParam String id) {
        confirmationService.confirmConfirmation(id);

        return ResponseEntity.status(HttpStatus.OK).body("Transaction confirmed");
    }
}
