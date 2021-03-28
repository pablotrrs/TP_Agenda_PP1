package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PersonaDAO;
import dto.PersonaDTO;

public class PersonaDAOSQL implements PersonaDAO {
	private static final String insert = "INSERT INTO personas(idPersona, usuario, nombre, telefono, calle, piso, altura, depto, email, fechaCumpleanios, idTipoContacto, idLocalidad) VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
	private static final String update = "UPDATE personas SET usuario = ? , nombre = ?, telefono = ?, calle = ?, piso = ?,altura = ?, depto = ?,  email = ?, fechaCumpleanios = ?, idTipoContacto = ?, idLocalidad = ? WHERE idPersona = ?";
	private static final String delete = "DELETE FROM personas WHERE idPersona = ?";
	private static final String readall = "SELECT * FROM personas";
	private static final String readUsuarios = "SELECT * FROM personas WHERE usuario = ?";

	public boolean insert(PersonaDTO persona) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;

		try {
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, persona.getIdPersona());
			statement.setString(2, persona.getNombreUsuario());
			statement.setString(3, persona.getNombre());
			statement.setString(4, persona.getTelefono());
			statement.setString(5, persona.getCalle());
			statement.setString(6, persona.getPiso());
			statement.setString(7, persona.getAltura());
			statement.setString(8, persona.getDepto());
			statement.setString(9, persona.getEmail());
			statement.setString(10, persona.getFechaCumpleanios());
			if (persona.getIdTipoContacto() != null) {
				statement.setInt(11, persona.getIdTipoContacto());
			} else {
				statement.setNull(11, Types.INTEGER);
			}
			if (persona.getIdLocalidad() != null) {
				statement.setString(12, persona.getIdLocalidad());
			} else {
				statement.setNull(12, Types.INTEGER);
			}

			if (statement.executeUpdate() > 0) {
				conexion.commit();
				isInsertExitoso = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return isInsertExitoso;
	}

	public boolean update(PersonaDTO persona) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;

		try {
			statement = conexion.prepareStatement(update);
			statement.setString(1, persona.getNombreUsuario());
			statement.setString(2, persona.getNombre());
			statement.setString(3, persona.getTelefono());
			statement.setString(4, persona.getCalle());
			statement.setString(5, persona.getPiso());
			statement.setString(6, persona.getAltura());
			statement.setString(7, persona.getDepto());
			statement.setString(8, persona.getEmail());
			statement.setString(9, persona.getFechaCumpleanios());
			if (persona.getIdTipoContacto() != null) {
				statement.setInt(10, persona.getIdTipoContacto());
			} else {
				statement.setNull(10, Types.INTEGER);
			}
			if (persona.getIdLocalidad() != null) {
				statement.setString(11, persona.getIdLocalidad());
			} else {
				statement.setNull(11, Types.INTEGER);
			}
			statement.setInt(12, persona.getIdPersona());

			if (statement.executeUpdate() > 0) {
				conexion.commit();
				isUpdateExitoso = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return isUpdateExitoso;
	}

	public boolean delete(PersonaDTO persona) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;

		try {
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(persona.getIdPersona()));
			if (statement.executeUpdate() > 0) {
				conexion.commit();
				isdeleteExitoso = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isdeleteExitoso;
	}

	public List<PersonaDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<PersonaDTO> personas = new ArrayList<PersonaDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				personas.add(getPersonaDTO(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personas;
	}

	private PersonaDTO getPersonaDTO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idPersona");
		Integer idTipoContacto = (resultSet.getInt("idTipoContacto") == 0) ? null : resultSet.getInt("idTipoContacto");
		String nombre = resultSet.getString("Nombre"), nombreUsuario = resultSet.getString("usuario"),
				tel = resultSet.getString("Telefono"), calle = resultSet.getString("calle"),
				piso = resultSet.getString("piso"), altura = resultSet.getString("altura"),
				depto = resultSet.getString("depto"), email = resultSet.getString("email"),
				fechaCumpleanios = resultSet.getString("fechaCumpleanios"),
				idLocalidad = (resultSet.getString("idLocalidad") == null) ? null : resultSet.getString("idLocalidad");

		return new PersonaDTO(id, nombreUsuario, nombre, tel, calle, piso, altura, depto, email, fechaCumpleanios,
				idTipoContacto, idLocalidad);
	}

	public PersonaDTO readUsers(String usuario) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		PersonaDTO personaNew = null;
		try {
			statement = conexion.getSQLConexion().prepareStatement(readUsuarios);
			statement.setString(1, usuario);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				personaNew = new PersonaDTO(resultSet.getInt("idPersona"), resultSet.getString("usuario"),
						resultSet.getString("Nombre"), resultSet.getString("Telefono"), resultSet.getString("calle"),
						resultSet.getString("piso"), resultSet.getString("altura"), resultSet.getString("depto"),
						resultSet.getString("email"), resultSet.getString("fechaCumpleanios"),
						resultSet.getInt("idTipoContacto"), resultSet.getString("idLocalidad"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return personaNew;
	}
}
