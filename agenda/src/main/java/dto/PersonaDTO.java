package dto;

public class PersonaDTO {
	private int idPersona;
	private String nombre, telefono, calle, piso, altura, depto, email, fechaCumpleanios , tipoContacto;

	public PersonaDTO(int idPersona, String nombre, String telefono, String calle, String piso, String altura,
			String depto, String email, String fechaCumpleanios, String tipoContacto) {
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
		this.tipoContacto = tipoContacto;
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

	public void setFechaCumpleanios(String fecha_cumpleaños) {
		this.fechaCumpleanios = fecha_cumpleaños;
	}

	public String getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(String tipoContacto) {
		this.tipoContacto = tipoContacto;
	}
}
