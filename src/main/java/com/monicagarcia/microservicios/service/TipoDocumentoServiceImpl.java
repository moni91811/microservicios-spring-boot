package com.monicagarcia.microservicios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monicagarcia.microservicios.model.TipoDocumento;
import com.monicagarcia.microservicios.repository.TipoDocumentoRepository;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
	
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;

	@Override
	public TipoDocumento getTipoDocumentoByCodigo(String codigo) {
		return tipoDocumentoRepository.findByCodigo(codigo);
	}

}
