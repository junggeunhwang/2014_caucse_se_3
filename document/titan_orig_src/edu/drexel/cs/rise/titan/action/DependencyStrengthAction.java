package edu.drexel.cs.rise.titan.action;

import edu.drexel.cs.rise.titan.Project;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class DependencyStrengthAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;

  public DependencyStrengthAction()
  {
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Show Dependency Strength");
    putValue("MnemonicKey", Integer.valueOf(68));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Project localProject = Project.getInstance();
    Boolean localBoolean = (Boolean)getValue("SwingSelectedKey");
    localProject.setWeighted(localBoolean.booleanValue());
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.DependencyStrengthAction
 * JD-Core Version:    0.6.2
 */