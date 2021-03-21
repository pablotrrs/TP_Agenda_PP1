package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import com.toedter.calendar.JDateChooser;
import modelo.Agenda;
import modelo.Localidad;
import modelo.Pais;
import modelo.Provincia;
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
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
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
	private Provincia provincia;
	private Pais pais;
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

		this.pais = new Pais(new DAOSQLFactory());
		this.provincia = new Provincia(new DAOSQLFactory());
		this.localidad = new Localidad(new DAOSQLFactory());

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
			List<LocalidadDTO> localidades = this.localidad.obtenerTodasLocalidades();
			Map<Integer, LocalidadDTO> localidadesById = new TreeMap<Integer, LocalidadDTO>();
			for (LocalidadDTO localidad : localidades) {
				localidadesById.put(localidad.getIdLocalidad(), localidad);
			}

			List<ProvinciaDTO> provincias = this.provincia.obtenerTodasProvincias();
			Map<Integer, ProvinciaDTO> provinciasById = new TreeMap<Integer, ProvinciaDTO>();
			for (ProvinciaDTO provincia : provincias) {
				provinciasById.put(provincia.getIdProvincia(), provincia);
			}

			List<PaisDTO> paises = this.pais.obtenerTodosPaises();
			Map<Integer, PaisDTO> paisesById = new TreeMap<Integer, PaisDTO>();
			for (PaisDTO pais : paises) {
				paisesById.put(pais.getIdPais(), pais);
			}
