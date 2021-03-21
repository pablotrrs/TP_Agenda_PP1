package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Statement;
import dto.PaisDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PaisDAO;

public class PaisDAOSQL implements PaisDAO {
	private static final String insert = "INSERT INTO paises (idPais, nombre) VALUES(?, ?)";
	private static final String update = "UPDATE paises SET nombre = ? WHERE idPais = ?";
	private static final String delete = "DELETE FROM paises WHERE idPais = ?";
	private static final String readall = "SELECT * FROM paises";
	private static final String select = "SELECT * FROM paises WHERE nombre = ?";
	private static final Conexion conexion = Conexion.getConexion();

	@Override
	public int insert(PaisDTO pais) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int idPais = 0;

		try {
			statement = conexion.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, pais.getIdPais());
			statement.setString(2, pais.getNombre());

			if (!statement.execute()) {
				conexion.commit();
			}

			ResultSet rs = statement.getGeneratedKeys();
			idPais = 0;

			if (rs.next()) {
				idPais = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return idPais;
	}

	@Override
	public boolean update(PaisDTO pais) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;

		try {
			statement = conexion.prepareStatement(update);

			statement.setString(1, pais.getNombre());
			statement.setInt(2, pais.getIdPais());

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
	public boolean delete(PaisDTO pais_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;

		try {
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, pais_a_eliminar.getIdPais());

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
	public List<PaisDTO> selectPais(String pais_a_eliminar) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		List<PaisDTO> paises = new ArrayList<PaisDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(select);
			statement.setString(1, pais_a_eliminar);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				paises.add(getPaisDTO(resultSet));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paises;
	}

	@Override
	public List<PaisDTO> selectPaises(String idPaises) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<PaisDTO> paises = new ArrayList<PaisDTO>();

		try {
			statement = conexion.getSQLConexion()
					.prepareStatement("SELECT * FROM paises WHERE idPais IN (" + idPaises + ")");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				paises.add(getPaisDTO(resultSet));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return paises;
	}

	@Override
	public List<PaisDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<PaisDTO> localidad = new ArrayList<PaisDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				localidad.add(getPaisDTO(resultSet));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return localidad;
	}

	private PaisDTO getPaisDTO(ResultSet resultSet) throws SQLException {
		return new PaisDTO(resultSet.getInt("idPais"), resultSet.getString("nombre"));
	}
}
