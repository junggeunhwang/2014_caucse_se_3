package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.util.ActionUtilities;
import edu.drexel.cs.rise.titan.util.ClusterUtilities;
import edu.drexel.cs.rise.titan.util.IconFactory;
import java.awt.event.ActionEvent;
import java.util.Set;
import javax.swing.AbstractAction;

public class NewAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;

  public NewAction()
  {
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "New Clustering");
    putValue("MnemonicKey", Integer.valueOf(78));
    putValue("ShortDescription", "New Clustering");
    putValue("SmallIcon", IconFactory.load("new-clsx.png"));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Project localProject = Project.getInstance();
    if (!ActionUtilities.querySave(localProject))
      return;
    Clustering localClustering = ClusterUtilities.buildDefaultCluster(localProject.getDependency());
    localProject.clearCollapsed();
    localProject.getCollapsed().add(localClustering);
    localProject.setCluster(localClustering, null);
    localProject.setModified(false);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.NewAction
 * JD-Core Version:    0.6.2
 */