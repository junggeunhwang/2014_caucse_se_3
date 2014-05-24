package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.minos.MinosException;
import edu.drexel.cs.rise.minos.cluster.FileParser;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.action.FileAction;
import edu.drexel.cs.rise.titan.util.ActionUtilities;
import edu.drexel.cs.rise.titan.util.ClusterUtilities;
import edu.drexel.cs.rise.titan.util.IconFactory;
import edu.drexel.cs.rise.util.Digraph;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadAction extends FileAction
{
  private static final long serialVersionUID = 10L;

  public LoadAction(Component paramComponent)
  {
    super(paramComponent);
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Load Clustering...");
    putValue("MnemonicKey", Integer.valueOf(76));
    putValue("ShortDescription", "Load Clustering");
    putValue("SmallIcon", IconFactory.load("open-clsx.png"));
    putValue("AcceleratorKey", KeyStroke.getKeyStroke(79, 0x1 | ActionUtilities.CMD_KEY));
    FileNameExtensionFilter localFileNameExtensionFilter = new FileNameExtensionFilter("Cluster file (*.clsx)", new String[] { "clsx" });
    this.chooser.setFileFilter(localFileNameExtensionFilter);
    this.chooser.setDialogTitle("Open Cluster File");
    this.chooser.setMultiSelectionEnabled(false);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Project localProject = Project.getInstance();
    if ((localProject.getDependency() == null) || (!ActionUtilities.querySave(localProject)))
      return;
    prepare();
    if (showOpenDialog() != 1)
    {
      File localFile = this.chooser.getSelectedFile();
      load(localFile);
    }
  }

  public void load(File paramFile)
  {
    Project localProject = Project.getInstance();
    try
    {
      Clustering localClustering = ClusterUtilities.trim(FileParser.load(paramFile), localProject.getDependency().vertices());
      localProject.clearCollapsed();
      localProject.getCollapsed().add(localClustering);
      localProject.setCluster(localClustering, paramFile);
      localProject.setModified(false);
    }
    catch (MinosException localMinosException)
    {
      JOptionPane.showMessageDialog(this.parent, "Unable to load cluster file: " + localMinosException.getMessage(), "Error", 0);
    }
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.LoadAction
 * JD-Core Version:    0.6.2
 */