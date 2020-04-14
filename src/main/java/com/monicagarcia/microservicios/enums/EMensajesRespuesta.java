package com.monicagarcia.microservicios.enums;

public enum EMensajesRespuesta {
	
	
	SUCCESS("00", "Acción realizada con éxito", "Exitoso"),
	ERROR("01","Ocurrió un error al realizar la operación", "Error"),
	NO_EXISTEN_CLIENTES("02", "No existen clientes registrados", "Exitoso"),
	CLIENTE_NO_EXISTE_CONSULTAR("03", "No existe el cliente consultado", "Exitoso"),
	CAMPO_NO_RECIBIDO("04", "Campo no recibido", "Error"),
	CLIENTE_EXISTE("05", "No se pudo guardar el cliente, ya se encuentra registrado", "Error"),
	CLIENTE_NO_EXISTE_ELIMINAR("06", "El cliente a eliminar no existe", "Error"),
	NO_EXISTE_ADJUNTO_CLIENTE("07", "El cliente no tiene relacionado un adjunto", "Error"),
	NO_EXISTEN_ADJUNTOS("08", "No existen adjuntos en el sistema", "Error"),
	NO_EXISTE_TIPO_DOCUMENTO("09", "No existe el tipo de documento enviado", "Error");
	
	private String codigo;
	private String mensaje;
	private String estado;

	EMensajesRespuesta(String codigo, String mensaje, String estado) {
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.estado = estado;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
