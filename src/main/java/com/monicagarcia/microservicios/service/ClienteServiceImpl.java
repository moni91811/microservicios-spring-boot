package com.monicagarcia.microservicios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monicagarcia.microservicios.model.Cliente;
import com.monicagarcia.microservicios.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	ClienteRepository clienteRepository;
	
	/**
	 * Consultar la lista de clientes.
	 * @return
	 */
	public List<Cliente> findAllClientes() {
		return clienteRepository.findAll();
	}
	
	/**
	 * Consultar un cliente por tipo de identificación y numero de identificación.
	 * @param tipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public Cliente findByTipoIdenANdNumeroIden(String tipoIdentificacion, String numeroIdentificacion) {
		return clienteRepository.findByTipoIdenAndNumeroIden(tipoIdentificacion, numeroIdentificacion);
	}
	
	/**
	 * Consultar los clientes por la edad.
	 * @param edad
	 * @return
	 */
	public List<Cliente> findByEdad(Integer edad) {
		return clienteRepository.findByEdad(edad);
	}
	
	/**
	 * Guardar el cliente.
	 * @param cliente
	 * @return
	 */
	public Cliente guardarCliente(Cliente cliente) {
		Cliente cli = clienteRepository.save(cliente);
		return cli;
	}
	
	/**
	 * Eliminar el cliente
	 * @param cliente
	 */
	public void eliminarCliente(Cliente cliente) {
		clienteRepository.delete(cliente);
	}

}
