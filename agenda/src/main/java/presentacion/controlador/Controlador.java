package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;

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

public class Controlador implements ActionListener
{
		private Vista vista;
		private VentanasTiposContacto vistaTiposContacto;
		private List<PersonaDTO> personasEnTabla;
		private List<TipoContactoDTO> tiposContactoEnTabla;
		private VentanaPersona ventanaPersona; 
		private VistaTiposContacto ventanaTiposContacto;
		private Agenda agenda;
		private TipoContacto tipoContacto;
	
		
		
		public Controlador(Vista vista, Agenda agenda)
		{
			this.vista = vista;
			
			this.vistaTiposContacto = new VentanasTiposContacto();
			this.ventanaTiposContacto = new VistaTiposContacto();
			
			this.vistaTiposContacto.getBtnEditar().addActionListener(v->ventanaEditarTipoContacto(v));
			this.ventanaTiposContacto.getBtnEditar().addActionListener(m->EditarTipoContacto(m));
			
			this.vista.getBtnAgregar().addActionListener(a->ventanaAgregarPersona(a));
			this.vista.getBtnEditar().addActionListener(z->ventanaEditarPersona(z));
						
			this.vista.getBtnBorrar().addActionListener(s->borrarPersona(s));
			this.vista.getBtnReporte().addActionListener(r->mostrarReporte(r));
			
			
			this.vista.getABMtiposContacto().addActionListener(t->ventanaTiposContacto(t));
			this.vistaTiposContacto.getBtnAgregar().addActionListener(b->ventanaAgregarTiposContacto(b));
			
			  
			this.ventanaPersona = VentanaPersona.getInstance();
			
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(p->guardarPersona(p));
			
			this.ventanaPersona.getBtnEditar().addActionListener(f->EditarPersona(f));
			
			
			this.vistaTiposContacto.getBtnBorrar().addActionListener(d->borrarTipoContacto(d));
			
			
			this.ventanaTiposContacto.getBtnAgregarTipoDeContacto().addActionListener(s->guardarTipoDeContacto(s));
			tipoContacto = new TipoContacto(new DAOSQLFactory());
			this.agenda = agenda;
			this.refrescarTablaContactos();

		}
		
		

		

		private void ventanaTiposContacto(ActionEvent c) {
			this.vistaTiposContacto.mostrarVentanaTiposContacto();
		}
		
		private void ventanaAgregarTiposContacto(ActionEvent c) {
			this.ventanaTiposContacto.setTitle("Agregar Tipo");
			this.ventanaTiposContacto.getBtnEditar().setVisible(false);
			this.ventanaTiposContacto.getBtnAgregarTipoDeContacto().setVisible(true);
			this.ventanaTiposContacto.mostrarVentanaTiposContacto();
		}
		
		private void ventanaAgregarPersona(ActionEvent a) {
			this.ventanaPersona.setTitle("Agregar Persona");
			this.ventanaPersona.getBtnEditar().setVisible(false);
			this.ventanaPersona.getBtnAgregarPersona().setVisible(true);
			this.ventanaPersona.mostrarVentana();
		}
		private void ventanaEditarPersona(ActionEvent a) {
			if(this.vista.getTablaPersonas().getSelectedRows().length == 1) {
			this.ventanaPersona.getBtnAgregarPersona().setVisible(false);
			this.ventanaPersona.getBtnEditar().setVisible(true);
			this.ventanaPersona.setTitle("Editar Persona");
			
			this.ventanaPersona.llenarCampos(this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]));
			
