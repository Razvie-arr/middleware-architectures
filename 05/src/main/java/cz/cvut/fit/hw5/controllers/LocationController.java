package cz.cvut.fit.hw5.controllers;

import cz.cvut.fit.hw5.dto.Country;
import cz.cvut.fit.hw5.dto.Location;
import cz.cvut.fit.hw5.services.LocationService;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/location")
public class LocationController {
    @Autowired
    LocationService locationService;

    @Operation(summary = "Get all locations")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    @GetMapping("/")
    public CollectionModel<Location> getLocations(@RequestParam String countryId) {
        List<Location> locations = new ArrayList<>();

        if (countryId != null) {
            locations = locationService.getLocationsInCountry(countryId);
        } else {
            locations = locationService.getLocations();
        }

        for (Location location : locations) {
            location.removeLinks();
            location.add(linkTo(LocationController.class).slash(location.getId()).withRel("DETAIL"));
        }
        CollectionModel<Location> result = CollectionModel.of(locations);
        result.add(linkTo(LocationController.class).withRel("ADD"));
        result.add(linkTo(LocationController.class).withRel("UPDATE"));
        return result;
    }

    @Operation(summary = "Add new location")
    @ApiResponse(responseCode = "201", description = "New location added")
    @PostMapping(value = "/")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        locationService.addLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/location/" + location.getId()).build();
    }

    @Operation(summary = "Get location by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the location",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Location.class))}),
            @ApiResponse(responseCode = "404", description = "Invalid location id",
                    content = @Content)})
    @GetMapping("/{id}")
    public HttpEntity<Location> getLocation(@PathVariable String id) {

        try {
            Location location = locationService.getLocationById(id);
            location.removeLinks();
            location.add(linkTo(LocationController.class).slash(id).withSelfRel());
            location.add(linkTo(LocationController.class).withRel("LIST"));
            return ResponseEntity.status(HttpStatus.OK).body(location);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Update location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the location",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Location.class))}),
            @ApiResponse(responseCode = "404", description = "Invalid location",
                    content = @Content)})
    @PutMapping("/")
    public ResponseEntity<?> updateLocation(@RequestBody Location location) {
        if (locationService.updateLocation(location)) {
            return ResponseEntity.status(HttpStatus.OK).body(location);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }
}
