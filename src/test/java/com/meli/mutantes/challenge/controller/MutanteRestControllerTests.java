package com.meli.mutantes.challenge.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.meli.mutantes.challenge.MutantesChallengeApplication;
import com.meli.mutantes.challenge.common.ApiException;
import com.meli.mutantes.challenge.config.H2TestProfileJPAConfig;
import com.meli.mutantes.challenge.controller.dtos.MutanteDTO;
import com.meli.mutantes.challenge.model.entity.Mutantes;
import com.meli.mutantes.challenge.model.projection.Stats;
import com.meli.mutantes.challenge.model.repository.IMutanteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MutantesChallengeApplication.class, H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
@Tag("api")
class MutanteRestControllerTests {

	@Autowired
	private MutanteRestController mutantesController;
	
	@Autowired
	private IMutanteRepository mutanteRepository;
	
	@Test
	void testvalidarAdnMutante() throws ApiException {
		String[] dna = {"TTTTTT","CAGTGC","TGATGT","AGTAGA","CACCTA","AGAAGG"};
		MutanteDTO mutante = new MutanteDTO();
		mutante.setDna(dna);
		ResponseEntity<Boolean> response = mutantesController.validarAdn(mutante);
		
		assertTrue(mutanteRepository.existsById(Arrays.toString(dna)));
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
	}
	
	@Test
	void testvalidarAdnHumano() throws ApiException {
		String[] dna = {"TTGCGA","CAGTGC","TTATGT","AGTCTT","CCCATA","TCACTG"};
		MutanteDTO mutante = new MutanteDTO();
		mutante.setDna(dna);
		ResponseEntity<Boolean> response = mutantesController.validarAdn(mutante);
		
		assertTrue(mutanteRepository.existsById(Arrays.toString(dna)));
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
		
	}
	
	@Test
	void testgetStats() {
		Mutantes[] mutantes = new Mutantes[] {new Mutantes("1", true), new Mutantes("2", false), new Mutantes("3", false)};
		mutanteRepository.saveAll(Arrays.asList(mutantes));
		
		Stats response = mutantesController.getStats();
		
		assertEquals(1, response.getCountMutantDna().intValue());
		assertEquals(2, response.getCountHumanDna().intValue());
		assertEquals(0.5D, response.getRatio(), 0.1);
	}

}
