package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;

public class VentanaRegistro extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VentanaRegistro INSTANCE;
	private JTextField textNombre;
	private JButton btnRegistrar;
	private JButton btnVolver;
	private JPasswordField textPassword;
	
	public static VentanaRegistro getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VentanaRegistro();
			return new VentanaRegistro();
		} else
			return INSTANCE;
	}

	public VentanaRegistro() {
		super();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 297, 330);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 71, 260, 103);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 11, 53, 14);
		panel.add(lblNombre);
		
		textNombre = new JTextField();
		textNombre.setBounds(66, 8, 164, 20);
		panel.add(textNombre);
		textNombre.setColumns(10);
		
		JLabel lblPassword = new JLabel("Clave:");
		lblPassword.setBounds(10, 57, 46, 14);
		panel.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(66, 54, 164, 20);
		panel.add(textPassword);
		
		JLabel lblvista = new JLabel("Registrar Usuario");
		lblvista.setFont(new Font("Yu Gothic Light", Font.PLAIN, 19));
		lblvista.setBounds(82, 23, 148, 37);
		contentPane.add(lblvista);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(100, 195, 89, 23);
		contentPane.add(btnRegistrar);
		
		 btnVolver = new JButton("Volver");
		btnVolver.setBounds(100, 243, 89, 23);
		contentPane.add(btnVolver);
		
		this.setVisible(false);
	
	}

	public JTextField getTextNombre() {
		return textNombre;
	}

	public void setTextNombre(JTextField textNombre) {
		this.textNombre = textNombre;
	}

	public JTextField getTextPassword() {
		return textPassword;
	}

	public void setTextPassword(JPasswordField textPassword) {
		this.textPassword = textPassword;
	}
	public void ponerTitulo() {
		this.setTitle("Registrar Usuario");
	}
		
	public void mostrarVentanaRegistro() {
		this.setVisible(true);
	}
	public void ActivarButton(JButton btn) {
		btn.setVisible(true);
	}

	public void ApagarButton(JButton btn) {
		btn.setVisible(false);
	}
	public void mostrarMensaje() {
		JOptionPane.showMessageDialog(this, "Registro exitoso.");
	}
	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}
	public void mostrarMensajeCampos() {
		JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacios.");
	}
	
	public void cerrar() {
		this.textNombre.setText(null);
		this.textPassword.setText(null);
		this.dispose();
	}
	public String passwordToString() {
		char[] array = this.textPassword.getPassword();
		String password = new String(array);
		return password;
	}
	public void vaciarCamposRegistro() {
		textNombre.setText("");
		textPassword.setText("");
	}
	public JButton getBtnVolver() {
		return btnVolver;
	}
	public void mensajeError() {
		JOptionPane.showMessageDialog(this, "El nombre ya esta en uso.");
	}
}
