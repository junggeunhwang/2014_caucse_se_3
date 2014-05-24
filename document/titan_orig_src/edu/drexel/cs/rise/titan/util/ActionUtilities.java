package edu.drexel.cs.rise.titan.util;

import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.action.cluster.SaveAction;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

public final class ActionUtilities
{
  public static final int CMD_KEY = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

  public static boolean querySave(Project paramProject)
  {
    if (paramProject.isModified())
    {
      int i = JOptionPane.showConfirmDialog(null, "Clustering has been modified. Save changes?", "Save Changes?", 1);
      switch (i)
      {
      case 2:
        return false;
      case 0:
        new SaveAction(null).actionPerformed(null);
      }
    }
    return true;
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.util.ActionUtilities
 * JD-Core Version:    0.6.2
 */