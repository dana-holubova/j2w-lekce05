package cz.czechitas.java2webapps.lekce5.entity;

//Entita reprezentující osobu

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

public class Person {
//    fieldy
    private String givenName;
    private String lastName;
    private LocalDate birthDate;

//    konstruktor bez parametrů
    public Person() {
    }

//    konstruktor s parametry
    public Person(String givenName, String lastName, LocalDate birthDate) {
        this.givenName = givenName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

//    gettery a settery - tím, že jsem vygenerova gettery a settery jsem na třídě vytvořila properties
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

// Anotace @DateTimeFormat pro Spring. Spring dostane datum z prohlížeče jako String. Ale LocalDate už je složitější typ
// objektu. Spring musí vědět, jak má ze Stringu udělat datum (LocalDate).
// Anotace říká: Očekávej datum ve formátu podle iso normy jako datum, tj. YYYY-MM-DD.
// Datum se z formuláře odešle v iso formátu rok-mesic-den (viz adresní řádek po odeslání formuláře),
// i když v políčku pro vyplňování data je to ve formátu den.mesic.rok
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}


