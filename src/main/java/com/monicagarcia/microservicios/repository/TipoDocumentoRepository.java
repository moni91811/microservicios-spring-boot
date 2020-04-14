package com.monicagarcia.microservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monicagarcia.microservicios.model.TipoDocumento;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
	
	TipoDocumento findByCodigo(String codigo);

}
