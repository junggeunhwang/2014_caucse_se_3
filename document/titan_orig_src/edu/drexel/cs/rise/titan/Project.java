package edu.drexel.cs.rise.titan;

import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.util.Digraph;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class Project
{
  private static final Project instance = new Project();
  private final List<ProjectListener> listeners = new ArrayList();
  private final Set<Clustering> collapsed = new HashSet();
  private Digraph<String> dependency = null;
  private Clustering cluster = null;
  private File clusterPath = null;
  private File currentDirectory = new File(".");
  private boolean modified = false;
  private boolean weighted = false;
  private boolean rowLabeled = true;

  public static final Project getInstance()
  {
    return instance;
  }

  public final Digraph<String> getDependency()
  {
    return this.dependency;
  }

  public final void setDependency(Digraph<String> paramDigraph)
  {
    this.dependency = paramDigraph;
    fireDependencyChanged();
  }

  public final Clustering getCluster()
  {
    return this.cluster;
  }

  public final void setCluster(Clustering paramClustering)
  {
    this.cluster = paramClustering;
    fireClusterChanged();
  }

  public final void setCluster(Clustering paramClustering, File paramFile)
  {
    this.cluster = paramClustering;
    this.clusterPath = paramFile;
    fireClusterChanged();
  }

  public final Set<Clustering> getCollapsed()
  {
    return this.collapsed;
  }

  public final void clearCollapsed()
  {
    this.collapsed.clear();
  }

  public final File getClusterPath()
  {
    return this.clusterPath;
  }

  public final void setClusterPath(File paramFile)
  {
    this.clusterPath = paramFile;
  }

  public final File getCurrentDirectory()
  {
    return this.currentDirectory;
  }

  public final void setCurrentDirectory(File paramFile)
  {
    this.currentDirectory = paramFile;
  }

  public final boolean isModified()
  {
    return this.modified;
  }

  public final void setModified(boolean paramBoolean)
  {
    this.modified = paramBoolean;
    fireModified();
  }

  public final boolean isWeighted()
  {
    return this.weighted;
  }

  public final void setWeighted(boolean paramBoolean)
  {
    this.weighted = paramBoolean;
  }

  public final boolean isRowLabeled()
  {
    return this.rowLabeled;
  }

  public final void setRowLabeled(boolean paramBoolean)
  {
    this.rowLabeled = paramBoolean;
  }

  public final void addProjectListener(ProjectListener paramProjectListener)
  {
    synchronized (this.listeners)
    {
      this.listeners.add(paramProjectListener);
    }
  }

  public final void removeProjectListener(ProjectListener paramProjectListener)
  {
    synchronized (this.listeners)
    {
      this.listeners.remove(paramProjectListener);
    }
  }

  protected final void fireDependencyChanged()
  {
    Iterator localIterator = getCurrentListeners(this.listeners).iterator();
    while (localIterator.hasNext())
    {
      ProjectListener localProjectListener = (ProjectListener)localIterator.next();
      localProjectListener.dependencyChanged(this);
    }
  }

  protected final void fireClusterChanged()
  {
    Iterator localIterator = getCurrentListeners(this.listeners).iterator();
    while (localIterator.hasNext())
    {
      ProjectListener localProjectListener = (ProjectListener)localIterator.next();
      localProjectListener.clusterChanged(this);
    }
  }

  protected final void fireModified()
  {
    Iterator localIterator = getCurrentListeners(this.listeners).iterator();
    while (localIterator.hasNext())
    {
      ProjectListener localProjectListener = (ProjectListener)localIterator.next();
      localProjectListener.modified(this);
    }
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
 * Qualified Name:     edu.drexel.cs.rise.titan.Project
 * JD-Core Version:    0.6.2
 */