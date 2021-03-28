package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.TipoContactoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TipoContactoDAO;

public class TipoContactoDAOSQL implements TipoContactoDAO {
	private static final String insert = "INSERT INTO tiposDeContactos (nombre) VALUES(?)";
	private static final String update = "UPDATE tiposDeContactos SET nombre = ? WHERE idTipoContacto = ?";
	private static final String delete = "DELETE FROM tiposDeContactos WHERE nombre = ?";
	private static final String readall = "SELECT * FROM tiposDeContactos";
	private static final String select = "SELECT * FROM tiposDeContactos WHERE idTipoContacto = ?";
	private static final Conexion conexion = Conexion.getConexion();

	public boolean insert(TipoContactoDTO tipoContacto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;

		try {
			statement = conexion.prepareStatement(insert);
			statement.setString(1, tipoContacto.getNombre());

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

	public boolean update(TipoContactoDTO tipoContacto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;

		try {
			statement = conexion.prepareStatement(update);

			statement.setString(1, tipoContacto.getNombre());
			statement.setInt(2, tipoContacto.getIdTipoContacto());

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

	public boolean delete(TipoContactoDTO tipoContacto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;

		try {
			statement = conexion.prepareStatement(delete);
			statement.setString(1, tipoContacto.getNombre());
			if (statement.executeUpdate() > 0) {
				conexion.commit();
				isdeleteExitoso = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isdeleteExitoso;
	}

	public TipoContactoDTO select(int idTipoContacto) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		TipoContactoDTO tipoContactoNew = null;

		try {
			statement = conexion.getSQLConexion().prepareStatement(select);
			statement.setInt(1, idTipoContacto);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				tipoContactoNew = new TipoContactoDTO(resultSet.getInt("idTipoContacto"),
						resultSet.getString("nombre"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tipoContactoNew;
	}

	@Override
	public List<TipoContactoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<TipoContactoDTO> tiposContactos = new ArrayList<TipoContactoDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				tiposContactos.add(getTipoContactoDTO(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tiposContactos;
	}

	private TipoContactoDTO getTipoContactoDTO(ResultSet resultSet) throws SQLException {
		return new TipoContactoDTO(resultSet.getInt("idTipoContacto"), resultSet.getString("nombre"));
	}
}
