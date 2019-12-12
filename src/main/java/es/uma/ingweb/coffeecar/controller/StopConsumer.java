package es.uma.ingweb.coffeecar.controller;


import es.uma.ingweb.coffeecar.entities.StopHierarchy;
import es.uma.ingweb.coffeecar.entities.GeographicalCoordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stops")
public class StopConsumer {

    private final RestTemplate restTemplate;
    private final String STOPS_URL = "http://datosabiertos.malaga.eu/api/3/action/datastore_search?resource_id=d7eb3174-dcfb-4917-9876-c0e21dd810e3";

    public StopConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private List<StopHierarchy.StopInfoResponse.StopData> getStops() {
        return Objects.requireNonNull(restTemplate.getForObject(STOPS_URL, StopHierarchy.class)).getResult().getStopsData();
    }

    @GetMapping(value = "/search/findNearby")
    public List<StopHierarchy.StopInfoResponse.StopData> findNearby(@RequestParam(name = "lat") float lat,
                                                                          @RequestParam(name = "lon") float lon) {
        List<StopHierarchy.StopInfoResponse.StopData> stops = getStops();
        GeographicalCoordinates requestCoordinates = new GeographicalCoordinates(lon, lat);

        List<StopHierarchy.StopInfoResponse.StopData> filteredStops = stops.stream().filter(stop -> {
            GeographicalCoordinates stopCoordinates = new GeographicalCoordinates(stop.getLon(), stop.getLat());
            return requestCoordinates.distanceTo(stopCoordinates) <= 1000;
        }).collect(Collectors.toList());
        return filteredStops;
    }

    @GetMapping(value = "/search/getLines")
    public List<Float> getLines(@RequestParam(name = "codParada") float codParada){
        List<StopHierarchy.StopInfoResponse.StopData> stops = getStops();
        return stops.stream()
                .filter(stop -> stop.getCodParada()==codParada)
                .map(StopHierarchy.StopInfoResponse.StopData::getCodLinea).collect(Collectors.toList());

    }
}
