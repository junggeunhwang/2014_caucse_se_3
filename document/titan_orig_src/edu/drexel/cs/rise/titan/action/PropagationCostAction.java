package edu.drexel.cs.rise.titan.action;

import edu.drexel.cs.rise.civitas.propagation.PropagationCost;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.Viewer;
import edu.drexel.cs.rise.util.Digraph;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PropagationCostAction extends FileAction
{
  private static final long serialVersionUID = 10L;
  private static final int height = 100;
  private static final int width = 300;
  protected final Viewer parent;

  public PropagationCostAction(Viewer paramViewer)
  {
    super(paramViewer);
    this.parent = paramViewer;
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Propagation Cost");
    putValue("MnemonicKey", Integer.valueOf(80));
    putValue("ShortDescription", "Calculate Propagation Cost");
    FileNameExtensionFilter localFileNameExtensionFilter = new FileNameExtensionFilter("Comma-Seperated Value File (*.csv)", new String[] { ".csv" });
    this.chooser.addChoosableFileFilter(localFileNameExtensionFilter);
    this.chooser.setFileFilter(localFileNameExtensionFilter);
    this.chooser.setDialogTitle("Export Propagation Costs");
    this.chooser.setMultiSelectionEnabled(false);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    JDialog localJDialog = new JDialog(this.parent, false);
    localJDialog.setTitle("Propagation Cost");
    localJDialog.setSize(300, 100);
    localJDialog.setLocationRelativeTo(this.parent);
    JPanel localJPanel = new JPanel();
    localJDialog.getContentPane().add(localJPanel);
    localJPanel.add(new JLabel("Propagation Cost:"), "East");
    JLabel localJLabel = new JLabel();
    localJPanel.add(localJLabel, "Center");
    double d = PropagationCost.calculatePropagationCost(Project.getInstance().getDependency());
    localJLabel.setText(Double.toString(d));
    JButton localJButton = new JButton("Export...");
    localJButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        PropagationCostAction.this.prepare();
        if (PropagationCostAction.this.showSaveDialog() != 1)
        {
          File localFile = PropagationCostAction.this.getPathWithExtension(PropagationCostAction.this.chooser.getSelectedFile(), ((FileNameExtensionFilter)PropagationCostAction.this.chooser.getFileFilter()).getExtensions()[0]);
          if (!PropagationCostAction.this.confirmOverwrite(localFile))
            return;
          PropagationCostAction.this.export(localFile);
        }
      }
    });
    localJPanel.add(localJButton, "South");
    localJDialog.getRootPane().setDefaultButton(localJButton);
    localJDialog.setVisible(true);
  }

  private void export(File paramFile)
  {
    Digraph localDigraph = Project.getInstance().getDependency();
    ArrayList localArrayList = new ArrayList();
    Object localObject1 = localDigraph.vertices().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (String)((Iterator)localObject1).next();
      localArrayList.add(localObject2);
    }
    localObject1 = PropagationCost.calculateFanInCosts(localDigraph);
    Object localObject2 = PropagationCost.calculateFanOutCosts(localDigraph);
    try
    {
      PrintStream localPrintStream = new PrintStream(new FileOutputStream(paramFile));
      localPrintStream.println("variables,fanin,fanout");
      for (int i = 0; i < localArrayList.size(); i++)
        localPrintStream.println((String)localArrayList.get(i) + "," + localObject1[i] + "," + localObject2[i]);
      localPrintStream.close();
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.PropagationCostAction
 * JD-Core Version:    0.6.2
 */