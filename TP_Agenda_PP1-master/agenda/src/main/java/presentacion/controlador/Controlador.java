package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import com.toedter.calendar.JDateChooser;
import modelo.Agenda;
import modelo.Localidad;
import modelo.Pais;
import modelo.Provincia;
import modelo.RegistrarPersona;
import modelo.TipoContacto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.ReporteAgenda;
import presentacion.vista.VentanaLocalidades;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaPersona;
import presentacion.vista.VentanaRegistro;
import presentacion.vista.VistaTiposContacto;
import presentacion.vista.Vista;
import presentacion.vista.VistaLocalidades;
import presentacion.vista.VentanasTiposContacto;
import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
import dto.RegistrarPersonaDTO;
import dto.TipoContactoDTO;

public class Controlador {
	private Vista vista;
	private VentanaPersona ventanaPersona;
	private VentanasTiposContacto ventanaTiposContacto;
	private VistaTiposContacto vistaTiposContacto;
	private VistaLocalidades vistaLocalidades;
	private VentanaLocalidades ventanaLocalidades;
	private Agenda agenda;
	private TipoContacto tipoContacto;
	private Localidad localidad;
	private Provincia provincia;
	private Pais pais;
	private RegistrarPersona registrar;
	private VentanaRegistro ventanaRegistro;
	private VentanaLogin ventanaLogin;
	private List<PersonaDTO> personasEnTabla;
	private List<TipoContactoDTO> tiposContactoEnTabla;
	private List<LocalidadDTO> localidadesEnTabla;
	private Map<String, LocalidadDTO> localidadesById;
	private Map<Integer, ProvinciaDTO> provinciasById;
	private Map<Integer, PaisDTO> paisesById;
	private Map<Integer, String> tiposDeContactosByIds;

	public Controlador(Vista vista, Agenda agenda) {
		this.vista = vista;
		this.vista.getBtnAgregar().addActionListener(a -> ventanaAgregarPersona(a));
		this.vista.getBtnEditar().addActionListener(z -> ventanaEditarPersona(z));
		this.vista.getBtnBorrar().addActionListener(s -> borrarPersona(s));
		this.vista.getBtnReporte().addActionListener(r -> mostrarReporte(r));
		this.vista.getABMtiposContacto().addActionListener(t -> ventanaTiposContacto(t));
		this.vista.getABMlocalidades().addActionListener(j -> ventanaLocalidades(j));

		this.ventanaPersona = VentanaPersona.getInstance();
		this.ventanaPersona.getBtnAgregarPersona().addActionListener(p -> guardarPersona(p));
		this.ventanaPersona.getBtnEditar().addActionListener(f -> editarPersona(f));
		this.ventanaLogin = VentanaLogin.getInstance();
		this.ventanaLogin.getBtnIngresar().addActionListener(x -> iniciarSesion(x));
		this.ventanaLogin.getBtnRegistrarse().addActionListener(w -> ventanaRegistrar(w));

		this.pais = new Pais(new DAOSQLFactory());
		this.provincia = new Provincia(new DAOSQLFactory());
		this.localidad = new Localidad(new DAOSQLFactory());
		this.tipoContacto = new TipoContacto(new DAOSQLFactory());
		this.registrar = new RegistrarPersona(new DAOSQLFactory());

		this.agenda = agenda;
	}

	private void ventanaTiposContacto(ActionEvent c) {
		this.ventanaTiposContacto.mostrarVentanaTiposContacto();
	}

	private void ventanaLocalidades(ActionEvent c) {
		this.vistaLocalidades.mostrarVistaLocalidades();
	}

	private void ventanaAgregarTiposContacto(ActionEvent c) {
		this.vistaTiposContacto.vaciarCamposContacto();
		this.vistaTiposContacto.cambiarTituloAagregar();
		this.vistaTiposContacto.ApagarButton(vistaTiposContacto.getBtnEditar());
		this.vistaTiposContacto.ActivarButton(vistaTiposContacto.getBtnAgregarTipoDeContacto());
		this.vistaTiposContacto.mostrarVentanaTiposContacto();
	}

	private void ventanaAgregarPersona(ActionEvent a) {
		this.ventanaPersona.getValueprov().removeAllElements();
		this.ventanaPersona.getValueloc().removeAllElements();
		this.ventanaPersona.vaciarCampos();
		this.ventanaPersona.cambiarTituloAagregar();
		this.ventanaPersona.ApagarButton(ventanaPersona.getBtnEditar());
		this.ventanaPersona.ActivarButton(ventanaPersona.getBtnAgregarPersona());
		this.ventanaPersona.mostrarVentana();
	}

	private void ventanaRegistrar(ActionEvent b) {

		if (this.registrar.obtenerTodosLosRegistrados().size() == 0) {
			this.ventanaLogin.cerrar();
			this.inicializarRegistro();
			this.ventanaRegistro.ponerTitulo();
			this.ventanaRegistro.vaciarCamposRegistro();

		}


	}

	private void mostrarVentanaLogin(ActionEvent a) {
		
		if(this.registrar.obtenerTodosLosRegistrados().size() == 1) {
			this.ventanaLogin.ApagarButton(this.ventanaLogin.getBtnRegistrarse());
			 this.ventanaLogin.DesactivarMensaje();
		}
		
		this.ventanaRegistro.cerrar();
		this.ventanaLogin.vaciarCamposLogin();
		this.ventanaLogin.mostrarVentanaLogin();
		this.ventanaLogin.ponerTitulo();
	}

