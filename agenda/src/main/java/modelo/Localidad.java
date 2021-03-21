package modelo;

import java.util.List;
import dto.LocalidadDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LocalidadDAO;

public class Localidad {
	private LocalidadDAO localidad;

	public Localidad(DAOAbstractFactory metodo_persistencia) {
		this.localidad = metodo_persistencia.createLocalidadDAO();
	}

	public int agregarLocalidad(LocalidadDTO localidad) {
		return this.localidad.insert(localidad);
	}

	public void borrarLocalidad(LocalidadDTO localidad_a_eliminar) {
		this.localidad.delete(localidad_a_eliminar);
	}

	public void editarLocalidad(LocalidadDTO localidad_a_editar) {
		this.localidad.update(localidad_a_editar);
	}

	public LocalidadDTO obtenerLocalidad(int localidad) {
		return this.localidad.select(localidad);
	}

	public List<LocalidadDTO> obtenerTodasLocalidades() {
		return this.localidad.readAll();
	}

	public List<LocalidadDTO> dameLocalidadesDeLaProvincia(String idProvincia) {
		return this.localidad.dameLocalidadesDeLaProvincia(idProvincia);
	}
}
