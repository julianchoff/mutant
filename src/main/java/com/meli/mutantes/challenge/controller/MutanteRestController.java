package com.meli.mutantes.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutantes.challenge.common.ApiException;
import com.meli.mutantes.challenge.controller.dtos.MutanteDTO;
import com.meli.mutantes.challenge.model.projection.Stats;
import com.meli.mutantes.challenge.model.service.MutanteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("v1")
@Api(tags = {"Controlador donde se realizan operaciones de validación de ADN"})
public class MutanteRestController {
	
	@Autowired
	private MutanteService mutanteService;
	
	

	@PostMapping("/mutant")
    @ApiOperation("Verifica si un humano es mutante por medio de un ADN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se realizó la validación exitosa de mutante y si el ADN no existe se guarda correctamente!!"),
            @ApiResponse(code = 403, message = "Se realizó la validación exitosa de humano y si el ADN no existe se guarda correctamente!!")
    })
	public ResponseEntity<Boolean> validarAdn(@RequestBody MutanteDTO mutante) throws ApiException {
		boolean esMutante = mutanteService.validarAdn(mutante);
		return new ResponseEntity<>(esMutante, esMutante ? HttpStatus.OK : HttpStatus.FORBIDDEN);
	}
	

    @GetMapping("/stats")
    @ApiOperation("Realiza la consulta de las estadísticas de los ADN analizados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se realizó la consulta de las estadísticas correctamente"),
            @ApiResponse(code = 500, message = "Error interno en el servidor al procesar la petición")
    })
    public Stats getStats() {
        return mutanteService.getEstadisticas();
    }
    
}
