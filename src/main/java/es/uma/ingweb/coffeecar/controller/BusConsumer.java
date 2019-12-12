package es.uma.ingweb.coffeecar.controller;

import es.uma.ingweb.coffeecar.entities.BusHierarchy;
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
@RequestMapping("/buses")
public class BusConsumer {
    private final RestTemplate restTemplate;
    private final String BUSES_URL = "http://datosabiertos.malaga.eu/api/3/action/datastore_search?resource_id=9bc05288-1c11-4eec-8792-d74b679c8fcf";

    public BusConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value="")
    public List<BusHierarchy.BusInfoResponse.BusData> getBusesPosition(){
        return Objects.requireNonNull(restTemplate.getForObject(BUSES_URL, BusHierarchy.class)).getResult().getBusesData();
    }

    @GetMapping(value="/search/findByLine")
    public List<BusHierarchy.BusInfoResponse.BusData> getBusesPosition(@RequestParam(name="line") float line){
        String uri = "http://datosabiertos.malaga.eu/api/3/action/datastore_search?resource_id=9bc05288-1c11-4eec-8792-d74b679c8fcf";
        List<BusHierarchy.BusInfoResponse.BusData> buses =
                Objects.requireNonNull(restTemplate.getForObject(BUSES_URL, BusHierarchy.class)).getResult().getBusesData();

        return buses.stream().filter(bus -> bus.getCodLinea()==line).collect(Collectors.toList());
    }


}
