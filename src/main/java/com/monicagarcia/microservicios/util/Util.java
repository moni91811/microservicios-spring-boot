package com.monicagarcia.microservicios.util;

import java.util.List;

import com.monicagarcia.microservicios.dto.RespuestaClienteDto;
import com.monicagarcia.microservicios.enums.EMensajesRespuesta;

public class Util {
	
	
	/**
	 * Validar si es null o vacio.
	 * @param cadena
	 * @return
	 */
	public boolean isNullOrEmpty(String cadena) {
		boolean rta = false;

		if (cadena == null || cadena.isEmpty()) {
			rta = true;
		}
		return rta;
	}
	
	/**
	 * Validar si es null o vacio.
	 * @param listCadena
	 * @return
	 */
	public boolean isNullOrEmpty(List<String> listCadena) {
		boolean rta = false;

		if (listCadena == null || listCadena.isEmpty()) {
			rta = true;
		}
		return rta;
	}

	

	
	/**
	 * Armar respuesta
	 * @return
	 */
	public RespuestaClienteDto armarRespuesta(EMensajesRespuesta mensajeRespuesta) {
		RespuestaClienteDto respuesta = new RespuestaClienteDto();
		respuesta.setEstado(mensajeRespuesta.getEstado());
		respuesta.setCodigo(mensajeRespuesta.getCodigo());
		respuesta.setMensaje(mensajeRespuesta.getMensaje());
		return respuesta;
	}
}
