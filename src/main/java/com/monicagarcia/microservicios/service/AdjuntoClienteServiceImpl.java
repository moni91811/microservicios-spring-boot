package com.monicagarcia.microservicios.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monicagarcia.microservicios.model.AdjuntoCliente;
import com.monicagarcia.microservicios.repository.AdjuntoClienteRepository;

@Service
public class AdjuntoClienteServiceImpl implements AdjuntoClienteService {
	
	@Autowired
	AdjuntoClienteRepository adjuntoClienteRepository;

	@Override
	public AdjuntoCliente guardarAdjuntoCliente(AdjuntoCliente adjunto) {
		return adjuntoClienteRepository.save(adjunto);
	}

	@Override
	public AdjuntoCliente consultarAdjuntoCliente(String id) {
		return adjuntoClienteRepository.findById(id).get();
	}

	@Override
	public List<AdjuntoCliente> consultarListaAdjuntos() {	
		return adjuntoClienteRepository.findAll();
	}
	
	@Override
	public HashMap<String, AdjuntoCliente> consultarMapaAdjuntos() {	
		List<AdjuntoCliente> listaAdjuntos = adjuntoClienteRepository.findAll();
		
		HashMap<String, AdjuntoCliente> mapaAdjuntos = new HashMap<>();
		for(AdjuntoCliente adjunto :listaAdjuntos) {
			mapaAdjuntos.put(adjunto.getId(), adjunto);
		}
		return mapaAdjuntos;
	}

	@Override
	public boolean eliminarAdjunto(String id) {
		AdjuntoCliente adjunto = new AdjuntoCliente();
		adjunto.setId(id);
		adjuntoClienteRepository.delete(adjunto);
		return true;
	}

}
