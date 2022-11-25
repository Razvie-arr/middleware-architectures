package cz.cvut.fit.hw5.controllers;

import cz.cvut.fit.hw5.dto.Customer;
import cz.cvut.fit.hw5.dto.Tour;
import cz.cvut.fit.hw5.services.CustomerService;
import cz.cvut.fit.hw5.services.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            tour.add(linkTo(CustomerController.class).slash(tour.getId()).withRel("DETAIL"));
        }
        CollectionModel<Tour> result = CollectionModel.of(tours);
        result.add(linkTo(TourController.class).withRel("ADD"));
        result.add(linkTo(TourController.class).withRel("UPDATE"));
        return result;
    }

    @Operation(summary = "Add new tour")
    @ApiResponse(responseCode = "201", description = "New tour added")
    @PostMapping(value = "/")
    public ResponseEntity<Tour> createTour(@RequestBody Tour tour) {
        tourService.addTour(tour);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/tour/" + tour.getId()).build();
    }

    @Operation(summary = "Get tour by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tour.class))}),
            @ApiResponse(responseCode = "404", description = "Invalid tour id",
                    content = @Content)})
    @GetMapping("/{id}")
    public HttpEntity<Tour> getTour(@PathVariable String id) {
        try {
            Tour tour = tourService.getTourById(id);
            tour.removeLinks();
            tour.add(linkTo(TourController.class).slash(id).withSelfRel());
            tour.add(linkTo(TourController.class).withRel("LIST"));
            return ResponseEntity.status(HttpStatus.OK).body(tour);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Update tour")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the tour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tour.class))}),
            @ApiResponse(responseCode = "404", description = "Invalid tour",
                    content = @Content)})
    @PutMapping("/")
    public ResponseEntity<?> updateTour(@RequestBody Tour tour) {
        if (tourService.updateTour(tour)) {
            return ResponseEntity.status(HttpStatus.OK).body(tour);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }
}
