package persistencia.dao.interfaz;

import dto.LocalidadDTO;

public interface DAOAbstractFactory 
{
	public PersonaDAO createPersonaDAO();
	
	public TipoContactoDAO createTipoContactoDAO();

	public LocalidadDAO createLocalidadDAO();
}
