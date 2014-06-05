package view.customtable;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object data[][];

	private String[] columnNames = null;	//���̺��� �� ���� ���� �̸���
	@SuppressWarnings("rawtypes")
	private Class[] columnClasses = null;//�� ���� ���� ���� Ŭ������
	
	public CustomTableModel() {
		
	}
	
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public void setColumnClasses(Class[] columnClasses) {
		this.columnClasses = columnClasses;
	}
	
	public void setData(Object[][] data) {
		this.data = data;		
	}
	public Object[][] getData() {
		return data;
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		data[r][c] = aValue;
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses[columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	@Override
	public int getColumnCount() {
		return data[0].length;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int r, int c) {
		return data[r][c];
	}

}