package modelo;

import java.util.List;

import dto.LocalidadDTO;
import dto.TipoContactoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LocalidadDAO;

public class Localidad {
	
	private LocalidadDAO localidad;
	
	public Localidad(DAOAbstractFactory metodo_persistencia) {
		this.localidad = metodo_persistencia.createLocalidadDAO();
	}
	public void agregarLocalidad(LocalidadDTO localidad) {
		this.localidad.insert(localidad);
	}

	public void borrarLocalidad(LocalidadDTO localidad_a_eliminar) {
		this.localidad.delete(localidad_a_eliminar);
	}
	public void editarLocalidad(LocalidadDTO localidad_a_editar) {
		this.localidad.update(localidad_a_editar);
	}

	public List<LocalidadDTO> obtenerLocalidades() {
		return this.localidad.readAll();
	}
}
	


