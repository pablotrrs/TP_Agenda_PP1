package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.javatuples.Triplet;

import dto.LocalidadDTO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.JButton;

public class VentanaLocalidades extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VentanaLocalidades INSTANCE = null;
	private JTextField txtPais;
	private JTextField txtProvincia;
	private JTextField txtLocalidad;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JTextField txtCP;

	public static VentanaLocalidades getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VentanaLocalidades();
			return new VentanaLocalidades();
		} else
			return INSTANCE;
	}

	public VentanaLocalidades() {
		super();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 308, 262);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 272, 136);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblPais = new JLabel("Pa\u00EDs");
		lblPais.setBounds(10, 11, 86, 14);
		panel.add(lblPais);

		txtPais = new JTextField();
		txtPais.setBounds(123, 8, 139, 20);
		panel.add(txtPais);
		txtPais.setColumns(10);

		JLabel lblProvincia = new JLabel("Provincia");
		lblProvincia.setBounds(10, 42, 86, 14);
		panel.add(lblProvincia);

		JLabel lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setBounds(10, 73, 86, 14);
		panel.add(lblLocalidad);

		txtProvincia = new JTextField();
		txtProvincia.setBounds(123, 39, 139, 20);
		panel.add(txtProvincia);
		txtProvincia.setColumns(10);

		txtLocalidad = new JTextField();
		txtLocalidad.setBounds(123, 70, 139, 20);
		panel.add(txtLocalidad);
		txtLocalidad.setColumns(10);

		JLabel lblCodigoPostal = new JLabel("C\u00F3digo Postal");
		lblCodigoPostal.setBounds(10, 111, 107, 14);
		panel.add(lblCodigoPostal);

		txtCP = new JTextField();
		txtCP.setColumns(10);
		txtCP.setBounds(123, 108, 139, 20);
		panel.add(txtCP);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(99, 158, 89, 23);
		contentPane.add(btnAgregar);

		btnEditar = new JButton("Actualizar");
		btnEditar.setBounds(88, 158, 106, 23);
		contentPane.add(btnEditar);

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

	public JTextField getTxtCP() {
		return txtCP;
	}

	public void setTxtCP(JTextField txtCP) {
		this.txtCP = txtCP;
	}

	public void mostrarVentanaLocalidades() {
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
		txtCP.setText("");
	}

	public void llenarCamposLocalidad(List<Triplet<String, String, LocalidadDTO>> list) {
		txtPais.setText(list.get(0).getValue0());
		txtProvincia.setText(list.get(0).getValue1());
		txtLocalidad.setText(list.get(0).getValue2().getNombre());
		txtCP.setText(String.valueOf(list.get(0).getValue2().getCodigoPostal()));
	}

	public void cambiarTituloAEditar() {
		this.setTitle("Editar Localidad");
	}

	public void cambiarTituloAagregar() {
		this.setTitle("Agregar Localidad");
	}

	public void ActivarButton(JButton btn) {
		btn.setVisible(true);
	}

	public void ApagarButton(JButton btn) {
		btn.setVisible(false);
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje);
	}
}
