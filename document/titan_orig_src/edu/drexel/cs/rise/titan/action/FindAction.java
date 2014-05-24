package edu.drexel.cs.rise.titan.action;

import edu.drexel.cs.rise.titan.Viewer;
import edu.drexel.cs.rise.titan.ui.Find;
import edu.drexel.cs.rise.titan.util.ActionUtilities;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class FindAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  protected final Viewer parent;

  public FindAction(Viewer paramViewer)
  {
    this.parent = paramViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Find...");
    putValue("MnemonicKey", Integer.valueOf(70));
    putValue("ShortDescription", "Find");
    putValue("AcceleratorKey", KeyStroke.getKeyStroke(70, ActionUtilities.CMD_KEY));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Find localFind = new Find(this.parent);
    localFind.setVisible(true);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.FindAction
 * JD-Core Version:    0.6.2
 */