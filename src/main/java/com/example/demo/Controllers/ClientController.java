package com.example.demo.Controllers;

import com.example.demo.Entity.Client;
import com.example.demo.Service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @Operation(summary = "Get all clients", description = "Get all clients")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Client>> getAll() {
        logger.info("Fetching all clients");
        Iterable<Client> clients = service.getClient();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Get client by ID", description = "Get a client by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Client> getById(@PathVariable UUID id) {
        logger.info("Fetching client with ID: {}", id);
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Client with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Create Client", description = "Create a new client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Client> create(@Valid @RequestBody Client client) {
        try {
            logger.info("Creating new client");
            Client newClient = service.save(client);
            return ResponseEntity.ok(newClient);
        } catch (Exception e) {
            logger.error("Error creating client", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Update Client", description = "Update an existing client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Client> update(@PathVariable UUID id, @Valid @RequestBody Client updatedClient) {
        logger.info("Updating client with ID: {}", id);
        Optional<Client> existingClient = service.getById(id);

        if (existingClient.isPresent()) {
            Client clientToUpdate = new Client(
                    id,
                    updatedClient.getName(),
                    updatedClient.getEmail(),
                    updatedClient.getDni()
            );
            Client savedClient = service.save(clientToUpdate);
            return ResponseEntity.ok(savedClient);
        } else {
            logger.warn("Client with ID {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete client", description = "Delete a client by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            logger.info("Deleting client with ID: {}", id);
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Error deleting client with ID {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}
