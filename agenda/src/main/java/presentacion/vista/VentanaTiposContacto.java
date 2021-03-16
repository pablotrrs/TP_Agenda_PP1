package presentacion.vista;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.TipoContactoDTO;
import java.awt.event.ActionEvent;

public class VentanaTiposContacto extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VentanaTiposContacto INSTANCE;
	private JTextField txtNombre;
	private JButton btnAgregar;
	
	public static VentanaTiposContacto getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new VentanaTiposContacto(); 	
			return new VentanaTiposContacto();
		}
		else
			return INSTANCE;
	}
	
	private VentanaTiposContacto() 
	{
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 340, 90);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 11, 113, 14);
		panel.add(lblNombre);
				
		txtNombre = new JTextField();
		txtNombre.setBounds(133, 8, 164, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(208, 56, 89, 23);
		panel.add(btnAgregar);
				
		this.setVisible(false);
	}
	
	
	public JTextField getTxtNombre() 
	{
		return txtNombre;
	}
	
	public JButton getBtnAgregarTipoDeContacto() {
		return btnAgregar;
	}
	
	public void mostrarVentanaTiposContacto()
	{
		this.setVisible(true);
	}

	public void cerrar()
	{
		this.txtNombre.setText(null);
		this.dispose();
	}
}
