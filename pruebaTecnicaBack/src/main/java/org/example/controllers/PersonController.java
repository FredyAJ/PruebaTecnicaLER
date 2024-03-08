package org.example.controllers;

import java.util.List;

import org.example.model.PaginationDTO;
import org.example.model.PersonDTO;
import org.example.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.MediaType;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonService personService;

    public PersonController(final PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPeople() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/{document}")
    public ResponseEntity<PersonDTO> getPerson(
            @PathVariable(name = "document") final String document) {
        return ResponseEntity.ok(personService.get(document));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createPerson(@RequestBody @Valid final PersonDTO personDTO) {
        final String createdDocument = personService.create(personDTO);
        return new ResponseEntity<>('"' + createdDocument + '"', HttpStatus.CREATED);
    }

    @PostMapping("filterPerson")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Page<Object[]>> findAllPersonFilter(@RequestBody @Valid final PaginationDTO paginationDTO) {
        return new ResponseEntity<Page<Object[]>>(personService.findAllPersonFilter(paginationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{document}")
    public ResponseEntity<String> updatePerson(
            @PathVariable(name = "document") final String document,
            @RequestBody @Valid final PersonDTO personDTO) {
        personService.update(document, personDTO);
        return ResponseEntity.ok('"' + document + '"');
    }

    @DeleteMapping("/{document}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePerson(
            @PathVariable(name = "document") final String document) {
        personService.delete(document);
        return ResponseEntity.noContent().build();
    }
}
