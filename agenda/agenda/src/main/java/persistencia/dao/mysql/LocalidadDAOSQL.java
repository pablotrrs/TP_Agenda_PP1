package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Statement;
import dto.LocalidadDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.LocalidadDAO;

public class LocalidadDAOSQL implements LocalidadDAO {
	private static final String insert = "INSERT INTO localidad (idLocalidad, idProvincia, nombre) VALUES(?, ?, ?)";
	private static final String update = "UPDATE localidad SET idProvincia = ?, nombre = ? WHERE idLocalidad = ?";
	private static final String delete = "DELETE FROM localidad WHERE idLocalidad = ?";
	private static final String readall = "SELECT * FROM localidad";
	private static final String select = "SELECT * FROM localidad WHERE idLocalidad = ?";
	private static final Conexion conexion = Conexion.getConexion();

	public int insert(LocalidadDTO localidad) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int generatedKey = 0;
		try {
			statement = conexion.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, localidad.getIdLocalidad());
			statement.setInt(2, localidad.getIdProvincia());
			statement.setString(3, localidad.getNombre());

			if (!statement.execute()) {
				conexion.commit();
			}
			 
			ResultSet rs = statement.getGeneratedKeys();
			 generatedKey = 0;
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			 

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return generatedKey;
	}

	public boolean update(LocalidadDTO localidad_a_editar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try {
			statement = conexion.prepareStatement(update);

			statement.setInt(1, localidad_a_editar.getIdProvincia());
			statement.setString(2, localidad_a_editar.getNombre());
			statement.setInt(3, localidad_a_editar.getIdLocalidad());

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

	public LocalidadDTO select(int idLocalidad) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		LocalidadDTO localidadNew = null;
		try {
			statement = conexion.getSQLConexion().prepareStatement(select);
			statement.setInt(1, idLocalidad);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				localidadNew = new LocalidadDTO(resultSet.getInt("idLocalidad"), resultSet.getInt("idProvincia"),
						resultSet.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return localidadNew;
	}
	
	@Override
	public List<LocalidadDTO> dameLocalidadesDeLaProvincia(String idProvincia) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<LocalidadDTO> provincias = new ArrayList<LocalidadDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement("SELECT * FROM localidad WHERE idProvincia IN (" + idProvincia + ")");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				provincias.add(getLocalidadDTO(resultSet));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return provincias;
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
		int idLocalidad = resultSet.getInt("idLocalidad"), idProvincia = resultSet.getInt("idProvincia");
		String nombre = resultSet.getString("nombre");

		return new LocalidadDTO(idLocalidad, idProvincia, nombre);
	}
}
