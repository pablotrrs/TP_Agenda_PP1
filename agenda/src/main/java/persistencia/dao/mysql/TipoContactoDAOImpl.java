package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PersonaDTO;
import dto.TipoContactoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TipoContactoDAO;

public class TipoContactoDAOImpl implements TipoContactoDAO 
{
	private static final String insert = "INSERT INTO tiposContacto (idTipoContacto, nombre) VALUES(?, ?)";
	private static final String update = "UPDATE tiposContacto SET nombre = ? WHERE idTipoContacto = ?";
	private static final String delete = "DELETE FROM tiposContacto WHERE idTipoContacto = ?";
	private static final String readall = "SELECT * FROM tiposContacto";
	private static final Conexion conexion = Conexion.getConexion();
	


	public boolean insert(TipoContactoDTO tipoContacto)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, tipoContacto.getIdTipoContacto());
			statement.setString(2, tipoContacto.getNombre());
			
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isInsertExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return isInsertExitoso;
	}
	public boolean update(TipoContactoDTO tipoContacto_a_editar) 
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(update);
			
			
			statement.setString(1, tipoContacto_a_editar.getNombre());
			statement.setInt(2, tipoContacto_a_editar.getIdTipoContacto());
			
			System.out.println(statement);
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isUpdateExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return isUpdateExitoso;
	}
	
	
	public boolean delete(TipoContactoDTO tipoContacto_a_eliminar)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(tipoContacto_a_eliminar.getIdTipoContacto()));
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isdeleteExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return isdeleteExitoso;
	}

	

	@Override
	public List<TipoContactoDTO> readAll() 
	{
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<TipoContactoDTO> tipoContactos = new ArrayList<TipoContactoDTO>();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				TipoContactoDTO tipoContacto = new TipoContactoDTO(resultSet.getInt("idTipoContacto"),resultSet.getString("nombre"));
				tipoContactos.add(tipoContacto);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return tipoContactos;
	}

}
