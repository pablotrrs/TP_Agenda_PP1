package presentacion.vista;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.javatuples.Pair;
import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
import javax.swing.JButton;
import persistencia.conexion.Conexion;
import java.awt.Dimension;

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
			"Tipo de Contacto", "Localidad" };

	public Vista() {
		super();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1350, 334);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 1314, 284);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane spPersonas = new JScrollPane();
		spPersonas.setBounds(10, 11, 1294, 228);
		panel.add(spPersonas);

		modelPersonas = new DefaultTableModel(null, nombreColumnas);
		tablaPersonas = new JTable(modelPersonas) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		;

		tablaPersonas.getColumnModel().getColumn(0).setPreferredWidth(103);
		tablaPersonas.getColumnModel().getColumn(0).setResizable(false);
		tablaPersonas.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaPersonas.getColumnModel().getColumn(1).setResizable(false);

		spPersonas.setViewportView(tablaPersonas);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(10, 250, 185, 23);
		panel.add(btnAgregar);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(237, 250, 186, 23);
		panel.add(btnEditar);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(462, 250, 179, 23);
		panel.add(btnBorrar);

		btnReporte = new JButton("Reporte");
		btnReporte.setBounds(687, 250, 179, 23);
		panel.add(btnReporte);

		btnABMtiposContacto = new JButton("ABM Tipos de contacto");
		btnABMtiposContacto.setBounds(906, 250, 179, 23);
		panel.add(btnABMtiposContacto);

		btnABMlocalidades = new JButton("ABM Localidades");
		btnABMlocalidades.setBounds(1125, 250, 179, 23);
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

	public void llenarTabla(List<Pair<String, PersonaDTO>> personas2, Map<Integer, ProvinciaDTO> provinciasById,
			Map<Integer, String> tiposDeContactosByIds, Map<Integer, LocalidadDTO> localidadesById,
			Map<Integer, PaisDTO> paisesById) {
		this.getModelPersonas().setRowCount(0); // Para vaciar la tabla
		this.getModelPersonas().setColumnCount(0);
		this.getModelPersonas().setColumnIdentifiers(this.getNombreColumnas());

		for (Pair<String, PersonaDTO> persona : personas2) {
			String nombre = persona.getValue1().getNombre();
			String tel = persona.getValue1().getTelefono();
			String domicilio = persona.getValue1().getCalle() + " " + persona.getValue1().getAltura() + "  "
					+ persona.getValue1().getPiso() + "   " + persona.getValue1().getDepto();
			String email = persona.getValue1().getEmail();
			String fechaCumpleanios = persona.getValue1().getFechaCumpleanios();

			LocalidadDTO localidad = localidadesById.get(persona.getValue1().getIdLocalidad());
			ProvinciaDTO provincia = provinciasById.get(localidad.getIdProvincia());
			PaisDTO pais = paisesById.get(provincia.getIdPais());

			String str = localidad.getNombre() + ", " + provincia.getNombre() + ", " + pais.getNombre() + ".";
			Object[] fila = { nombre, tel, domicilio, email, fechaCumpleanios, persona.getValue0(), str };

			this.getModelPersonas().addRow(fila);
		}
	}
}