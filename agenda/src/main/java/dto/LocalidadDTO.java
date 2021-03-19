package dto;

public class LocalidadDTO {

	private int idLocalidad;
	private String pais;
	private String provincia;
	private String localidad;

	public LocalidadDTO(String pais, String provincia, String localidad, int idLocalidad) {
		super();
		this.pais = pais;
		this.provincia = provincia;
		this.localidad = localidad;
		this.idLocalidad = idLocalidad;

	}

	public String getPais() {
		return pais;
	}

	public int getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

}
