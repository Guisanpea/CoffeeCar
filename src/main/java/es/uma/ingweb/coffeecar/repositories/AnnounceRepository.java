package es.uma.ingweb.coffeecar.repositories;

import es.uma.ingweb.coffeecar.entities.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "announces", path = "announces")
public interface AnnounceRepository extends CrudRepository<Announce, Long> {
    @Query("SELECT DISTINCT a from Announce a JOIN a.driver d " +
          "WHERE d.email <> ?1 and ?1 NOT IN (SELECT p.email from a.passengers p)")
    List<Announce> findAvailableAnnounces(String email);
    @Query("SELECT DISTINCT a from Announce a JOIN a.driver d " +
            "WHERE d.email = ?1 or ?1 IN (SELECT p.email from a.passengers p)")
    List<Announce> findUserTrips(String email);
}
