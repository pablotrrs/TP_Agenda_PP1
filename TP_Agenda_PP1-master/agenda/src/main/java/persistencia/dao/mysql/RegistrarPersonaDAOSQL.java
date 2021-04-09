package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.RegistrarPersonaDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.RegistrarPersonaDAO;

public class RegistrarPersonaDAOSQL implements RegistrarPersonaDAO {
	
	
	private static final String insert = "INSERT INTO usuarios (nombre, password, activo) VALUES(?,?,?)";
	private static final String delete = "DELETE FROM mysql.user WHERE user = ? ";
	private static final String update = "UPDATE usuarios SET activo = ?";
	private static final String readall = "SELECT * FROM usuarios";
	private static final String readUsers = "SELECT user FROM mysql.user";
	private static final Conexion conexion = Conexion.getConexion();
	
	public boolean insert(RegistrarPersonaDTO persona_para_registrar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;

		try {
			statement = conexion.prepareStatement(insert);
		
			statement.setString(1, persona_para_registrar.getNombre());
			statement.setString(2, persona_para_registrar.getPassword());
			statement.setInt(3, persona_para_registrar.getActivo());

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
	public boolean CrearUsuario(RegistrarPersonaDTO persona_para_registrar) {
		PreparedStatement statement;
		PreparedStatement statement2;
		PreparedStatement statement3;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean registerExitoso = false;
		boolean r2 = false;
		boolean r3 = false;

		try {
			final String refrescar = "FLUSH PRIVILEGES";
			final String registrar = "CREATE USER "+ "'"+persona_para_registrar.getNombre()+"'"+"@'localhost' IDENTIFIED BY "+ "'"+persona_para_registrar.getPassword() +"'";
			final String privilegios = " GRANT ALL PRIVILEGES ON * . * TO"+ "'"+persona_para_registrar.getNombre()+"'"+"@'localhost';";
			
			
			statement2 = conexion.prepareStatement(refrescar);
			statement = conexion.prepareStatement(registrar);
			statement3 = conexion.prepareStatement(privilegios);
			
			if (statement2.executeUpdate() > 0) {
				conexion.commit();
				registerExitoso = true;
			}

			if (statement.executeUpdate() > 0) {
				conexion.commit();
				r2 = true;
			}
			if (statement3.executeUpdate() > 0) {
				conexion.commit();
				r3 = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return registerExitoso && r2 && r3;
	}
	
	public boolean updateUser(RegistrarPersonaDTO persona_a_actualizar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;

		try {
			statement = conexion.prepareStatement(update);
		
			statement.setInt(1, 1);

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
	
	
	@Override
	public boolean delete(RegistrarPersonaDTO persona_a_borrar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;

		try {
			statement = conexion.prepareStatement(delete);
			statement.setString(1, persona_a_borrar.getNombre());

			if (statement.executeUpdate() > 0) {
				conexion.commit();
				isdeleteExitoso = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isdeleteExitoso;
	}
	
	
	public List<RegistrarPersonaDTO> readUsers() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<RegistrarPersonaDTO> personas = new ArrayList<RegistrarPersonaDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readUsers);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				personas.add(getUser(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return personas ;
	}

	
	@Override
	public List<RegistrarPersonaDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<RegistrarPersonaDTO> personas = new ArrayList<RegistrarPersonaDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				personas.add(getRegistrarPersonaDTO(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return personas ;
	}

	private RegistrarPersonaDTO getRegistrarPersonaDTO(ResultSet resultSet) throws SQLException {
		return new RegistrarPersonaDTO(resultSet.getString("nombre"), resultSet.getString("password"), resultSet.getInt("activo"));
	}
	
	private RegistrarPersonaDTO getUser(ResultSet resultSet) throws SQLException {
		return new RegistrarPersonaDTO(resultSet.getString("User"), null, 0);
	}

}
