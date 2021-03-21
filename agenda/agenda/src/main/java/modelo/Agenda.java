package modelo;

import java.util.List;
import dto.PersonaDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PersonaDAO;

public class Agenda {
	private PersonaDAO persona;

	public Agenda(DAOAbstractFactory metodoPersistencia) {
		this.persona = metodoPersistencia.createPersonaDAO();
	}

	public void agregarPersona(PersonaDTO nuevaPersona) {
		this.persona.insert(nuevaPersona);
	}

	public void editarPersona(PersonaDTO persona) {
		this.persona.update(persona);
	}

	public void borrarPersona(PersonaDTO persona) {
		this.persona.delete(persona);
	}

	public List<PersonaDTO> obtenerTodasLasPersonas() {
		return this.persona.readAll();
	}
}
