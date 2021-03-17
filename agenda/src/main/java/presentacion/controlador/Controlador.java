package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import modelo.Agenda;
import modelo.TipoContacto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.ReporteAgenda;
import presentacion.vista.VentanaPersona;
import presentacion.vista.VistaTiposContacto;
import presentacion.vista.Vista;
import presentacion.vista.VentanasTiposContacto;
import dto.PersonaDTO;
import dto.TipoContactoDTO;

public class Controlador {
	private Vista vista;
	private VentanaPersona ventanaPersona;
	private VentanasTiposContacto vistaTiposContacto;
	private VistaTiposContacto ventanaTiposContacto;
	private Agenda agenda;
	private TipoContacto tipoContacto;
	private List<PersonaDTO> personasEnTabla;
	private List<TipoContactoDTO> tiposContactoEnTabla;

	public Controlador(Vista vista, Agenda agenda) {
		this.vista = vista;
		this.vista.getBtnAgregar().addActionListener(a -> ventanaAgregarPersona(a));
		this.vista.getBtnEditar().addActionListener(z -> ventanaEditarPersona(z));
		this.vista.getBtnBorrar().addActionListener(s -> borrarPersona(s));
		this.vista.getBtnReporte().addActionListener(r -> mostrarReporte(r));
		this.vista.getABMtiposContacto().addActionListener(t -> ventanaTiposContacto(t));
		this.ventanaPersona = VentanaPersona.getInstance();
		this.ventanaPersona.getBtnAgregarPersona().addActionListener(p -> guardarPersona(p));
		this.ventanaPersona.getBtnEditar().addActionListener(f -> EditarPersona(f));

		this.agenda = agenda;
	}

	private void ventanaTiposContacto(ActionEvent c) {
		this.vistaTiposContacto.mostrarVentanaTiposContacto();
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
				fechaCumpleanios = toStringFecha(ventanaPersona.getTxtFecha_cumpleaños()),
				tipoContacto = (ventanaPersona.getComboBoxTipoContacto().getSelectedItem() == null) ? null
						: ventanaPersona.getComboBoxTipoContacto().getSelectedItem().toString();

		System.out.println("****tipoContato: " + ventanaPersona.getComboBoxTipoContacto().getSelectedItem());
		if (tipoContacto != null) {
			PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel, calle, piso, altura, depto, email,
					fechaCumpleanios, tipoContacto);

			this.agenda.agregarPersona(nuevaPersona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
	}

	private void EditarTipoContacto(ActionEvent l) {
		TipoContactoDTO tipoContacto = null;
		int[] filasSeleccionadas = this.vistaTiposContacto.getTablaTiposContacto().getSelectedRows();

		for (int fila : filasSeleccionadas) {
			tipoContacto = this.tiposContactoEnTabla.get(fila);
			tipoContacto.setNombre(ventanaTiposContacto.getTxtNombre().getText());
		}

		this.tipoContacto.editarTipoContacto(tipoContacto);
		this.refrescarTablaContactos();
		this.ventanaTiposContacto.cerrar();
	}

	private void EditarPersona(ActionEvent z) {
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

		TipoContactoDTO nuevoTipo = new TipoContactoDTO(nombre);

		this.tipoContacto.agregarTipoContacto(nuevoTipo);
		this.refrescarTablaContactos();
		this.ventanaTiposContacto.cerrar();
		obtenerTiposDeContacto();
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

	public void inicializar() {
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

	public void obtenerTiposDeContacto() {
		this.ventanaPersona.getDefaultComboBoxModelValue().removeAllElements();
		List<String> tiposContactosString = new ArrayList<String>();
		for(TipoContactoDTO tipoContacto : tipoContacto.obtenerTipoContacto()) {
			tiposContactosString.add(tipoContacto.toString());
		}
		this.ventanaPersona.getDefaultComboBoxModelValue().addAll(tiposContactosString);
	}
	
	private void refrescarTabla() {

		this.personasEnTabla = agenda.obtenerPersonas();
		this.vista.llenarTabla(this.personasEnTabla);
	}

	private void refrescarTablaContactos() {
		this.tiposContactoEnTabla = tipoContacto.obtenerTipoContacto();
		this.vistaTiposContacto.llenarTabla(this.tiposContactoEnTabla);
	}
}
