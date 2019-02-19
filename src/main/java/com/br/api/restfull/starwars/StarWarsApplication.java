package com.br.api.restfull.starwars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.br.api.restfull.starwars.model.Planeta;
import com.br.api.restfull.starwars.services.PlanetaService;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;

@SpringBootApplication
public class StarWarsApplication implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StarWarsApplication.class);
	
	@Autowired
    private PlanetaService planetaService;

	public static void main(String[] args) {
		SpringApplication.run(StarWarsApplication.class, args);
	}
	
	@Override
    public void run(String... strings) throws Exception {

        Planeta planeta;

        LOGGER.info("Inserindo 10 planetas no Mysql");

        for (int i = 1; i <= 10; i++) {

            String planetString = Unirest.get("https://swapi.co/api/planets/{id}")
                    .routeParam("id", String.valueOf(i))
                    .asJson()
                    .getBody()
                    .toString();

            planeta = new Gson().fromJson(planetString, Planeta.class);

           System.out.println(planetString);
        }

    } 
	 
}
