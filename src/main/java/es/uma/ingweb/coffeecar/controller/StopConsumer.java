package es.uma.ingweb.coffeecar.controller;


import es.uma.ingweb.coffeecar.entities.StopHierarchy;
import es.uma.ingweb.coffeecar.entities.GeographicalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/getStops")
public class StopConsumer {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/all")
    public List<StopHierarchy.StopInfoResponse.StopData> getBus(){
        String uri = "http://datosabiertos.malaga.eu/api/3/action/datastore_search?resource_id=d7eb3174-dcfb-4917-9876-c0e21dd810e3";

        return Objects.requireNonNull(restTemplate.getForObject(uri, StopHierarchy.class)).getResult().getStopsData();
    }

    @GetMapping(value="/near")
    public List<StopHierarchy.StopInfoResponse.StopData> getBusByPosition(@RequestParam(name="lat", required=true) float lat,
                                                                         @RequestParam(name="lon", required=true) float lon){
        String uri = "http://datosabiertos.malaga.eu/api/3/action/datastore_search?resource_id=d7eb3174-dcfb-4917-9876-c0e21dd810e3";
        List<StopHierarchy.StopInfoResponse.StopData> stops =
                Objects.requireNonNull(restTemplate.getForObject(uri, StopHierarchy.class)).getResult().getStopsData();
        GeographicalCoordinates current = new GeographicalCoordinates(lat, lon);

        return stops.stream()
                .filter(stop -> current.distanceTo(new GeographicalCoordinates(stop.getLon(), stop.getLat()))>=1.5)
                .collect(Collectors.toList());

    }
}