package edu.drexel.cs.rise.titan.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class AboutAction extends AbstractAction
{
  private static final long serialVersionUID = 10L;
  private static final String message = "Titan%nversion %s%nCopyright(c) 2009-%s, Drexel University";
  protected final Component parent;

  public AboutAction(Component paramComponent)
  {
    this.parent = paramComponent;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "About...");
    putValue("MnemonicKey", Integer.valueOf(65));
    putValue("ShortDescription", "About");
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    String str = String.valueOf(Calendar.getInstance().get(1));
    JOptionPane.showMessageDialog(this.parent, String.format("Titan%nversion %s%nCopyright(c) 2009-%s, Drexel University", new Object[] { "1.0", str }), "About Titan", 1);
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.AboutAction
 * JD-Core Version:    0.6.2
 */