package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.model.ClusterModel;
import edu.drexel.cs.rise.titan.ui.ClusterViewer;
import edu.drexel.cs.rise.titan.util.IconFactory;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class DeleteAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  protected final ClusterViewer viewer;

  public DeleteAction(ClusterViewer paramClusterViewer)
  {
    this.viewer = paramClusterViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Delete");
    putValue("ShortDescription", "Delete");
    putValue("SmallIcon", IconFactory.load("delete.png"));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    TreeSelectionModel localTreeSelectionModel = this.viewer.getSelectionModel();
    Project localProject = Project.getInstance();
    for (TreePath localTreePath1 : localTreeSelectionModel.getSelectionPaths())
    {
      TreePath localTreePath2 = localTreePath1.getParentPath();
      if (localTreePath2 != null)
      {
        ClusterSet localClusterSet = (ClusterSet)localTreePath2.getLastPathComponent();
        Clustering localClustering = (Clustering)localTreePath1.getLastPathComponent();
        localClusterSet.removeCluster(localClustering.getName());
        localClustering.setParent(null);
        this.viewer.getModel().fireChangeEvent(localTreePath2);
        localProject.setModified(true);
      }
    }
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.DeleteAction
 * JD-Core Version:    0.6.2
 */