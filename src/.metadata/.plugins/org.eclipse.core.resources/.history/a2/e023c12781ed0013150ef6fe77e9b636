package view.customtable;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import model.ModelInfo;
import view.Main_view;

public class CustomJTable extends JTable {

	/**  
	 *   
	 */
	private static final long serialVersionUID = 1L;
	private CustomJTable instance;
	
	private boolean isEditable;
	
	CustomTableCellRenderer cellRenderer;

	public CustomJTable() {
		this.isEditable = true;
	}

	public CustomJTable(CustomTableModel model) {
		super(model);
		instance = this;
		cellRenderer = new CustomTableCellRenderer(model.getRowCount(),
				model.getColumnCount());

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(isEditable){
					JTable target = (JTable)arg0.getSource();
					int row = target.getSelectedRow();
					int col = target.getSelectedColumn();
					if(col!=0){
						String value = (String)target.getValueAt(row, col);
						if(value.compareTo("x")==0){
							target.setValueAt("", row, col);
						}
						else
							target.setValueAt("x", row, col);

						ArrayList<int[]> rows = ModelInfo.getInstance().getDependData();
						int[] selected_row = rows.get(row);
						selected_row[col-1]=1;
						rows.set(row, selected_row);
						
						ModelInfo.getInstance().setDependData(rows);
						
						target.repaint();
						//System.out.println("("+row + "," + col+") Clicked");//for Debug
					}
					else{
						target.setValueAt(row+1 +"  "+ Main_view.getInstance().setNewName(), row, col);
						target.repaint();
					}
				}	
			}
		});
	}

	
	
	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
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
