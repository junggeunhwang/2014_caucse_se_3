package edu.drexel.cs.rise.titan.ui;

import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.ProjectListener;
import edu.drexel.cs.rise.titan.model.MatrixModel;
import edu.drexel.cs.rise.titan.util.ClusterUtilities;
import edu.drexel.cs.rise.util.Matrix;
import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class MatrixViewer extends JPanel
{
  private static final long serialVersionUID = 10L;
  private final MatrixModel model;
  private final JScrollPane body;
  private final JTable table;
  private final DefaultListModel rows;
  private final JList headers;
  private final TableCell renderer;

  public MatrixViewer()
  {
    this(new MatrixModel());
  }

  public MatrixViewer(MatrixModel paramMatrixModel)
  {
    this.model = paramMatrixModel;
    this.table = new JTable(paramMatrixModel);
    this.rows = new DefaultListModel();
    this.headers = new JList(this.rows);
    this.body = new JScrollPane(this.table);
    this.renderer = new TableCell();
    initialize();
  }

  private void initialize()
  {
    this.table.setDefaultRenderer(String.class, this.renderer);
    this.table.setAutoResizeMode(0);
    this.table.setDoubleBuffered(true);
    JTableHeader localJTableHeader = this.table.getTableHeader();
    localJTableHeader.setDefaultRenderer(new RowHeader(0));
    localJTableHeader.setReorderingAllowed(false);
    localJTableHeader.setResizingAllowed(true);
    this.headers.setCellRenderer(new RowHeader(10));
    this.headers.setOpaque(false);
    this.body.setDoubleBuffered(true);
    setLayout(new BorderLayout());
    add(this.body);
    Project localProject = Project.getInstance();
    localProject.addProjectListener(new Listener());
  }

  public MatrixModel getModel()
  {
    return this.model;
  }

  public DefaultListModel getRows()
  {
    return this.rows;
  }

  public JScrollBar getVerticalScrollBar()
  {
    return this.body.getVerticalScrollBar();
  }

  public void update(Project paramProject)
  {
    ClusterUtilities.buildMatrix(paramProject, this.model);
    Matrix localMatrix = this.model.getData();
    this.rows.clear();
    if (paramProject.isRowLabeled())
    {
      Iterator localIterator = localMatrix.getLabels().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        this.rows.addElement(str + "  ");
      }
    }
    else
    {
      for (int i = 1; i <= localMatrix.length(); i++)
        this.rows.addElement(String.valueOf(i) + " ");
    }
    FontMetrics localFontMetrics = this.table.getFontMetrics(this.table.getFont());
    int j = localFontMetrics.stringWidth("x");
    TableColumnModel localTableColumnModel = this.table.getColumnModel();
    for (int k = 0; k < localMatrix.length(); k++)
      localTableColumnModel.getColumn(k).setPreferredWidth(j);
    this.headers.setFixedCellHeight(this.table.getRowHeight());
    this.body.setRowHeaderView(this.headers);
    this.renderer.recache(localMatrix.length(), localMatrix.length(), this.model.getModules());
  }

  protected class Listener
    implements ProjectListener
  {
    protected Listener()
    {
    }

    public void clusterChanged(Project paramProject)
    {
      MatrixViewer.this.update(paramProject);
    }

    public void dependencyChanged(Project paramProject)
    {
    }

    public void modified(Project paramProject)
    {
    }
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.ui.MatrixViewer
 * JD-Core Version:    0.6.2
 */