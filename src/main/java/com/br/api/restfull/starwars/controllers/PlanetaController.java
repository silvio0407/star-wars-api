package com.br.api.restfull.starwars.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.restfull.starwars.dominio.MensagemResponse;
import com.br.api.restfull.starwars.dto.PlanetaDto;
import com.br.api.restfull.starwars.exception.BusinessException;
import com.br.api.restfull.starwars.exception.PlanetaNaoEncontradoException;
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
	@GetMapping
	public ResponseEntity<Response<PlanetaDto>> buscarPorNome(@RequestParam("nome") String nome) {
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
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<PlanetaDto>> buscarPorId(@PathVariable("id") Long id) {
		log.info("Buscando planeta pelo ID: {}", id);
		Response<PlanetaDto> response = new Response<PlanetaDto>();
		Optional<Planeta> planeta = planetaService.buscarPorId(id);

		if (!planeta.isPresent()) {
			log.info("Planeta não encontrada com ID: {}", id);
			response.getErrors().add("Planeta não encontrada com ID " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterPlanetaDto(planeta.get()));
		return ResponseEntity.ok(response);
	}
	
	
	 /**
     * Adicionar Planeta
     *
     * @param planetaDto dados para o cadastro da solicitação
     * @return Retorna o Planeta criado
     */
	@ResponseBody
    @PostMapping
    public ResponseEntity<Response<PlanetaDto>> adicionarPlaneta(@RequestBody PlanetaDto planetaDto) {
		
		Response<PlanetaDto> response = new Response<PlanetaDto>();

        try {
        	Planeta planeta = planetaService.salvarPlaneta(planetaDto);
        	response.setData(converterPlanetaDto(planeta));
        }catch (PlanetaNaoEncontradoException e) {
        	response.getErrors().add("Não foi possível salvar o Planeta com Nome " + planetaDto.getNome() +", Planeta inexistente na API SWAPI.");
			return ResponseEntity.badRequest().body(response);
		}

        
        return ResponseEntity.ok(response);
    }
	
	@GetMapping(value = "/searchAll")
	public List<Planeta> consultarPlanetas(){
		
		List<Planeta> planetas = null;
		
		try{
			planetas = planetaService.consultarPlanetas();
		}catch (Exception e) {
			String message = "Ocorreu um erro ao obter a lista de Planetas.";
			
			log.error(this.getClass().getName() + ": " + message);
			log.error(e.getMessage());
			throw new BusinessException(message);
		}
		
		return planetas;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MensagemResponse> removerPlaneta(@PathVariable Long id) {

		Optional<Planeta> planeta = planetaService.buscarPorId(id);
		
		MensagemResponse mensagemResponse = new MensagemResponse();

        if (planeta.isPresent()) {
        	 
        	planetaService.removerPlaneta(planeta.get());
        	
        	mensagemResponse.setCodigoResponse(200);
        	mensagemResponse.setMensagem("Planeta excluido com sucesso!");
        	
        	 return ResponseEntity.ok(mensagemResponse);
           
        } else {
        	mensagemResponse.setMensagem(PLANETA_NOT_FIND);
        	return ResponseEntity.ok(mensagemResponse);
        }
    }
	
	/**
	 * Popula um DTO com os dados de um planeta.
	 * 
	 * @param planeta
	 * @return PlanetaDto
	 */
	private PlanetaDto converterPlanetaDto(Planeta planeta) {
		PlanetaDto planetaDto = new PlanetaDto();
		planetaDto.setId(planeta.getId());
		planetaDto.setNome(planeta.getNome());
		planetaDto.setClima(planeta.getClima());
		planetaDto.setTerreno(planeta.getTerreno());
		planetaDto.setQuantidadesAparicoes(planeta.getQuantidadesAparicoes());
		return planetaDto;
	}
}
