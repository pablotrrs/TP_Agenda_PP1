package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

import com.toedter.calendar.JDateChooser;
import modelo.Agenda;
import modelo.Localidad;
import modelo.TipoContacto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.ReporteAgenda;
import presentacion.vista.VentanaLocalidades;
import presentacion.vista.VentanaPersona;
import presentacion.vista.VistaTiposContacto;
import presentacion.vista.Vista;
import presentacion.vista.VistaLocalidades;
import presentacion.vista.VentanasTiposContacto;
import dto.LocalidadDTO;
import dto.PersonaDTO;
import dto.TipoContactoDTO;

public class Controlador {
	private Vista vista;
	private VentanaPersona ventanaPersona;
	private VentanasTiposContacto vistaTiposContacto;
	private VistaTiposContacto ventanaTiposContacto;
	private VistaLocalidades vistaLocalidades;
	private VentanaLocalidades ventanaLocalidades;
	private Agenda agenda;
	private TipoContacto tipoContacto;
	private Localidad localidad;
	private List<PersonaDTO> personasEnTabla;
	private List<TipoContactoDTO> tiposContactoEnTabla;
	private List<LocalidadDTO> localidadesEnTabla;

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

		this.agenda = agenda;
	}

	private void ventanaTiposContacto(ActionEvent c) {
		this.vistaTiposContacto.mostrarVentanaTiposContacto();
	}

	private void ventanaLocalidades(ActionEvent c) {
		this.vistaLocalidades.mostrarVistaLocalidades();
	}

	private void ventanaAgregarTiposContacto(ActionEvent c) {
		this.ventanaTiposContacto.vaciarCamposContacto();
		this.ventanaTiposContacto.setTitle("Agregar Tipo");
		this.ventanaTiposContacto.getBtnEditar().setVisible(false);
		this.ventanaTiposContacto.getBtnAgregarTipoDeContacto().setVisible(true);
		this.ventanaTiposContacto.mostrarVentanaTiposContacto();
	}

	private void ventanaAgregarPersona(ActionEvent a) {
		this.ventanaPersona.vaciarCampos();
		this.ventanaPersona.setTitle("Agregar Persona");
		this.ventanaPersona.getBtnEditar().setVisible(false);
		this.ventanaPersona.getBtnAgregarPersona().setVisible(true);
		this.ventanaPersona.mostrarVentana();
	}

	private void ventanaEditarPersona(ActionEvent a) {
		if (this.vista.getTablaPersonas().getSelectedRows().length == 1) {
			this.ventanaPersona.getBtnAgregarPersona().setVisible(false);
			this.ventanaPersona.getBtnEditar().setVisible(true);
			this.ventanaPersona.setTitle("Editar Persona");
			System.out.println("****PERONSA A DITAR: "
					+ this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]));

			this.ventanaPersona
					.llenarCampos(this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]));

			this.ventanaPersona.mostrarVentana();
		}
	}

	private void ventanaEditarTipoContacto(ActionEvent x) {
		if (this.vistaTiposContacto.getTablaTiposContacto().getSelectedRows().length == 1) {
			this.ventanaTiposContacto.getBtnAgregarTipoDeContacto().setVisible(false);
			this.ventanaTiposContacto.getBtnEditar().setVisible(true);
			this.ventanaTiposContacto.setTitle("Editar Tipo");

			this.ventanaTiposContacto.llenarCamposContacto(this.tiposContactoEnTabla
					.get(this.vistaTiposContacto.getTablaTiposContacto().getSelectedRows()[0]));
			this.ventanaTiposContacto.mostrarVentanaTiposContacto();
		}
	}

	private void ventanaAgregarLocalidad(ActionEvent c) {
		this.ventanaLocalidades.vaciarCampolocalidades();
		this.ventanaLocalidades.setTitle("Agregar Localidad");
		this.ventanaLocalidades.getBtnEditarLocalidad().setVisible(false);
		this.ventanaLocalidades.getBtnAgregarLocalidad().setVisible(true);
		this.ventanaLocalidades.mostrarVentanaLocalidades();

	}

	private void ventanaEditarLocalidad(ActionEvent h) {
		if (this.vistaLocalidades.getTablaLocalidades().getSelectedRows().length == 1) {
			this.ventanaLocalidades.getBtnAgregarLocalidad().setVisible(false);
			this.ventanaLocalidades.getBtnEditarLocalidad().setVisible(true);
			this.ventanaLocalidades.setTitle("Editar Localidad");
			this.ventanaLocalidades.llenarCamposLocalidad(
					this.localidadesEnTabla.get(this.vistaLocalidades.getTablaLocalidades().getSelectedRows()[0]));
			this.ventanaLocalidades.mostrarVentanaLocalidades();
		}
	}

	public String toStringFecha(JDateChooser fecha) {
		if (fecha.getCalendar() == null) {
			return null;
		}

		String dia = Integer.toString(fecha.getCalendar().get(Calendar.DAY_OF_MONTH));
		String mes = Integer.toString(fecha.getCalendar().get(Calendar.MONTH) + 1);
		String year = Integer.toString(fecha.getCalendar().get(Calendar.YEAR));
		String date = (year + "-" + mes + "-" + dia);
		return date;
	}

	private void guardarPersona(ActionEvent p) {
		String nombre = this.ventanaPersona.getTxtNombre().getText(), tel = ventanaPersona.getTxtTelefono().getText(),
				calle = ventanaPersona.getTxtCalle().getText(), piso = ventanaPersona.getTxtPiso().getText(),
				altura = ventanaPersona.getTxtAltura().getText(), depto = ventanaPersona.getTxtDepto().getText(),
				email = ventanaPersona.getTxtEmail().getText(),
				fechaCumpleanios = toStringFecha(ventanaPersona.getTxtFecha_cumpleaños());
		TipoContactoDTO tipoContacto = (TipoContactoDTO) ventanaPersona.getComboBoxTipoContacto().getSelectedItem();
		LocalidadDTO localidad = (LocalidadDTO) ventanaPersona.getComboBoxLocalidades().getSelectedItem();
		System.out.println("****localidad: " + ventanaPersona.getComboBoxLocalidades().getSelectedItem());
		System.out.println("****tipoContato: " + ventanaPersona.getComboBoxTipoContacto().getSelectedItem());
		if (tipoContacto != null) {
			// if (tipoContacto != null && localidad != null) {
			PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel, calle, piso, altura, depto, email,
					fechaCumpleanios, tipoContacto.getIdTipoContacto(), 1);

			this.agenda.agregarPersona(nuevaPersona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
	}

	private void agregarLocalidad(ActionEvent k) {

		String pais = this.ventanaLocalidades.getTxtPais().getText();
		String provincia = this.ventanaLocalidades.getTxtProvincia().getText();
		String localidad = this.ventanaLocalidades.getTxtLocalidad().getText();

		LocalidadDTO nuevalocalidad = new LocalidadDTO(pais, provincia, localidad, 0);

		this.localidad.agregarLocalidad(nuevalocalidad);
		this.refrescarLocalidades();
		this.ventanaLocalidades.cerrar();
	}

	private void editarLocalidad(ActionEvent x) {
		LocalidadDTO localidad = null;

		int[] filasSeleccionadas = this.vistaLocalidades.getTablaLocalidades().getSelectedRows();

		for (int fila : filasSeleccionadas) {
			localidad = this.localidadesEnTabla.get(fila);
			localidad.setPais(ventanaLocalidades.getTxtPais().getText());
			localidad.setProvincia(ventanaLocalidades.getTxtProvincia().getText());
			localidad.setLocalidad(ventanaLocalidades.getTxtLocalidad().getText());
		}
		this.localidad.editarLocalidad(localidad);
		this.refrescarLocalidades();
		this.ventanaLocalidades.cerrar();

	}

	private void borrarLocalidad(ActionEvent p) {

		int[] filasSeleccionadas = this.vistaLocalidades.getTablaLocalidades().getSelectedRows();
		for (int fila : filasSeleccionadas) {
			this.localidad.borrarLocalidad((this.localidadesEnTabla.get(fila)));
		}
		this.refrescarLocalidades();
	}

	private void EditarTipoContacto(ActionEvent l) {
		TipoContactoDTO tipoContacto = null;
		int[] filasSeleccionadas = this.vistaTiposContacto.getTablaTiposContacto().getSelectedRows();
		for (int fila : filasSeleccionadas) {

			tipoContacto = this.tiposContactoEnTabla.get(fila);

			tipoContacto.setIdTipoContacto(this.tiposContactoEnTabla.get(fila).getIdTipoContacto());
			tipoContacto.setNombre(ventanaTiposContacto.getTxtNombre().getText());
		}

		List<String> nombresTiposContactos = new ArrayList<String>();
		for (TipoContactoDTO tc : this.tipoContacto.obtenerTipoContacto()) {
			nombresTiposContactos.add(tc.getNombre());
		}
		System.out.println(nombresTiposContactos);
		if (!nombresTiposContactos.contains(tipoContacto.getNombre())) {
			this.tipoContacto.editarTipoContacto(tipoContacto);
			this.refrescarTablaContactos();
			this.ventanaTiposContacto.cerrar();
		}
	}

	private void editarPersona(ActionEvent z) {
		PersonaDTO persona = null;
		int[] filasSeleccionadas = this.vista.getTablaPersonas().getSelectedRows();
		for (int fila : filasSeleccionadas) {
			persona = this.personasEnTabla.get(fila);
			persona.setIdPersona(this.personasEnTabla.get(fila).getIdPersona());
			persona.setNombre(ventanaPersona.getTxtNombre().getText());
			persona.setCalle(ventanaPersona.getTxtCalle().getText());
			persona.setAltura(ventanaPersona.getTxtAltura().getText());
			persona.setPiso(ventanaPersona.getTxtPiso().getText());
			persona.setTelefono(ventanaPersona.getTxtTelefono().getText());
			persona.setDepto(ventanaPersona.getTxtDepto().getText());
			persona.setEmail(ventanaPersona.getTxtEmail().getText());
			persona.setFechaCumpleanios(this.toStringFecha(ventanaPersona.getTxtFecha_cumpleaños()));
		}

		this.agenda.editarPersona(persona);
		this.refrescarTabla();
		this.ventanaPersona.cerrar();
	}

	private void guardarTipoDeContacto(ActionEvent s) {
		String nombre = this.ventanaTiposContacto.getTxtNombre().getText();

		TipoContactoDTO nuevoTipo = new TipoContactoDTO(0, nombre);

		List<String> nombresTiposContactos = new ArrayList<String>();
		for (TipoContactoDTO tc : this.tipoContacto.obtenerTipoContacto()) {
			nombresTiposContactos.add(tc.getNombre());
		}
		if (!nombresTiposContactos.contains(nuevoTipo.getNombre())) {
			this.tipoContacto.agregarTipoContacto(nuevoTipo);
			this.refrescarTablaContactos();
			this.ventanaTiposContacto.cerrar();
			obtenerTiposDeContacto();
		}
	}

	private void mostrarReporte(ActionEvent r) {
		ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
		reporte.mostrar();
	}

	public void borrarPersona(ActionEvent s) {
		int[] filasSeleccionadas = this.vista.getTablaPersonas().getSelectedRows();
		for (int fila : filasSeleccionadas) {
			this.agenda.borrarPersona(this.personasEnTabla.get(fila));
		}

		this.refrescarTabla();
	}

	public void borrarTipoContacto(ActionEvent s) {

		int[] filasSeleccionadas = this.vistaTiposContacto.getTablaTiposContacto().getSelectedRows();
		for (int fila : filasSeleccionadas) {
			this.tipoContacto.borrarTipoContacto((this.tiposContactoEnTabla.get(fila)));
		}

		this.refrescarTablaContactos();
	}

	public void inicializarTipoContacto() {
		this.refrescarTabla();
		this.vista.show();

		this.vistaTiposContacto = new VentanasTiposContacto();
		this.vistaTiposContacto.getBtnEditar().addActionListener(v -> ventanaEditarTipoContacto(v));
		this.vistaTiposContacto.getBtnAgregar().addActionListener(b -> ventanaAgregarTiposContacto(b));
		this.vistaTiposContacto.getBtnBorrar().addActionListener(d -> borrarTipoContacto(d));
		this.ventanaTiposContacto = new VistaTiposContacto();
		this.ventanaTiposContacto.getBtnEditar().addActionListener(m -> EditarTipoContacto(m));
		this.ventanaTiposContacto.getBtnAgregarTipoDeContacto().addActionListener(s -> guardarTipoDeContacto(s));
		this.tipoContacto = new TipoContacto(new DAOSQLFactory());

		this.tiposContactoEnTabla = tipoContacto.obtenerTipoContacto();
		obtenerTiposDeContacto();
		this.refrescarTablaContactos();
	}

	public void inicializarLocalidades() {
		this.refrescarTabla();
		this.vista.show();

		this.vistaLocalidades = new VistaLocalidades();
		this.vistaLocalidades.getBtnAgregarLocalidad().addActionListener(s -> ventanaAgregarLocalidad(s));
		this.vistaLocalidades.getBtnEditarLocalidad().addActionListener(b -> ventanaEditarLocalidad(b));
		this.vistaLocalidades.getBtnBorrarLocalidad().addActionListener(u -> borrarLocalidad(u));

		this.ventanaLocalidades = new VentanaLocalidades();
		this.ventanaLocalidades.getBtnAgregarLocalidad().addActionListener(a -> agregarLocalidad(a));
		this.ventanaLocalidades.getBtnEditarLocalidad().addActionListener(g -> editarLocalidad(g));
		this.localidad = new Localidad(new DAOSQLFactory());

		this.localidadesEnTabla = localidad.obtenerLocalidades();
		obtenerTiposDeContacto();
		this.refrescarLocalidades();

	}

	public void obtenerTiposDeContacto() {
		this.ventanaPersona.getDefaultComboBoxModelValue().removeAllElements();
		this.ventanaPersona.getDefaultComboBoxModelValue().addAll(tipoContacto.obtenerTipoContacto());
	}

	private void refrescarTabla() {
		this.personasEnTabla = agenda.obtenerPersonas();

		this.tipoContacto = new TipoContacto(new DAOSQLFactory());
		Map<Integer, String> tiposDeContactosByIds = new LinkedHashMap<Integer, String>();
		for (TipoContactoDTO tipoDeContacto : this.tipoContacto.obtenerTipoContacto()) {
			if (!tiposDeContactosByIds.containsKey(tipoDeContacto.getIdTipoContacto())) {
				tiposDeContactosByIds.put(tipoDeContacto.getIdTipoContacto(), tipoDeContacto.getNombre());
			}
		}

		List<Pair<Integer, PersonaDTO>> personas = new ArrayList<Pair<Integer, PersonaDTO>>();
		for (PersonaDTO persona : this.personasEnTabla) {
			personas.add(new Pair<Integer, PersonaDTO>(persona.getTipoContacto(), persona));
		}

		List<Pair<String, PersonaDTO>> personas2 = new ArrayList<Pair<String, PersonaDTO>>();
		for (Pair<Integer, PersonaDTO> persona : personas) {
			personas2.add(new Pair<String, PersonaDTO>(tiposDeContactosByIds.get(persona.getValue1().getTipoContacto()),
					persona.getValue1()));
		}

		this.vista.llenarTabla(personas2);
	}

	private void refrescarTablaContactos() {
		this.tiposContactoEnTabla = tipoContacto.obtenerTipoContacto();
		this.vistaTiposContacto.llenarTabla(this.tiposContactoEnTabla);
	}

	private void refrescarLocalidades() {
		this.localidadesEnTabla = localidad.obtenerLocalidades();
		this.vistaLocalidades.llenarTabla(this.localidadesEnTabla);
	}
}
