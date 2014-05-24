package edu.drexel.cs.rise.titan.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class RowHeader extends JLabel
  implements ListCellRenderer, TableCellRenderer, SwingConstants
{
  private static final long serialVersionUID = 10L;

  public RowHeader(int paramInt)
  {
    super("", paramInt);
    initialize();
  }

  private void initialize()
  {
    setOpaque(true);
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));
  }

  public Component getListCellRendererComponent(JList paramJList, Object paramObject, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    return getCellRendererComponent(paramObject, paramBoolean1, paramBoolean2);
  }

  public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
  {
    return getCellRendererComponent(paramObject, paramBoolean1, paramBoolean2);
  }

  protected Component getCellRendererComponent(Object paramObject, boolean paramBoolean1, boolean paramBoolean2)
  {
    setText(paramObject.toString());
    return this;
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.ui.RowHeader
 * JD-Core Version:    0.6.2
 */