//----------------------------------------------------------------------------------------------------------------------------------------------
			LocalidadDTO loc = localidadesById
					.get(this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]).getLocalidad());
			
			ProvinciaDTO prov = provinciasById.get(loc.getIdProvincia());
			PaisDTO pai = paisesById.get(prov.getIdPais());

			int indexPais = -1, indexProv = -1, indexLoc = -1;
			for (int i = 0; i < this.ventanaPersona.getComboBox_pais().getItemCount(); i++) {
				System.out.println("pais: " + pais);
				if (this.ventanaPersona.getComboBox_pais().getItemAt(i).equals(pai)) {
					indexPais = i;
				}
			}
			this.ventanaPersona.getComboBox_pais().setSelectedIndex(indexPais);

			System.out.println("indexPais: " + indexPais);

			for (int i = 0; i < this.ventanaPersona.getComboBox_provincia().getItemCount(); i++) {
				if (this.ventanaPersona.getComboBox_provincia().getItemAt(i).equals(prov)) {
					indexProv = i;
				}

			}
			this.ventanaPersona.getComboBox_provincia().setSelectedIndex(indexProv);
			System.out.println("indexProv: " + indexProv);

			for (int i = 0; i < this.ventanaPersona.getComboBox_localidad().getItemCount(); i++) {
				if (this.ventanaPersona.getComboBox_localidad().getItemAt(i).equals(loc)) {
					indexLoc = i;
				}

			}
			this.ventanaPersona.getComboBox_localidad().setSelectedIndex(indexLoc);
			System.out.println("indexLoc: " + indexLoc);

			this.ventanaPersona.getBtnAgregarPersona().setVisible(false);
			this.ventanaPersona.getBtnEditar().setVisible(true);
			this.ventanaPersona.setTitle("Editar Persona");

			this.ventanaPersona.llenarCampos(
					this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]),
					this.tipoContacto.obtenerTipoContacto(this.personasEnTabla
							.get(this.vista.getTablaPersonas().getSelectedRows()[0]).getTipoContacto()));

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

			LocalidadDTO localidad = this.localidadesEnTabla
					.get(this.vistaLocalidades.getTablaLocalidades().getSelectedRows()[0]);
			List<LocalidadDTO> localidades = new ArrayList<>();
			localidades.add(localidad);
			this.ventanaLocalidades.llenarCamposLocalidad(getLocalidadesWithNames(localidades));
			System.out.println("****getLocalidadesWithNames(localidades): " + getLocalidadesWithNames(localidades));

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
				fechaCumpleanios = toStringFecha(ventanaPersona.getTxtFechaCumpleanios());
		TipoContactoDTO tipoContacto = (TipoContactoDTO) ventanaPersona.getComboBoxTipoContacto().getSelectedItem();
		LocalidadDTO localidad = (LocalidadDTO) ventanaPersona.getComboBox_localidad().getSelectedItem();

		if (tipoContacto != null && localidad != null && fechaCumpleanios != null) {
			PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel, calle, piso, altura, depto, email,
					fechaCumpleanios, tipoContacto.getIdTipoContacto(), localidad.getIdLocalidad());

			this.agenda.agregarPersona(nuevaPersona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();

		}
	}

	private void agregarLocalidad(ActionEvent k) {
		String pais = this.ventanaLocalidades.getTxtPais().getText();
		String provincia = this.ventanaLocalidades.getTxtProvincia().getText();
		String localidad = this.ventanaLocalidades.getTxtLocalidad().getText();

		PaisDTO nuevopais = new PaisDTO(0, pais);
		ProvinciaDTO nuevaprovincia = new ProvinciaDTO(0, this.pais.agregarPais(nuevopais), provincia);
		LocalidadDTO nuevalocalidad = new LocalidadDTO(0, this.provincia.agregarProvincia(nuevaprovincia), localidad);

		this.localidad.agregarLocalidad(nuevalocalidad);

		this.refrescarLocalidades();
		this.ventanaLocalidades.cerrar();
		this.obtenerPaises();
		// this.obtenerProvincias();
		// this.obtenerLocalidades();
	}

	private void editarLocalidad(ActionEvent x) {
		LocalidadDTO localidad = this.localidadesEnTabla
				.get(this.vistaLocalidades.getTablaLocalidades().getSelectedRows()[0]);
		List<ProvinciaDTO> provincia = this.provincia.obtenerProvincia(String.valueOf(localidad.getIdProvincia()));
		List<PaisDTO> pais = this.pais.obtenerPais(String.valueOf(String.valueOf(provincia.get(0).getIdPais())));

		localidad.setNombre(ventanaLocalidades.getTxtLocalidad().getText());
		provincia.get(0).setNombre(ventanaLocalidades.getTxtProvincia().getText());
		pais.get(0).setNombre(ventanaLocalidades.getTxtPais().getText());

		System.out.println("****localidad Editada: " + localidad);
		System.out.println("****provincia Editada: " + provincia.get(0));
		System.out.println("****pais Editada: " + pais.get(0));

		this.localidad.editarLocalidad(localidad);
		this.provincia.editarProvincia(provincia.get(0));
		this.pais.editarPais(pais.get(0));

		obtenerPaises();
		// obtenerProvincias();
		// obtenerLocalidades();}
		this.refrescarTabla();
		this.refrescarLocalidades();
		this.ventanaLocalidades.cerrar();
		this.ventanaPersona.cerrar();
		this.ventanaTiposContacto.cerrar();
	}

	private void borrarLocalidad(ActionEvent p) {
		List<ProvinciaDTO> provincias = this.provincia.obtenerTodasProvincias();
		Map<Integer, ProvinciaDTO> provinciasById = new TreeMap<Integer, ProvinciaDTO>();
		for (ProvinciaDTO provincia : provincias) {
			provinciasById.put(provincia.getIdProvincia(), provincia);
		}

		List<PaisDTO> paises = this.pais.obtenerTodosPaises();
		Map<Integer, PaisDTO> paisesById = new TreeMap<Integer, PaisDTO>();
		for (PaisDTO pais : paises) {
			paisesById.put(pais.getIdPais(), pais);
		}

		int[] filasSeleccionadas = this.vistaLocalidades.getTablaLocalidades().getSelectedRows();
		for (int fila : filasSeleccionadas) {

			if (this.perteneceLocalidad(this.localidadesEnTabla.get(fila).getIdLocalidad())) {
				JOptionPane.showMessageDialog(ventanaLocalidades,
						"Debe cambiar la localidad de las personas que poseen esta localidad para borrarla.");
			} else {

				this.localidad.borrarLocalidad((this.localidadesEnTabla.get(fila)));

				this.provincia
						.borrarProvincia(provinciasById.get((this.localidadesEnTabla.get(fila)).getIdProvincia()));
				this.pais.borrarPais(paisesById
						.get(provinciasById.get((this.localidadesEnTabla.get(fila)).getIdProvincia()).getIdPais()));
			}
		}
		this.ventanaPersona.getValuepais().removeAllElements();
		this.ventanaPersona.getValuepais().addAll(pais.obtenerTodosPaises());
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
		for (TipoContactoDTO tc : this.tipoContacto.obtenerTodosTiposContactos()) {
			nombresTiposContactos.add(tc.getNombre());
		}
		System.out.println(nombresTiposContactos);
		if (!nombresTiposContactos.contains(tipoContacto.getNombre())) {
			this.tipoContacto.editarTipoContacto(tipoContacto);
			this.refrescarTablaContactos();
			this.refrescarTabla();
			this.ventanaTiposContacto.cerrar();
			this.ventanaPersona.cerrar();
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
			persona.setFechaCumpleanios(this.toStringFecha(ventanaPersona.getTxtFechaCumpleanios()));
			TipoContactoDTO tp = (TipoContactoDTO) ventanaPersona.getComboBoxTipoContacto().getSelectedItem();
			persona.setTipoContacto(tp.getIdTipoContacto());
			LocalidadDTO localidad = (LocalidadDTO) ventanaPersona.getComboBox_localidad().getSelectedItem();
			persona.setLocalidad(localidad.getIdLocalidad());
		}

		this.agenda.editarPersona(persona);
		this.refrescarTabla();
		this.ventanaPersona.cerrar();
	}

	private void guardarTipoDeContacto(ActionEvent s) {
		String nombre = this.ventanaTiposContacto.getTxtNombre().getText();

		TipoContactoDTO nuevoTipo = new TipoContactoDTO(0, nombre);

		List<String> nombresTiposContactos = new ArrayList<String>();
		for (TipoContactoDTO tc : this.tipoContacto.obtenerTodosTiposContactos()) {
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
			if (this.perteneceTipoContacto(this.tiposContactoEnTabla.get(fila).getIdTipoContacto())) {
				JOptionPane.showMessageDialog(ventanaTiposContacto,
						"Debe cambiar el tipo de contacto de las personas que poseen este tipo de contacto para borrarlo.");
			} else
				this.tipoContacto.borrarTipoContacto((this.tiposContactoEnTabla.get(fila)));
		}
		obtenerTiposDeContacto();

		this.refrescarTablaContactos();
	}

	public boolean perteneceTipoContacto(int id) {
		boolean ret = false;
		for (int i = 0; i < this.agenda.obtenerPersonas().size(); i++) {
			if (this.agenda.obtenerPersonas().get(i).getTipoContacto() == id) {
				ret = true;
			}
		}
		return ret;
	}

	public boolean perteneceLocalidad(int id) {
		boolean ret = false;
		for (int i = 0; i < this.agenda.obtenerPersonas().size(); i++) {
			if (this.agenda.obtenerPersonas().get(i).getLocalidad() == id) {
				ret = true;
			}
		}
		return ret;
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

		this.tiposContactoEnTabla = tipoContacto.obtenerTodosTiposContactos();
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

		this.localidadesEnTabla = localidad.obtenerTodasLocalidades();
		obtenerPaises();
		this.refrescarLocalidades();
	}

	public void obtenerTiposDeContacto() {
		this.ventanaPersona.getDefaultComboBoxModelValue().removeAllElements();
		this.ventanaPersona.getDefaultComboBoxModelValue().addAll(tipoContacto.obtenerTodosTiposContactos());
	}

	public void obtenerPaises() {
		this.ventanaPersona.getValuepais().removeAllElements();
		this.ventanaPersona.getValuepais().addAll(pais.obtenerTodosPaises());

		this.ventanaPersona.getComboBox_pais().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ventanaPersona.getComboBox_pais().getSelectedItem() != null) {
					ventanaPersona.getComboBox_provincia().setSelectedIndex(-1);
					ventanaPersona.getComboBox_localidad().setSelectedIndex(-1);

					PaisDTO pais = (PaisDTO) ventanaPersona.getComboBox_pais().getSelectedItem();
					obtenerProvincias(String.valueOf(pais.getIdPais()));
				}
			}
		});
	}

	public void obtenerProvincias(String idPais) {
		this.ventanaPersona.getValueprov().removeAllElements();
		this.provincia.dameProvinciasDelPais(idPais);

		this.ventanaPersona.getComboBox_provincia().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ventanaPersona.getComboBox_provincia().getSelectedItem() != null) {
					ProvinciaDTO provincia = (ProvinciaDTO) ventanaPersona.getComboBox_provincia().getSelectedItem();
					obtenerLocalidades(String.valueOf(provincia.getIdProvincia()));
				}
			}
		});
		this.ventanaPersona.getValueprov().addAll(this.provincia.dameProvinciasDelPais(idPais));

	}

	public void obtenerLocalidades(String idProvincia) {
		this.ventanaPersona.getValueloc().removeAllElements();
		this.ventanaPersona.getValueloc().addAll(this.localidad.dameLocalidadesDeLaProvincia(idProvincia));

	}

	private void refrescarTabla() {
		this.personasEnTabla = agenda.obtenerPersonas();

		List<LocalidadDTO> localidades = this.localidad.obtenerTodasLocalidades();
		Map<Integer, LocalidadDTO> localidadesById = new TreeMap<Integer, LocalidadDTO>();
		for (LocalidadDTO localidad : localidades) {
			localidadesById.put(localidad.getIdLocalidad(), localidad);
		}

		List<ProvinciaDTO> provincias = this.provincia.obtenerTodasProvincias();
		Map<Integer, ProvinciaDTO> provinciasById = new TreeMap<Integer, ProvinciaDTO>();
		for (ProvinciaDTO provincia : provincias) {
			provinciasById.put(provincia.getIdProvincia(), provincia);
		}

		List<PaisDTO> paises = this.pais.obtenerTodosPaises();
		Map<Integer, PaisDTO> paisesById = new TreeMap<Integer, PaisDTO>();
		for (PaisDTO pais : paises) {
			paisesById.put(pais.getIdPais(), pais);
		}

		this.tipoContacto = new TipoContacto(new DAOSQLFactory());
		Map<Integer, String> tiposDeContactosByIds = new LinkedHashMap<Integer, String>();
		for (TipoContactoDTO tipoDeContacto : this.tipoContacto.obtenerTodosTiposContactos()) {
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

		this.vista.llenarTabla(personas2, provinciasById, tiposDeContactosByIds, localidadesById, paisesById);
	}

	private void refrescarTablaContactos() {
		this.tiposContactoEnTabla = tipoContacto.obtenerTodosTiposContactos();
		this.vistaTiposContacto.llenarTabla(this.tiposContactoEnTabla);
	}

	private void refrescarLocalidades() {
		this.localidadesEnTabla = localidad.obtenerTodasLocalidades();

		List<Triplet<String, String, String>> localidades = getLocalidadesWithNames(this.localidadesEnTabla);

		this.vistaLocalidades.llenarTabla(localidades);
	}

	public List<Triplet<String, String, String>> getLocalidadesWithNames(List<LocalidadDTO> localidades) {
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

		// System.out.println("provinciasByName: " + provinciasByName);
		// System.out.println("paisesByName: " + paisesByName);
		// System.out.println("****localidadesEnTabla: " + localidadesEnTabla);

		List<Triplet<String, String, String>> localidadesWithNames = new ArrayList<Triplet<String, String, String>>();
		for (LocalidadDTO localidad : localidades) {
			localidadesWithNames.add(new Triplet<String, String, String>(
					paisesByName.get(provinciasIDsByPaisID.get(localidad.getIdProvincia())),
					provinciasByName.get(localidad.getIdProvincia()), localidad.getNombre()));
		}
		return localidadesWithNames;
	}
}
