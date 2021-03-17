package presentacion.vista;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dto.PersonaDTO;
import javax.swing.JButton;
import persistencia.conexion.Conexion;

public class Vista {
	private JFrame frame;
	private JTable tablaPersonas;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnBorrar;
	private JButton btnReporte;
	private JButton btnABMtiposContacto;
	private JButton btnABMlocalidades;
	private DefaultTableModel modelPersonas;
	private String[] nombreColumnas = { "Nombre y apellido", "Teléfono", "Domicilio", "Email", "Fecha de Cumpleaños",
			"Tipo de Contacto" };

	public Vista() {
		super();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1193, 334);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 1157, 284);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane spPersonas = new JScrollPane();
		spPersonas.setBounds(10, 11, 1137, 182);
		panel.add(spPersonas);

		modelPersonas = new DefaultTableModel(null, nombreColumnas);
		tablaPersonas = new JTable(modelPersonas);
		
		tablaPersonas.getColumnModel().getColumn(0).setPreferredWidth(103);
		tablaPersonas.getColumnModel().getColumn(0).setResizable(false);
		tablaPersonas.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaPersonas.getColumnModel().getColumn(1).setResizable(false);

		spPersonas.setViewportView(tablaPersonas);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(10, 228, 185, 23);
		panel.add(btnAgregar);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(205, 228, 186, 23);
		panel.add(btnEditar);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(401, 228, 179, 23);
		panel.add(btnBorrar);

		btnReporte = new JButton("Reporte");
		btnReporte.setBounds(590, 228, 179, 23);
		panel.add(btnReporte);

		btnABMtiposContacto = new JButton("ABM Tipos de contacto");
		btnABMtiposContacto.setBounds(779, 228, 179, 23);
		panel.add(btnABMtiposContacto);

		btnABMlocalidades = new JButton("ABM Localidades");
		btnABMlocalidades.setBounds(968, 228, 179, 23);
		panel.add(btnABMlocalidades);
	}

	public void show() {
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null, "¿Estás seguro que quieres salir de la Agenda?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					Conexion.getConexion().cerrarConexion();
					System.exit(0);
				}
			}
		});
		this.frame.setVisible(true);
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public JButton getABMtiposContacto() {
		return btnABMtiposContacto;
	}

	public JButton getABMlocalidades() {
		return btnABMlocalidades;
	}

	public JButton getBtnBorrar() {
		return btnBorrar;
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public JButton getBtnReporte() {
		return btnReporte;
	}

	public DefaultTableModel getModelPersonas() {
		return modelPersonas;
	}

	public JTable getTablaPersonas() {
		return tablaPersonas;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void llenarTabla(List<PersonaDTO> personasEnTabla) {
		this.getModelPersonas().setRowCount(0); // Para vaciar la tabla
		this.getModelPersonas().setColumnCount(0);
		this.getModelPersonas().setColumnIdentifiers(this.getNombreColumnas());
		String domicilio;
		for (PersonaDTO p : personasEnTabla) {
			String nombre = p.getNombre();
			String tel = p.getTelefono();
			domicilio = p.getCalle() + " " + p.getAltura() + "  " + p.getPiso() + "   " + p.getDepto();
			String email = p.getEmail();
			String fechaCumpleanios = p.getFechaCumpleanios();
			String tipoContacto = p.getTipoContacto();
			Object[] fila = { nombre, tel, domicilio, email, fechaCumpleanios,  tipoContacto};
			this.getModelPersonas().addRow(fila);
		}
	}
}