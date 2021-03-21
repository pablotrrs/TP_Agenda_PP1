package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.Color;
import java.util.GregorianCalendar;
import com.toedter.calendar.JDateChooser;
import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
import dto.TipoContactoDTO;
import javax.swing.DefaultComboBoxModel;

public class VentanaPersona extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono, txtCalle, txtPiso, txtAltura, txtDepto;
	private JDateChooser fecha_cumpleaños;
	private JButton btnAgregarPersona;
	private JButton btnEditar;
	private static VentanaPersona INSTANCE;
	private JTextField txtEmail;
	private JComboBox<TipoContactoDTO> comboBox_tipo_contacto;
	private DefaultComboBoxModel<TipoContactoDTO> value;
	private DefaultComboBoxModel<PaisDTO> valuepais;
	private DefaultComboBoxModel<ProvinciaDTO> valueprov;
	private DefaultComboBoxModel<LocalidadDTO> valueloc;
	private JComboBox<PaisDTO> comboBox_pais;
	private JLabel lblProvincia;
	private JComboBox<ProvinciaDTO> comboBox_provincia;
	private JLabel lblLocalidad;
	private JComboBox<LocalidadDTO> comboBox_localidad;

	public static VentanaPersona getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VentanaPersona();
			return new VentanaPersona();
		} else
			return INSTANCE;
	}

	private VentanaPersona() {
		super();
		setBackground(Color.WHITE);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 355, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 319, 396);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNombreYApellido = new JLabel("Nombre y apellido");
		lblNombreYApellido.setBounds(209, 113, 35, 14);
		panel.add(lblNombreYApellido);

		JLabel lblTelfono = new JLabel("Telefono");
		lblTelfono.setBounds(10, 42, 113, 14);
		panel.add(lblTelfono);

		JLabel lblCalle = new JLabel("Calle");
		lblCalle.setBounds(10, 77, 113, 14);
		panel.add(lblCalle);

		JLabel lblAltura = new JLabel("Altura");
		lblAltura.setBounds(10, 113, 113, 14);
		panel.add(lblAltura);

		JLabel lblDepto = new JLabel("Depto");
		lblDepto.setBounds(10, 180, 113, 14);
		panel.add(lblDepto);
		lblNombreYApellido.setBounds(10, 11, 113, 14);

		txtNombre = new JTextField();
		txtNombre.setBounds(133, 8, 164, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(133, 39, 164, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);

		txtCalle = new JTextField();
		txtCalle.setBounds(133, 77, 164, 20);
		panel.add(txtCalle);
		txtCalle.setColumns(10);

		txtPiso = new JTextField();
		txtPiso.setBounds(133, 141, 164, 20);
		panel.add(txtPiso);
		txtPiso.setColumns(10);

		txtAltura = new JTextField();
		txtAltura.setBounds(133, 110, 164, 20);
		panel.add(txtAltura);
		txtAltura.setColumns(10);

		txtDepto = new JTextField();
		txtDepto.setBounds(133, 177, 164, 20);
		panel.add(txtDepto);
		txtDepto.setColumns(10);

		JLabel lblPiso = new JLabel("Piso");
		lblPiso.setBounds(10, 144, 46, 14);
		panel.add(lblPiso);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 303, 46, 14);
		panel.add(lblEmail);

		JLabel lblFecha_cumpleaños = new JLabel("Fecha de cumplea\u00F1os");
		lblFecha_cumpleaños.setBounds(10, 331, 113, 14);
		panel.add(lblFecha_cumpleaños);

		JLabel lblPais = new JLabel("Pa\u00EDs");
		lblPais.setBounds(10, 212, 100, 14);
		panel.add(lblPais);

		txtEmail = new JTextField();
		txtEmail.setBounds(133, 299, 164, 20);
		panel.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblTipo_contacto = new JLabel("Tipo de contacto");
		lblTipo_contacto.setBounds(10, 356, 113, 14);
		panel.add(lblTipo_contacto);

		comboBox_tipo_contacto = new JComboBox<TipoContactoDTO>();
		comboBox_tipo_contacto.setBounds(133, 359, 164, 22);
		value = new DefaultComboBoxModel<TipoContactoDTO>();
		comboBox_tipo_contacto.setModel(value);
		panel.add(comboBox_tipo_contacto);

		fecha_cumpleaños = new JDateChooser();
		fecha_cumpleaños.setBounds(133, 331, 164, 20);
		panel.add(fecha_cumpleaños);

		comboBox_pais = new JComboBox<PaisDTO>();
		comboBox_pais.setBounds(133, 208, 164, 22);
		valuepais = new DefaultComboBoxModel<PaisDTO>();
		comboBox_pais.setModel(valuepais);
		panel.add(comboBox_pais);

		lblProvincia = new JLabel("Provincia");
		lblProvincia.setBounds(10, 245, 100, 14);
		panel.add(lblProvincia);

		comboBox_provincia = new JComboBox<ProvinciaDTO>();
		comboBox_provincia.setBounds(133, 241, 164, 22);
		valueprov = new DefaultComboBoxModel<ProvinciaDTO>();
		comboBox_provincia.setModel(valueprov);
		panel.add(comboBox_provincia);

		lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setBounds(10, 274, 100, 14);
		panel.add(lblLocalidad);

		comboBox_localidad = new JComboBox<LocalidadDTO>();
		comboBox_localidad.setBounds(133, 270, 164, 22);
		valueloc = new DefaultComboBoxModel<LocalidadDTO>();
		comboBox_localidad.setModel(valueloc);
		panel.add(comboBox_localidad);

		btnAgregarPersona = new JButton("Agregar");
		btnAgregarPersona.setBounds(217, 418, 89, 23);
		contentPane.add(btnAgregarPersona);

		btnEditar = new JButton("Actualizar");
		btnEditar.setBounds(196, 418, 110, 23);
		contentPane.add(btnEditar);

		this.setVisible(false);
	}

	public void llenarCampos(PersonaDTO persona, TipoContactoDTO tipoContactoDTO) {
		txtNombre.setText(persona.getNombre());
		txtTelefono.setText(persona.getTelefono());
		txtEmail.setText(persona.getEmail());
		txtCalle.setText(persona.getCalle());
		txtAltura.setText(persona.getAltura());
		txtPiso.setText(persona.getPiso());
		txtDepto.setText(persona.getDepto());
		toStringFechaInverso(fecha_cumpleaños, persona.getFechaCumpleanios());
		comboBox_tipo_contacto.setSelectedItem(tipoContactoDTO);
	}

	public void toStringFechaInverso(JDateChooser fecha_cumpleaños, String fecha) {
		String String_anio = fecha.substring(0, 4);
		String String_mes = fecha.substring(5, 7);
		String String_dia = fecha.substring(8, 10);
		int anio = Integer.parseInt(String_anio);
		int mes = Integer.parseInt(String_mes) - 1;
		int dia = Integer.parseInt(String_dia);
		fecha_cumpleaños.setCalendar(new GregorianCalendar(anio, mes, dia));
	}

	public void vaciarCampos() {
		txtNombre.setText("");
		txtTelefono.setText("");
		txtEmail.setText("");
		txtCalle.setText("");
		txtAltura.setText("");
		txtPiso.setText("");
		txtDepto.setText("");
		comboBox_tipo_contacto.setSelectedIndex(-1);
		comboBox_pais.setSelectedIndex(-1);
		comboBox_provincia.setSelectedIndex(-1);
		comboBox_localidad.setSelectedIndex(-1);

		fecha_cumpleaños.setDate(null);
	}
	
	public void setValorComboBoxPais(int index) {
		comboBox_pais.setSelectedIndex(index);
	}
	
	public void setValorComboBoxProvincia(int index) {
		comboBox_provincia.setSelectedIndex(index);
	}
	
	public void setValorComboBoxLocalidad(int index) {
		comboBox_localidad.setSelectedIndex(index);
	}
	
	public void cerrar() {
		this.txtNombre.setText(null);
		this.txtTelefono.setText(null);
		this.dispose();
	}

	public void mostrarVentana() {
		this.setVisible(true);
	}

	public void cambiarTituloAEditar() {
		this.setTitle("Editar Persona");
	}

	public void cambiarTituloAagregar() {
		this.setTitle("Agregar Persona");
	}

	public void ActivarButton(JButton btn) {
		btn.setVisible(true);
	}

	public void ApagarButton(JButton btn) {
		btn.setVisible(false);
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public JTextField getTxtCalle() {
		return txtCalle;
	}

	public JTextField getTxtPiso() {
		return txtPiso;
	}

	public JTextField getTxtAltura() {
		return txtAltura;
	}

	public JTextField getTxtDepto() {
		return txtDepto;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public JButton getBtnAgregarPersona() {
		return btnAgregarPersona;
	}

	public JDateChooser getTxtFechaCumpleanios() {
		return fecha_cumpleaños;
	}

	public JComboBox<TipoContactoDTO> getComboBoxTipoContacto() {
		return comboBox_tipo_contacto;
	}

	public DefaultComboBoxModel<TipoContactoDTO> getDefaultComboBoxModelValue() {
		return value;
	}

	public DefaultComboBoxModel<PaisDTO> getValuepais() {
		return valuepais;
	}

	public DefaultComboBoxModel<ProvinciaDTO> getValueprov() {
		return valueprov;
	}

	public DefaultComboBoxModel<LocalidadDTO> getValueloc() {
		return valueloc;
	}

	public JComboBox<PaisDTO> getComboBoxPais() {
		return comboBox_pais;
	}

	public JComboBox<ProvinciaDTO> getComboBoxProvincia() {
		return comboBox_provincia;
	}

	public JComboBox<LocalidadDTO> getComboBoxLocalidad() {
		return comboBox_localidad;
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}
}
