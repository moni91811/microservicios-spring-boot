package com.monicagarcia.microservicios.service;

import java.util.List;

import com.monicagarcia.microservicios.model.Cliente;

public interface ClienteService {

	/**
	 * Consultar la lista de clientes.
	 * @return
	 */
	public List<Cliente> findAllClientes();
	
	/**
	 * Consultar un cliente por tipo de identificación y numero de identificación.
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public Cliente findByTipoIdenANdNumeroIden(String tipoIdentificacion, String numeroIdentificacion);
	
	/**
	 * Consultar los clientes por la edad.
	 * @param edad
	 * @return
	 */
	public List<Cliente> findByEdad(Integer edad);
	
	/**
	 * Guardar el cliente.
	 * @param cliente
	 * @return
	 */
	public Cliente guardarCliente(Cliente cliente);
	
	/**
	 * Eliminar el cliente
	 * @param cliente
	 */
	public void eliminarCliente(Cliente cliente);
}
