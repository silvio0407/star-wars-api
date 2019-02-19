package com.br.api.restfull.starwars.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.restfull.starwars.dto.PlanetaDto;
import com.br.api.restfull.starwars.model.Planeta;
import com.br.api.restfull.starwars.response.Response;
import com.br.api.restfull.starwars.services.PlanetaService;

@RestController
@RequestMapping("/api/planetas")
public class PlanetaController {
	
	private static final Logger log = LoggerFactory.getLogger(PlanetaController.class);

	private static final String PLANETA_NOT_FIND = "Planeta não localizado!";

	@Autowired
    private PlanetaService planetaService;
	
	/**
	 * Retorna um planeta dado um Nome.
	 * 
	 * @param nome
	 * @return ResponseEntity<Response<PlanetaDto>>
	 */
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Response<PlanetaDto>> buscarPorNome(@PathVariable("nome") String nome) {
		log.info("Buscando planeta pelo Nome: {}", nome);
		Response<PlanetaDto> response = new Response<PlanetaDto>();
		Optional<Planeta> planeta = planetaService.buscarPorNome(nome);

		if (!planeta.isPresent()) {
			log.info("Planeta não encontrada com Nome: {}", nome);
			response.getErrors().add("Planeta não encontrada com Nome " + nome);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterPlanetaDto(planeta.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Popula um DTO com os dados de um planeta.
	 * 
	 * @param planeta
	 * @return PlanetaDto
	 */
	private PlanetaDto converterPlanetaDto(Planeta planeta) {
		PlanetaDto empresaDto = new PlanetaDto();

		return empresaDto;
	}
}
