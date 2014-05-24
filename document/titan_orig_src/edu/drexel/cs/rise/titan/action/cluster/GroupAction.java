package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.model.ClusterModel;
import edu.drexel.cs.rise.titan.ui.ClusterViewer;
import edu.drexel.cs.rise.titan.util.ActionUtilities;
import edu.drexel.cs.rise.titan.util.ClusterUtilities;
import edu.drexel.cs.rise.titan.util.IconFactory;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class GroupAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  protected final ClusterViewer viewer;

  public GroupAction(ClusterViewer paramClusterViewer)
  {
    this.viewer = paramClusterViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Group");
    putValue("ShortDescription", "Group");
    putValue("SmallIcon", IconFactory.load("group.png"));
    putValue("AcceleratorKey", KeyStroke.getKeyStroke(71, ActionUtilities.CMD_KEY));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    TreeSelectionModel localTreeSelectionModel = this.viewer.getSelectionModel();
    if (localTreeSelectionModel.isSelectionEmpty())
      return;
    TreePath localTreePath1 = localTreeSelectionModel.getSelectionPath().getParentPath();
    ClusterSet localClusterSet1 = localTreePath1 != null ? (ClusterSet)localTreePath1.getLastPathComponent() : null;
    if (localClusterSet1 == null)
      return;
    String str = queryName();
    if (str == null)
      return;
    if (!ClusterUtilities.isValidName(str))
    {
      JOptionPane.showMessageDialog(null, "Invalid group name.", "Invalid Input", 2);
      return;
    }
    JTree localJTree = this.viewer.getTree();
    ClusterSet localClusterSet2 = new ClusterSet(str, localClusterSet1);
    int[] arrayOfInt = localTreeSelectionModel.getSelectionRows();
    Arrays.sort(arrayOfInt);
    int i = localJTree.getRowCount();
    for (int j = arrayOfInt.length - 1; j >= 0; j--)
    {
      TreePath localTreePath2 = localJTree.getPathForRow(arrayOfInt[j]);
      TreePath localTreePath3 = localTreePath2.getParentPath();
      if ((localTreePath3 == null) || (localTreePath3.getLastPathComponent() != localClusterSet1))
        return;
      Clustering localClustering = (Clustering)localTreePath2.getLastPathComponent();
      int k = localClusterSet1.getClusterIndex(localClustering);
      if (k < i)
        i = k;
      localClusterSet1.removeCluster(localClustering.getName());
      localClustering.setParent(localClusterSet2);
      localClusterSet2.addCluster(localClustering);
    }
    localClusterSet1.addCluster(i, localClusterSet2);
    this.viewer.getModel().fireChangeEvent(localTreePath1);
    localTreeSelectionModel.setSelectionPath(localTreePath1.pathByAddingChild(localClusterSet2));
    Project.getInstance().setModified(true);
  }

  protected String queryName()
  {
    return JOptionPane.showInputDialog(null, "Enter group name:", "Group Name", -1);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.GroupAction
 * JD-Core Version:    0.6.2
 */