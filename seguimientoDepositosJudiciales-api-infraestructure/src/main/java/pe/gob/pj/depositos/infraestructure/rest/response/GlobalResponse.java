package pe.gob.pj.depositos.infraestructure.rest.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class GlobalResponse implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String descripcion;
	private String codigoOperacion;
	private Object data;

}
