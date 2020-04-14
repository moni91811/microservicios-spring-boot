package com.monicagarcia.microservicios.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monicagarcia.microservicios.dto.RespuestaClienteDto;
import com.monicagarcia.microservicios.enums.EMensajesRespuesta;
import com.monicagarcia.microservicios.model.AdjuntoCliente;
import com.monicagarcia.microservicios.service.AdjuntoClienteService;
import com.monicagarcia.microservicios.util.Util;

@RestController
@RequestMapping("/api/adjunto")
public class AdjuntoClienteController {
	
	
	@Autowired
	AdjuntoClienteService adjuntoClienteService;
	
	public Util util = new Util();
	
	/**
	 * Guardar la información del adjunto del cliente.
	 * @param adjunto
	 * @return
	 */
	@PostMapping
	public ResponseEntity<RespuestaClienteDto> guardarAdjuntoCliente(@RequestBody AdjuntoCliente adjunto) {
		adjunto = adjuntoClienteService.guardarAdjuntoCliente(adjunto);
		
		RespuestaClienteDto respuesta = new RespuestaClienteDto();
		respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
		respuesta.setData(adjunto);
		
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	/**
	 * Actualizar la información del adjunto del cliente.
	 * @param adjunto
	 * @return
	 */
	@PutMapping
	public ResponseEntity<RespuestaClienteDto> actualizarAdjuntoCliente(@RequestBody AdjuntoCliente adjunto) {
		
		AdjuntoCliente adjuntoExistente = adjuntoClienteService.consultarAdjuntoCliente(adjunto.getId());
		if(adjuntoExistente != null) {
			adjunto.setId(adjuntoExistente.getId());
		}
		
		//Guardar o actualizar el adjunto.
		adjunto = adjuntoClienteService.guardarAdjuntoCliente(adjunto);
		
		RespuestaClienteDto respuesta = new RespuestaClienteDto();
		respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
		respuesta.setData(adjunto);
		
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	/**
	 * Consultar la información del adjunto del cliente.
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<RespuestaClienteDto> consultarAdjuntoCliente(@PathVariable("id") String id) {
		RespuestaClienteDto respuesta = new RespuestaClienteDto();
		AdjuntoCliente adjunto = adjuntoClienteService.consultarAdjuntoCliente(id);
		if(adjunto == null) {
			respuesta = util.armarRespuesta(EMensajesRespuesta.NO_EXISTE_ADJUNTO_CLIENTE);
			respuesta.setData(null);
		}else {
			respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
			respuesta.setData(adjunto);
		}
		
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	
	/**
	 * Consultar la información de todos los adjuntos de los clientes.
	 * @return
	 */
	@GetMapping
	public ResponseEntity<RespuestaClienteDto> consultarAdjuntosClientes() {
		
		List<AdjuntoCliente> listAdjuntos = adjuntoClienteService.consultarListaAdjuntos();
		
		RespuestaClienteDto respuesta = new RespuestaClienteDto();	
		if(listAdjuntos == null || listAdjuntos.isEmpty()) {
			respuesta = util.armarRespuesta(EMensajesRespuesta.NO_EXISTEN_ADJUNTOS);
			respuesta.setData(new ArrayList<AdjuntoCliente>());
		}else {
			respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
			respuesta.setData(listAdjuntos);
		}
		
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	
	/**
	 * Eliminar la información del adjunto del cliente.
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<RespuestaClienteDto> eliminarAdjuntoCliente(@PathVariable("id") String id) {
		RespuestaClienteDto respuesta = new RespuestaClienteDto();
		boolean rta = adjuntoClienteService.eliminarAdjunto(id);
		if(rta) {
			respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
			respuesta.setData(null);
		}else {
			respuesta = util.armarRespuesta(EMensajesRespuesta.ERROR);
			respuesta.setData(null);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
}
