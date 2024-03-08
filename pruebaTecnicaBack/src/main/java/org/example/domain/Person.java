package org.example.domain;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person {

    @Id
    @Column(nullable = false, updatable = false)
    private String document;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String lastname;

    @Column
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

