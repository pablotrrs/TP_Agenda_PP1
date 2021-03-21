package modelo;

import java.util.List;
import dto.ProvinciaDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.ProvinciaDAO;

public class Provincia {
	private ProvinciaDAO provincia;

	public Provincia(DAOAbstractFactory metodo_persistencia) {
		this.provincia = metodo_persistencia.createProvinciaDAO();
	}

	public int agregarProvincia(ProvinciaDTO provincia) {
		return this.provincia.insert(provincia);
	}

	public void borrarProvincia(ProvinciaDTO provincia_a_eliminar) {
		this.provincia.delete(provincia_a_eliminar);
	}

	public void editarProvincia(ProvinciaDTO provincia_a_editar) {
		this.provincia.update(provincia_a_editar);
	}
	
	public List<ProvinciaDTO> obtenerProvincia(String provincia) {
		return this.provincia.select(provincia);
	}

	public List<ProvinciaDTO> obtenerTodasProvincias() {
		return this.provincia.readAll();
	}

	public List<ProvinciaDTO> dameProvinciasDelPais(String idPais){
		return this.provincia.dameProvinciasDelPais(idPais);
	}
}
