package edu.drexel.cs.rise.titan.model;

import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.util.Interval;
import edu.drexel.cs.rise.util.Matrix;
import edu.drexel.cs.rise.util.TreeNode;
import javax.swing.table.DefaultTableModel;

public class MatrixModel extends DefaultTableModel
{
  private static final long serialVersionUID = 10L;
  private Matrix<Integer, String> data;
  private TreeNode<Interval> modules;

  public MatrixModel()
  {
    this(null);
  }

  public MatrixModel(Matrix<Integer, String> paramMatrix)
  {
    this.data = paramMatrix;
  }

  public final Matrix<Integer, String> getData()
  {
    return this.data;
  }

  public final void setData(Matrix<Integer, String> paramMatrix)
  {
    setData(paramMatrix, null);
  }

  public final void setData(Matrix<Integer, String> paramMatrix, TreeNode<Interval> paramTreeNode)
  {
    this.data = paramMatrix;
    this.modules = paramTreeNode;
    super.fireTableStructureChanged();
  }

  public final TreeNode<Interval> getModules()
  {
    return this.modules;
  }

  public final void setModules(TreeNode<Interval> paramTreeNode)
  {
    this.modules = paramTreeNode;
    super.fireTableDataChanged();
  }

  public int getColumnCount()
  {
    return this.data != null ? this.data.length() : 0;
  }

  public String getColumnName(int paramInt)
  {
    return String.valueOf(paramInt + 1);
  }

  public int getRowCount()
  {
    return getColumnCount();
  }

  public Class<?> getColumnClass(int paramInt)
  {
    return String.class;
  }

  public Object getValueAt(int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2)
      return ".";
    return getText((Integer)this.data.get(paramInt1, paramInt2));
  }

  public boolean isCellEditable(int paramInt1, int paramInt2)
  {
    return false;
  }

  protected final String getText(Integer paramInteger)
  {
    Project localProject = Project.getInstance();
    if ((paramInteger == null) || (paramInteger.intValue() == 0))
      return "";
    if (localProject.isWeighted())
      return paramInteger.toString();
    return "x";
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.model.MatrixModel
 * JD-Core Version:    0.6.2
 */