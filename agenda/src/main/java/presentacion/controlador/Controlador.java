package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
	private VentanasTiposContacto ventanaTiposContacto;
	private VistaTiposContacto vistaTiposContacto;
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
	private Map<Integer, LocalidadDTO> localidadesById;
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

		this.pais = new Pais(new DAOSQLFactory());
		this.provincia = new Provincia(new DAOSQLFactory());
		this.localidad = new Localidad(new DAOSQLFactory());
		this.tipoContacto = new TipoContacto(new DAOSQLFactory());

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
		this.ventanaPersona.vaciarCampos();
		this.ventanaPersona.cambiarTituloAagregar();
		this.ventanaPersona.ApagarButton(ventanaPersona.getBtnEditar());
		this.ventanaPersona.ActivarButton(ventanaPersona.getBtnAgregarPersona());
		this.ventanaPersona.mostrarVentana();
	}

	private void ventanaEditarPersona(ActionEvent a) {
		if (this.vista.getTablaPersonas().getSelectedRows().length == 1) {
			LocalidadDTO localidad = localidadesById
					.get(this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]).getIdLocalidad());
			ProvinciaDTO provincia = provinciasById.get(localidad.getIdProvincia());
			PaisDTO pais = paisesById.get(provincia.getIdPais());

			this.setComboBoxesValues(localidad, provincia, pais);

			this.ventanaPersona.ApagarButton(this.ventanaPersona.getBtnAgregarPersona());
			this.ventanaPersona.ActivarButton(this.ventanaPersona.getBtnEditar());
			this.ventanaPersona.cambiarTituloAEditar();

			this.ventanaPersona.llenarCampos(
					this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]),
					this.tipoContacto.obtenerTipoContacto(this.personasEnTabla
							.get(this.vista.getTablaPersonas().getSelectedRows()[0]).getIdTipoContacto()));

			this.ventanaPersona.mostrarVentana();
		}
	}

	private void setComboBoxesValues(LocalidadDTO localidad, ProvinciaDTO provincia, PaisDTO pais) {
		int indexPais = -1, indexProv = -1, indexLoc = -1;
		for (int i = 0; i < this.ventanaPersona.getComboBoxPais().getItemCount(); i++) {
			if (this.ventanaPersona.getComboBoxPais().getItemAt(i).equals(pais)) {
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
				fechaCumpleanios = fechaToString(ventanaPersona.getTxtFechaCumpleanios());
		TipoContactoDTO tipoContacto = (TipoContactoDTO) ventanaPersona.getComboBoxTipoContacto().getSelectedItem();
		LocalidadDTO localidad = (LocalidadDTO) ventanaPersona.getComboBoxLocalidad().getSelectedItem();

		if (tipoContacto != null && localidad != null && fechaCumpleanios != null) {
			nuevaPersona = new PersonaDTO(0, nombre, tel, calle, piso, altura, depto, email, fechaCumpleanios,
					tipoContacto.getIdTipoContacto(), localidad.getIdLocalidad());
		}

		return nuevaPersona;
	}

	private void agregarLocalidad(ActionEvent k) {
		String pais = this.ventanaLocalidades.getTxtPais().getText();
		String provincia = this.ventanaLocalidades.getTxtProvincia().getText();
		String localidad = this.ventanaLocalidades.getTxtLocalidad().getText();
		
		int idPais = (this.pais.selectPais(pais).size() != 0 && this.pais.selectPais(pais).get(0) != null)
				? this.pais.selectPais(pais).get(0).getIdPais()
				: this.pais.agregarPais(new PaisDTO(0, pais));

		int idProvincia = (this.provincia.existeProvincia(idPais, provincia) != null)
				? this.provincia.existeProvincia(idPais, provincia).getIdProvincia()
				: this.provincia.agregarProvincia(new ProvinciaDTO(0, idPais, provincia));

		this.localidad.agregarLocalidad(new LocalidadDTO(0, idProvincia, localidad));
		this.refrescarTablaLocalidades();
		this.ventanaLocalidades.cerrar();
		this.obtenerPaises();
		cargarDatos();
		this.ventanaPersona.setValorComboBoxPais(-1);
		this.ventanaPersona.setValorComboBoxProvincia(-1);
		this.ventanaPersona.setValorComboBoxLocalidad(-1);
	}

	private void editarLocalidad(ActionEvent x) {
		LocalidadDTO localidad = this.localidadesEnTabla
				.get(this.vistaLocalidades.getTablaLocalidades().getSelectedRows()[0]);
		ProvinciaDTO provincia = this.provinciasById.get(localidad.getIdProvincia());
		PaisDTO pais = this.paisesById.get(provincia.getIdPais());

		localidad.setNombre(ventanaLocalidades.getTxtLocalidad().getText());
		provincia.setNombre(ventanaLocalidades.getTxtProvincia().getText());
		pais.setNombre(ventanaLocalidades.getTxtPais().getText());

		this.localidad.editarLocalidad(localidad);
		this.provincia.editarProvincia(provincia);
		this.pais.editarPais(pais);

		obtenerPaises();
		this.refrescarTabla();
		this.refrescarTablaLocalidades();
		this.ventanaLocalidades.cerrar();
		this.ventanaPersona.cerrar();
		this.vistaTiposContacto.cerrar();
		this.ventanaPersona.getValueprov().removeAllElements();
		this.ventanaPersona.getValueloc().removeAllElements();
		cargarDatos();
	}

	private void borrarLocalidad(ActionEvent p) {
		int[] filasSeleccionadas = this.vistaLocalidades.getTablaLocalidades().getSelectedRows();

		if (filasSeleccionadas.length > 1) {
			return;
		}

		Map<Integer, Integer> cantidadLocalidadesPorProvincia = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> cantidadProvinciasPorPais = new TreeMap<Integer, Integer>();
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

		for (int fila : filasSeleccionadas) {
			if (this.perteneceLocalidad(this.localidadesEnTabla.get(fila).getIdLocalidad())) {
				this.ventanaLocalidades.mostrarMensajeError();
			}

			else {
				this.localidad.borrarLocalidad((this.localidadesEnTabla.get(fila)));

				if (cantidadLocalidadesPorProvincia.get(this.localidadesEnTabla.get(fila).getIdProvincia()) == 1) {
					this.provincia
							.borrarProvincia(provinciasById.get(this.localidadesEnTabla.get(fila).getIdProvincia()));
				}

				if (cantidadProvinciasPorPais.get(
						provinciasById.get((this.localidadesEnTabla.get(fila)).getIdProvincia()).getIdPais()) == 1) {
					this.pais.borrarPais(paisesById
							.get(provinciasById.get((this.localidadesEnTabla.get(fila)).getIdProvincia()).getIdPais()));
				}
			}
		}
		this.ventanaPersona.setValorComboBoxPais(-1);
		this.ventanaPersona.setValorComboBoxProvincia(-1);
		this.ventanaPersona.setValorComboBoxLocalidad(-1);
		this.ventanaPersona.getValuepais().removeAllElements();
		this.ventanaPersona.getValuepais().addAll(pais.obtenerTodosPaises());
		this.ventanaPersona.getValueprov().removeAllElements();
		this.ventanaPersona.getValueloc().removeAllElements();
		this.refrescarTablaLocalidades();
		cargarDatos();
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

		if (!nombresTiposContactos.contains(tipoContacto.getNombre())) {
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

		this.agenda.editarPersona(persona);
		this.refrescarTabla();
		this.ventanaPersona.cerrar();
	}

	private PersonaDTO copiarPersona() {
		PersonaDTO persona = this.personasEnTabla.get(this.vista.getTablaPersonas().getSelectedRows()[0]);
		persona.setIdPersona(persona.getIdPersona());
		persona.setNombre(ventanaPersona.getTxtNombre().getText());
		persona.setCalle(ventanaPersona.getTxtCalle().getText());
		persona.setAltura(ventanaPersona.getTxtAltura().getText());
		persona.setPiso(ventanaPersona.getTxtPiso().getText());
		persona.setTelefono(ventanaPersona.getTxtTelefono().getText());
		persona.setDepto(ventanaPersona.getTxtDepto().getText());
		persona.setEmail(ventanaPersona.getTxtEmail().getText());
		persona.setFechaCumpleanios(this.fechaToString(ventanaPersona.getTxtFechaCumpleanios()));
		TipoContactoDTO tp = (TipoContactoDTO) ventanaPersona.getComboBoxTipoContacto().getSelectedItem();
		persona.setIdTipoContacto(tp.getIdTipoContacto());
		LocalidadDTO localidad = (LocalidadDTO) ventanaPersona.getComboBoxLocalidad().getSelectedItem();
		persona.setIdLocalidad(localidad.getIdLocalidad());

		return persona;
	}

	private void guardarTipoDeContacto(ActionEvent s) {
		TipoContactoDTO nuevoTipo = new TipoContactoDTO(0, this.vistaTiposContacto.getTxtNombre().getText());

		List<String> nombresTiposContactos = new ArrayList<String>();
		for (TipoContactoDTO tc : this.tipoContacto.obtenerTodosLosTiposContactos()) {
			nombresTiposContactos.add(tc.getNombre());
		}
		if (!nombresTiposContactos.contains(nuevoTipo.getNombre())) {
			this.tipoContacto.agregarTipoContacto(nuevoTipo);
			this.refrescarTablaContactos();
			this.vistaTiposContacto.cerrar();
			obtenerTiposDeContacto();
			cargarDatos();
		}
	}

	private void mostrarReporte(ActionEvent r) {
		ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerTodasLasPersonas());
		reporte.mostrar();
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
			if (this.perteneceTipoContacto(this.tiposContactoEnTabla.get(fila).getIdTipoContacto())) {
				this.vistaTiposContacto.mostrarMensajeError();
			} else
				this.tipoContacto.borrarTipoContacto((this.tiposContactoEnTabla.get(fila)));
		}
		obtenerTiposDeContacto();
		cargarDatos();
		this.refrescarTablaContactos();
	}

	private boolean perteneceTipoContacto(int id) {
		boolean ret = false;
		for (int i = 0; i < this.agenda.obtenerTodasLasPersonas().size(); i++) {
			if (this.agenda.obtenerTodasLasPersonas().get(i).getIdTipoContacto() == id) {
				ret = true;
			}
		}
		return ret;
	}

	private boolean perteneceLocalidad(int id) {
		boolean ret = false;
		for (int i = 0; i < this.agenda.obtenerTodasLasPersonas().size(); i++) {
			if (this.agenda.obtenerTodasLasPersonas().get(i).getIdLocalidad() == id) {
				ret = true;
			}
		}
		return ret;
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
		this.ventanaPersona.getDefaultComboBoxModelValue().addAll(tipoContacto.obtenerTodosLosTiposContactos());
	}

	private void obtenerPaises() {
		this.ventanaPersona.getValuepais().removeAllElements();
		this.ventanaPersona.getValuepais().addAll(pais.obtenerTodosPaises());

		this.ventanaPersona.getComboBoxPais().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ventanaPersona.getComboBoxPais().getSelectedItem() != null) {
					ventanaPersona.setValorComboBoxProvincia(-1);
					ventanaPersona.setValorComboBoxLocalidad(-1);

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
			personas.add(new Pair<String, PersonaDTO>(tiposDeContactosByIds.get(persona.getIdTipoContacto()), persona));
		}

		this.vista.llenarTabla(personas, provinciasById, tiposDeContactosByIds, localidadesById, paisesById);
	}

	private void refrescarTablaContactos() {
		this.tiposContactoEnTabla = tipoContacto.obtenerTodosLosTiposContactos();
		this.ventanaTiposContacto.llenarTabla(this.tiposContactoEnTabla);
	}

	private void refrescarTablaLocalidades() {
		this.localidadesEnTabla = localidad.obtenerTodasLocalidades();
		List<Triplet<String, String, String>> localidades = getLocalidadesWithNames(this.localidadesEnTabla);
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

	private List<Triplet<String, String, String>> getLocalidadesWithNames(List<LocalidadDTO> localidades) {
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

		List<Triplet<String, String, String>> localidadesWithNames = new ArrayList<Triplet<String, String, String>>();
		for (LocalidadDTO localidad : localidades) {
			localidadesWithNames.add(new Triplet<String, String, String>(
					paisesByName.get(provinciasIDsByPaisID.get(localidad.getIdProvincia())),
					provinciasByName.get(localidad.getIdProvincia()), localidad.getNombre()));
		}
		return localidadesWithNames;
	}

	private void cargarDatos() {
		localidadesById = new TreeMap<Integer, LocalidadDTO>();
		for (LocalidadDTO localidad : this.localidad.obtenerTodasLocalidades()) {
			localidadesById.put(localidad.getIdLocalidad(), localidad);
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

	public void inicializar() {
		this.refrescarTabla();
		this.inicializarLocalidades();
		this.inicializarTipoContacto();
		this.vista.show();
	}
}
