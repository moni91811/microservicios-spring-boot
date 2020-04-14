package com.monicagarcia.microservicios.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.monicagarcia.microservicios.model.AdjuntoCliente;

@Repository
public interface AdjuntoClienteRepository extends MongoRepository<AdjuntoCliente, String> {

}
