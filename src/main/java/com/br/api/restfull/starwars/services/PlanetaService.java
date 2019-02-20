package com.br.api.restfull.starwars.services;

import java.util.Optional;

import com.br.api.restfull.starwars.dto.PlanetaDto;
import com.br.api.restfull.starwars.model.Planeta;

public interface PlanetaService {

	/**
	 * Retorna um planeta pelo nome.
	 * 
	 * @param nome
	 * @return Optional<Planeta>
	 */
	Optional<Planeta> buscarPorNome(String nome);
	
	Planeta salvarPlaneta(PlanetaDto planeta);
	
	Integer verificaPlanetaNaApiSwapi(String nome);
}
