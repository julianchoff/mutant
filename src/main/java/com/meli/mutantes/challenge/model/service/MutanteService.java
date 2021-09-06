package com.meli.mutantes.challenge.model.service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.mutantes.challenge.common.ApiException;
import com.meli.mutantes.challenge.controller.dtos.MutanteDTO;
import com.meli.mutantes.challenge.model.entity.Mutantes;
import com.meli.mutantes.challenge.model.projection.Stats;
import com.meli.mutantes.challenge.model.repository.IMutanteRepository;

@Service
public class MutanteService {

	@Autowired
	private IMutanteRepository mutanteRepository;

	/**
	 * Valida si el ADN enviado es de un mutante.
	 *
	 * @param dna ADN para validar.
	 * @return boolean
	 * @throws ApiException
	 */
	@Transactional
	public boolean validarAdn(MutanteDTO mutanteDto) throws ApiException {
		validarEstructuraCadena(mutanteDto.getDna());
		
		boolean esMutante = esMutante(mutanteDto.getDna());
		String dna = Arrays.toString(mutanteDto.getDna());
		Mutantes mutante = new Mutantes();
		mutante.setAdn(dna);
		mutante.setMutante(esMutante);
		
		if (!mutanteRepository.existsById(dna)) {
			mutanteRepository.save(mutante);
		}

		return esMutante;
	}

	/**
	 * Valida si el ADN enviado tiene una estructura correcta.
	 *
	 * @param dna ADN para validar.
	 * @return boolean
	 * @throws ApiException
	 */
	private void validarEstructuraCadena(String[] dna) throws ApiException {
		Pattern pat = Pattern.compile("(A|T|C|G)+");
		for (String c : dna) {
			Matcher mat = pat.matcher(c);
			if (!mat.matches()) {
				throw new ApiException("DNA invalid");
			}
		}
	}

	/**
	 * Valida si el ADN enviado es de un mutante.
	 *
	 * @param dna ADN para validar.
	 * @return boolean
	 * @throws ApiException
	 */
	private boolean esMutante(String[] dna) throws ApiException {
		String[][] dnaMatriz = null;
		boolean mutante = false;
		dnaMatriz = crearMatriz(dna);
		mutante = validarDnaMatriz(dnaMatriz);
		return mutante;
	}

	/**
	 * Crea una matriz de dimensiones N*N .
	 *
	 * @param dna dna ADN a analizar.
	 * @return String[][]
	 * @throws ApiException
	 */
	private String[][] crearMatriz(String[] dna) throws ApiException {

		int dnaLength = dna.length;
		String[][] dnaMatriz = new String[dnaLength][dnaLength];
		for (int fila = 0; fila < dnaLength; fila++) {
			String[] dnaFila = dna[fila].split("");
			if (dnaFila.length == dnaLength) {
				System.arraycopy(dnaFila, 0, dnaMatriz[fila], 0, dnaLength);
			} else {
				throw new ApiException("Error al momento de crear la Matriz");
			}

		}
		return dnaMatriz;
	}

	/**
	 * Valida la matriz de forma oblicua, horizontal o vertical para identificar si
	 * el ADN es de un mutante.
	 *
	 * @param dnaMatriz matriz con el ADN a analizar.
	 * @return boolean
	 */
	private boolean validarDnaMatriz(String[][] dnaMatriz) {

		int matrixDnaLength = dnaMatriz.length;
		boolean esMutante = false;

		// Horizontal
		for (int fila = 0; fila < matrixDnaLength; fila++) {
			String[] arrayOfStringsHorizontal = new String[matrixDnaLength];
			for (int columna = 0; columna < matrixDnaLength; columna++) {
				arrayOfStringsHorizontal[columna] = dnaMatriz[fila][columna];
			}
			esMutante = validarSecuencia(arrayOfStringsHorizontal);
			if (esMutante) {
				esMutante = true;
				break;
			}
		}

		// Vertical
		if (!esMutante) {
			for (int columna = 0; columna < matrixDnaLength; columna++) {
				String[] arrayOfStringsVertical = new String[matrixDnaLength];
				for (int fila = 0; fila < matrixDnaLength; fila++) {
					arrayOfStringsVertical[fila] = dnaMatriz[fila][columna];
				}
				esMutante = validarSecuencia(arrayOfStringsVertical);
				if (esMutante) {
					esMutante = true;
					break;
				}
			}
		}

		if (!esMutante) {
			// Oblicua
			String[] arrayOfStringsDiagonal = null;
			int contador = 0;
			for (
					// Recorre los inicios de cada diagonal en los bordes de la matriz.
					Integer diagonal = 1 - matrixDnaLength; // Comienza con un número negativo.
					diagonal <= matrixDnaLength - 1; // Mientras no llegue a la última diagonal.
					diagonal += 1 // Avanza hasta el comienzo de la siguiente diagonal.
					) {
				contador = 0;
				arrayOfStringsDiagonal = new String[matrixDnaLength];
				for (
						// Recorre cada una de las diagonales a partir del extremo superior izquierdo.
						Integer vertical = Math.max(0,
								diagonal), horizontal = -Math.min(0, diagonal); vertical < matrixDnaLength
										&& horizontal < matrixDnaLength; // Mientras no excedan los límites.
						vertical += 1, horizontal += 1 // Avanza en diagonal incrementando ambos ejes.
						) {
					arrayOfStringsDiagonal[contador] = dnaMatriz[vertical][horizontal];
					contador++;
				}
				String[] tmp = null;
				tmp = Arrays.asList(arrayOfStringsDiagonal).stream().filter(a -> a != null).map(m -> m.toString())
						.collect(Collectors.toList()).toArray(new String[0]);
				if (tmp.length >= 4) {
					esMutante = validarSecuencia(tmp);
					if (esMutante) {
						esMutante = true;
						break;
					}
				}
			}
		}

		return esMutante;
	}

	/**
	 * Valida si la cadena posee alguna de las secuencias que dicen si es mutante.
	 *
	 * @param cadena String con parte de la matriz de ADN.
	 */
	private boolean validarSecuencia(String[] cadena) {

		String arrayToString = Arrays.stream(cadena).collect(Collectors.joining());
		boolean mutante = false;
		if (arrayToString.contains("AAAA")) {
			mutante = true;
		} else if (arrayToString.contains("TTTT")) {
			mutante = true;
		} else if (arrayToString.contains("CCCC")) {
			mutante = true;
		} else if (arrayToString.contains("GGGG")) {
			mutante = true;
		}

		return mutante;
	}

	/**
	 * Obtiene el resultado de las estadísticas de las validaciones realizadas.
	 *
	 *
	 */
	public Stats getEstadisticas() {
		return mutanteRepository.getStats();
	}

}
