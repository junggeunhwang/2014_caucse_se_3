package view.customtable;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static public final int left_alignment = SwingConstants.LEFT;
	static public final int center_alignment = SwingConstants.CENTER;
	static public final int right_alignment = SwingConstants.RIGHT;
	
	Color bkColor[][];
	int alignment[][];
	
	public CustomTableCellRenderer(int rowSize,int colSize) {
			bkColor = new Color[rowSize][colSize];
			for(int i = 0 ; i < rowSize ; i++){
				for(int j = 0 ; j < colSize ; j++){
					bkColor[i][j] = new Color(255,255,255);
				}
			}
			
			alignment = new int[rowSize][colSize];
			for(int i = 0 ; i < rowSize ; i++){
				for(int j = 0 ; j < colSize ; j++){
					alignment[i][j]= SwingConstants.LEFT;
				}
			}
	}
	
	public void setAlignment(int row,int column,int align){
		switch(align)
		{
		case left_alignment:
			alignment[row][column] = SwingConstants.LEFT;
			break;
		case center_alignment:
			alignment[row][column] = SwingConstants.CENTER;
			break;
		case right_alignment:
			alignment[row][column] = SwingConstants.RIGHT;
			break;
		}
		this.alignment[row][column] = align;
	}
	
	public void setBkColor(int row,int column,Color color){
		this.bkColor[row][column] = color;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
		
		super.setHorizontalAlignment(alignment[row][column]);
		final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		c.setBackground(bkColor[row][column]);
		
		return c;
	}
}
