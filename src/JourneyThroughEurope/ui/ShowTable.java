package JourneyThroughEurope.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ShowTable {
	private JTable jta;
	private String cols[];

	public ShowTable(String[] cols) {
		jta = new JTable();
		jta.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				cols));
		this.cols = cols;

	}

	/**
	 * @return the jta
	 */
	public JTable getJta() {
		return jta;
	}

	/**
	 * @param jta
	 *            the jta to set
	 */
	public void setJta(JTable jta) {
		this.jta = jta;
	}

	public void setTable(List<String> list) {
		// String[] th = list.remove(0).split("\\;");
		jta.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null } },
				this.cols));
		DefaultTableModel tableModel = (DefaultTableModel) jta.getModel();
		tableModel.setRowCount(0);// clear the table
		if (list != null) {
			for (String line : list) {
				tableModel.addRow(line.split("\\;"));
			}
		}
	}

	public static void addRow1(JTable mytable, String content) {
		DefaultTableModel tableModel = (DefaultTableModel) mytable.getModel();
		tableModel.addRow(content.split("\\;"));
	}

	
	/**
	 */
	public static void addRow(JTable jta, String add) {
		DefaultTableModel tableModel = (DefaultTableModel) jta.getModel();
		tableModel.addRow(add.split("\\;"));
	}

}
