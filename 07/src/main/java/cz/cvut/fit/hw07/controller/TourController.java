package cz.cvut.fit.hw07.controller;

import cz.cvut.fit.hw07.dto.Tour;
import cz.cvut.fit.hw07.service.TourService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/tour")
public class TourController {
    @Autowired
    TourService tourService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got list of tours",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Tour.class))) }),
            @ApiResponse(responseCode = "304", description = "Not modified",
                    content = @Content) })
    @GetMapping("/strong")
    public ResponseEntity<List<Tour>> getToursWithStrongETag(@RequestHeader(required = false, value = "If-None-Match") String ifNoneMatch) throws NoSuchAlgorithmException, IOException {
        String strongETag = tourService.generateStrongETag();

        if (strongETag.equals(ifNoneMatch)) {
            return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .eTag(strongETag)
                .lastModified(tourService.getLastModified())
                .build();
        } else {
            return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(strongETag)
                .lastModified(tourService.getLastModified())
                .body(tourService.getTours());
        }

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got list of tours",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Tour.class))) }),
            @ApiResponse(responseCode = "304", description = "Not modified",
                    content = @Content) })
    @GetMapping("/weak")
    public ResponseEntity<List<Tour>> getToursWithWeakETag(@RequestHeader(required = false, value = "If-None-Match") String ifNoneMatch) throws NoSuchAlgorithmException, IOException {
        String weakETag = tourService.generateWeakETag();

        if (weakETag.equals(ifNoneMatch)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .eTag(weakETag)
                    .lastModified(tourService.getLastModified())
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .eTag(weakETag)
                    .lastModified(tourService.getLastModified())
                    .body(tourService.getTours());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got list of tours",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Tour.class))) }),
            @ApiResponse(responseCode = "304", description = "Not modified",
                    content = @Content) })
    @GetMapping("/modified")
    public ResponseEntity<List<Tour>> getToursWithModifiedSince(@RequestHeader(required = false, value = "If-Modified-Since") String ifModifiedSince) throws ParseException {
        if (ifModifiedSince != null) {
            long ifModifiedSinceLong = tourService.convertDateStringToMillis(ifModifiedSince);
            if (ifModifiedSinceLong == tourService.getLastModified()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_MODIFIED)
                        .lastModified(tourService.getLastModified())
                        .build();
            }
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(tourService.getLastModified())
                .body(tourService.getTours());
    }

    @PostMapping("/")
    public HttpStatus addTour(@RequestBody Tour tour) {
        tourService.addTour(tour);
        tourService.updateLastModified();
        return HttpStatus.OK;
    }
}

