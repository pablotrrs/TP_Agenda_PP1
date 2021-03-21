package persistencia.dao.interfaz;

import java.util.List;

import dto.TipoContactoDTO;

public interface TipoContactoDAO 
{
	public boolean update(TipoContactoDTO tipoContacto_a_editar);
	
	public boolean insert(TipoContactoDTO tipoContacto);

	public boolean delete(TipoContactoDTO tipoContacto_a_eliminar);
	
	public TipoContactoDTO select(int idTipoContacto);
	
	public List<TipoContactoDTO> readAll();
}
