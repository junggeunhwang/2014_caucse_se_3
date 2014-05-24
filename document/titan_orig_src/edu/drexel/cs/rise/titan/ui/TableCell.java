package edu.drexel.cs.rise.titan.ui;

import edu.drexel.cs.rise.titan.model.MatrixModel;
import edu.drexel.cs.rise.util.Interval;
import edu.drexel.cs.rise.util.Matrix;
import edu.drexel.cs.rise.util.TreeNode;
import java.awt.Color;
import java.awt.Component;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableCell extends JLabel
  implements TableCellRenderer
{
  private static final long serialVersionUID = 10L;
  private static final Color[] colors = { new Color(255, 255, 255), new Color(102, 204, 255), new Color(102, 255, 204), new Color(255, 204, 102), new Color(204, 102, 255), new Color(255, 102, 204) };
  protected int[][] cache = (int[][])null;

  public TableCell()
  {
    super("", 0);
    initialize();
  }

  private void initialize()
  {
    setOpaque(true);
  }

  public void recache(int paramInt1, int paramInt2, TreeNode<Interval> paramTreeNode)
  {
    this.cache = new int[paramInt1][paramInt2];
    for (int i = 0; i < paramInt2; i++)
      for (int j = 0; j < paramInt1; j++)
        this.cache[i][j] = computeModuleLevel(0, i, j, paramTreeNode);
  }

  public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
  {
    String str1 = paramObject.toString();
    setText(str1);
    MatrixModel localMatrixModel = (MatrixModel)paramJTable.getModel();
    int i = this.cache[paramInt1][paramInt2];
    if (i >= 0)
      setBackground(colors[(i % colors.length)]);
    if (str1.length() == 0)
    {
      setToolTipText(null);
    }
    else if (!str1.equals("."))
    {
      String str2 = (String)localMatrixModel.getData().getLabel(paramInt1);
      String str3 = (String)localMatrixModel.getData().getLabel(paramInt2);
      setToolTipText(str2 + " -> " + str3);
    }
    return this;
  }

  protected static final int computeModuleLevel(int paramInt1, int paramInt2, int paramInt3, TreeNode<Interval> paramTreeNode)
  {
    if ((paramTreeNode == null) || (!contains((Interval)paramTreeNode.getValue(), paramInt2, paramInt3)))
      return paramInt1;
    Iterator localIterator = paramTreeNode.iterator();
    while (localIterator.hasNext())
    {
      TreeNode localTreeNode = (TreeNode)localIterator.next();
      if (contains((Interval)localTreeNode.getValue(), paramInt2, paramInt3))
        return computeModuleLevel(paramInt1 + 1, paramInt2, paramInt3, localTreeNode);
    }
    return paramInt1;
  }

  protected static final boolean contains(Interval paramInterval, int paramInt1, int paramInt2)
  {
    return (paramInterval.contains(paramInt1)) && (paramInterval.contains(paramInt2));
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.ui.TableCell
 * JD-Core Version:    0.6.2
 */