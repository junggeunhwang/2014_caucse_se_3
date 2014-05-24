package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.model.ClusterModel;
import edu.drexel.cs.rise.titan.ui.ClusterViewer;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class SortAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  protected final ClusterViewer viewer;

  public SortAction(ClusterViewer paramClusterViewer)
  {
    this.viewer = paramClusterViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Sort");
    putValue("ShortDescription", "Sort children");
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    TreeSelectionModel localTreeSelectionModel = this.viewer.getSelectionModel();
    if (localTreeSelectionModel.isSelectionEmpty())
      return;
    TreePath localTreePath = localTreeSelectionModel.getLeadSelectionPath();
    if (!(localTreePath.getLastPathComponent() instanceof ClusterSet))
      return;
    ClusterSet localClusterSet = (ClusterSet)localTreePath.getLastPathComponent();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localClusterSet.iterator();
    Clustering localClustering;
    while (localIterator.hasNext())
    {
      localClustering = (Clustering)localIterator.next();
      localArrayList.add(localClustering);
    }
    localClusterSet.clear();
    Collections.sort(localArrayList, new Comparator()
    {
      public int compare(Clustering paramAnonymousClustering1, Clustering paramAnonymousClustering2)
      {
        return paramAnonymousClustering1.getName().compareToIgnoreCase(paramAnonymousClustering2.getName());
      }
    });
    localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      localClustering = (Clustering)localIterator.next();
      localClusterSet.addCluster(localClustering);
    }
    this.viewer.getModel().fireChangeEvent(localTreePath);
    Project.getInstance().setModified(true);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.SortAction
 * JD-Core Version:    0.6.2
 */