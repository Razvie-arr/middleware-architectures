package cz.cvut.fit.hw07.controller;

import cz.cvut.fit.hw07.dto.Tour;
import cz.cvut.fit.hw07.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/tour")
public class TourController {
    @Autowired
    TourService tourService;

    @Operation(summary = "Get all tours")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    @GetMapping("/")
    public CollectionModel<Tour> getTours() {
        List<Tour> tours = tourService.getTours();

        for (Tour tour : tours) {
            tour.removeLinks();
            tour.add(linkTo(TourController.class).slash(tour.getId()).withRel("DETAIL"));
        }
        CollectionModel<Tour> result = CollectionModel.of(tours);
        result.add(linkTo(TourController.class).withRel("ADD"));
        result.add(linkTo(TourController.class).withRel("UPDATE"));
        return result;
    }
}

