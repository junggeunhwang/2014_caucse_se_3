package edu.drexel.cs.rise.titan.action;

import edu.drexel.cs.rise.tellus.cluster.ClusterItem;
import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.ClusterVisitor;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.ui.ClusterViewer;
import edu.drexel.cs.rise.titan.ui.MatrixViewer;
import edu.drexel.cs.rise.titan.util.IconFactory;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

public class RedrawAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  protected final ClusterViewer cluster;
  protected final MatrixViewer matrix;

  public RedrawAction(ClusterViewer paramClusterViewer, MatrixViewer paramMatrixViewer)
  {
    this.cluster = paramClusterViewer;
    this.matrix = paramMatrixViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Redraw");
    putValue("MnemonicKey", Integer.valueOf(82));
    putValue("ShortDescription", "Redraw");
    putValue("SmallIcon", IconFactory.load("redraw.png"));
    putValue("AcceleratorKey", KeyStroke.getKeyStroke("F5"));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Project localProject = Project.getInstance();
    final Set localSet = localProject.getCollapsed();
    localSet.clear();
    localProject.getCluster().visit(new ClusterVisitor()
    {
      private Stack<ClusterSet> path = new Stack();

      public void visit(ClusterSet paramAnonymousClusterSet)
      {
        this.path.push(paramAnonymousClusterSet);
        if (!RedrawAction.this.cluster.isExpanded(new TreePath(this.path.toArray())))
        {
          localSet.add(paramAnonymousClusterSet);
        }
        else
        {
          Iterator localIterator = paramAnonymousClusterSet.iterator();
          while (localIterator.hasNext())
          {
            Clustering localClustering = (Clustering)localIterator.next();
            localClustering.visit(this);
          }
        }
        this.path.pop();
      }

      public void visit(ClusterItem paramAnonymousClusterItem)
      {
      }
    });
    this.matrix.update(localProject);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.RedrawAction
 * JD-Core Version:    0.6.2
 */