package dto;

public class RegistrarPersonaDTO {
	
	String nombre, password;
	int activo;
	
	public RegistrarPersonaDTO(String nombre, String password, int activo) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.activo = activo;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
