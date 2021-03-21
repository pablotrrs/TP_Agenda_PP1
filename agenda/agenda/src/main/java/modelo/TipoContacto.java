package modelo;

import java.util.List;
import dto.TipoContactoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.TipoContactoDAO;

public class TipoContacto {
	private TipoContactoDAO tipoContacto;

	public TipoContacto(DAOAbstractFactory metodoPersistencia) {
		this.tipoContacto = metodoPersistencia.createTipoContactoDAO();
	}

	public void agregarTipoContacto(TipoContactoDTO nuevoTipoContacto) {
		this.tipoContacto.insert(nuevoTipoContacto);
	}

	public void borrarTipoContacto(TipoContactoDTO tipoContacto) {
		this.tipoContacto.delete(tipoContacto);
	}

	public void editarTipoContacto(TipoContactoDTO tipoContacto) {
		this.tipoContacto.update(tipoContacto);
	}

	public TipoContactoDTO obtenerTipoContacto(int idTipoContacto) {
		return this.tipoContacto.select(idTipoContacto);
	}

	public List<TipoContactoDTO> obtenerTodosLosTiposContactos() {
		return this.tipoContacto.readAll();
	}
}
