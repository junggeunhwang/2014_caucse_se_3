package edu.drexel.cs.rise.titan.ui;

import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.ProjectListener;
import edu.drexel.cs.rise.titan.action.cluster.CollapseAllAction;
import edu.drexel.cs.rise.titan.action.cluster.DeleteAction;
import edu.drexel.cs.rise.titan.action.cluster.DownAction;
import edu.drexel.cs.rise.titan.action.cluster.ExpandAllAction;
import edu.drexel.cs.rise.titan.action.cluster.GroupAction;
import edu.drexel.cs.rise.titan.action.cluster.RenameAction;
import edu.drexel.cs.rise.titan.action.cluster.SortAction;
import edu.drexel.cs.rise.titan.action.cluster.UngroupAction;
import edu.drexel.cs.rise.titan.action.cluster.UpAction;
import edu.drexel.cs.rise.titan.model.ClusterModel;
import edu.drexel.cs.rise.titan.util.ClusterUtilities;
import edu.drexel.cs.rise.util.Digraph;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.Action;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class ClusterViewer extends JPanel
{
  private static final long serialVersionUID = 10L;
  private final Map<String, Action> actions = new HashMap();
  private final ClusterModel model;
  private final JTree tree;
  private JPopupMenu contextMenu;

  public ClusterViewer()
  {
    this(new ClusterModel());
  }

  public ClusterViewer(ClusterModel paramClusterModel)
  {
    this.model = paramClusterModel;
    this.tree = new JTree(paramClusterModel);
    initialize();
  }

  private void initialize()
  {
    if (this.model == null)
      throw new NullPointerException();
    buildActions();
    setLayout(new BorderLayout());
    add(new JScrollPane(this.tree), "Center");
    add(buildToolbar(), "First");
    this.contextMenu = buildContextMenu();
    Project localProject = Project.getInstance();
    localProject.addProjectListener(new Listener());
    enableDragAndDrop();
    enableAllButtons(false);
    this.tree.addTreeSelectionListener(new TreeSelectionListener()
    {
      public void valueChanged(TreeSelectionEvent paramAnonymousTreeSelectionEvent)
      {
        ClusterViewer.this.enableTreeButtons();
      }
    });
    this.tree.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent paramAnonymousMouseEvent)
      {
        showContextMenu(paramAnonymousMouseEvent);
      }

      public void mouseReleased(MouseEvent paramAnonymousMouseEvent)
      {
        showContextMenu(paramAnonymousMouseEvent);
      }

      private void showContextMenu(MouseEvent paramAnonymousMouseEvent)
      {
        if ((paramAnonymousMouseEvent.isPopupTrigger()) && (!paramAnonymousMouseEvent.isControlDown()))
        {
          int i = paramAnonymousMouseEvent.getX();
          int j = paramAnonymousMouseEvent.getY();
          int k = ClusterViewer.this.tree.getRowForLocation(i, j);
          if ((k >= 0) && ((ClusterViewer.this.tree.getPathForRow(k).getLastPathComponent() instanceof ClusterSet)))
          {
            ClusterViewer.this.tree.setSelectionRow(k);
            ClusterViewer.this.contextMenu.show(ClusterViewer.this.tree, i, j);
          }
        }
      }
    });
  }

  private void buildActions()
  {
    this.actions.put("expand", new ExpandAllAction(this.tree));
    this.actions.put("collapse", new CollapseAllAction(this.tree));
    this.actions.put("group", new GroupAction(this));
    this.actions.put("ungroup", new UngroupAction(this));
    this.actions.put("up", new UpAction(this));
    this.actions.put("down", new DownAction(this));
    this.actions.put("delete", new DeleteAction(this));
    this.actions.put("rename", new RenameAction(this));
    this.actions.put("sort", new SortAction(this));
  }

  private JComponent buildToolbar()
  {
    JToolBar localJToolBar = new JToolBar();
    localJToolBar.setFloatable(false);
    JButton localJButton = new JButton((Action)this.actions.get("expand"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJButton = new JButton((Action)this.actions.get("collapse"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJToolBar.addSeparator();
    localJButton = new JButton((Action)this.actions.get("group"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJButton = new JButton((Action)this.actions.get("ungroup"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJToolBar.addSeparator();
    localJButton = new JButton((Action)this.actions.get("up"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJButton = new JButton((Action)this.actions.get("down"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJToolBar.addSeparator();
    localJButton = new JButton((Action)this.actions.get("delete"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    return localJToolBar;
  }

  private JPopupMenu buildContextMenu()
  {
    JPopupMenu localJPopupMenu = new JPopupMenu();
    localJPopupMenu.add(new JMenuItem((Action)this.actions.get("rename")));
    localJPopupMenu.add(new JMenuItem((Action)this.actions.get("sort")));
    return localJPopupMenu;
  }

  private void enableDragAndDrop()
  {
    this.tree.setDragEnabled(true);
    this.tree.setDropMode(DropMode.ON);
  }

  public ClusterModel getModel()
  {
    return this.model;
  }

  public TreeSelectionModel getSelectionModel()
  {
    return this.tree.getSelectionModel();
  }

  public JTree getTree()
  {
    return this.tree;
  }

  protected void enableAllButtons(boolean paramBoolean)
  {
    ((Action)this.actions.get("expand")).setEnabled(paramBoolean);
    ((Action)this.actions.get("collapse")).setEnabled(paramBoolean);
    enableTreeButtons();
  }

  protected void enableTreeButtons()
  {
    TreeSelectionModel localTreeSelectionModel = this.tree.getSelectionModel();
    enableGroupButtons(localTreeSelectionModel);
    enabledMoveButtons(localTreeSelectionModel);
    enableDeleteButton(localTreeSelectionModel);
  }

  protected void enableGroupButtons(TreeSelectionModel paramTreeSelectionModel)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    if (paramTreeSelectionModel.isSelectionEmpty())
    {
      bool1 = false;
      bool2 = false;
    }
    else
    {
      TreePath localTreePath1 = paramTreeSelectionModel.getSelectionPath().getParentPath();
      Object localObject = localTreePath1 != null ? (ClusterSet)localTreePath1.getLastPathComponent() : null;
      if (localObject == null)
      {
        bool1 = false;
        bool2 = false;
      }
      else
      {
        for (TreePath localTreePath2 : paramTreeSelectionModel.getSelectionPaths())
        {
          TreePath localTreePath3 = localTreePath2.getParentPath();
          if ((localTreePath3 == null) || (localTreePath3.getLastPathComponent() != localObject))
            bool1 = false;
          if (!(localTreePath2.getLastPathComponent() instanceof ClusterSet))
            bool2 = false;
          if ((!bool1) && (!bool2))
            break;
        }
      }
    }
    ((Action)this.actions.get("group")).setEnabled(bool1);
    ((Action)this.actions.get("ungroup")).setEnabled(bool2);
  }

  protected void enabledMoveButtons(TreeSelectionModel paramTreeSelectionModel)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    if (paramTreeSelectionModel.isSelectionEmpty())
    {
      bool1 = false;
      bool2 = false;
    }
    else
    {
      TreePath localTreePath1 = paramTreeSelectionModel.getSelectionPath().getParentPath();
      Object localObject = localTreePath1 != null ? (ClusterSet)localTreePath1.getLastPathComponent() : null;
      if (localObject == null)
      {
        bool1 = false;
        bool2 = false;
      }
      else
      {
        for (TreePath localTreePath2 : paramTreeSelectionModel.getSelectionPaths())
        {
          TreePath localTreePath3 = localTreePath2.getParentPath();
          if ((localTreePath3 == null) || (localTreePath3.getLastPathComponent() != localObject))
          {
            bool1 = false;
            bool2 = false;
          }
          else
          {
            Clustering localClustering = (Clustering)localTreePath2.getLastPathComponent();
            int k = localObject.getClusterIndex(localClustering);
            if (k == 0)
              bool1 = false;
            if (k == localObject.clusters().size() - 1)
              bool2 = false;
            if ((!bool1) && (!bool2))
              break;
          }
        }
      }
    }
    ((Action)this.actions.get("up")).setEnabled(bool1);
    ((Action)this.actions.get("down")).setEnabled(bool2);
  }

  protected void enableDeleteButton(TreeSelectionModel paramTreeSelectionModel)
  {
    Clustering localClustering = Project.getInstance().getCluster();
    TreePath localTreePath = localClustering == null ? null : new TreePath(localClustering);
    boolean bool = (!paramTreeSelectionModel.isSelectionEmpty()) && ((paramTreeSelectionModel.getSelectionCount() != 1) || (!paramTreeSelectionModel.getSelectionPath().equals(localTreePath)));
    ((Action)this.actions.get("delete")).setEnabled(bool);
  }

  public boolean isExpanded(TreePath paramTreePath)
  {
    return this.tree.isExpanded(paramTreePath);
  }

  protected class Listener
    implements ProjectListener
  {
    protected Listener()
    {
    }

    public void clusterChanged(Project paramProject)
    {
      ClusterViewer.this.enableAllButtons(paramProject.getCluster() != null);
      Clustering localClustering = paramProject.getCluster();
      if (localClustering != ClusterViewer.this.model.getRoot())
        ClusterViewer.this.model.setRoot(localClustering);
      if (localClustering != null)
        ClusterViewer.this.tree.collapsePath(new TreePath(localClustering));
    }

    public void dependencyChanged(Project paramProject)
    {
      Digraph localDigraph = Project.getInstance().getDependency();
      Clustering localClustering = ClusterUtilities.buildDefaultCluster(localDigraph);
      paramProject.clearCollapsed();
      if (localClustering != null)
        paramProject.getCollapsed().add(localClustering);
      ClusterViewer.this.model.setRoot(localClustering);
      paramProject.setCluster(localClustering);
    }

    public void modified(Project paramProject)
    {
    }
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.ui.ClusterViewer
 * JD-Core Version:    0.6.2
 */