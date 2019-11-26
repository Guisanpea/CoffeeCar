package es.uma.ingweb.coffeecar.repositories;

import es.uma.ingweb.coffeecar.entities.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "Announce", path = "announced")
public interface AnnounceRepository extends PagingAndSortingRepository<Announce, Long> {
    List<Announce> findAnnouncesByDriver(@Param("driver") User driver);
    List<Announce> findAnnouncesByPassengers(@Param("passengers") User passenger);
    List<Announce> findAnnouncesByDriverNotAndPassengersNotContaining(@Param("user") User user, @Param("user") User passenger);
    List<Announce> findAnnouncesByArrival(@Param("arrival")String arrival);
    List<Announce> findAnnouncesByArrivalAndArrivalDate(@Param("arrival")String arrival,@Param("arrivalDate") Date arrivalDate);
    List<Announce> findAnnouncesByArrivalDate(@Param("arrival")Date arrivalDate);
}
