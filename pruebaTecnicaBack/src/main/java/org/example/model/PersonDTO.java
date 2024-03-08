package org.example.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

public class PersonDTO {
     @Size(max = 255)
    @PersonDocumentValid
    private String document;

    @Size(max = 30)
    private String name;

    @Size(max = 30)
    private String lastname;

    private LocalDate datebirth;

    public String getDocument() {
        return document;
    }

    public void setDocument(final String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDatebirth() {
        return datebirth;
    }

    public void setDatebirth(final LocalDate dateBirth) {
        this.datebirth = dateBirth;
    }
}
