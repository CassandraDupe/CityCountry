package monprojet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }

    @Test
    void onSaitCompterLesPopulations(){
        log.info("On s'assure que la somme des City correspond");
        Country myOnlyCountry = new Country("IT","Italia");
        City myFirstCity = new City("Rome", myOnlyCountry);
        myFirstCity.setPop(5);
        City mySecondCity = new City("Venice", myOnlyCountry);
        myFirstCity.setPop(8);
        int combienDHabitantsDansCountry = 5 + 8;
        int habs = countryDAO.countInhabitants(myOnlyCountry.getId());
        assertEquals(combienDHabitantsDansCountry, habs, "On doit trouver 13 habitants");
    }

    @Test
    void onRegardeLesListesDePays(){
        log.info("Je n'en peux plus de ces TP");
        Country myOnlyCountry = new Country("IT","Italia");
        City myFirstCity = new City("Rome", myOnlyCountry);
        myFirstCity.setPop(5);
        City mySecondCity = new City("Venice", myOnlyCountry);
        myFirstCity.setPop(8);

        Country mySecondCountry = new Country("AU","Australia");
        City myFirstSecondCity = new City("Sydney", mySecondCountry);
        myFirstCity.setPop(9);
        City mySecondSecondCity = new City("unTruc", mySecondCountry);
        myFirstCity.setPop(4);
        //Pas fini
    }
}
