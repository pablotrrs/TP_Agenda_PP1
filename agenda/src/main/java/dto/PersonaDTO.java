package dto;



public class PersonaDTO 
{
	private int idPersona;
	private String nombre;
	private String telefono;
	private String calle;
	private String piso, altura, depto;
	private String email;
	private String fecha_cumplea�os;
	
	
	

	public PersonaDTO(int idPersona, String nombre, String telefono, String calle, String piso, String altura, String depto,
			String email, String fecha_cumplea�os) {
		super();
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.telefono = telefono;
		this.calle = calle;
		this.piso = piso;
		this.altura = altura;
		this.depto = depto;
		this.email = email;
		this.fecha_cumplea�os = fecha_cumplea�os;
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

	public String getFecha_cumplea�os() {
		return fecha_cumplea�os;
	}

	public void setFecha_cumplea�os(String fecha_cumplea�os) {
		this.fecha_cumplea�os = fecha_cumplea�os;
	}
}
