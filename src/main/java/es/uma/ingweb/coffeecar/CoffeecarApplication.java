package es.uma.ingweb.coffeecar;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeecarApplication {
    public static void main(String[] args) {
        Person person = new Person();
        SpringApplication.run(CoffeecarApplication.class, args);
    }

}
