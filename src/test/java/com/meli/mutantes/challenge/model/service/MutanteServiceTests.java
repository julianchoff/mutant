package com.meli.mutantes.challenge.model.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.meli.mutantes.challenge.common.ApiException;
import com.meli.mutantes.challenge.controller.dtos.MutanteDTO;
import com.meli.mutantes.challenge.model.entity.Mutantes;
import com.meli.mutantes.challenge.model.repository.IMutanteRepository;
import com.meli.mutantes.challenge.model.service.MutanteService;

@RunWith(MockitoJUnitRunner.class)
class MutanteServiceTests {

	@Mock
	private IMutanteRepository mutanteRepository;

	@InjectMocks
	private MutanteService mutantesService = new MutanteService();
	
    @BeforeEach
    public void antesDe() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	void debeLanzarErrorCuandoElDnaTengaEstructuraInvalida() {
		String[] dna = new String[]{"GCPP","CAGT","TTAT","AGTC"};
		MutanteDTO mutanteDTO = new MutanteDTO();
		mutanteDTO.setDna(dna);
		
        assertThrows(ApiException.class, ()-> mutantesService.validarAdn(mutanteDTO), "DNA invalid");
	}
	
	@Test
	void debeGuardarElDnaCuandoEsUnMutanteValido() throws ApiException {
		ArgumentCaptor<Mutantes> mutantesCaptor = ArgumentCaptor.forClass(Mutantes.class);
		
		String[] dna = new String[]{"CTTTTA","CAGTGC","TTATGT","AGTCGG","CCCATA","TCACTG"};
		MutanteDTO mutanteDTO = new MutanteDTO();
		mutanteDTO.setDna(dna);
		
		mutantesService.validarAdn(mutanteDTO);
		
        verify(mutanteRepository, times(1)).save(mutantesCaptor.capture());
        
        Mutantes mutante = mutantesCaptor.getValue();
        
        assertEquals(Arrays.toString(dna), mutante.getAdn());
        assertTrue(mutante.getMutante());
	}
	
	@Test
	void debeGuardarElDnaCuandoEsUnHumanoValido() throws ApiException {
		ArgumentCaptor<Mutantes> mutantesCaptor = ArgumentCaptor.forClass(Mutantes.class);
		
		String[] dna = new String[]{"TTGCGA","CAGTGC","TTATGT","AGTCTT","CCCATA","TCACTG"};
		MutanteDTO mutanteDTO = new MutanteDTO();
		mutanteDTO.setDna(dna);
		
		mutantesService.validarAdn(mutanteDTO);
		
        verify(mutanteRepository, times(1)).save(mutantesCaptor.capture());
        
        Mutantes mutante = mutantesCaptor.getValue();
        
        assertEquals(Arrays.toString(dna), mutante.getAdn());
        assertFalse(mutante.getMutante());
	}
	
	@Test
	void noDebeGuardarElDnaCuandoEsteExiste() throws ApiException {
		String[] dna = new String[]{"ATGCGA","CAGTGC","TTATGT","AGTCGG","CCCATA","TCACTG"};
		MutanteDTO mutanteDTO = new MutanteDTO();
		mutanteDTO.setDna(dna);
		
		when(mutanteRepository.existsById(anyString())).thenReturn(true);
		
		mutantesService.validarAdn(mutanteDTO);
		
        verify(mutanteRepository, never()).save(any());
	}
	
	@Test
	void debeValidarLaMatrizHorizontalmente() throws ApiException {
		String[] dna = new String[]{"CCCCGT","CAGTGC","TTATGT","AGTCGG","CCCATA","TCACTG"};
		MutanteDTO mutanteDTO = new MutanteDTO();
		mutanteDTO.setDna(dna);
		
		mutantesService.validarAdn(mutanteDTO);
		
        verify(mutanteRepository, times(1)).save(any());
	}
	
	@Test
	void debeValidarLaMatrizOblicuamente() throws ApiException {
		String[] dna = new String[]{"ATGCCA","CAGTGC","TTATGT","AGTAGG","CCCATA","TCACTG"};
		MutanteDTO mutanteDTO = new MutanteDTO();
		mutanteDTO.setDna(dna);
		
		mutantesService.validarAdn(mutanteDTO);
		
        verify(mutanteRepository, times(1)).save(any());
	}
	
	@Test
	void debeObtenerLasEstadisticasCuandoSeLlameElMetodo() {
		mutantesService.getEstadisticas();
		
		verify(mutanteRepository, times(1)).getStats();
	}

}
