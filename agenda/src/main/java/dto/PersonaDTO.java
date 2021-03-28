package dto;

public class PersonaDTO {

	private Integer idPersona, idTipoContacto;
	private String nombre, nombreUsuario, telefono, calle, piso, altura, depto, email, fechaCumpleanios, idLocalidad;

	public PersonaDTO(Integer idPersona, String nombreUsuario, String nombre, String telefono, String calle, String piso, String altura,
			String depto, String email, String fechaCumpleanios, Integer idTipoContacto, String idLocalidad) {
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
		this.nombreUsuario = nombreUsuario;
	}

	public String getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(String codigoPostal) {
		this.idLocalidad = codigoPostal;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
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

	public Integer getIdTipoContacto() {
		return idTipoContacto;
	}

	public void setIdTipoContacto(Integer idTipoContacto) {
		this.idTipoContacto = idTipoContacto;
	}

	@Override
	public String toString() {
		return "PersonaDTO [idPersona=" + idPersona + ", tipoContacto=" + idTipoContacto + ", localidad=" + idLocalidad
				+ ", nombreUsuario="+ nombreUsuario+ ", nombre=" + nombre + ", telefono=" + telefono + ", calle=" + calle + ", piso=" + piso + ", altura="
				+ altura + ", depto=" + depto + ", email=" + email + ", fechaCumpleanios=" + fechaCumpleanios + "]";
	}
}
