package com.meli.mutantes.challenge.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.meli.mutantes.challenge.model.entity.Mutantes;
import com.meli.mutantes.challenge.model.projection.Stats;

@Repository
public interface IMutanteRepository extends JpaRepository<Mutantes, String> {

	@Query(value = "SELECT IFNULL(SUM(CASE WHEN mutante = true THEN 1 ELSE 0 END),0) AS countMutantDna, "
			+ "IFNULL(SUM(CASE WHEN mutante = false THEN 1 ELSE 0 END),0) AS countHumanDna FROM mutantes", nativeQuery = true)
	Stats getStats();
}
