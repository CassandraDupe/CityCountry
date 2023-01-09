package monprojet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.City;
import monprojet.entity.Country;

// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query(value = "Select sum(population) "
    +"From City "
    +"where City.country_id = :idCountry ",
    nativeQuery = true)
    public int countInhabitants (Integer idCountry);

    @Query(value = "Select Country.name as name, sum(population) as pop "
    +"From Country inner join City on (City.country_id = Country.id) "
    +"group by name ",
    nativeQuery = true)
    public List<PopulationPerCountry> exposeInhabitants();
}
