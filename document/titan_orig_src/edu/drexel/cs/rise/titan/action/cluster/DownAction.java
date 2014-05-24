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
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class DownAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  protected final ClusterViewer viewer;

  public DownAction(ClusterViewer paramClusterViewer)
  {
    this.viewer = paramClusterViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Move Down");
    putValue("ShortDescription", "Move Down");
    putValue("SmallIcon", IconFactory.load("down.png"));
    putValue("AcceleratorKey", KeyStroke.getKeyStroke(40, ActionUtilities.CMD_KEY));
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
    int[] arrayOfInt = localTreeSelectionModel.getSelectionRows();
    Arrays.sort(arrayOfInt);
    for (int i = arrayOfInt.length - 1; i >= 0; i--)
    {
      TreePath localTreePath2 = localJTree.getPathForRow(arrayOfInt[i]);
      TreePath localTreePath3 = localTreePath2.getParentPath();
      if ((localTreePath3 == null) || (localTreePath3.getLastPathComponent() != localObject))
        return;
      Clustering localClustering = (Clustering)localTreePath2.getLastPathComponent();
      int j = localObject.getClusterIndex(localClustering);
      if (j != localObject.clusters().size() - 1)
      {
        localObject.removeCluster(localClustering.getName());
        localObject.addCluster(j + 1, localClustering);
        localArrayList.add(localTreePath2);
      }
    }
    this.viewer.getModel().fireChangeEvent(localTreePath1);
    localTreeSelectionModel.setSelectionPaths((TreePath[])localArrayList.toArray(new TreePath[localArrayList.size()]));
    Project.getInstance().setModified(true);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.DownAction
 * JD-Core Version:    0.6.2
 */