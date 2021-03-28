package modelo;

import java.util.List;
import dto.ProvinciaDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.ProvinciaDAO;

public class Provincia {
	private ProvinciaDAO provincia;

	public Provincia(DAOAbstractFactory metodoPersistencia) {
		this.provincia = metodoPersistencia.createProvinciaDAO();
	}

	public int agregarProvincia(ProvinciaDTO nuevaProvincia) {
		return this.provincia.insert(nuevaProvincia);
	}

	public void borrarProvincia(ProvinciaDTO provincia) {
		this.provincia.delete(provincia);
	}

	public void editarProvincia(ProvinciaDTO provincia) {
		this.provincia.update(provincia);
	}
	
	public List<ProvinciaDTO> obtenerTodasLasProvincias() {
		return this.provincia.readAll();
	}
	
	public List<ProvinciaDTO> obtenerProvincia(String provincia) {
		return this.provincia.selectProvincias(provincia);
	}

	public ProvinciaDTO existeProvincia(int idPais, String provincia) {
		return this.provincia.existeProvincia(idPais, provincia);
	}

	public List<ProvinciaDTO> obtenerProvinciasDelPais(String idPais){
		return this.provincia.obtenerProvinciasDelPais(idPais);
	}

	public ProvinciaDTO selectById(int idProvincia) {
		return this.provincia.selectById(idProvincia);
	}
}
