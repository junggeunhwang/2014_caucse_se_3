package edu.drexel.cs.rise.titan.model;

import edu.drexel.cs.rise.tellus.cluster.ClusterItem;
import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ClusterModel
  implements TreeModel
{
  private Clustering root;
  private final List<TreeModelListener> listeners = Collections.synchronizedList(new ArrayList());

  public ClusterModel()
  {
    this(null);
  }

  public ClusterModel(Clustering paramClustering)
  {
    this.root = paramClustering;
  }

  public Clustering getRoot()
  {
    return this.root;
  }

  public void setRoot(Clustering paramClustering)
  {
    this.root = paramClustering;
    fireChangeEvent(new TreePath(this.root));
  }

  public Clustering getChild(Object paramObject, int paramInt)
  {
    ClusterSet localClusterSet = castToClusterSet(paramObject);
    return localClusterSet.getCluster(paramInt);
  }

  public int getChildCount(Object paramObject)
  {
    ClusterSet localClusterSet = castToClusterSet(paramObject);
    return localClusterSet.clusters().size();
  }

  public int getIndexOfChild(Object paramObject1, Object paramObject2)
  {
    ClusterSet localClusterSet = castToClusterSet(paramObject1);
    Clustering localClustering = castToClustering(paramObject2);
    return localClusterSet.getClusterIndex(localClustering);
  }

  public boolean isLeaf(Object paramObject)
  {
    if (paramObject == null)
      throw new NullPointerException();
    if ((paramObject instanceof ClusterItem))
      return true;
    if ((paramObject instanceof ClusterSet))
      return false;
    throw new IllegalArgumentException();
  }

  public void valueForPathChanged(TreePath paramTreePath, Object paramObject)
  {
    Object localObject = paramTreePath.getLastPathComponent();
    Clustering localClustering = castToClustering(localObject);
    if ((paramObject instanceof String))
    {
      localClustering.setName(paramObject.toString());
    }
    else if ((paramObject instanceof Clustering))
    {
      ClusterSet localClusterSet = castToClusterSet(paramTreePath.getParentPath().getLastPathComponent());
      localClusterSet.removeCluster(localClustering.getName());
      localClusterSet.addCluster(castToClustering(paramObject));
    }
  }

  protected static final Clustering castToClustering(Object paramObject)
  {
    if (paramObject == null)
      throw new NullPointerException();
    if ((paramObject instanceof Clustering))
      return (Clustering)paramObject;
    throw new IllegalArgumentException();
  }

  protected static final ClusterSet castToClusterSet(Object paramObject)
  {
    if (paramObject == null)
      throw new NullPointerException();
    if ((paramObject instanceof ClusterSet))
      return (ClusterSet)paramObject;
    throw new IllegalArgumentException();
  }

  public void fireChangeEvent(TreePath paramTreePath)
  {
    List localList = getCurrentListeners(this.listeners);
    TreeModelEvent localTreeModelEvent = new TreeModelEvent(this, paramTreePath);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      TreeModelListener localTreeModelListener = (TreeModelListener)localIterator.next();
      localTreeModelListener.treeStructureChanged(localTreeModelEvent);
    }
  }

  public void fireChangeEvent(TreePath paramTreePath, int[] paramArrayOfInt, Clustering[] paramArrayOfClustering)
  {
    List localList = getCurrentListeners(this.listeners);
    TreeModelEvent localTreeModelEvent = new TreeModelEvent(this, paramTreePath, paramArrayOfInt, paramArrayOfClustering);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      TreeModelListener localTreeModelListener = (TreeModelListener)localIterator.next();
      localTreeModelListener.treeStructureChanged(localTreeModelEvent);
    }
  }

  public synchronized void addTreeModelListener(TreeModelListener paramTreeModelListener)
  {
    this.listeners.add(paramTreeModelListener);
  }

  public synchronized void removeTreeModelListener(TreeModelListener paramTreeModelListener)
  {
    this.listeners.remove(paramTreeModelListener);
  }

  protected static final <T> List<T> getCurrentListeners(List<T> paramList)
  {
    synchronized (paramList)
    {
      return new ArrayList(paramList);
    }
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.model.ClusterModel
 * JD-Core Version:    0.6.2
 */