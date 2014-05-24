package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.model.ClusterModel;
import edu.drexel.cs.rise.titan.ui.ClusterViewer;
import edu.drexel.cs.rise.titan.util.ClusterUtilities;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class RenameAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  private final ClusterViewer viewer;

  public RenameAction(ClusterViewer paramClusterViewer)
  {
    this.viewer = paramClusterViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Rename");
    putValue("ShortDescription", "Rename");
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    TreeSelectionModel localTreeSelectionModel = this.viewer.getSelectionModel();
    if (localTreeSelectionModel.isSelectionEmpty())
      return;
    TreePath localTreePath = localTreeSelectionModel.getSelectionPath();
    String str = queryName();
    if (str == null)
      return;
    if (!ClusterUtilities.isValidName(str))
    {
      JOptionPane.showMessageDialog(null, "Invalid group name.", "Invalid Input", 2);
      return;
    }
    Clustering localClustering = (Clustering)localTreePath.getLastPathComponent();
    localClustering.setName(str);
    this.viewer.getModel().fireChangeEvent(localTreePath);
    Project.getInstance().setModified(true);
  }

  protected String queryName()
  {
    return JOptionPane.showInputDialog(null, "Enter new group name:", "Group Name", -1);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.RenameAction
 * JD-Core Version:    0.6.2
 */