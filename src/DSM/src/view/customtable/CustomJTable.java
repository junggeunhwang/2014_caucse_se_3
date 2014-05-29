package view.customtable;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CustomJTable extends JTable {

	/**  
	 *   
	 */
	private static final long serialVersionUID = 1L;

	CustomTableCellRenderer cellRenderer;

	public CustomJTable() {
	}

	public CustomJTable(DefaultTableModel model) {
		super(model);
		cellRenderer = new CustomTableCellRenderer(model.getRowCount(),
				model.getColumnCount());
	}

	public void setAlignment(int row, int column, int align) {
		cellRenderer.setAlignment(row, column, align);
		super.setDefaultRenderer(Object.class, cellRenderer);
	}

	public void setColor(int row, int column, Color color) {
		cellRenderer.setBkColor(row, column, color);
		super.setDefaultRenderer(Object.class, cellRenderer);
	}

}
