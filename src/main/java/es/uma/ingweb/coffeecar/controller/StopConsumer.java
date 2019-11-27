package es.uma.ingweb.coffeecar.controller;


import es.uma.ingweb.coffeecar.entities.StopHierarchy;
import es.uma.ingweb.coffeecar.entities.GeographicalCoordinates;
import lombok.*;
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
    private static final String URI = "http://datosabiertos.malaga.eu/api/3/action/datastore_search?resource_id=d7eb3174-dcfb-4917-9876-c0e21dd810e3";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/all")
    public List<StopHierarchy.StopInfoResponse.StopData> getBus(){
        return Objects.requireNonNull(restTemplate.getForObject(URI, StopHierarchy.class)).getResult().getStopsData();
    }

    @GetMapping(value="/near")
    public List<StopHierarchy.StopInfoResponse.StopData> getBusByPosition(@RequestParam(name="lat", required=true) float lat,
                                                                         @RequestParam(name="lon", required=true) float lon){
        List<StopHierarchy.StopInfoResponse.StopData> stops =
                Objects.requireNonNull(restTemplate.getForObject(URI, StopHierarchy.class)).getResult().getStopsData();
        GeographicalCoordinates current = new GeographicalCoordinates(lon, lat);

        return stops.stream()
                .filter(stop -> current.distanceTo(new GeographicalCoordinates(stop.getLon(), stop.getLat()))>=1.5)
                .collect(Collectors.toList());

    }
}
