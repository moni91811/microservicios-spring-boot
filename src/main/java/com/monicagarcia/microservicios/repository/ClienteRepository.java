package com.monicagarcia.microservicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monicagarcia.microservicios.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	/**
	 * Consulta por tipo y numero de identificacion.
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	 @Query("select c from Cliente c where c.tipoDocumento.codigo = :tipoIdentificacion and c.numeroIdentificacion = :numeroIdentificacion")
	 Cliente findByTipoIdenAndNumeroIden(String tipoIdentificacion, String numeroIdentificacion);
	 
	 
	 @Query("select c from Cliente c where c.edad >= :edad")
	 List<Cliente> findByEdad(Integer edad);
	
	 
}
