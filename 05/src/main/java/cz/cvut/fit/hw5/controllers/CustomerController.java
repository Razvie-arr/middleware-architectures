package cz.cvut.fit.hw5.controllers;

import cz.cvut.fit.hw5.dto.Customer;
import cz.cvut.fit.hw5.services.CustomerService;
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
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Operation(summary = "Get all customers")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    @GetMapping("/")
    public CollectionModel<Customer> getCustomers() {
        List<Customer> customers = customerService.getCustomers();

        for (Customer customer : customers) {
            customer.removeLinks();
            customer.add(linkTo(CustomerController.class).slash(customer.getId()).withRel("DETAIL"));
        }
        CollectionModel<Customer> result = CollectionModel.of(customers);
        result.add(linkTo(CustomerController.class).withRel("ADD"));
        result.add(linkTo(CustomerController.class).withRel("UPDATE"));
        return result;
    }

    @Operation(summary = "Add new customer")
    @ApiResponse(responseCode = "201", description = "New customer added")
    @PostMapping(value = "/")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/customer/" + customer.getId()).build();
    }

    @Operation(summary = "Get customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customer",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "404", description = "Invalid customer id",
                    content = @Content)})
    @GetMapping("/{id}")
    public HttpEntity<Customer> getCustomer(@PathVariable String id) {

        try {
            Customer customer = customerService.getCustomerById(id);
            customer.removeLinks();
            customer.add(linkTo(CustomerController.class).slash(id).withSelfRel());
            customer.add(linkTo(CustomerController.class).withRel("LIST"));
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Update customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the customer",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "404", description = "Invalid customer",
                    content = @Content)})
    @PutMapping("/")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        if (customerService.updateCustomer(customer)) {
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }

    @Operation(summary = "Delete customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the customer",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Invalid customer",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public HttpStatus deleteCustomer(@PathVariable String id) {
        if (customerService.deleteCustomer(id)) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}
