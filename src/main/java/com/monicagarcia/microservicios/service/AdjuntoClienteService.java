package com.monicagarcia.microservicios.service;

import java.util.HashMap;
import java.util.List;

import com.monicagarcia.microservicios.model.AdjuntoCliente;

public interface AdjuntoClienteService {
	
	/**
	 * Guardar el adjunto del cliente.
	 * @param adjunto
	 * @return
	 */
	public AdjuntoCliente guardarAdjuntoCliente(AdjuntoCliente adjunto);
	
	/**
	 * Consultar el adjunto del cliente.
	 * @param id
	 * @return
	 */
	public AdjuntoCliente consultarAdjuntoCliente(String id);
	
	/**
	 * Consultar la lista de adjuntos.
	 * @return
	 */
	public HashMap<String, AdjuntoCliente> consultarMapaAdjuntos();
	
	/**
	 * Consultar la lista de adjuntos.
	 * @return
	 */
	public List<AdjuntoCliente> consultarListaAdjuntos();
	
	/**
	 * Eliminar el adjunto
	 * @param id
	 * @return
	 */
	public boolean eliminarAdjunto(String id);

}
