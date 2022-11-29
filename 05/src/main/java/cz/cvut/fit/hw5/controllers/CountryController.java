package cz.cvut.fit.hw5.controllers;

import cz.cvut.fit.hw5.dto.Country;
import cz.cvut.fit.hw5.dto.Location;
import cz.cvut.fit.hw5.services.CountryService;
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
@RequestMapping(value = "/country")
public class CountryController {
    @Autowired
    CountryService countryService;

    @Operation(summary = "Get all countries")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    @GetMapping("/")
    public CollectionModel<Country> getCountries() {
        List<Country> countries = countryService.getCountries();
        for (Country country : countries) {
            country.removeLinks();
            country.add(linkTo(CountryController.class).slash(country.getId()).withRel("DETAIL"));
        }
        CollectionModel<Country> result = CollectionModel.of(countries);
        result.add(linkTo(CountryController.class).withRel("ADD"));
        return result;
    }

    @Operation(summary = "Add new country")
    @ApiResponse(responseCode = "201", description = "New country added")
    @PostMapping(value = "/")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        countryService.addCountry(country);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/country/" + country.getId()).build();
    }

    @Operation(summary = "Get country by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the country",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Country.class)) }),
            @ApiResponse(responseCode = "404", description = "Invalid country id",
                    content = @Content) })
    @GetMapping("/{id}")
    public HttpEntity<Country> getCountry(@PathVariable String id) {
        try {
            Country country = countryService.getCountryById(id);
            country.removeLinks();
            country.add(linkTo(CountryController.class).slash(id).withSelfRel());
            country.add(linkTo(CountryController.class).withRel("LIST"));
            return ResponseEntity.status(HttpStatus.OK).body(country);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Update country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the country",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Country.class)) }),
            @ApiResponse(responseCode = "404", description = "Invalid country",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country, @RequestParam String id) {
        Country updatedCountry = countryService.updateCountry(id, country);
        if (updatedCountry != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedCountry);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Delete country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the country",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Invalid country",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public HttpStatus deleteCountry(@PathVariable String id) {
        if (countryService.deleteCountry(id)) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}
