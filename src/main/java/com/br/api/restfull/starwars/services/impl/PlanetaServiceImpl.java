package com.br.api.restfull.starwars.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.api.restfull.starwars.model.Planeta;
import com.br.api.restfull.starwars.repositories.PlanetaRepository;
import com.br.api.restfull.starwars.services.PlanetaService;

@Service
public class PlanetaServiceImpl implements PlanetaService {

private static final Logger log = LoggerFactory.getLogger(PlanetaServiceImpl.class);
	
	@Autowired
	private PlanetaRepository planetaRepository;
	
	@Override
	public Optional<Planeta> buscarPorNome(String nome) {
		log.info("Buscando um planeta pelo Nome {}", nome);
		return Optional.ofNullable(planetaRepository.findByNome(nome));
	}

}
