package com.meli.mutantes.challenge.model.projection;

/**
 * Representación en objeto DTO de los datos de las estadísticas.
 */
public interface Stats {
	public Integer getCountMutantDna();

	public Integer getCountHumanDna();

	public default Double getRatio() {
		Double countHumanDna = Double.valueOf(getCountHumanDna());
		return countHumanDna > 0 ? (Double.valueOf(getCountMutantDna()) / countHumanDna) : 0.0;
	};
}
