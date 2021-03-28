package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Statement;
import dto.ProvinciaDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ProvinciaDAO;

public class ProvinciaDAOSQL implements ProvinciaDAO {
	private static final String insert = "INSERT INTO provincias (idProvincia, idPais, nombre) VALUES(?, ?, ?)";
	private static final String update = "UPDATE provincias SET idPais = ?, nombre = ? WHERE idProvincia = ?";
	private static final String delete = "DELETE FROM provincias WHERE idProvincia = ?";
	private static final String readall = "SELECT * FROM provincias";
	private static final String select = "SELECT * FROM provincias WHERE idPais = ? AND nombre = ?";
	private static final Conexion conexion = Conexion.getConexion();

	@Override
	public int insert(ProvinciaDTO provincia) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int idProvincia = 0;

		try {
			statement = conexion.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, provincia.getIdProvincia());
			statement.setInt(2, provincia.getIdPais());
			statement.setString(3, provincia.getNombre());

			if (!statement.execute()) {
				conexion.commit();
			}

			ResultSet rs = statement.getGeneratedKeys();
			idProvincia = 0;

			if (rs.next()) {
				idProvincia = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return idProvincia;
	}

	@Override
	public boolean update(ProvinciaDTO provincia_a_editar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;

		try {
			statement = conexion.prepareStatement(update);
			statement.setInt(1, provincia_a_editar.getIdPais());
			statement.setString(2, provincia_a_editar.getNombre());
			statement.setInt(3, provincia_a_editar.getIdProvincia());

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
	public boolean delete(ProvinciaDTO provincia_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;

		try {
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, provincia_a_eliminar.getIdProvincia());

			if (statement.executeUpdate() > 0) {
				conexion.commit();
				isdeleteExitoso = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isdeleteExitoso;
	}

	@Override
	public ProvinciaDTO existeProvincia(int idPais, String provincia) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ProvinciaDTO pais = null;

		try {
			statement = conexion.getSQLConexion().prepareStatement(select);
			statement.setInt(1, idPais);
			statement.setString(2, provincia);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pais = new ProvinciaDTO(resultSet.getInt("idProvincia"), resultSet.getInt("idPais"),
						resultSet.getString("nombre"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pais;
	}

	@Override
	public List<ProvinciaDTO> selectProvincias(String idProvincias) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();

		try {
			statement = conexion.getSQLConexion()
					.prepareStatement("SELECT * FROM provincias WHERE idProvincia IN (" + idProvincias + ")");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				provincias.add(getProvinciaDTO(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return provincias;
	}

	public List<ProvinciaDTO> obtenerProvinciasDelPais(String idPais) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();

		try {
			statement = conexion.getSQLConexion()
					.prepareStatement("SELECT * FROM provincias WHERE idPais IN (" + idPais + ")");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				provincias.add(getProvinciaDTO(resultSet));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return provincias;
	}

	@Override
	public List<ProvinciaDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				provincias.add(getProvinciaDTO(resultSet));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return provincias;
	}

	private ProvinciaDTO getProvinciaDTO(ResultSet resultSet) throws SQLException {
		return new ProvinciaDTO(resultSet.getInt("idProvincia"), resultSet.getInt("idPais"),
				resultSet.getString("nombre"));
	}
}
