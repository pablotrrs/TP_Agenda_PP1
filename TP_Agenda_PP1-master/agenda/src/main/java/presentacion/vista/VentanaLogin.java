package presentacion.vista;

import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class VentanaLogin extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VentanaLogin INSTANCE;
	private JTextField textNombre;
	private JButton btnIngresar;
	private JPasswordField textPassword;
	private JButton btnRegistrarse;
	private JLabel mensaje;
	
	public static VentanaLogin getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VentanaLogin();
			return new VentanaLogin();
		} else
			return INSTANCE;
	}

	private VentanaLogin() {
		super();
		setBackground(Color.BLACK);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 289, 386);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
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
		textPassword.setBackground(Color.WHITE);
		textPassword.setBounds(66, 54, 164, 20);
		panel.add(textPassword);
		
		JLabel lblvista = new JLabel("Iniciar Sesi\u00F3n");
		lblvista.setFont(new Font("Yu Gothic Light", Font.PLAIN, 19));
		lblvista.setBounds(90, 23, 160, 37);
		contentPane.add(lblvista);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIngresar.setBounds(100, 187, 89, 23);
		contentPane.add(btnIngresar);
		
		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRegistrarse.setBounds(90, 282, 111, 23);
		contentPane.add(btnRegistrarse);
		
		mensaje = new JLabel("\u00BFNo tienes usuario? ");
		mensaje.setFont(new Font("Arial", Font.PLAIN, 11));
		mensaje.setBounds(100, 257, 101, 14);
		contentPane.add(mensaje);
		
		this.setVisible(false);
	
	}
	
	public void DesactivarMensaje() {
		
		this.mensaje.setText("");
	}
	public JTextField getTextNombre() {
		return textNombre;
	}
	public String passwordToString() {
		char[] array = this.textPassword.getPassword();
		String password = new String(array);
		return password;
	}

	public void setTextNombre(JTextField textNombre) {
		this.textNombre = textNombre;
	}
	public void ponerTitulo() {
		this.setTitle("Iniciar Sesión");
	}

	public JTextField getTextPassword() {
		return textPassword;
	}
	public void ActivarButton(JButton btn) {
		btn.setVisible(true);
	}

	public void ApagarButton(JButton btn) {
		btn.setVisible(false);
	}

	public void setTextPassword(JPasswordField textPassword) {
		this.textPassword = textPassword;
	}
	public JButton getBtnIngresar() {
		return btnIngresar;
	}
	public JButton getBtnRegistrarse() {
		return btnRegistrarse;
	}
	public void cerrar() {
		this.textNombre.setText(null);
		this.textPassword.setText(null);
		this.dispose();
	}
	public void vaciarCamposLogin() {
		textNombre.setText("");
		textPassword.setText("");
	}
	public void mensajeError() {
		JOptionPane.showMessageDialog(this, "El usuario y/o contraseña son incorrectos.");
	} 

	public void mostrarVentanaLogin() {
		this.setVisible(true);
	}
	public void cambiarDimension() {
		this.setBounds(100, 100, 289, 275);
	}
}
