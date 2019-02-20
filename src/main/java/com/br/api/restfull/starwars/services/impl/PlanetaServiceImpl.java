package com.br.api.restfull.starwars.services.impl;

import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.api.restfull.starwars.dto.PlanetaDto;
import com.br.api.restfull.starwars.exception.PlanetaNaoEncontradoException;
import com.br.api.restfull.starwars.model.Planeta;
import com.br.api.restfull.starwars.repositories.PlanetaRepository;
import com.br.api.restfull.starwars.services.PlanetaService;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class PlanetaServiceImpl implements PlanetaService {

private static final Logger log = LoggerFactory.getLogger(PlanetaServiceImpl.class);
	
	@Autowired
	private PlanetaRepository planetaRepository;
	
	private static final String NOT_FOUND = "0";
	
	@Override
	public Optional<Planeta> buscarPorNome(String nome) {
		log.info("Buscando um planeta pelo Nome {}", nome);
		return Optional.ofNullable(planetaRepository.findByNome(nome));
	}

	@Override
	public Planeta salvarPlaneta(PlanetaDto planetaDto) {
		
		Integer aparicoes = verificaPlanetaNaApiSwapi(planetaDto.getNome());
		Planeta planeta = converterPlaneta(planetaDto);
		planeta.setQuantidadesAparicoes(aparicoes);
		
		planeta = planetaRepository.save(planeta);
		
		return planeta;
	}
	
	@Override
	public Integer verificaPlanetaNaApiSwapi(String nome) {
		
		 Integer quantidadesAparicoes = 0;
		
		try {
			String planetaApi = Unirest.get("https://swapi.co/api/planets/?search={name}")
					.routeParam("name", nome)
			        .asJson()
			        .getBody()
			        .toString();
			
			JSONObject planetaJson = new JSONObject(planetaApi);
            
            String count = planetaJson.get("count").toString();
            
            if(NOT_FOUND.equals(count)) {
            	throw new PlanetaNaoEncontradoException("Não encontrado");
            }
            
            JSONArray results = planetaJson.getJSONArray("results");
            
            JSONObject filmesJson = results.getJSONObject(0);
            
            quantidadesAparicoes = filmesJson.getJSONArray("films").length();
            
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return quantidadesAparicoes;
	}
	
	private Planeta converterPlaneta(PlanetaDto planetaDto) {
		Planeta planeta = new Planeta();
		planeta.setNome(planetaDto.getNome());
		planeta.setClima(planetaDto.getClima());
		planeta.setTerreno(planetaDto.getTerreno());

		return planeta;
	}

}
