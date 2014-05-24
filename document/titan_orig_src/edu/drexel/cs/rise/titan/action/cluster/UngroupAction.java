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
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class UngroupAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  protected final ClusterViewer viewer;

  public UngroupAction(ClusterViewer paramClusterViewer)
  {
    this.viewer = paramClusterViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Ungroup");
    putValue("ShortDescription", "Ungroup");
    putValue("SmallIcon", IconFactory.load("ungroup.png"));
    putValue("AcceleratorKey", KeyStroke.getKeyStroke(72, ActionUtilities.CMD_KEY));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    TreeSelectionModel localTreeSelectionModel = this.viewer.getSelectionModel();
    if (localTreeSelectionModel.isSelectionEmpty())
      return;
    JTree localJTree = this.viewer.getTree();
    ArrayList localArrayList = new ArrayList();
    int[] arrayOfInt = localTreeSelectionModel.getSelectionRows();
    Arrays.sort(arrayOfInt);
    for (int i = arrayOfInt.length - 1; i >= 0; i--)
      if (arrayOfInt[i] != 0)
      {
        TreePath localTreePath1 = localJTree.getPathForRow(arrayOfInt[i]);
        if ((localTreePath1.getLastPathComponent() instanceof ClusterSet))
        {
          TreePath localTreePath2 = localTreePath1.getParentPath();
          ClusterSet localClusterSet1 = (ClusterSet)localTreePath1.getLastPathComponent();
          ClusterSet localClusterSet2 = localClusterSet1.getParent();
          int j = localClusterSet2.getClusterIndex(localClusterSet1);
          Iterator localIterator = localClusterSet1.iterator();
          while (localIterator.hasNext())
          {
            Clustering localClustering = (Clustering)localIterator.next();
            localClusterSet2.addCluster(j++, localClustering);
            localClustering.setParent(localClusterSet2);
            localArrayList.add(localTreePath2.pathByAddingChild(localClustering));
          }
          localClusterSet2.removeCluster(localClusterSet1.getName());
          localClusterSet1.setParent(null);
          this.viewer.getModel().fireChangeEvent(localTreePath2);
        }
      }
    localTreeSelectionModel.setSelectionPaths((TreePath[])localArrayList.toArray(new TreePath[localArrayList.size()]));
    Project.getInstance().setModified(true);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.UngroupAction
 * JD-Core Version:    0.6.2
 */