	private void ventanaEditarPersona(ActionEvent a) {
		if (this.vista.getTablaPersonas().getSelectedRows().length == 1) {
			this.ventanaPersona.getValueprov().removeAllElements();
			this.ventanaPersona.getValueloc().removeAllElements();

			if (!this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]).getIdLocalidad()
					.equals("Sin asignar")) {
				LocalidadDTO localidad = localidadesById.get(
						this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]).getIdLocalidad());
				if (localidad != null) {
					ProvinciaDTO provincia = provinciasById.get(localidad.getIdProvincia());
					PaisDTO pais = paisesById.get(provincia.getIdPais());
					this.setComboBoxesValues(localidad, provincia, pais);
				}
			} else if (this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]).getIdLocalidad()
					.equals("Sin asignar")) {
				this.setComboBoxesValues(null, null, null);
			}
			this.ventanaPersona.ApagarButton(this.ventanaPersona.getBtnAgregarPersona());
			this.ventanaPersona.ActivarButton(this.ventanaPersona.getBtnEditar());
			this.ventanaPersona.cambiarTituloAEditar();

			TipoContactoDTO tipoContacto = (this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0])
					.getIdTipoContacto() == null) ? null
							: this.tipoContacto.obtenerTipoContacto(this.personasEnTabla
									.get(this.vista.getTablaPersonas().getSelectedRows()[0]).getIdTipoContacto());

			this.ventanaPersona.llenarCampos(
					this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]), tipoContacto);

			this.ventanaPersona.mostrarVentana();
		}
	}

	private void setComboBoxesValues(LocalidadDTO localidad, ProvinciaDTO provincia, PaisDTO pais) {
		int indexPais = -1, indexProv = -1, indexLoc = -1;
		for (int i = 0; i < this.ventanaPersona.getComboBoxPais().getItemCount(); i++) {
			if (this.ventanaPersona.getComboBoxPais().getItemAt(i) != null
					&& this.ventanaPersona.getComboBoxPais().getItemAt(i).equals(pais)) {
				indexPais = i;
			}
		}
		this.ventanaPersona.setValorComboBoxPais(indexPais);

		for (int i = 0; i < this.ventanaPersona.getComboBoxProvincia().getItemCount(); i++) {
			if (this.ventanaPersona.getComboBoxProvincia().getItemAt(i).equals(provincia)) {
				indexProv = i;
			}

		}
		this.ventanaPersona.setValorComboBoxProvincia(indexProv);

		for (int i = 0; i < this.ventanaPersona.getComboBoxLocalidad().getItemCount(); i++) {
			if (this.ventanaPersona.getComboBoxLocalidad().getItemAt(i).equals(localidad)) {
				indexLoc = i;
			}

		}
		this.ventanaPersona.setValorComboBoxLocalidad(indexLoc);
	}

	private void ventanaEditarTipoContacto(ActionEvent x) {
		if (this.ventanaTiposContacto.getTablaTiposContacto().getSelectedRows().length == 1) {
			this.vistaTiposContacto.ApagarButton(vistaTiposContacto.getBtnAgregarTipoDeContacto());
			this.vistaTiposContacto.ActivarButton(vistaTiposContacto.getBtnEditar());
			this.vistaTiposContacto.cambiarTituloAEditar();

			this.vistaTiposContacto.llenarCamposContacto(this.tiposContactoEnTabla
					.get(this.ventanaTiposContacto.getTablaTiposContacto().getSelectedRows()[0]));
			this.vistaTiposContacto.mostrarVentanaTiposContacto();
		}
	}

	private void ventanaAgregarLocalidad(ActionEvent c) {
		this.ventanaLocalidades.vaciarCampolocalidades();
		this.ventanaLocalidades.cambiarTituloAagregar();
		this.ventanaLocalidades.ApagarButton(ventanaLocalidades.getBtnEditarLocalidad());
		this.ventanaLocalidades.ActivarButton(ventanaLocalidades.getBtnAgregarLocalidad());
		this.ventanaLocalidades.mostrarVentanaLocalidades();
	}

	private void ventanaEditarLocalidad(ActionEvent h) {
		if (this.vistaLocalidades.getTablaLocalidades().getSelectedRows().length == 1) {
			this.ventanaLocalidades.ApagarButton(this.ventanaLocalidades.getBtnAgregarLocalidad());
			this.ventanaLocalidades.ActivarButton(this.ventanaLocalidades.getBtnEditarLocalidad());
			this.ventanaLocalidades.cambiarTituloAEditar();

			LocalidadDTO localidad = this.localidadesEnTabla
					.get(this.vistaLocalidades.getTablaLocalidades().getSelectedRows()[0]);
			List<LocalidadDTO> localidades = new ArrayList<>();
			localidades.add(localidad);
			this.ventanaLocalidades.llenarCamposLocalidad(getLocalidadesWithNames(localidades));
			this.ventanaLocalidades.mostrarVentanaLocalidades();
		}
	}

	private void guardarPersona(ActionEvent p) {
		PersonaDTO persona = this.construirPersona();
		if (persona != null) {
			this.agenda.agregarPersona(persona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
	}

	private PersonaDTO construirPersona() {
		PersonaDTO nuevaPersona = null;
		String nombre = this.ventanaPersona.getTxtNombre().getText(), tel = ventanaPersona.getTxtTelefono().getText(),
				calle = ventanaPersona.getTxtCalle().getText(), piso = ventanaPersona.getTxtPiso().getText(),
				altura = ventanaPersona.getTxtAltura().getText(), depto = ventanaPersona.getTxtDepto().getText(),
				email = ventanaPersona.getTxtEmail().getText(),
				nombreUsuario = ventanaPersona.getTxtUsuario().getText(),
				fechaCumpleanios = fechaToString(ventanaPersona.getTxtFechaCumpleanios());
		TipoContactoDTO tipoContacto = (TipoContactoDTO) ventanaPersona.getComboBoxTipoContacto().getSelectedItem();
		LocalidadDTO localidad = (LocalidadDTO) ventanaPersona.getComboBoxLocalidad().getSelectedItem();

		boolean ret = personaValida(tel, piso, altura, depto, email, nombreUsuario, true, 0);
		if (ret && nombre.length() != 0 && email.length() != 0 && tel.length() != 0) {
			nuevaPersona = new PersonaDTO(0, nombreUsuario, nombre, tel, calle, piso, altura, depto, email,
					(fechaCumpleanios == null) ? null : fechaCumpleanios,
					(tipoContacto == null) ? null : tipoContacto.getIdTipoContacto(),
					(localidad == null) ? "Sin asignar" : localidad.getCodigoPostal());
		} else {
			this.ventanaPersona.mostrarMensajeCamposRequeridos();
		}

		return nuevaPersona;
	}

	private boolean personaValida(String tel, String piso, String altura, String depto, String email,
			String nombreUsuario, boolean ret, int idPersona) {
		if (!cumpleRegex(tel, "^\\+[1-9]{1}[0-9]{3,14}$")) {
			ret &= false;
			this.ventanaPersona.mostrarMensajeFormatoDeCampos(
					"El teléfono tiene un formato no permitido! Debe ingresarlo con el formato internacional.");
		}
		if (!cumpleRegex(email,
				"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
			ret &= false;
			this.ventanaPersona.mostrarMensajeFormatoDeCampos("El email tiene un formato no permitido!");
		}
		if ((altura.length() != 0 && !Pattern.matches("^[0-9]+$", altura))
				|| (piso.length() != 0 && !Pattern.matches("^[0-9]+$", piso))
				|| (depto.length() != 0 && !Pattern.matches("^[0-9]+$", depto))) {
			ret &= false;
			this.ventanaPersona
					.mostrarMensajeFormatoDeCampos("Los campos 'altura', 'piso' y 'depto' deben ser números!");
		}

		PersonaDTO persona = this.agenda.obtenerNombresLinkedin(nombreUsuario);
		if (nombreUsuario.length() != 0 && persona != null && persona.getIdPersona() != idPersona) {
			ret &= false;
			this.ventanaPersona.mostrarMensajeFormatoDeCampos("El nombre de usuario ya está en uso.");
		}
		return ret;
	}

	private void agregarLocalidad(ActionEvent k) {
		String pais = this.ventanaLocalidades.getTxtPais().getText();
		String provincia = this.ventanaLocalidades.getTxtProvincia().getText();
		String localidad = this.ventanaLocalidades.getTxtLocalidad().getText();
		String cp = this.ventanaLocalidades.getTxtCP().getText();

		crearLocalidad(pais, provincia, localidad, cp, false);
	}

	private boolean crearLocalidad(String pais, String provincia, String localidad, String cp, boolean editing) {
		boolean ret = false, paisYaExistia = false, provinciaYaExistia = false;
		if (pais.length() == 0 || provincia.length() == 0 || localidad.length() == 0 || cp.length() == 0) {
			this.ventanaLocalidades.mostrarMensaje("Todos los campos son requeridos para agregar una localidad.");
			this.ventanaLocalidades.cerrar();
			return ret;
		}

		if (this.pais.selectPais(pais).size() != 0 && this.pais.selectPais(pais).get(0) != null) {
			paisYaExistia = true;
		}

		int idPais = (this.pais.selectPais(pais).size() != 0 && this.pais.selectPais(pais).get(0) != null)
				? this.pais.selectPais(pais).get(0).getIdPais()
				: this.pais.agregarPais(new PaisDTO(0, pais));

		if (this.provincia.existeProvincia(idPais, provincia) != null) {
			provinciaYaExistia = true;
		}

		int idProvincia = (this.provincia.existeProvincia(idPais, provincia) != null)
				? this.provincia.existeProvincia(idPais, provincia).getIdProvincia()
				: this.provincia.agregarProvincia(new ProvinciaDTO(0, idPais, provincia));

		if (this.localidad.existeLocalidad(localidad, idProvincia)) {
			this.ventanaLocalidades.mostrarMensaje("Ya existe una localidad con ese nombre en la provincia!");
			this.ventanaLocalidades.cerrar();
			eliminarBasura(pais, provincia, paisYaExistia, provinciaYaExistia, idPais);
			return ret;
		} else if (!cumpleRegex(cp, "[A-Z]{1}[0-9]{4}[A-Z]{3}")) {
			this.ventanaLocalidades.mostrarMensaje(
					"El código postal debe ser de la forma C1663FDA (La primera letra es la provincia y las últimas 3 son para identificar la cara de la manzana).");
			this.ventanaLocalidades.cerrar();
			eliminarBasura(pais, provincia, paisYaExistia, provinciaYaExistia, idPais);
			return ret;
		} else if (cp.length() != 0 && this.localidad.obtenerLocalidad(cp) != null) {
			this.ventanaLocalidades.mostrarMensaje("Ya existe una localidad con ese código postal!");
			this.ventanaLocalidades.cerrar();
			eliminarBasura(pais, provincia, paisYaExistia, provinciaYaExistia, idPais);
			return ret;
		} else {
			if (editing) {
				this.agenda.updateByLocalidad(cp, this.localidadesEnTabla
						.get(this.vistaLocalidades.getTablaLocalidades().getSelectedRows()[0]).getCodigoPostal());
			}

			this.localidad.agregarLocalidad(new LocalidadDTO(cp, idProvincia, localidad));
			this.refrescarTablaLocalidades();
			this.ventanaLocalidades.cerrar();
			this.obtenerPaises();
			cargarDatos();
			this.ventanaPersona.setValorComboBoxPais(-1);
			this.ventanaPersona.setValorComboBoxProvincia(-1);
			this.ventanaPersona.setValorComboBoxLocalidad(-1);
			ret = true;
		}

		return ret;
	}

	private void eliminarBasura(String pais, String provincia, boolean paisYaExistia, boolean provinciaYaExistia,
			int idPais) {
		if (!provinciaYaExistia) {
			this.provincia.borrarProvincia(this.provincia.existeProvincia(idPais, provincia));
		}
		if (!paisYaExistia) {
			this.pais.borrarPais(this.pais.selectPais(pais).get(0));
		}
	}

	private void editarLocalidad(ActionEvent x) {
		LocalidadDTO localidad = this.localidadesEnTabla
				.get(this.vistaLocalidades.getTablaLocalidades().getSelectedRows()[0]);
		ProvinciaDTO provincia = this.provincia.selectById(localidad.getIdProvincia());
		PaisDTO pais = this.pais.selectById(provincia.getIdPais());
		System.out.println("PAIS: " + pais.getIdPais() + " " + pais.getNombre());
		System.out.println(
				"PROV: " + provincia.getIdProvincia() + " " + provincia.getIdPais() + " " + provincia.getNombre());

		System.out.println(
				"LOC: " + localidad.getCodigoPostal() + " " + localidad.getIdProvincia() + " " + localidad.getNombre());

		Map<Integer, Integer> cantidadLocalidadesPorProvincia = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> cantidadProvinciasPorPais = new TreeMap<Integer, Integer>();
		this.contarDependencias(cantidadLocalidadesPorProvincia, cantidadProvinciasPorPais);

		borrarLocalidadesEscalonado(this.vistaLocalidades.getTablaLocalidades().getSelectedRows(),
				cantidadLocalidadesPorProvincia, cantidadProvinciasPorPais);

		if (!crearLocalidad(ventanaLocalidades.getTxtPais().getText(), ventanaLocalidades.getTxtProvincia().getText(),
				ventanaLocalidades.getTxtLocalidad().getText(), this.ventanaLocalidades.getTxtCP().getText(), true)) {
			PaisDTO pais2 = this.pais.selectById(provincia.getIdPais());
			ProvinciaDTO provincia2 = this.provincia.selectById(localidad.getIdProvincia());
			if (pais2 != null && provincia2 != null) {
				this.localidad.agregarLocalidad(new LocalidadDTO(localidad.getCodigoPostal(),
						provincia.getIdProvincia(), localidad.getNombre()));
			} else if (pais2 != null && provincia2 == null) {
				this.provincia.agregarProvincia(provincia);
				this.localidad.agregarLocalidad(new LocalidadDTO(localidad.getCodigoPostal(),
						provincia.getIdProvincia(), localidad.getNombre()));
			} else if (pais2 == null && provincia2 == null) {
				this.pais.agregarPais(pais);
				this.provincia.agregarProvincia(provincia);
				this.localidad.agregarLocalidad(new LocalidadDTO(localidad.getCodigoPostal(),
						provincia.getIdProvincia(), localidad.getNombre()));
			}
		}
		this.refrescarTablaLocalidades();
		this.refrescarTabla();
	}

	private void borrarLocalidad(ActionEvent p) {
		int[] filasSeleccionadas = this.vistaLocalidades.getTablaLocalidades().getSelectedRows();

		if (filasSeleccionadas.length > 1) {
			return;
		}

		this.agenda.updateByLocalidad("Sin asignar", this.localidadesEnTabla
				.get(this.vistaLocalidades.getTablaLocalidades().getSelectedRows()[0]).getCodigoPostal());

		Map<Integer, Integer> cantidadLocalidadesPorProvincia = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> cantidadProvinciasPorPais = new TreeMap<Integer, Integer>();
		this.contarDependencias(cantidadLocalidadesPorProvincia, cantidadProvinciasPorPais);

		borrarLocalidadesEscalonado(filasSeleccionadas, cantidadLocalidadesPorProvincia, cantidadProvinciasPorPais);
		this.ventanaPersona.setValorComboBoxPais(-1);
		this.ventanaPersona.setValorComboBoxProvincia(-1);
		this.ventanaPersona.setValorComboBoxLocalidad(-1);
		this.ventanaPersona.getValuepais().removeAllElements();
		this.ventanaPersona.getValuepais().addAll(pais.obtenerTodosPaises());
		this.ventanaPersona.getValueprov().removeAllElements();
		this.ventanaPersona.getValueloc().removeAllElements();
		this.refrescarTablaLocalidades();
		this.refrescarTabla();
		cargarDatos();
	}

	private void borrarLocalidadesEscalonado(int[] filasSeleccionadas,
			Map<Integer, Integer> cantidadLocalidadesPorProvincia, Map<Integer, Integer> cantidadProvinciasPorPais) {
		for (int fila : filasSeleccionadas) {
			this.cambiarLocalidadEnPersonasQueLaTienen(this.localidadesEnTabla.get(fila).getCodigoPostal());

			this.localidad.borrarLocalidad((this.localidadesEnTabla.get(fila)));

			if (cantidadLocalidadesPorProvincia.get(this.localidadesEnTabla.get(fila).getIdProvincia()) == 1) {
				this.provincia.borrarProvincia(provinciasById.get(this.localidadesEnTabla.get(fila).getIdProvincia()));
			}

			if (cantidadProvinciasPorPais
					.get(provinciasById.get((this.localidadesEnTabla.get(fila)).getIdProvincia()).getIdPais()) == 1) {
				this.pais.borrarPais(paisesById
						.get(provinciasById.get((this.localidadesEnTabla.get(fila)).getIdProvincia()).getIdPais()));
			}

		}
	}

	private void contarDependencias(Map<Integer, Integer> cantidadLocalidadesPorProvincia,
			Map<Integer, Integer> cantidadProvinciasPorPais) {
		for (LocalidadDTO localidad : this.localidad.obtenerTodasLocalidades()) {
			ProvinciaDTO prov = provinciasById.get(localidad.getIdProvincia());
			if (!cantidadLocalidadesPorProvincia.containsKey(prov.getIdProvincia())) {
				cantidadLocalidadesPorProvincia.put(prov.getIdProvincia(), 1);
			} else {
				cantidadLocalidadesPorProvincia.put(prov.getIdProvincia(),
						cantidadLocalidadesPorProvincia.get(prov.getIdProvincia()) + 1);
			}

			PaisDTO pais = paisesById.get(prov.getIdPais());
			if (!cantidadProvinciasPorPais.containsKey(pais.getIdPais())) {
				cantidadProvinciasPorPais.put(pais.getIdPais(), 1);
			} else {
				cantidadProvinciasPorPais.put(pais.getIdPais(), cantidadProvinciasPorPais.get(pais.getIdPais()) + 1);
			}
		}
	}

	private void editarTipoContacto(ActionEvent l) {
		TipoContactoDTO tipoContacto = this.tiposContactoEnTabla
				.get(this.ventanaTiposContacto.getTablaTiposContacto().getSelectedRows()[0]);

		tipoContacto.setIdTipoContacto(tipoContacto.getIdTipoContacto());
		tipoContacto.setNombre(vistaTiposContacto.getTxtNombre().getText());

		List<String> nombresTiposContactos = new ArrayList<String>();
		for (TipoContactoDTO tc : this.tipoContacto.obtenerTodosLosTiposContactos()) {
			nombresTiposContactos.add(tc.getNombre());
		}

		if (!nombresTiposContactos.contains(tipoContacto.getNombre()) && tipoContacto.getNombre().length() != 0) {
			this.tipoContacto.editarTipoContacto(tipoContacto);
			this.refrescarTablaContactos();
			this.refrescarTabla();
			this.vistaTiposContacto.cerrar();
			this.ventanaPersona.cerrar();
			this.vistaTiposContacto.cerrar();
		}
		cargarDatos();
	}

	private void editarPersona(ActionEvent z) {
		PersonaDTO persona = copiarPersona();
		if (persona != null) {
			this.agenda.editarPersona(persona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
	}

	private PersonaDTO copiarPersona() {
		PersonaDTO persona = null;

		String tel = ventanaPersona.getTxtTelefono().getText(), piso = ventanaPersona.getTxtPiso().getText(),
				altura = ventanaPersona.getTxtAltura().getText(), depto = ventanaPersona.getTxtDepto().getText(),
				email = ventanaPersona.getTxtEmail().getText(), nombre = ventanaPersona.getTxtNombre().getText(),
				nombreUsuario = ventanaPersona.getTxtUsuario().getText();

		boolean ret = personaValida(tel, piso, altura, depto, email, nombreUsuario, true,
				this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]).getIdPersona());
		if (ret && nombre.length() != 0 && email.length() != 0 & tel.length() != 0) {
			persona = this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]);
			persona.setIdPersona(persona.getIdPersona());
			persona.setNombre(nombre);
			persona.setCalle(ventanaPersona.getTxtCalle().getText());
			persona.setAltura(altura);
			persona.setPiso(piso);
			persona.setTelefono(tel);
			persona.setDepto(depto);
			persona.setEmail(email);
			persona.setFechaCumpleanios(this.fechaToString(ventanaPersona.getTxtFechaCumpleanios()));
			persona.setNombreUsuario(nombreUsuario);
			TipoContactoDTO tp = (TipoContactoDTO) ventanaPersona.getComboBoxTipoContacto().getSelectedItem();
			persona.setIdTipoContacto((tp == null) ? null : tp.getIdTipoContacto());
			LocalidadDTO localidad = (LocalidadDTO) ventanaPersona.getComboBoxLocalidad().getSelectedItem();
			persona.setIdLocalidad((localidad == null) ? "Sin asignar" : localidad.getCodigoPostal());
		} else {
			this.ventanaPersona.mostrarMensajeCamposRequeridos();
		}

		return persona;
	}

	private void guardarTipoDeContacto(ActionEvent s) {
		TipoContactoDTO nuevoTipo = new TipoContactoDTO(0, this.vistaTiposContacto.getTxtNombre().getText());

		List<String> nombresTiposContactos = new ArrayList<String>();
		for (TipoContactoDTO tc : this.tipoContacto.obtenerTodosLosTiposContactos()) {
			nombresTiposContactos.add(tc.getNombre());
		}
		if (!nombresTiposContactos.contains(nuevoTipo.getNombre()) && nuevoTipo.getNombre().length() != 0) {
			this.tipoContacto.agregarTipoContacto(nuevoTipo);
			this.refrescarTablaContactos();
			this.vistaTiposContacto.cerrar();
			obtenerTiposDeContacto();
			cargarDatos();
		}
	}

	private void registrarPersona(ActionEvent s) {
		RegistrarPersonaDTO persona_a_registrar = new RegistrarPersonaDTO(
				this.ventanaRegistro.getTextNombre().getText(), this.ventanaRegistro.passwordToString(), 0);
		List<String> nombresRegistrados = new ArrayList<String>();

		List<String> registrados = new ArrayList<String>();
		
		
		for (RegistrarPersonaDTO rs : this.registrar.obtenerTodosLosUsers()) {
			registrados.add(rs.getNombre());

		}

		for (RegistrarPersonaDTO rp : this.registrar.obtenerTodosLosRegistrados()) {
			nombresRegistrados.add(rp.getNombre());
		}
		if (!registrados.contains(persona_a_registrar.getNombre().toLowerCase())) {
			if (persona_a_registrar.getNombre().isEmpty() || persona_a_registrar.getPassword().isEmpty()) {
				this.ventanaRegistro.mostrarMensajeCampos();
			} else {
				this.registrar.agregarPersonaRegistrada(persona_a_registrar);
				this.registrar.crearUsuario(persona_a_registrar);
				this.ventanaRegistro.mostrarMensaje();
				this.ventanaRegistro.cerrar();
				if(this.registrar.obtenerTodosLosRegistrados().size() >= 1) {
					this.ventanaLogin.ApagarButton(this.ventanaLogin.getBtnRegistrarse());
					 this.ventanaLogin.DesactivarMensaje();
					 this.ventanaLogin.cambiarDimension();
				}
				this.ventanaLogin.mostrarVentanaLogin();
			}

		} else {
			this.ventanaRegistro.mensajeError();
			if (!nombresRegistrados.contains(persona_a_registrar.getNombre().toLowerCase())) {
				this.registrar.agregarPersonaRegistrada(persona_a_registrar);

			}

		}

	}

	private void mostrarReporte(ActionEvent r) {
		ReporteAgenda reporte;
		try {
			reporte = new ReporteAgenda(agenda.obtenerTodasLasPersonas());
			reporte.mostrar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	private void borrarPersona(ActionEvent s) {
		int[] filasSeleccionadas = this.vista.getTablaPersonas().getSelectedRows();
		for (int fila : filasSeleccionadas) {
			this.agenda.borrarPersona(this.personasEnTabla.get(fila));
		}

		this.refrescarTabla();
	}

	private void borrarTipoContacto(ActionEvent s) {
		int[] filasSeleccionadas = this.ventanaTiposContacto.getTablaTiposContacto().getSelectedRows();
		for (int fila : filasSeleccionadas) {
			this.cambiarTiposDeContactosEnPersonasQueLoTienen(this.tiposContactoEnTabla.get(fila).getIdTipoContacto());
			this.tipoContacto.borrarTipoContacto((this.tiposContactoEnTabla.get(fila)));
		}

		obtenerTiposDeContacto();
		cargarDatos();
		this.refrescarTablaContactos();
	}

	private void cambiarTiposDeContactosEnPersonasQueLoTienen(int id) {
		Set<PersonaDTO> personasAEditar = new HashSet<PersonaDTO>();

		for (int i = 0; i < this.agenda.obtenerTodasLasPersonas().size(); i++) {
			if (this.agenda.obtenerTodasLasPersonas().get(i).getIdTipoContacto() != null
					&& this.agenda.obtenerTodasLasPersonas().get(i).getIdTipoContacto() == id) {
				personasAEditar.add(this.agenda.obtenerTodasLasPersonas().get(i));
			}
		}

		for (PersonaDTO persona : personasAEditar) {
			persona.setIdTipoContacto(null);
			this.agenda.editarPersona(persona);
		}
		this.refrescarTabla();
	}

	private void cambiarLocalidadEnPersonasQueLaTienen(String id) {
		Set<PersonaDTO> personasAEditar = new HashSet<PersonaDTO>();

		for (int i = 0; i < this.agenda.obtenerTodasLasPersonas().size(); i++) {
			if (this.agenda.obtenerTodasLasPersonas().get(i).getIdLocalidad() != null
					&& this.agenda.obtenerTodasLasPersonas().get(i).getIdLocalidad() == id) {
				personasAEditar.add(this.agenda.obtenerTodasLasPersonas().get(i));
			}
		}

		for (PersonaDTO persona : personasAEditar) {
			persona.setIdLocalidad("Sin asignar");
			this.agenda.editarPersona(persona);
		}

		this.refrescarTabla();
	}

	private void iniciarSesion(ActionEvent i) {
		;
		RegistrarPersonaDTO posibleUsuario = new RegistrarPersonaDTO(this.ventanaLogin.getTextNombre().getText(),
				this.ventanaLogin.getTextPassword().getText(), 0);
		Map<String, String> nombresRegistrados = new TreeMap<String, String>();

		List<String> usuarios = new ArrayList<String>();
		for (RegistrarPersonaDTO rs : this.registrar.obtenerTodosLosUsers()) {
			usuarios.add(rs.getNombre());

		}

		for (RegistrarPersonaDTO rp : this.registrar.obtenerTodosLosRegistrados()) {
			nombresRegistrados.put(rp.getNombre(), rp.getPassword());
		}

		if (usuarios.contains(posibleUsuario.getNombre()) && !nombresRegistrados.containsKey(posibleUsuario.getNombre())
				&& !posibleUsuario.getNombre().equals("root")) {
			this.registrar.eliminarUsuario(posibleUsuario);

			ventanaLogin.mensajeError();

		} else if (nombresRegistrados.containsKey(posibleUsuario.getNombre())
				&& nombresRegistrados.containsValue(posibleUsuario.getPassword())) {

			this.registrar.update(posibleUsuario);

			this.inicializarAll();

			ventanaLogin.cerrar();

		} else if (usuarios.contains(posibleUsuario.getNombre()) && posibleUsuario.getNombre().equals("root")) {
			this.inicializarAll();
			ventanaLogin.cerrar();
		}

		else
			ventanaLogin.mensajeError();

	}

	private void inicializarRegistro() {
		this.ventanaRegistro = new VentanaRegistro();
		this.ventanaRegistro.mostrarVentanaRegistro();
		this.ventanaRegistro.getBtnRegistrar().addActionListener(z -> registrarPersona(z));
		this.ventanaRegistro.getBtnVolver().addActionListener(k -> mostrarVentanaLogin(k));

	}

	private void inicializarTipoContacto() {
		this.ventanaTiposContacto = new VentanasTiposContacto();
		this.ventanaTiposContacto.getBtnEditar().addActionListener(v -> ventanaEditarTipoContacto(v));
		this.ventanaTiposContacto.getBtnAgregar().addActionListener(b -> ventanaAgregarTiposContacto(b));
		this.ventanaTiposContacto.getBtnBorrar().addActionListener(d -> borrarTipoContacto(d));

		this.vistaTiposContacto = new VistaTiposContacto();
		this.vistaTiposContacto.getBtnEditar().addActionListener(m -> editarTipoContacto(m));
		this.vistaTiposContacto.getBtnAgregarTipoDeContacto().addActionListener(s -> guardarTipoDeContacto(s));

		this.tiposContactoEnTabla = tipoContacto.obtenerTodosLosTiposContactos();

		this.obtenerTiposDeContacto();
		this.refrescarTablaContactos();
	}

	private void inicializarLocalidades() {
		this.vistaLocalidades = new VistaLocalidades();
		this.vistaLocalidades.getBtnAgregarLocalidad().addActionListener(s -> ventanaAgregarLocalidad(s));
		this.vistaLocalidades.getBtnEditarLocalidad().addActionListener(b -> ventanaEditarLocalidad(b));
		this.vistaLocalidades.getBtnBorrarLocalidad().addActionListener(u -> borrarLocalidad(u));

		this.ventanaLocalidades = new VentanaLocalidades();
		this.ventanaLocalidades.getBtnAgregarLocalidad().addActionListener(a -> agregarLocalidad(a));
		this.ventanaLocalidades.getBtnEditarLocalidad().addActionListener(g -> editarLocalidad(g));

		this.localidadesEnTabla = localidad.obtenerTodasLocalidades();
		this.obtenerPaises();
		this.refrescarTablaLocalidades();
	}

	private void obtenerTiposDeContacto() {
		this.ventanaPersona.getDefaultComboBoxModelValue().removeAllElements();
		this.ventanaPersona.getDefaultComboBoxModelValue().addElement(null);
		this.ventanaPersona.getDefaultComboBoxModelValue().addAll(tipoContacto.obtenerTodosLosTiposContactos());
	}

	private void obtenerPaises() {
		this.ventanaPersona.getValuepais().removeAllElements();
		this.ventanaPersona.getValuepais().addElement(null);
		this.ventanaPersona.getValuepais().addAll(pais.obtenerTodosPaises());

		this.ventanaPersona.getComboBoxPais().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPersona.setValorComboBoxProvincia(-1);
				ventanaPersona.setValorComboBoxLocalidad(-1);
				if (ventanaPersona.getComboBoxPais().getSelectedItem() != null) {
					PaisDTO pais = (PaisDTO) ventanaPersona.getComboBoxPais().getSelectedItem();
					obtenerProvincias(String.valueOf(pais.getIdPais()));
				}
			}
		});
	}

	private void obtenerProvincias(String idPais) {
		this.ventanaPersona.getValueprov().removeAllElements();
		this.provincia.obtenerProvinciasDelPais(idPais);

		this.ventanaPersona.getComboBoxProvincia().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ventanaPersona.getComboBoxProvincia().getSelectedItem() != null) {
					ProvinciaDTO provincia = (ProvinciaDTO) ventanaPersona.getComboBoxProvincia().getSelectedItem();
					ventanaPersona.setValorComboBoxLocalidad(-1);
					obtenerLocalidades(String.valueOf(provincia.getIdProvincia()));
				}
			}
		});

		this.ventanaPersona.getValueprov().addAll(this.provincia.obtenerProvinciasDelPais(idPais));
	}

	private void obtenerLocalidades(String idProvincia) {
		this.ventanaPersona.getValueloc().removeAllElements();
		this.ventanaPersona.getValueloc().addAll(this.localidad.obtenerLocalidadesDeLaProvincia(idProvincia));
	}

	private void refrescarTabla() {
		this.personasEnTabla = agenda.obtenerTodasLasPersonas();
		this.cargarDatos();

		List<Pair<String, PersonaDTO>> personas = new ArrayList<Pair<String, PersonaDTO>>();
		for (PersonaDTO persona : this.personasEnTabla) {
			personas.add(new Pair<String, PersonaDTO>(
					(persona.getIdTipoContacto() == null) ? "" : tiposDeContactosByIds.get(persona.getIdTipoContacto()),
					persona));
		}

		this.vista.llenarTabla(personas, provinciasById, tiposDeContactosByIds, localidadesById, paisesById);
	}

	private void refrescarTablaContactos() {
		this.tiposContactoEnTabla = tipoContacto.obtenerTodosLosTiposContactos();
		this.ventanaTiposContacto.llenarTabla(this.tiposContactoEnTabla);
	}

	private void refrescarTablaLocalidades() {
		this.localidadesEnTabla = localidad.obtenerTodasLocalidades();
		List<Triplet<String, String, LocalidadDTO>> localidades = getLocalidadesWithNames(this.localidadesEnTabla);
		this.vistaLocalidades.llenarTabla(localidades);
	}

	private String fechaToString(JDateChooser fecha) {
		if (fecha.getCalendar() == null) {
			return null;
		}

		String dia = Integer.toString(fecha.getCalendar().get(Calendar.DAY_OF_MONTH));
		String mes = Integer.toString(fecha.getCalendar().get(Calendar.MONTH) + 1);
		String year = Integer.toString(fecha.getCalendar().get(Calendar.YEAR));
		String date = (year + "-" + mes + "-" + dia);

		return date;
	}

	private List<Triplet<String, String, LocalidadDTO>> getLocalidadesWithNames(List<LocalidadDTO> localidades) {
		Map<Integer, String> provinciasByName = new TreeMap<>();
		Map<Integer, String> paisesByName = new TreeMap<>();
		Map<Integer, Integer> provinciasIDsByPaisID = new TreeMap<>();

		String idsProvincias = "";
		String idsPaises = "";

		for (LocalidadDTO localidad : localidades) {
			idsProvincias += (String.valueOf(localidad.getIdProvincia()) + ", ");
		}

		if (idsProvincias.length() > 0) {
			idsProvincias = idsProvincias.substring(0, idsProvincias.length() - 2);
		} else {
			idsProvincias = "-1";
		}

		List<ProvinciaDTO> provincias = this.provincia.obtenerProvincia(idsProvincias);

		for (ProvinciaDTO provincia : provincias) {
			provinciasByName.put(provincia.getIdProvincia(), provincia.getNombre());
			provinciasIDsByPaisID.put(provincia.getIdProvincia(), provincia.getIdPais());
			idsPaises += (String.valueOf(provincia.getIdPais()) + ", ");
		}

		if (idsPaises.length() > 0) {
			idsPaises = idsPaises.substring(0, idsPaises.length() - 2);
		} else {
			idsPaises = "-1";
		}

		List<PaisDTO> paises = this.pais.obtenerPais(idsPaises);

		for (PaisDTO pais : paises) {
			paisesByName.put(pais.getIdPais(), pais.getNombre());
		}

		List<Triplet<String, String, LocalidadDTO>> localidadesWithNames = new ArrayList<Triplet<String, String, LocalidadDTO>>();
		for (LocalidadDTO localidad : localidades) {
			localidadesWithNames.add(new Triplet<String, String, LocalidadDTO>(
					paisesByName.get(provinciasIDsByPaisID.get(localidad.getIdProvincia())),
					provinciasByName.get(localidad.getIdProvincia()), localidad));
		}
		return localidadesWithNames;
	}

	private void cargarDatos() {
		localidadesById = new TreeMap<String, LocalidadDTO>();
		for (LocalidadDTO localidad : this.localidad.obtenerTodasLocalidades()) {
			localidadesById.put(localidad.getCodigoPostal(), localidad);
		}

		provinciasById = new TreeMap<Integer, ProvinciaDTO>();
		for (ProvinciaDTO provincia : this.provincia.obtenerTodasLasProvincias()) {
			provinciasById.put(provincia.getIdProvincia(), provincia);
		}

		paisesById = new TreeMap<Integer, PaisDTO>();
		for (PaisDTO pais : this.pais.obtenerTodosPaises()) {
			paisesById.put(pais.getIdPais(), pais);
		}

		tiposDeContactosByIds = new TreeMap<Integer, String>();
		for (TipoContactoDTO tipoDeContacto : this.tipoContacto.obtenerTodosLosTiposContactos()) {
			if (!tiposDeContactosByIds.containsKey(tipoDeContacto.getIdTipoContacto())) {
				tiposDeContactosByIds.put(tipoDeContacto.getIdTipoContacto(), tipoDeContacto.getNombre());
			}
		}
	}

	private boolean cumpleRegex(String texto, String regex) {
		return texto.length() != 0 && Pattern.matches(regex, texto);
	}

	public void inicializar() {
		
		if(this.registrar.obtenerTodosLosRegistrados().size() >= 1) {
			this.ventanaLogin.ApagarButton(this.ventanaLogin.getBtnRegistrarse());
			 this.ventanaLogin.DesactivarMensaje();
			 this.ventanaLogin.cambiarDimension();
		}

		if (!this.registrar.obtenerTodosLosRegistrados().isEmpty()
				&& this.registrar.obtenerTodosLosRegistrados().get(0).getActivo() == 1) {
			this.inicializarAll();
			 
		} else {

			this.ventanaLogin.ponerTitulo();
			this.ventanaLogin.mostrarVentanaLogin();
		}
	}

	public void inicializarAll() {

		this.refrescarTabla();
		this.inicializarLocalidades();
		this.inicializarTipoContacto();
		this.vista.show();
	}
}
