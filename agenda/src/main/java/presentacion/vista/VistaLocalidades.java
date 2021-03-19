package presentacion.vista;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dto.LocalidadDTO;


public class VistaLocalidades {
	
	private JFrame frame;
	private JTable tablaLocalidades;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnBorrar;
	private DefaultTableModel modelLocalidades;
	private String[] nombreColumnas = { "Pais", "Provincia", "Localidad" };
	
	public VistaLocalidades() 
	{
		super();
		initialize();
	}
	
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 340, 280);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 320, 250);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane spTiposContacto = new JScrollPane();
		spTiposContacto.setBounds(10, 11, 300, 182);
		panel.add(spTiposContacto);

		modelLocalidades = new DefaultTableModel(null, nombreColumnas);
		tablaLocalidades = new JTable(modelLocalidades);

		tablaLocalidades.getColumnModel().getColumn(0).setPreferredWidth(103);
		tablaLocalidades.getColumnModel().getColumn(0).setResizable(false);
		
		
		spTiposContacto.setViewportView(tablaLocalidades);
		

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(10, 204, 89, 23);
		panel.add(btnAgregar);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(111, 204, 89, 23);
		panel.add(btnEditar);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(210, 204, 89, 23);
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
	
	public void llenarTabla(List<LocalidadDTO> localidadesEnTabla) {
		this.getModelLocalidades().setRowCount(0); // Para vaciar la tabla
		this.getModelLocalidades().setColumnCount(0);
		this.getModelLocalidades().setColumnIdentifiers(this.getNombreColumnas());

		for (LocalidadDTO p : localidadesEnTabla) {
			String pais = p.getPais();
			String provincia = p.getProvincia();
			String localidad = p.getLocalidad();
			Object[] fila = { pais, provincia, localidad};

			this.getModelLocalidades().addRow(fila);
		}

}
}