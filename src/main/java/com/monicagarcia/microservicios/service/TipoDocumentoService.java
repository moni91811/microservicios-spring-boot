package com.monicagarcia.microservicios.service;

import com.monicagarcia.microservicios.model.TipoDocumento;

public interface TipoDocumentoService {
	
	/**
	 * Consultar el tipo de documento por código.
	 * @param codigo
	 * @return
	 */
	TipoDocumento getTipoDocumentoByCodigo(String codigo);

}
