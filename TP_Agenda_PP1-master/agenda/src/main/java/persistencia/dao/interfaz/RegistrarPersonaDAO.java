package persistencia.dao.interfaz;

import java.util.List;
import dto.RegistrarPersonaDTO;

public interface RegistrarPersonaDAO {
	
	public boolean insert(RegistrarPersonaDTO persona);
	
	public boolean CrearUsuario(RegistrarPersonaDTO persona);
	

	public List<RegistrarPersonaDTO> readAll();

	public boolean delete(RegistrarPersonaDTO persona_a_borrar);
	
	public boolean updateUser(RegistrarPersonaDTO persona_a_actualizar);
	
	public List<RegistrarPersonaDTO> readUsers();
}
