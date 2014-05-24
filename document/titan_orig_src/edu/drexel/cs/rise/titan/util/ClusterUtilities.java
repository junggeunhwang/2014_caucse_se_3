package edu.drexel.cs.rise.titan.util;

import edu.drexel.cs.rise.tellus.cluster.ClusterItem;
import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.ClusterVisitor;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.model.MatrixModel;
import edu.drexel.cs.rise.util.Digraph;
import edu.drexel.cs.rise.util.Interval;
import edu.drexel.cs.rise.util.Matrix;
import edu.drexel.cs.rise.util.TreeNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ClusterUtilities
{
  private static final Pattern nameRegex = Pattern.compile("[a-zA-Z_$][a-zA-Z0-9_$.]*");

  public static Clustering buildDefaultCluster(Digraph<String> paramDigraph)
  {
    ClusterSet localClusterSet = new ClusterSet("$root");
    Set localSet = paramDigraph.vertices();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localClusterSet.addCluster(new ClusterItem(str));
    }
    return localClusterSet;
  }

  public static Clustering trim(Clustering paramClustering, final Set<String> paramSet)
  {
    Stack localStack = new Stack();
    paramClustering.visit(new ClusterVisitor()
    {
      public void visit(ClusterSet paramAnonymousClusterSet)
      {
        ClusterSet localClusterSet = new ClusterSet(paramAnonymousClusterSet.getName());
        if (!this.val$stack.empty())
        {
          localObject = (ClusterSet)this.val$stack.peek();
          localClusterSet.setParent((ClusterSet)localObject);
          ((ClusterSet)localObject).addCluster(localClusterSet);
        }
        this.val$stack.push(localClusterSet);
        Object localObject = paramAnonymousClusterSet.iterator();
        while (((Iterator)localObject).hasNext())
        {
          Clustering localClustering = (Clustering)((Iterator)localObject).next();
          localClustering.visit(this);
        }
        if (this.val$stack.size() > 1)
          this.val$stack.pop();
      }

      public void visit(ClusterItem paramAnonymousClusterItem)
      {
        String str = paramAnonymousClusterItem.getName();
        if (!paramSet.contains(str))
          return;
        if (this.val$stack.empty())
          this.val$stack.push(new ClusterSet("$root"));
        ClusterSet localClusterSet = (ClusterSet)this.val$stack.peek();
        ClusterItem localClusterItem = new ClusterItem(str, localClusterSet);
        localClusterSet.addCluster(localClusterItem);
      }
    });
    if (localStack.empty())
      return null;
    return (Clustering)localStack.pop();
  }

  public static void buildMatrix(Project paramProject, MatrixModel paramMatrixModel)
  {
    Clustering localClustering = paramProject.getCluster();
    if (localClustering == null)
    {
      paramMatrixModel.setData(new Matrix(Collections.emptyList()), null);
      return;
    }
    Digraph localDigraph = paramProject.getDependency();
    final LinkedHashMap localLinkedHashMap = new LinkedHashMap(localDigraph.order());
    Set localSet = paramProject.getCollapsed();
    final Stack localStack = new Stack();
    localClustering.visit(new ClusterVisitor()
    {
      private List<String> childs = null;
      private int n = 1;
      private int k = 0;

      public void visit(ClusterSet paramAnonymousClusterSet)
      {
        int i = (this.childs == null) && (this.val$collapsed.contains(paramAnonymousClusterSet)) ? 1 : 0;
        if (i != 0)
          this.childs = new ArrayList();
        TreeNode localTreeNode = new TreeNode();
        if (!localStack.empty())
          ((TreeNode)localStack.peek()).appendChild(localTreeNode);
        localStack.push(localTreeNode);
        int j = this.k;
        Object localObject = paramAnonymousClusterSet.iterator();
        while (((Iterator)localObject).hasNext())
        {
          Clustering localClustering = (Clustering)((Iterator)localObject).next();
          localClustering.visit(this);
        }
        if (i != 0)
        {
          localObject = getName(paramAnonymousClusterSet);
          localLinkedHashMap.put(localObject, this.childs);
          this.childs = null;
          this.k += 1;
        }
        int m = this.k;
        localTreeNode.setValue(new Interval(j, m));
        if (localStack.size() > 1)
          localStack.pop();
      }

      public void visit(ClusterItem paramAnonymousClusterItem)
      {
        if (this.childs != null)
        {
          this.childs.add(paramAnonymousClusterItem.getName());
        }
        else
        {
          String str = getName(paramAnonymousClusterItem);
          localLinkedHashMap.put(str, Collections.singletonList(paramAnonymousClusterItem.getName()));
          this.k += 1;
        }
      }

      private String getName(Clustering paramAnonymousClustering)
      {
        String str = this.n + "  " + paramAnonymousClustering.getName();
        this.n += 1;
        return str;
      }
    });
    ArrayList localArrayList = new ArrayList(localLinkedHashMap.keySet());
    Matrix localMatrix = new Matrix(localArrayList);
    for (int i = 0; i < localArrayList.size(); i++)
    {
      List localList1 = (List)localLinkedHashMap.get(localArrayList.get(i));
      for (int j = 0; j < localArrayList.size(); j++)
        if (i != j)
        {
          List localList2 = (List)localLinkedHashMap.get(localArrayList.get(j));
          localMatrix.set(i, j, Integer.valueOf(countDependencies(localList2, localList1, localDigraph)));
        }
    }
    TreeNode localTreeNode = null;
    if (!localStack.empty())
      localTreeNode = (TreeNode)localStack.pop();
    paramMatrixModel.setData(localMatrix, localTreeNode);
  }

  protected static int countDependencies(Collection<String> paramCollection1, Collection<String> paramCollection2, Digraph<String> paramDigraph)
  {
    int i = 0;
    Iterator localIterator1 = paramCollection1.iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      Iterator localIterator2 = paramCollection2.iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        if (paramDigraph.containsEdge(str1, str2))
          i++;
      }
    }
    return i;
  }

  public static boolean isValidName(String paramString)
  {
    Matcher localMatcher;
    synchronized (nameRegex)
    {
      localMatcher = nameRegex.matcher(paramString);
    }
    return localMatcher.matches();
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.util.ClusterUtilities
 * JD-Core Version:    0.6.2
 */