package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PersonaDAO;
import dto.PersonaDTO;

public class PersonaDAOSQL implements PersonaDAO {
	private static final String insert = "INSERT INTO personas(idPersona, nombre, telefono, calle, piso, altura, depto, email, fechaCumpleanios, tipoContacto) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String update = "UPDATE personas SET nombre = ?, telefono = ?, calle = ?, piso = ?,altura = ?, depto = ?,  email = ?, fechaCumpleanios = ?, tipoContacto = ? WHERE idPersona = ?";
	private static final String delete = "DELETE FROM personas WHERE idPersona = ?";
	private static final String readall = "SELECT * FROM personas";

	public boolean insert(PersonaDTO persona) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try {
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, persona.getIdPersona());
			statement.setString(2, persona.getNombre());
			statement.setString(3, persona.getTelefono());
			statement.setString(4, persona.getCalle());
			statement.setString(5, persona.getPiso());
			statement.setString(6, persona.getAltura());
			statement.setString(7, persona.getDepto());
			statement.setString(8, persona.getEmail());
			statement.setString(9, persona.getFechaCumpleanios());
			statement.setString(10, persona.getTipoContacto());

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

			statement.setString(1, persona.getNombre());
			statement.setString(2, persona.getTelefono());
			statement.setString(3, persona.getCalle());
			statement.setString(4, persona.getPiso());
			statement.setString(5, persona.getAltura());
			statement.setString(6, persona.getDepto());
			statement.setString(7, persona.getEmail());
			statement.setString(8, persona.getFechaCumpleanios());
			statement.setString(9, persona.getTipoContacto());
			statement.setInt(10, persona.getIdPersona());

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

	public boolean delete(PersonaDTO persona_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try {
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(persona_a_eliminar.getIdPersona()));
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
		String nombre = resultSet.getString("Nombre"), tel = resultSet.getString("Telefono"),
				calle = resultSet.getString("calle"), piso = resultSet.getString("piso"),
				altura = resultSet.getString("altura"), depto = resultSet.getString("depto"),
				email = resultSet.getString("email"), fechaCumpleanios = resultSet.getString("fechaCumpleanios"),
				tipoContacto = resultSet.getString("tipoContacto");

		return new PersonaDTO(id, nombre, tel, calle, piso, altura, depto, email, fechaCumpleanios, tipoContacto);
	}
}
