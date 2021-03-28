package dto;

public class TipoContactoDTO {
	private Integer idTipoContacto;
	private String nombre;

	public TipoContactoDTO(Integer idTipoContacto, String nombre) {
		this.idTipoContacto = idTipoContacto;
		this.nombre = nombre;
	}

	public Integer getIdTipoContacto() {
		return idTipoContacto;
	}

	public void setIdTipoContacto(Integer idTipoContacto) {
		this.idTipoContacto = idTipoContacto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		return this.getNombre();
	}

	@Override
	public int hashCode() {
		final Integer prime = 31;
		Integer result = 1;
		result = prime * result + idTipoContacto;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoContactoDTO other = (TipoContactoDTO) obj;
		if (idTipoContacto != other.idTipoContacto)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
}
