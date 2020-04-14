package com.monicagarcia.microservicios.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "adjunto_cliente")
public class AdjuntoCliente {
	
	@Id
	private String id;
		
	@Field(value="adjunto")
	private String adjunto;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the adjunto
	 */
	public String getAdjunto() {
		return adjunto;
	}

	/**
	 * @param adjunto the adjunto to set
	 */
	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}
	
	
	
	
	
	

}
