package edu.drexel.cs.rise.titan.action;

import edu.drexel.cs.rise.minos.MinosException;
import edu.drexel.cs.rise.minos.dsm.FileParser;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.util.ActionUtilities;
import edu.drexel.cs.rise.titan.util.IconFactory;
import edu.drexel.cs.rise.util.Digraph;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OpenMatrixAction extends FileAction
{
  private static final long serialVersionUID = 10L;

  public OpenMatrixAction(Component paramComponent)
  {
    super(paramComponent);
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Open DSM...");
    putValue("MnemonicKey", Integer.valueOf(79));
    putValue("ShortDescription", "Open DSM");
    putValue("SmallIcon", IconFactory.load("open-dsm.png"));
    putValue("AcceleratorKey", KeyStroke.getKeyStroke(79, ActionUtilities.CMD_KEY));
    FileNameExtensionFilter localFileNameExtensionFilter = new FileNameExtensionFilter("DSM file (*.dsm)", new String[] { "dsm" });
    this.chooser.setFileFilter(localFileNameExtensionFilter);
    this.chooser.setDialogTitle("Open DSM File");
    this.chooser.setMultiSelectionEnabled(false);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Project localProject = Project.getInstance();
    if ((localProject.getDependency() != null) && (!ActionUtilities.querySave(localProject)))
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
    try
    {
      Digraph localDigraph = FileParser.load(paramFile);
      Project localProject = Project.getInstance();
      localProject.setDependency(localDigraph);
      localProject.setClusterPath(null);
      localProject.setModified(false);
    }
    catch (MinosException localMinosException)
    {
      JOptionPane.showMessageDialog(this.parent, "Unable to load DSM file: " + localMinosException.getMessage(), "Error", 0);
    }
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.OpenMatrixAction
 * JD-Core Version:    0.6.2
 */