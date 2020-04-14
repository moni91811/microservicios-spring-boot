package com.monicagarcia.microservicios.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UtilPropiedades {
	
	@Value("${adjuntos.url}")
	private String urlAdjuntos;

	/**
	 * @return the urlAdjuntos
	 */
	public String getUrlAdjuntos() {
		return urlAdjuntos;
	}

	/**
	 * @param urlAdjuntos the urlAdjuntos to set
	 */
	public void setUrlAdjuntos(String urlAdjuntos) {
		this.urlAdjuntos = urlAdjuntos;
	}
	
	
	

}
