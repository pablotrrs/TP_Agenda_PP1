/**
 * 
 */
package persistencia.dao.mysql;

import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LocalidadDAO;
import persistencia.dao.interfaz.PaisDAO;
import persistencia.dao.interfaz.PersonaDAO;
import persistencia.dao.interfaz.ProvinciaDAO;
import persistencia.dao.interfaz.RegistrarPersonaDAO;
import persistencia.dao.interfaz.TipoContactoDAO;

public class DAOSQLFactory implements DAOAbstractFactory {
	/*
	 * (non-Javadoc)
	 * 
	 * @see persistencia.dao.interfaz.DAOAbstractFactory#createPersonaDAO()
	 */
	public PersonaDAO createPersonaDAO() {
		return new PersonaDAOSQL();
	}

	public TipoContactoDAO createTipoContactoDAO() {
		return new TipoContactoDAOSQL();
	}

	public LocalidadDAO createLocalidadDAO() {
		return new LocalidadDAOSQL();
	}

	public PaisDAO createPaisDAO() {
		return new PaisDAOSQL();
	}

	public ProvinciaDAO createProvinciaDAO() {
		return new ProvinciaDAOSQL();
	}

	
	public RegistrarPersonaDAO createRegistrarPersonaDAO() {
		
		return new RegistrarPersonaDAOSQL();
	}
}
