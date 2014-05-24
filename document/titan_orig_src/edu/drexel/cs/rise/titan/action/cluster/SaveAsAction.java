package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.minos.MinosException;
import edu.drexel.cs.rise.minos.cluster.FileWriter;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.action.FileAction;
import edu.drexel.cs.rise.titan.util.IconFactory;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveAsAction extends FileAction
{
  private static final long serialVersionUID = 10L;
  private static final String extension = ".clsx";

  public SaveAsAction(Component paramComponent)
  {
    super(paramComponent);
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Save Clustering As...");
    putValue("MnemonicKey", Integer.valueOf(65));
    putValue("ShortDescription", "Save Clustering As");
    putValue("SmallIcon", IconFactory.load("save-clsx-as.png"));
    FileNameExtensionFilter localFileNameExtensionFilter = new FileNameExtensionFilter("Cluster file (*.clsx)", new String[] { "clsx" });
    this.chooser.setFileFilter(localFileNameExtensionFilter);
    this.chooser.setDialogTitle("Save Cluster File");
    this.chooser.setMultiSelectionEnabled(false);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    prepare();
    if (showSaveDialog() != 1)
    {
      File localFile = getPathWithExtension(this.chooser.getSelectedFile(), ".clsx");
      if (!confirmOverwrite(localFile))
        return;
      save(localFile);
    }
  }

  protected void save(File paramFile)
  {
    try
    {
      Project localProject = Project.getInstance();
      Clustering localClustering = localProject.getCluster();
      FileWriter.save(localClustering, paramFile);
      localProject.setClusterPath(paramFile);
      localProject.setModified(false);
    }
    catch (MinosException localMinosException)
    {
      JOptionPane.showMessageDialog(this.parent, "Unable to save cluster file: " + localMinosException.getMessage(), "Error", 0);
    }
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.SaveAsAction
 * JD-Core Version:    0.6.2
 */