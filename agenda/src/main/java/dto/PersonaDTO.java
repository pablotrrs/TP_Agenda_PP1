package dto;

public class PersonaDTO {

	private int idPersona, idTipoContacto, idLocalidad;
	private String nombre, telefono, calle, piso, altura, depto, email, fechaCumpleanios;

	public PersonaDTO(int idPersona, String nombre, String telefono, String calle, String piso, String altura,
			String depto, String email, String fechaCumpleanios, int idTipoContacto, int idLocalidad) {
		super();
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.telefono = telefono;
		this.calle = calle;
		this.piso = piso;
		this.altura = altura;
		this.depto = depto;
		this.email = email;
		this.fechaCumpleanios = fechaCumpleanios;
		this.idTipoContacto = idTipoContacto;
		this.idLocalidad = idLocalidad;
	}

	public int getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFechaCumpleanios() {
		return fechaCumpleanios;
	}

	public void setFechaCumpleanios(String fechaCumpleanios) {
		this.fechaCumpleanios = fechaCumpleanios;
	}

	public int getIdTipoContacto() {
		return idTipoContacto;
	}

	public void setIdTipoContacto(int idTipoContacto) {
		this.idTipoContacto = idTipoContacto;
	}

	@Override
	public String toString() {
		return "PersonaDTO [idPersona=" + idPersona + ", tipoContacto=" + idTipoContacto + ", localidad=" + idLocalidad
				+ ", nombre=" + nombre + ", telefono=" + telefono + ", calle=" + calle + ", piso=" + piso + ", altura="
				+ altura + ", depto=" + depto + ", email=" + email + ", fechaCumpleanios=" + fechaCumpleanios + "]";
	}
}
