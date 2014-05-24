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

public class CollapseAllAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  private final JTree tree;

  public CollapseAllAction(JTree paramJTree)
  {
    this.tree = paramJTree;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Collapse All");
    putValue("ShortDescription", "Collapse All");
    putValue("SmallIcon", IconFactory.load("collapse.png"));
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
        Object localObject = paramAnonymousClusterSet.iterator();
        while (((Iterator)localObject).hasNext())
        {
          Clustering localClustering = (Clustering)((Iterator)localObject).next();
          localClustering.visit(this);
        }
        localObject = new TreePath(this.path.toArray());
        this.val$tree.collapsePath((TreePath)localObject);
        this.path.pop();
      }

      public void visit(ClusterItem paramAnonymousClusterItem)
      {
      }
    });
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.CollapseAllAction
 * JD-Core Version:    0.6.2
 */