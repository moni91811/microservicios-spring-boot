package com.monicagarcia.microservicios.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.monicagarcia.microservicios.exception.ServiceException;
import com.monicagarcia.microservicios.model.AdjuntoCliente;
import com.monicagarcia.microservicios.model.Cliente;
import com.monicagarcia.microservicios.model.TipoDocumento;
import com.monicagarcia.microservicios.rest.AdjuntoRestClient;
import com.monicagarcia.microservicios.service.AdjuntoClienteServiceImpl;
import com.monicagarcia.microservicios.service.ClienteServiceImpl;
import com.monicagarcia.microservicios.service.TipoDocumentoServiceImpl;
import com.monicagarcia.microservicios.util.UtilPropiedades;
import com.monicagarcia.microservicios.util.Util;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);
	 
	@Autowired
	ClienteServiceImpl clienteService;
	
	@Autowired
	AdjuntoClienteServiceImpl adjuntoClienteService;
	
	@Autowired
	TipoDocumentoServiceImpl tipoDocumentoService;
	
	@Autowired
	UtilPropiedades utilPropiedades;
	
	
	public Util util = new Util();
	
	/**
	 * Servicio para consultar todos los clientes.
	 * @return
	 * @throws ServiceException 
	 */
	@GetMapping()
	public ResponseEntity<RespuestaClienteDto> listaClientes() throws ServiceException{
		RespuestaClienteDto respuesta = new RespuestaClienteDto();
		//Consultar los clientes.
		List<Cliente> listaClientes = clienteService.findAllClientes();
		
		//Consultar el valor de la imagen.
		if(listaClientes != null && !listaClientes.isEmpty()) {
			listaClientes = llenarInformacionAdjuntosClientes(listaClientes);
			respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
			respuesta.setData(listaClientes);
		}else {
			respuesta = util.armarRespuesta(EMensajesRespuesta.NO_EXISTEN_CLIENTES);
			respuesta.setData(new ArrayList<Cliente>());
		}
		
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	/**
	 * Servicio para consultar por tipo y número de identificación.
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 * @throws ServiceException 
	 */
	@GetMapping("/{tipoIdentificacion}/{numeroIdentificacion}")
	public ResponseEntity<RespuestaClienteDto> consultarClienteTipoIdenNumeroIden(
			@Valid @PathVariable("tipoIdentificacion") String tipoIdentificacion,
			@Valid @PathVariable("numeroIdentificacion") String numeroIdentificacion) throws ServiceException {

		RespuestaClienteDto respuesta = new RespuestaClienteDto();
					
		//Consultar el cliente.
		Cliente cliente = clienteService.findByTipoIdenANdNumeroIden(tipoIdentificacion, numeroIdentificacion);
		if (cliente != null) {
			cliente = llenarInformacionAdjuntoCliente(cliente);
			cliente.setTipoIdentificacion(cliente.getTipoDocumento().getCodigo());
			respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
			respuesta.setData(cliente);
		} else {
			respuesta = util.armarRespuesta(EMensajesRespuesta.CLIENTE_NO_EXISTE_CONSULTAR);
			respuesta.setData(null);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	
	/**
	 * Servicio para consultar la lista de clientes que tienen una edad mayor a la enviada.
	 * @param edad
	 * @return
	 * @throws ServiceException 
	 */
	@GetMapping("/edad/{edad}")
	public ResponseEntity<RespuestaClienteDto> consultarClienteEdad(
			@Valid @PathVariable("edad") Integer edad) throws ServiceException {

		RespuestaClienteDto respuesta = new RespuestaClienteDto();
		//Consultar el cliente.
		List<Cliente> listaClientes = clienteService.findByEdad(edad);
		if (listaClientes != null && !listaClientes.isEmpty()) {
			listaClientes = llenarInformacionAdjuntosClientes(listaClientes);			
			respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
			respuesta.setData(listaClientes);
		} else {
			respuesta = util.armarRespuesta(EMensajesRespuesta.NO_EXISTEN_CLIENTES);
			respuesta.setData(new ArrayList<Cliente>());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	
	@PostMapping()
	public ResponseEntity<RespuestaClienteDto> guardarCliente(@RequestBody Cliente cliente) {
		RespuestaClienteDto respuesta = new RespuestaClienteDto();

		try {
			
			if(cliente.getTipoIdentificacion() == null || cliente.getNumeroIdentificacion() == null
					|| cliente.getTipoIdentificacion().equals("") || cliente.getNumeroIdentificacion().equals("")
					|| cliente.getNombres() == null || cliente.getNombres().equals("")
					|| cliente.getApellidos() == null || cliente.getApellidos().equals("")
					|| cliente.getFoto() == null || cliente.getFoto().equals("")) {
				respuesta = util.armarRespuesta(EMensajesRespuesta.CAMPO_NO_RECIBIDO);
				respuesta.setData(new Cliente());
				return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
			}
			
			//Consultar el tipo de documento.
			TipoDocumento tipoDocumento = tipoDocumentoService.getTipoDocumentoByCodigo(cliente.getTipoIdentificacion());
			if(tipoDocumento == null) {
				respuesta = util.armarRespuesta(EMensajesRespuesta.NO_EXISTE_TIPO_DOCUMENTO);
				respuesta.setData(null);
				return new ResponseEntity<>(respuesta, HttpStatus.OK);
			}
						
			Cliente clienteExiste = clienteService.findByTipoIdenANdNumeroIden(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());
			if (clienteExiste != null) {
				respuesta = util.armarRespuesta(EMensajesRespuesta.CLIENTE_EXISTE);
				respuesta.setData(cliente);
				return new ResponseEntity<>(respuesta, HttpStatus.OK);
			}
			
			cliente.setTipoDocumento(tipoDocumento);
			AdjuntoCliente adjuntoCliente = guardarInformacionAdjuntoCliente(cliente);
			cliente.setFoto(adjuntoCliente.getId());
			Cliente cli = clienteService.guardarCliente(cliente);
			
			respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
			cli.setFoto(adjuntoCliente.getAdjunto());
			cli.setTipoIdentificacion(cli.getTipoDocumento().getCodigo());
			respuesta.setData(cli);
			
		} catch (Exception exc) {
			respuesta = util.armarRespuesta(EMensajesRespuesta.ERROR);
			respuesta.setData(null);
			LOGGER.error("Error al guardar el cliente. "+exc);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	
	@PutMapping()
	public ResponseEntity<RespuestaClienteDto> actualizarCliente(@RequestBody Cliente cliente) {
		RespuestaClienteDto respuesta = new RespuestaClienteDto();

		try {
			
			if(cliente.getTipoIdentificacion() == null || cliente.getNumeroIdentificacion() == null
					|| cliente.getTipoIdentificacion().equals("") || cliente.getNumeroIdentificacion().equals("")
					|| cliente.getNombres() == null || cliente.getNombres().equals("")
					|| cliente.getApellidos() == null || cliente.getApellidos().equals("")
					|| cliente.getFoto() == null || cliente.getFoto().equals("")) {
				respuesta = util.armarRespuesta(EMensajesRespuesta.CAMPO_NO_RECIBIDO);
				respuesta.setData(new Cliente());
				return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
			}
			
			//Consultar el tipo de documento.
			TipoDocumento tipoDocumento = tipoDocumentoService.getTipoDocumentoByCodigo(cliente.getTipoIdentificacion());
			if(tipoDocumento == null) {
				respuesta = util.armarRespuesta(EMensajesRespuesta.NO_EXISTE_TIPO_DOCUMENTO);
				respuesta.setData(null);
				return new ResponseEntity<>(respuesta, HttpStatus.OK);
			}
			
			AdjuntoCliente adjuntoCliente = new AdjuntoCliente();
			adjuntoCliente.setAdjunto(cliente.getFoto());
			
			//Verificar si existe el cliente.
			Cliente clienteExiste = clienteService.findByTipoIdenANdNumeroIden(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());
			if (clienteExiste != null) {
				cliente.setIdCliente(clienteExiste.getIdCliente());
				adjuntoCliente.setId(clienteExiste.getFoto());
				adjuntoCliente = actualizarAdjuntoCliente(adjuntoCliente);
			}else {
				adjuntoCliente = guardarInformacionAdjuntoCliente(cliente);
			}
			
			cliente.setTipoDocumento(tipoDocumento);
			//Registrar o actualizar el cliente.	
			cliente.setFoto(adjuntoCliente.getId());
			Cliente cli = clienteService.guardarCliente(cliente);
			//Respuesta
			respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);
			cli.setFoto(adjuntoCliente.getAdjunto());
			cli.setTipoIdentificacion(cli.getTipoDocumento().getCodigo());
			respuesta.setData(cli);

		} catch (Exception exc) {
			respuesta = util.armarRespuesta(EMensajesRespuesta.ERROR);
			respuesta.setData(null);
			LOGGER.error("Error al actualizar el cliente. " + exc);
		}

		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	
	@DeleteMapping
	public ResponseEntity<RespuestaClienteDto> eliminarCliente(@RequestBody Cliente cliente) {
		RespuestaClienteDto respuesta = new RespuestaClienteDto();

		try {
			
			if(cliente.getTipoIdentificacion() == null || cliente.getNumeroIdentificacion() == null
				|| cliente.getTipoIdentificacion().equals("") || cliente.getNumeroIdentificacion().equals("")) {
				respuesta = util.armarRespuesta(EMensajesRespuesta.CAMPO_NO_RECIBIDO);
				respuesta.setData(null);
				return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
			}
			
			
			// Verificar si existe el cliente.
			Cliente clienteExiste = clienteService.findByTipoIdenANdNumeroIden(cliente.getTipoIdentificacion(),
					cliente.getNumeroIdentificacion());
			if (clienteExiste != null) {
				cliente.setIdCliente(clienteExiste.getIdCliente());
			} else {
				respuesta = util.armarRespuesta(EMensajesRespuesta.CLIENTE_NO_EXISTE_ELIMINAR);
				respuesta.setData(null);
				return new ResponseEntity<>(respuesta, HttpStatus.OK);
			}

			// Eliminar cliente.
			eliminarAdjuntoCliente(clienteExiste.getFoto());
			clienteService.eliminarCliente(cliente);
			respuesta = util.armarRespuesta(EMensajesRespuesta.SUCCESS);

		} catch (Exception exc) {
			respuesta = util.armarRespuesta(EMensajesRespuesta.ERROR);
			LOGGER.error("Error al eliminar el cliente. " + exc);
		}
		
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	
	/**
	 * Llenar información de los adjuntos de los clientes al consumir el servicio web.
	 * @param listaClientes
	 * @return
	 * @throws ServiceException
	 */
	private List<Cliente> llenarInformacionAdjuntosClientes(List<Cliente> listaClientes) throws ServiceException{
		
		//Consultar el adjunto del cliente para retornar la imagen.
		AdjuntoRestClient adjuntoClientRest = new AdjuntoRestClient(utilPropiedades.getUrlAdjuntos(), "");
		//Consultar la información de los adjuntos de los clientes.
		HashMap<String, AdjuntoCliente> mapaAdjuntos  = adjuntoClientRest.getConsultaAdjuntosClientes();
		
		for(Cliente cliente :listaClientes) {
			cliente.setTipoIdentificacion(cliente.getTipoDocumento().getCodigo());
			if(cliente.getFoto() != null) {
				if(mapaAdjuntos.get(cliente.getFoto()) != null) {
					cliente.setFoto(mapaAdjuntos.get(cliente.getFoto()).getAdjunto());
				}
			}
		}
		return listaClientes;
	}
	

	
	/**
	 * Llenar información del adjunto de un cliente al consumir el servicio web.
	 * @param cliente
	 * @return
	 * @throws ServiceException 
	 */
	private Cliente llenarInformacionAdjuntoCliente(Cliente cliente) throws ServiceException {
		//Consultar el adjunto del cliente para retornar la imagen.
		AdjuntoRestClient adjuntoClientRest = new AdjuntoRestClient(utilPropiedades.getUrlAdjuntos(), "");
        AdjuntoCliente adjunto = adjuntoClientRest.getConsultaAdjuntoCliente(cliente.getFoto());
		cliente.setFoto(adjunto == null ? null : adjunto.getAdjunto());
		return cliente;
	}
	
	/**
	 * Metodo para guardar la información de un adjunto para un cliente.
	 * @param cliente
	 * @return
	 * @throws ServiceException
	 */
	private AdjuntoCliente guardarInformacionAdjuntoCliente(Cliente cliente) throws ServiceException {
		// Consultar el adjunto del cliente para retornar la imagen.
		AdjuntoRestClient adjuntoClientRest = new AdjuntoRestClient(utilPropiedades.getUrlAdjuntos(), "");
		AdjuntoCliente adjunto = adjuntoClientRest.guardarAdjuntoCliente(cliente.getFoto());
		return adjunto;
	}
	
	
	/**
	 * Metodo para actualizar la información de un adjunto para un cliente.
	 * @param cliente
	 * @return
	 * @throws ServiceException
	 */
	private AdjuntoCliente actualizarAdjuntoCliente(AdjuntoCliente adjuntoCliente) throws ServiceException {
		// Consultar el adjunto del cliente para retornar la imagen.
		AdjuntoRestClient adjuntoClientRest = new AdjuntoRestClient(utilPropiedades.getUrlAdjuntos(), "");
		return adjuntoClientRest.actualizarAdjuntoCliente(adjuntoCliente);
	}
	
	/**
	 * Metodo para eliminar un adjunto para un cliente.
	 * @param cliente
	 * @return
	 * @throws ServiceException
	 */
	private boolean eliminarAdjuntoCliente(String id) throws ServiceException {
		// Consultar el adjunto del cliente para retornar la imagen.
		AdjuntoRestClient adjuntoClientRest = new AdjuntoRestClient(utilPropiedades.getUrlAdjuntos(), "");
		return adjuntoClientRest.eliminarAdjuntoCliente(id); 
	}

}
