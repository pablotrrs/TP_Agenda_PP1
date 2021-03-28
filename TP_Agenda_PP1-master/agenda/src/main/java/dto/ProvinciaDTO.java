package dto;

public class ProvinciaDTO {
	private int idProvincia, idPais;
	private String nombre;

	public ProvinciaDTO(int idProvincia, int idPais, String nombre) {
		super();
		this.idProvincia = idProvincia;
		this.idPais = idPais;
		this.nombre = nombre;
	}

	public int getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}

	public int getIdPais() {
		return idPais;
	}

	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPais;
		result = prime * result + idProvincia;
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
		ProvinciaDTO other = (ProvinciaDTO) obj;
		if (idPais != other.idPais)
			return false;
		if (idProvincia != other.idProvincia)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
}
