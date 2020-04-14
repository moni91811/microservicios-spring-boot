package com.monicagarcia.microservicios.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monicagarcia.microservicios.dto.RespuestaClienteDto;
import com.monicagarcia.microservicios.enums.EMensajesRespuesta;
import com.monicagarcia.microservicios.exception.ServiceException;
import com.monicagarcia.microservicios.model.AdjuntoCliente;


public class AdjuntoRestClient extends AbstractRestClient {

	public AdjuntoRestClient(String url, String contextPath) {
		super(url, contextPath);
	}
	
	/**
	 * Método para consultar el adjunto de un cliente.
	 * @return
	 * @throws ServiceException
	 */
	public AdjuntoCliente getConsultaAdjuntoCliente(String id) throws ServiceException {
		
		ObjectMapper mapper = new ObjectMapper();
		WebTarget client = createClient(id);
		Response response = client.request(MediaType.APPLICATION_JSON).get();
		AdjuntoCliente adjunto = null;
		Integer status = response.getStatus();
		
		//verificar el estado del mensaje.
		if (Status.OK.getStatusCode() == status) {
			RespuestaClienteDto resp = new RespuestaClienteDto();
			resp = response.readEntity(RespuestaClienteDto.class);
			if(resp != null && resp.getCodigo().equals(EMensajesRespuesta.SUCCESS.getCodigo()) 
					&& resp.getData() != null) {
				adjunto = mapper.convertValue(resp.getData(), AdjuntoCliente.class);
			}
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		
		return adjunto;
	}
	
	
	/**
	 * Método para consultar los adjuntos existentes.
	 * @return
	 * @throws ServiceException
	 */
	public HashMap<String, AdjuntoCliente> getConsultaAdjuntosClientes() throws ServiceException {
		
		ObjectMapper mapper = new ObjectMapper();
		WebTarget client = createClient("");
		Response response = client.request(MediaType.APPLICATION_JSON).get();
		List<AdjuntoCliente> listAdjuntosClientes = new ArrayList<AdjuntoCliente>();
		HashMap<String, AdjuntoCliente> mapaAdjuntosClientes = new HashMap<>();
		 
		Integer status = response.getStatus();
		//verificar el estado del mensaje.
		if (Status.OK.getStatusCode() == status) {
			RespuestaClienteDto resp = new RespuestaClienteDto();
			resp = response.readEntity(RespuestaClienteDto.class);
			
			if(resp != null && resp.getCodigo().equals(EMensajesRespuesta.SUCCESS.getCodigo()) 
					&& resp.getData() != null) {
				
				listAdjuntosClientes = mapper.convertValue(resp.getData(), new TypeReference<List<AdjuntoCliente>>() { });
				if(!listAdjuntosClientes.isEmpty()) {
					for(AdjuntoCliente adjunto : listAdjuntosClientes) {
						mapaAdjuntosClientes.put(adjunto.getId(), adjunto);
					}
				}
			}
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		
		return mapaAdjuntosClientes;
	}
	
	/**
	 * Metodo para guardar un adjunto.
	 * @param adjuntoCliente
	 * @return
	 * @throws ServiceException
	 */
	public AdjuntoCliente guardarAdjuntoCliente(String fotoCliente) throws ServiceException {
		
		ObjectMapper mapper = new ObjectMapper();
		AdjuntoCliente adjuntoCliente = new AdjuntoCliente();
		adjuntoCliente.setAdjunto(fotoCliente);
		
		WebTarget target = createClient("");
		Response response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN_TYPE).post(Entity.json(adjuntoCliente), Response.class);
		Integer status = response.getStatus();
		
		//verificar el estado del mensaje.
		if (Status.OK.getStatusCode() == status) {
			RespuestaClienteDto resp = new RespuestaClienteDto();
			resp = response.readEntity(RespuestaClienteDto.class);
			if(resp != null && resp.getCodigo().equals(EMensajesRespuesta.SUCCESS.getCodigo()) 
					&& resp.getData() != null) {
				adjuntoCliente = mapper.convertValue(resp.getData(), AdjuntoCliente.class);
			}
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		
		return adjuntoCliente;
	}
	
	
	/**
	 * Metodo para actualizar un adjunto.
	 * @param adjuntoCliente
	 * @return
	 * @throws ServiceException
	 */
	public AdjuntoCliente actualizarAdjuntoCliente(AdjuntoCliente adjuntoCliente) throws ServiceException {
		
		ObjectMapper mapper = new ObjectMapper();
				
		WebTarget target = createClient("");
		Response response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN_TYPE).put(Entity.json(adjuntoCliente), Response.class);
		Integer status = response.getStatus();
		
		//verificar el estado del mensaje.
		if (Status.OK.getStatusCode() == status) {
			RespuestaClienteDto resp = new RespuestaClienteDto();
			resp = response.readEntity(RespuestaClienteDto.class);
			if(resp != null && resp.getCodigo().equals(EMensajesRespuesta.SUCCESS.getCodigo()) 
					&& resp.getData() != null) {
				adjuntoCliente = mapper.convertValue(resp.getData(), AdjuntoCliente.class);
			}
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		
		return adjuntoCliente;
	}
	
	
	/**
	 * Metodo para eliminar un adjunto.
	 * @param adjuntoCliente
	 * @return
	 * @throws ServiceException
	 */
	public boolean eliminarAdjuntoCliente(String id) throws ServiceException {
		
		boolean rta = false;	
		WebTarget target = createClient(id);
		Response response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN_TYPE).delete();
		Integer status = response.getStatus();
		
		//verificar el estado del mensaje.
		if (Status.OK.getStatusCode() == status) {
			RespuestaClienteDto resp = new RespuestaClienteDto();
			resp = response.readEntity(RespuestaClienteDto.class);
			if(resp != null && resp.getCodigo().equals(EMensajesRespuesta.SUCCESS.getCodigo())) {
				rta = true;
			}
		} else {
			throw new ServiceException(response.readEntity(String.class), status);
		}
		return rta;
	}

}
