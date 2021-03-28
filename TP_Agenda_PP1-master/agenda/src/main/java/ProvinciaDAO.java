package persistencia.dao.interfaz;

import java.util.List;
import dto.ProvinciaDTO;

public interface ProvinciaDAO {

	public boolean update(ProvinciaDTO provincia_a_editar);

	public int insert(ProvinciaDTO provincia);

	public boolean delete(ProvinciaDTO provincia_a_eliminar);

	public List<ProvinciaDTO> selectProvincias(String idProvincias);

	public List<ProvinciaDTO> readAll();

	public List<ProvinciaDTO> obtenerProvinciasDelPais(String idPais);

	public ProvinciaDTO existeProvincia(int idPais, String provincia);

	public ProvinciaDTO selectById(int idProvincia);
}
