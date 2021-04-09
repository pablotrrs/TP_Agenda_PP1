package modelo;

import java.util.List;
import dto.RegistrarPersonaDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.RegistrarPersonaDAO;

public class RegistrarPersona {
	
	RegistrarPersonaDAO registrarPersona;
	
	
	public RegistrarPersona(DAOAbstractFactory metodoPersistencia) {
		this.registrarPersona = metodoPersistencia.createRegistrarPersonaDAO();
	}

	public boolean agregarPersonaRegistrada(RegistrarPersonaDTO nuevoRegistro) {
		return this.registrarPersona.insert(nuevoRegistro);
	}
	public boolean crearUsuario(RegistrarPersonaDTO nuevoRegistro) {
		return this.registrarPersona.CrearUsuario(nuevoRegistro);
	}
	public boolean update(RegistrarPersonaDTO actualizar) {
		return this.registrarPersona.updateUser(actualizar);
	}

	public List<RegistrarPersonaDTO> obtenerTodosLosRegistrados() {
		return this.registrarPersona.readAll();
	}
	public boolean eliminarUsuario(RegistrarPersonaDTO user) {
		return this.registrarPersona.delete(user);
	}
	public List<RegistrarPersonaDTO> obtenerTodosLosUsers() {
		return this.registrarPersona.readUsers();
	}
}
