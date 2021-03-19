package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dto.LocalidadDTO;
import dto.TipoContactoDTO;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaLocalidades extends JFrame{
	
	private JFrame frame;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static  VentanaLocalidades INSTANCE = null;
	private JTextField txtPais;
	private JTextField txtProvincia;
	private JTextField txtLocalidad;
	private JButton btnAgregar;
	private JButton btnEditar;

	public static VentanaLocalidades getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new VentanaLocalidades(); 	
			return new VentanaLocalidades();
		}
		else
			return INSTANCE;
	}
	
	public VentanaLocalidades() 
	{
		super();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 294, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 340, 102);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblPais = new JLabel("Pais");
		lblPais.setBounds(10, 11, 46, 14);
		panel.add(lblPais);
		
		txtPais = new JTextField();
		txtPais.setBounds(76, 8, 86, 20);
		panel.add(txtPais);
		txtPais.setColumns(10);
		
		JLabel lblProvincia = new JLabel("Provincia");
		lblProvincia.setBounds(10, 42, 46, 14);
		panel.add(lblProvincia);
		
		JLabel lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setBounds(10, 73, 46, 14);
		panel.add(lblLocalidad);
		
		txtProvincia = new JTextField();
		txtProvincia.setBounds(76, 39, 86, 20);
		panel.add(txtProvincia);
		txtProvincia.setColumns(10);
		
		txtLocalidad = new JTextField();
		txtLocalidad.setBounds(76, 70, 86, 20);
		panel.add(txtLocalidad);
		txtLocalidad.setColumns(10);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(102, 124, 89, 23);
		contentPane.add(btnAgregar);
		
		btnEditar = new JButton("Actualizar");
		btnEditar.setBounds(102, 124, 89, 23);
		contentPane.add(btnEditar);
		
		//frame.setTitle("Agregar localidad");
	
		this.setVisible(false);
	}

	public JTextField getTxtPais() {
		return txtPais;
	}

	public void setTxtPais(JTextField txtPais) {
		this.txtPais = txtPais;
	}

	public JTextField getTxtProvincia() {
		return txtProvincia;
	}

	public void setTxtProvincia(JTextField txtProvincia) {
		this.txtProvincia = txtProvincia;
	}

	public JTextField getTxtLocalidad() {
		return txtLocalidad;
	}

	public void setTxtLocalidad(JTextField localidad) {
		this.txtLocalidad = localidad;
	}

	public JButton getBtnAgregarLocalidad() {
		return btnAgregar;
	}
	
	public JButton getBtnEditarLocalidad() {
		return btnEditar;
	}
	
	public void mostrarVentanaLocalidades()
	{
		this.setVisible(true);
	}
	public void cerrar() {
		this.txtPais.setText(null);
		this.txtProvincia.setText(null);
		this.txtLocalidad.setText(null);
		this.dispose();
	}
	public void vaciarCampolocalidades() {
		txtPais.setText("");
		txtProvincia.setText("");
		txtLocalidad.setText("");
	}
	public void llenarCamposLocalidad(LocalidadDTO localidad) {
		txtPais.setText(localidad.getPais());
		txtProvincia.setText(localidad.getProvincia());
		txtLocalidad.setText(localidad.getLocalidad());
	}
	
}