package com.meli.mutantes.challenge.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad que representa el análisis de un ADN y si es o no mutante.
 */
@Entity
@Table(name="mutantes")
public class Mutantes implements Serializable {
	
	@Id
	private String adn;
	
	private boolean mutante;

	public Mutantes() {
		super();
	}

	public Mutantes(String adn, boolean mutante) {
		super();
		this.adn = adn;
		this.mutante = mutante;
	}

	public String getAdn() {
		return adn;
	}

	public void setAdn(String adn) {
		this.adn = adn;
	}
	
	public boolean getMutante() {
		return mutante;
	}

	public void setMutante(boolean mutante) {
		this.mutante = mutante;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
