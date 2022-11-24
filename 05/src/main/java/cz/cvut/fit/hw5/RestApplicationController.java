package cz.cvut.fit.hw5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApplicationController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
