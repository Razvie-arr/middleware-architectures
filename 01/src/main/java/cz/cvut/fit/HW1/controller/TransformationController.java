package cz.cvut.fit.HW1.controller;

import cz.cvut.fit.HW1.model.SubmitDTO;
import cz.cvut.fit.HW1.model.Transformation;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransformationController {
    @PostMapping(value="/transformation", consumes = "text/plain", produces = "application/json")
    SubmitDTO transformMessage(@RequestBody String message) {
        Transformation transformation = new Transformation(message);
        transformation.transform();
        return transformation.getTransformedSubmit();
    }
}
