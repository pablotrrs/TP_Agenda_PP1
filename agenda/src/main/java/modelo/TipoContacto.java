package modelo;

import java.util.List;
import dto.TipoContactoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.TipoContactoDAO;

public class TipoContacto {
	private TipoContactoDAO tipoContacto;

	public TipoContacto(DAOAbstractFactory metodo_persistencia) {
		this.tipoContacto = metodo_persistencia.createTipoContactoDAO();
	}

	public void agregarTipoContacto(TipoContactoDTO nuevoTipoContacto) {
		System.out.println(this.tipoContacto.insert(nuevoTipoContacto));
	}

	public void borrarTipoContacto(TipoContactoDTO tipoContacto_a_eliminar) {
		this.tipoContacto.delete(tipoContacto_a_eliminar);
	}

	public void editarTipoContacto(TipoContactoDTO tipoContacto_a_editar) {
		this.tipoContacto.update(tipoContacto_a_editar);
	}

	public TipoContactoDTO obtenerTipoContacto(int idTipoContacto) {
		return this.tipoContacto.select(idTipoContacto);
	}

	public List<TipoContactoDTO> obtenerTodosTiposContactos() {
		return this.tipoContacto.readAll();
	}
}
