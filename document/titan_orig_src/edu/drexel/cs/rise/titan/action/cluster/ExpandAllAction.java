package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.tellus.cluster.ClusterItem;
import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.ClusterVisitor;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.util.IconFactory;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Stack;
import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

public class ExpandAllAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  private final JTree tree;

  public ExpandAllAction(JTree paramJTree)
  {
    this.tree = paramJTree;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Expand All");
    putValue("ShortDescription", "Expand All");
    putValue("SmallIcon", IconFactory.load("expand.png"));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    execute(this.tree, Project.getInstance().getCluster());
  }

  public static void execute(JTree paramJTree, Clustering paramClustering)
  {
    if (paramClustering == null)
      return;
    paramClustering.visit(new ClusterVisitor()
    {
      final Stack<Clustering> path = new Stack();

      public void visit(ClusterSet paramAnonymousClusterSet)
      {
        this.path.add(paramAnonymousClusterSet);
        Iterator localIterator = paramAnonymousClusterSet.iterator();
        while (localIterator.hasNext())
        {
          Clustering localClustering = (Clustering)localIterator.next();
          localClustering.visit(this);
        }
        this.path.pop();
      }

      public void visit(ClusterItem paramAnonymousClusterItem)
      {
        TreePath localTreePath = new TreePath(this.path.toArray());
        this.val$tree.expandPath(localTreePath);
      }
    });
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.ExpandAllAction
 * JD-Core Version:    0.6.2
 */