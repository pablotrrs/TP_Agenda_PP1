package persistencia.dao.interfaz;

import java.util.List;
import dto.PaisDTO;

public interface PaisDAO {

	public boolean update(PaisDTO pais_a_editar);

	public int insert(PaisDTO pais);

	public boolean delete(PaisDTO pais_a_eliminar);

	public List<PaisDTO> select(String paises);

	public List<PaisDTO> readAll();
}
