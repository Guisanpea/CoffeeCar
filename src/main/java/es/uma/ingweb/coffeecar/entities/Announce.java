package es.uma.ingweb.coffeecar.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Announce {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String departureTime;
    private String arrivalDate;
    private String description;
    private String arrival;
    private double arrivalLatitude;
    private double arrivalLongitude;
    private double departureLatitude;
    private double departureLongitude;
    private String imgLink;
    private int seats;
    @ManyToOne
    private User driver;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<User> passengers;
}
