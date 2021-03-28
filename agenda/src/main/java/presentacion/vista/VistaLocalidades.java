package presentacion.vista;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.javatuples.Triplet;

import dto.LocalidadDTO;

public class VistaLocalidades {

	private JFrame frame;
	private JTable tablaLocalidades;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnBorrar;
	private DefaultTableModel modelLocalidades;
	private String[] nombreColumnas = { "Pais", "Provincia", "Localidad", "Código Postal" };

	public VistaLocalidades() {
		super();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 444, 280);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 418, 250);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane spLocalidad = new JScrollPane();
		spLocalidad.setBounds(10, 11, 398, 182);
		panel.add(spLocalidad);

		modelLocalidades = new DefaultTableModel(null, nombreColumnas);
		tablaLocalidades = new JTable(modelLocalidades) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaLocalidades.getColumnModel().getColumn(0).setPreferredWidth(103);
		tablaLocalidades.getColumnModel().getColumn(0).setResizable(false);

		spLocalidad.setViewportView(tablaLocalidades);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(10, 204, 89, 23);
		panel.add(btnAgregar);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(167, 204, 89, 23);
		panel.add(btnEditar);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(319, 204, 89, 23);
		panel.add(btnBorrar);

		frame.setTitle("ABM Localidades");
	}

	public void mostrarVistaLocalidades() {
		this.frame.setVisible(true);
	}

	public JButton getBtnAgregarLocalidad() {
		return btnAgregar;
	}

	public JButton getBtnEditarLocalidad() {
		return btnEditar;
	}

	public JButton getBtnBorrarLocalidad() {
		return btnBorrar;
	}

	public DefaultTableModel getModelLocalidades() {
		return modelLocalidades;
	}

	public JTable getTablaLocalidades() {
		return tablaLocalidades;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void llenarTabla(List<Triplet<String, String, LocalidadDTO>> localidades) {
		this.getModelLocalidades().setRowCount(0); // Para vaciar la tabla
		this.getModelLocalidades().setColumnCount(0);
		this.getModelLocalidades().setColumnIdentifiers(this.getNombreColumnas());

		for (Triplet<String, String, LocalidadDTO> p : localidades) {
			String pais = p.getValue0(), provincia = p.getValue1(), localidad = p.getValue2().getNombre(),
					cp = p.getValue2().getCodigoPostal();
			Object[] fila = { pais, provincia, localidad, cp };

			this.getModelLocalidades().addRow(fila);
		}

	}

}
