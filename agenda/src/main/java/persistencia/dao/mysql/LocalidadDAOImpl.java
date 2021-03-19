package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.LocalidadDTO;
import dto.PersonaDTO;
import dto.TipoContactoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.LocalidadDAO;

public class LocalidadDAOImpl implements LocalidadDAO {
	
	private static final String insert = "INSERT INTO localidad (pais, provincia, localidad, idLocalidad) VALUES(?, ?, ?, ?)";
	private static final String update = "UPDATE localidad SET pais = ?, provincia = ?, localidad = ? WHERE idLocalidad = ?";
	private static final String delete = "DELETE FROM localidad WHERE idLocalidad = ?";
	private static final String readall = "SELECT * FROM localidad";
	private static final Conexion conexion = Conexion.getConexion();

	


	public boolean insert(LocalidadDTO localidad) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try {
			statement = conexion.prepareStatement(insert);
			statement.setString(1, localidad.getPais());
			statement.setString(2, localidad.getProvincia());
			statement.setString(3, localidad.getLocalidad());
			statement.setInt(4, localidad.getIdLocalidad());
			
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
	
	public boolean update(LocalidadDTO localidad_a_editar) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try {
			statement = conexion.prepareStatement(update);

			statement.setString(1, localidad_a_editar.getPais());
			statement.setString(2, localidad_a_editar.getProvincia());
			statement.setString(3, localidad_a_editar.getLocalidad());
			statement.setInt(4, localidad_a_editar.getIdLocalidad());

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

	
	public boolean delete(LocalidadDTO localidad_a_eliminar) {
		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try {
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, localidad_a_eliminar.getIdLocalidad());
			
			if (statement.executeUpdate() > 0) {
				conexion.commit();
				isdeleteExitoso = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isdeleteExitoso;
		
		
	}

	
	public List<LocalidadDTO> readAll() {
		
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<LocalidadDTO> localidad = new ArrayList<LocalidadDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				localidad.add(getLocalidadDTO(resultSet));
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return localidad;
		
		
	}
	
	private LocalidadDTO getLocalidadDTO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idLocalidad");
		String pais = resultSet.getString("Pais"), provincia = resultSet.getString("Provincia"),
				localidad = resultSet.getString("Localidad"); 
				
		return new LocalidadDTO(pais, provincia, localidad, id);
	}

}
