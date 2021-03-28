package modelo;

import java.util.List;
import dto.LocalidadDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LocalidadDAO;

public class Localidad {
	private LocalidadDAO localidad;

	public Localidad(DAOAbstractFactory metodoPersistencia) {
		this.localidad = metodoPersistencia.createLocalidadDAO();
	}

	public int agregarLocalidad(LocalidadDTO nuevaLocalidad) {
		return this.localidad.insert(nuevaLocalidad);
	}

	public void borrarLocalidad(LocalidadDTO localidad) {
		this.localidad.delete(localidad);
	}

	public void editarLocalidad(LocalidadDTO localidad) {
		this.localidad.update(localidad);
	}
	
	public List<LocalidadDTO> obtenerTodasLocalidades() {
		return this.localidad.readAll();
	}

	public LocalidadDTO obtenerLocalidad(String cp) {
		return this.localidad.select(cp);
	}

	public List<LocalidadDTO> obtenerLocalidadesDeLaProvincia(String idProvincia) {
		return this.localidad.dameLocalidadesDeLaProvincia(idProvincia);
	}

	public boolean existeLocalidad(String localidad, int provincia) {
		return this.localidad.selectByName(localidad, provincia);
	}
}
