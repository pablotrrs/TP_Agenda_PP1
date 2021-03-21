package persistencia.dao.interfaz;

import java.util.List;
import dto.ProvinciaDTO;

public interface ProvinciaDAO {

	public boolean update(ProvinciaDTO provincia_a_editar);

	public int insert(ProvinciaDTO provincia);

	public boolean delete(ProvinciaDTO provincia_a_eliminar);

	public List<ProvinciaDTO> select(String idProvincias);

	public List<ProvinciaDTO> readAll();

	public List<ProvinciaDTO> dameProvinciasDelPais(String idPais);
	//public List<ProvinciaDTO> selectWherePais(String idPais);
}
