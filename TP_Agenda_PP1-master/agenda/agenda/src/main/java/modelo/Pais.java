package modelo;

import java.util.List;
import dto.PaisDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PaisDAO;

public class Pais {
	private PaisDAO pais;

	public Pais(DAOAbstractFactory metodoPersistencia) {
		this.pais = metodoPersistencia.createPaisDAO();
	}

	public int agregarPais(PaisDTO nuevoPais) {
		return this.pais.insert(nuevoPais);
	}

	public void borrarPais(PaisDTO pais) {
		this.pais.delete(pais);
	}

	public void editarPais(PaisDTO pais) {
		this.pais.update(pais);
	}
	
	public List<PaisDTO> obtenerTodosPaises() {
		return this.pais.readAll();
	}

	public List<PaisDTO> obtenerPais(String paises) {
		return this.pais.selectPaises(paises);
	}
	
	public List<PaisDTO> selectPais(String pais) {
		return this.pais.selectPais(pais);
	}

	public PaisDTO selectById(int idPais) {
		return this.pais.selectById(idPais);
	}
}
