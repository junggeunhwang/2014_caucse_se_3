package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.model.ClusterModel;
import edu.drexel.cs.rise.titan.ui.ClusterViewer;
import edu.drexel.cs.rise.titan.util.ActionUtilities;
import edu.drexel.cs.rise.titan.util.IconFactory;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class UpAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  protected final ClusterViewer viewer;

  public UpAction(ClusterViewer paramClusterViewer)
  {
    this.viewer = paramClusterViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Move Up");
    putValue("ShortDescription", "Move Up");
    putValue("SmallIcon", IconFactory.load("up.png"));
    putValue("AcceleratorKey", KeyStroke.getKeyStroke(38, ActionUtilities.CMD_KEY));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    TreeSelectionModel localTreeSelectionModel = this.viewer.getSelectionModel();
    if (localTreeSelectionModel.isSelectionEmpty())
      return;
    TreePath localTreePath1 = localTreeSelectionModel.getSelectionPath().getParentPath();
    Object localObject = localTreePath1 != null ? (ClusterSet)localTreePath1.getLastPathComponent() : null;
    if (localObject == null)
      return;
    JTree localJTree = this.viewer.getTree();
    ArrayList localArrayList = new ArrayList();
    int[] arrayOfInt1 = localTreeSelectionModel.getSelectionRows();
    Arrays.sort(arrayOfInt1);
    for (int k : arrayOfInt1)
    {
      TreePath localTreePath2 = localJTree.getPathForRow(k);
      TreePath localTreePath3 = localTreePath2.getParentPath();
      if ((localTreePath3 == null) || (localTreePath3.getLastPathComponent() != localObject))
        return;
      Clustering localClustering = (Clustering)localTreePath2.getLastPathComponent();
      int m = localObject.getClusterIndex(localClustering);
      if (m != 0)
      {
        localObject.removeCluster(localClustering.getName());
        localObject.addCluster(m - 1, localClustering);
        localArrayList.add(localTreePath2);
      }
    }
    this.viewer.getModel().fireChangeEvent(localTreePath1);
    localTreeSelectionModel.setSelectionPaths((TreePath[])localArrayList.toArray(new TreePath[localArrayList.size()]));
    Project.getInstance().setModified(true);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.UpAction
 * JD-Core Version:    0.6.2
 */