			this.ventanaPersona.mostrarVentana();
			
			}
		}
		private void ventanaEditarTipoContacto(ActionEvent x) {
			if(this.vistaTiposContacto.getTablaTiposContacto().getSelectedRows().length == 1) {
				this.ventanaTiposContacto.getBtnAgregarTipoDeContacto().setVisible(false);
				this.ventanaTiposContacto.getBtnEditar().setVisible(true);
				this.ventanaTiposContacto.setTitle("Editar Tipo");
				
				this.ventanaTiposContacto.llenarCamposContacto(this.tiposContactoEnTabla.get(this.vistaTiposContacto.getTablaTiposContacto().getSelectedRows()[0]));
				this.ventanaTiposContacto.mostrarVentanaTiposContacto();
			}
		}
		
		public String toStringFecha(JDateChooser fecha) {
			if(fecha.getCalendar() == null) {
				return null;
			}
					
			
			String dia = Integer.toString(fecha.getCalendar().get(Calendar.DAY_OF_MONTH));
			String mes = Integer.toString(fecha.getCalendar().get(Calendar.MONTH) + 1);
			String year = Integer.toString(fecha.getCalendar().get(Calendar.YEAR));
			String date = (year + "-" + mes+ "-" + dia);
			return date;
		}

		private void guardarPersona(ActionEvent p) {
			String nombre = this.ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			String calle = ventanaPersona.getTxtCalle().getText();
			String piso = ventanaPersona.getTxtPiso().getText();
			String altura = ventanaPersona.getTxtAltura().getText();
			String depto = ventanaPersona.getTxtDepto().getText();
			String email = ventanaPersona.getTxtEmail().getText();
			String fecha_cumpleaños = toStringFecha(ventanaPersona.getTxtFecha_cumpleaños());
			
	
			
			PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel,calle,piso,altura,depto,email,fecha_cumpleaños);
			
			this.agenda.agregarPersona(nuevaPersona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
		
		private void EditarTipoContacto(ActionEvent l) {
			
		
			TipoContactoDTO tipoContacto = null;
			int[] filasSeleccionadas = this.vistaTiposContacto.getTablaTiposContacto().getSelectedRows();
			for (int fila : filasSeleccionadas)
			{
				
				
				tipoContacto = this.tiposContactoEnTabla.get(fila);
				
				tipoContacto.setIdTipoContacto(this.tiposContactoEnTabla.get(fila).getIdTipoContacto());
				tipoContacto.setNombre(ventanaTiposContacto.getTxtNombre().getText());
		}
			this.tipoContacto.editarTipoContacto(tipoContacto);
			this.refrescarTablaContactos();
			this.ventanaTiposContacto.cerrar();
			
		}
		
		private void EditarPersona(ActionEvent z)
		{
			
			PersonaDTO persona = null;
			int[] filasSeleccionadas = this.vista.getTablaPersonas().getSelectedRows();
			for (int fila : filasSeleccionadas)
			{
				
				
				persona = this.personasEnTabla.get(fila);
				
				persona.setIdPersona(this.personasEnTabla.get(fila).getIdPersona());
				persona.setNombre(ventanaPersona.getTxtNombre().getText());
				persona.setCalle(ventanaPersona.getTxtCalle().getText());
				persona.setAltura(ventanaPersona.getTxtAltura().getText());
				persona.setPiso(ventanaPersona.getTxtPiso().getText());
				persona.setTelefono(ventanaPersona.getTxtTelefono().getText());
				persona.setDepto(ventanaPersona.getTxtDepto().getText());
				persona.setEmail(ventanaPersona.getTxtEmail().getText());
				
				persona.setFecha_cumpleaños(this.toStringFecha(ventanaPersona.getTxtFecha_cumpleaños()));
				
			}
			
			
			this.agenda.editarPersona(persona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
			
				 
		}
		
		private void guardarTipoDeContacto(ActionEvent s) {
			
			
			String nombre = this.ventanaTiposContacto.getTxtNombre().getText();
			
			TipoContactoDTO nuevoTipo = new TipoContactoDTO(0, nombre);
			
			this.tipoContacto.agregarTipoContacto(nuevoTipo);
			this.refrescarTablaContactos();
			this.ventanaTiposContacto.cerrar();
			obtenerTiposDeContacto();
		}

		private void mostrarReporte(ActionEvent r) {
			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
			reporte.mostrar();	
		}

		public void borrarPersona(ActionEvent s)
		{
			int[] filasSeleccionadas = this.vista.getTablaPersonas().getSelectedRows();
			for (int fila : filasSeleccionadas)
			{
				this.agenda.borrarPersona(this.personasEnTabla.get(fila));
			}
			
			this.refrescarTabla();
		}
		public void borrarTipoContacto(ActionEvent s)
		
		{
			
			int[] filasSeleccionadas = this.vistaTiposContacto.getTablaTiposContacto().getSelectedRows();
			for (int fila : filasSeleccionadas)
			{
				this.tipoContacto.borrarTipoContacto((this.tiposContactoEnTabla.get(fila)));
			}
			
			this.refrescarTablaContactos();
		}
		
		public void inicializar()
		{
			this.refrescarTabla();
			this.vista.show();
			this.tiposContactoEnTabla = tipoContacto.obtenerTipoContacto();
			
			obtenerTiposDeContacto();
		}

		public void obtenerTiposDeContacto() {
			this.ventanaPersona.getDefaultComboBoxModelValue().removeAllElements();
			this.ventanaPersona.getDefaultComboBoxModelValue().addAll(tipoContacto.obtenerTipoContacto());
		}
		
		private void refrescarTabla()
		{
			
			this.personasEnTabla = agenda.obtenerPersonas();
			this.vista.llenarTabla(this.personasEnTabla);
		}

		
		private void refrescarTablaContactos()
		{	
			this.tiposContactoEnTabla = tipoContacto.obtenerTipoContacto();
			this.vistaTiposContacto.llenarTabla(this.tiposContactoEnTabla);
		}

		@Override
		public void actionPerformed(ActionEvent e) { }
		
}
