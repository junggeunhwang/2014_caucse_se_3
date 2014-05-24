package edu.drexel.cs.rise.titan.action;

import edu.drexel.cs.rise.titan.Project;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class RowLabelAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;

  public RowLabelAction()
  {
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Show Row Labels");
    putValue("MnemonicKey", Integer.valueOf(76));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Project localProject = Project.getInstance();
    Boolean localBoolean = (Boolean)getValue("SwingSelectedKey");
    localProject.setRowLabeled(localBoolean.booleanValue());
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.RowLabelAction
 * JD-Core Version:    0.6.2
 */