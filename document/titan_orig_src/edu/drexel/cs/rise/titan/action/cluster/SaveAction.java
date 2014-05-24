package edu.drexel.cs.rise.titan.action.cluster;

import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.util.ActionUtilities;
import edu.drexel.cs.rise.titan.util.IconFactory;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.KeyStroke;

public class SaveAction extends SaveAsAction
{
  private static final long serialVersionUID = 10L;

  public SaveAction(Component paramComponent)
  {
    super(paramComponent);
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Save Clustering");
    putValue("MnemonicKey", Integer.valueOf(83));
    putValue("ShortDescription", "Save Clustering");
    putValue("SmallIcon", IconFactory.load("save-clsx.png"));
    putValue("AcceleratorKey", KeyStroke.getKeyStroke(83, ActionUtilities.CMD_KEY));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    File localFile = Project.getInstance().getClusterPath();
    if (localFile == null)
      super.actionPerformed(paramActionEvent);
    else
      super.save(localFile);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.cluster.SaveAction
 * JD-Core Version:    0.6.2
 */