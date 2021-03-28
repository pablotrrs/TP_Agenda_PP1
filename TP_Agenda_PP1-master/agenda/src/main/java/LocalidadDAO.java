package persistencia.dao.interfaz;

import java.util.List;
import dto.LocalidadDTO;

public interface LocalidadDAO {

	public boolean update(LocalidadDTO localidad_a_editar);

	public int insert(LocalidadDTO localidad);

	public boolean delete(LocalidadDTO localidad_a_eliminar);

	public LocalidadDTO select(String cp);

	public List<LocalidadDTO> readAll();

	public List<LocalidadDTO> dameLocalidadesDeLaProvincia(String idProvincia);

	public boolean selectByName(String localidad, int provincia);
}
