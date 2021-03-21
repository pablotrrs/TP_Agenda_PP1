package modelo;

import java.util.List;
import dto.PaisDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PaisDAO;

public class Pais {
	private PaisDAO pais;

	public Pais(DAOAbstractFactory metodo_persistencia) {
		this.pais = metodo_persistencia.createPaisDAO();
	}

	public int agregarPais(PaisDTO provincia) {
		return this.pais.insert(provincia);
	}

	public void borrarPais(PaisDTO provincia_a_eliminar) {
		this.pais.delete(provincia_a_eliminar);
	}

	public void editarPais(PaisDTO provincia_a_editar) {
		this.pais.update(provincia_a_editar);
	}

	public List<PaisDTO> obtenerPais(String provincias) {
		return this.pais.select(provincias);
	}

	public List<PaisDTO> obtenerTodosPaises() {
		return this.pais.readAll();
	}
}
