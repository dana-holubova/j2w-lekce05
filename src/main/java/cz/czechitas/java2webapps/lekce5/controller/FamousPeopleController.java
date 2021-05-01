package cz.czechitas.java2webapps.lekce5.controller;

import cz.czechitas.java2webapps.lekce5.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Filip Jirsák
 */
@Controller
public class FamousPeopleController {

//  deklarace fieldu people
//  final znamená, že to musí vzniknout právě jednou
  private final List<Person> people;

//  konstruktor bez parametrů
  public FamousPeopleController() {
//    inicializace/naplnění proměnné people
//    vytvářím rovnou prázdný list, abych do něj mohla později plnit data
//    list se plní objekty typu Person, které se tvoří z entity Person
//    Ze začátku je seznam naplněn třemi hodnotami. Když něco přidám přes formulář, tak se to přidá a načítá se to z paměti.
//    Když ale aplikaci restartuji, tak se to z paměti vymaže a jsou tam znovu jen ty tři předem nastavené hodnoty.
//    Zatím se to nikam neukládá.
    people = new ArrayList<>();
    people.add(new Person("Angela", "Merkelová", LocalDate.of(1954,7,17)));
    people.add(new Person("Bill", "Gates", LocalDate.of(1955, 10, 28)));
    people.add(new Person("Greta", "Thunbergová", LocalDate.of(2003, 1, 3)));
  }

  @GetMapping("/")
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("index");
result.addObject("people", people);
    return result;
  }

//  Metoda pro vyhledávání na stránce
//  Spring tuto metodu zavolá jen když tam bude parametr query. Takže je tam odlišné namapování než u metody list()
  @GetMapping(value ="/", params="query")
  public ModelAndView query(String query) {
//    vyberu všechna jména, která v křestním jménu nebo příjmení obsahují query
//    vezmu seznam, řeknu, že se má procházet, postupně filtrovat, a vyfiltrovaný seznam chci zase předělat zpátky na
//    list. Ten pak předám do atributu modelu.
    List<Person> filteredPeople = people.stream()
            .filter(person -> person.getGivenName().contains(query) || person.getLastName().contains(query))
            .collect(Collectors.toList());
    ModelAndView result = new ModelAndView("index");
//    zde se předává filtrovaný seznam lidí
    result.addObject("people", filteredPeople);
//    vracím dotaz do formuláře
    result.addObject("query", query);
    return result;
  }

//  formulář se odesílá metodou get, proto @GetMapping
//  adresa (parametr value) je stejná jako adresa, na kterou se má odeslat formulář (atribut action)
//  metoda vyžaduje následující parametry, proto není mamapovaná na stejnou adresu jako předchozí metoda
//  když chci mít víc parametrů, tak se to zadává jako pole ve složených závorkách

//  @GetMapping(value = "/", params = {"givenName", "lastName", "birthDate"})
//Změna anotace @GetMapping na @PostMapping. Je potřeba změnit i metodu ve formuláři na post
  @PostMapping(value = "/", params = {"givenName", "lastName", "birthDate"})

//  složitější způsob, který se nepoužívá
//public ModelAndView append(String givenName, String lastName, String birthDate) {
//    ModelAndView result = new ModelAndView("index");
//
////    vytvořím nový objekt person, do kterého přes settery vložím proměnné z parametrů metody
//    Person person = new Person();
//    person.setGivenName(givenName);
//    person.setLastName(lastName);
//    person.setBirthDate(birthDate);
//
////    objekt person připojím k listu people
//    people.add(person);
//
//    result.addObject("people",people);
//    return result;
//  }

//  Místo toho, abych metodě předala jednotlivé parametry, co dojdou z formuláře metodou get, a pak je
//  přidávala pomocí setterů do objektu person (viz předchozí příklad), tak do parametrů metody append vložím jen
//  entitu Person.
//  Spring zařídí, že se jednotlivé parametry správně načtou. Tj. udělá to samé, co se provedlo v příkladu výše.
//  Spring nehledá parametr v cestě, ale podívá do entity Person, jaké jsou tam properties, a nastaví jména dovnitř
//  (tj. do prostoru pro vložení parametrů metody append). Je nezbytné, aby jména polí (atributy name ve formuláři) byla
//  shodná se jmény properties v entitě Person. Jinak to nebude fungovat.

  public String append(Person person) {

    //    hotový objekt vložím do seznamu
    people.add(person);

//    ModelAndView result = new ModelAndView("index");
//    result.addObject("people", people);
//    return result;

//    kód pro tvorbu ModelAndView a vložení objektu people je stejný jako v předchozí metodě list(), proto jej
//    neopakuji, ale zavolám rovnou tuto metodu

//    return list();
// Pomocí redirect se přesměruji na jinou stránku s adresou "/". Na ni je namapovaná metoda list(), která zobrazuje data.
//    Redirect je text, proto musí metoda vracet String a ne ModelAndView. Když se něco přesměrovává, tak to pak jde vždy
//    přes get, tj. spustí se metoda s adresou "/" a anotací @GetMapping
    return "redirect:/";
  }

  @PostMapping(value="/", params="id")
  public String delete(int id) {
    people.remove(id);

    return "redirect:/";
  }
}
