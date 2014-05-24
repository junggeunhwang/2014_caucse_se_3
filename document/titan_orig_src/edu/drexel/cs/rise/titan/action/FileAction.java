package edu.drexel.cs.rise.titan.action;

import edu.drexel.cs.rise.titan.Project;
import java.awt.Component;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public abstract class FileAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  protected final JFileChooser chooser = new JFileChooser();
  protected final Component parent;
  private int option;

  protected FileAction(Component paramComponent)
  {
    this.parent = paramComponent;
  }

  protected void prepare()
  {
    Project localProject = Project.getInstance();
    this.chooser.setCurrentDirectory(localProject.getCurrentDirectory());
    this.option = 1;
  }

  protected final int getOption()
  {
    return this.option;
  }

  protected final int showOpenDialog()
  {
    this.option = this.chooser.showOpenDialog(this.parent);
    updateCurrentDirectory();
    return this.option;
  }

  protected final int showSaveDialog()
  {
    this.option = this.chooser.showSaveDialog(this.parent);
    updateCurrentDirectory();
    return this.option;
  }

  protected final void updateCurrentDirectory()
  {
    if (this.option != 1)
      Project.getInstance().setCurrentDirectory(this.chooser.getCurrentDirectory());
  }

  protected final File getPathWithExtension(File paramFile, String paramString)
  {
    if (!paramFile.getName().endsWith(paramString))
      return new File(paramFile.getParentFile(), paramFile.getName() + paramString);
    return paramFile;
  }

  protected final boolean confirmOverwrite(File paramFile)
  {
    return (!paramFile.exists()) || (queryOverwrite(paramFile.getName()) != 2);
  }

  protected final int queryOverwrite(String paramString)
  {
    String str = String.format("File \"%s\" already exists. Do you want to replace it?", new Object[] { paramString });
    return JOptionPane.showConfirmDialog(this.parent, str, "Confirm Overwrite", 0);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.FileAction
 * JD-Core Version:    0.6.2
 */