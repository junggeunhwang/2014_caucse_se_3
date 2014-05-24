package edu.drexel.cs.rise.titan.action;

import edu.drexel.cs.rise.minos.MinosException;
import edu.drexel.cs.rise.minos.dsm.FileWriter;
import edu.drexel.cs.rise.tellus.cluster.ClusterItem;
import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.ClusterVisitor;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.model.MatrixModel;
import edu.drexel.cs.rise.titan.ui.MatrixViewer;
import edu.drexel.cs.rise.titan.util.IconFactory;
import edu.drexel.cs.rise.util.Digraph;
import edu.drexel.cs.rise.util.Matrix;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExportMatrixAction extends FileAction
{
  private static final long serialVersionUID = 10L;
  private static final String extension = ".dsm";
  private final MatrixViewer viewer;

  public ExportMatrixAction(Component paramComponent, MatrixViewer paramMatrixViewer)
  {
    super(paramComponent);
    this.viewer = paramMatrixViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "DSM...");
    putValue("MnemonicKey", Integer.valueOf(68));
    putValue("ShortDescription", "Export DSM File");
    putValue("SmallIcon", IconFactory.load("dsm.png"));
    FileNameExtensionFilter localFileNameExtensionFilter = new FileNameExtensionFilter("DSM file (*.dsm)", new String[] { "dsm" });
    this.chooser.setFileFilter(localFileNameExtensionFilter);
    this.chooser.setDialogTitle("Export DSM File");
    this.chooser.setMultiSelectionEnabled(false);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    prepare();
    if (showSaveDialog() != 1)
    {
      File localFile = getPathWithExtension(this.chooser.getSelectedFile(), ".dsm");
      if (!confirmOverwrite(localFile))
        return;
      export(localFile);
    }
  }

  protected void export(File paramFile)
  {
    Matrix localMatrix = this.viewer.getModel().getData();
    Digraph localDigraph = buildGraph(localMatrix);
    Clustering localClustering = buildCluster(localDigraph);
    try
    {
      FileWriter.save(localDigraph, localClustering, paramFile);
    }
    catch (MinosException localMinosException)
    {
      JOptionPane.showMessageDialog(this.parent, "Unable to export DSM file: " + localMinosException.getMessage(), "Error", 0);
    }
  }

  protected Digraph<String> buildGraph(Matrix<Integer, String> paramMatrix)
  {
    ArrayList localArrayList = new ArrayList();
    Digraph localDigraph = new Digraph();
    Iterator localIterator = paramMatrix.getLabels().iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localObject = renameLabel(str);
      localArrayList.add(localObject);
      localDigraph.addVertex(localObject);
    }
    for (int i = 0; i < paramMatrix.length(); i++)
      for (int j = 0; j < paramMatrix.length(); j++)
        if (i != j)
        {
          localObject = (Integer)paramMatrix.get(i, j);
          if ((localObject != null) && (((Integer)localObject).intValue() != 0))
            localDigraph.addEdge(localArrayList.get(j), localArrayList.get(i));
        }
    return localDigraph;
  }

  protected Clustering buildCluster(final Digraph<String> paramDigraph)
  {
    Clustering localClustering = Project.getInstance().getCluster();
    final ClusterSet localClusterSet = new ClusterSet("$root");
    localClustering.visit(new ClusterVisitor()
    {
      public void visit(ClusterSet paramAnonymousClusterSet)
      {
        String str = paramAnonymousClusterSet.getName();
        if (paramDigraph.containsVertex(str))
        {
          localClusterSet.addCluster(new ClusterItem(str, localClusterSet));
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
      }

      public void visit(ClusterItem paramAnonymousClusterItem)
      {
        String str = paramAnonymousClusterItem.getName();
        if (paramDigraph.containsVertex(str))
          localClusterSet.addCluster(new ClusterItem(str, localClusterSet));
      }
    });
    return localClusterSet;
  }

  protected String renameLabel(String paramString)
  {
    return paramString.replaceFirst("^\\d+\\s+", "");
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.ExportMatrixAction
 * JD-Core Version:    0.6.2
 */