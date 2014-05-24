package edu.drexel.cs.rise.titan.ui;

import edu.drexel.cs.rise.tellus.cluster.ClusterItem;
import edu.drexel.cs.rise.tellus.cluster.ClusterSet;
import edu.drexel.cs.rise.tellus.cluster.ClusterVisitor;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.Viewer;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public final class Find extends JDialog
  implements ActionListener
{
  private static final long serialVersionUID = 10L;
  private static final int height = 100;
  private static final int width = 300;
  private final Viewer frame;
  private final JTextField text;
  private final JPanel panel;
  private final JButton find = new JButton("find");
  private final JButton cancel = new JButton("cancel");
  private final JCheckBox casesensitive = new JCheckBox("casesensitive");

  public Find(Viewer paramViewer)
  {
    super(paramViewer, false);
    this.frame = paramViewer;
    this.panel = new JPanel();
    getContentPane().add(this.panel);
    getRootPane().setDefaultButton(this.find);
    this.panel.add(new JLabel("Find"));
    this.text = new JTextField(10);
    this.text.getDocument().addDocumentListener(new DocumentListener()
    {
      public void changedUpdate(DocumentEvent paramAnonymousDocumentEvent)
      {
        check();
      }

      public void insertUpdate(DocumentEvent paramAnonymousDocumentEvent)
      {
        check();
      }

      public void removeUpdate(DocumentEvent paramAnonymousDocumentEvent)
      {
        check();
      }

      private void check()
      {
        Find.this.find.setEnabled(Find.this.text.getDocument().getLength() > 0);
      }
    });
    this.panel.add(this.text);
    this.casesensitive.setText("Case Sensitive");
    this.casesensitive.setSelected(false);
    this.panel.add(this.casesensitive);
    this.find.setText("Find");
    this.find.setEnabled(false);
    this.find.addActionListener(this);
    this.panel.add(this.find);
    this.cancel.setText("Cancel");
    this.cancel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        Find.this.setVisible(false);
      }
    });
    this.panel.add(this.cancel);
    pack();
    setTitle("Find...");
    setSize(300, 100);
    setLocationRelativeTo(paramViewer);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    setVisible(false);
    Clustering localClustering = Project.getInstance().getCluster();
    final JTree localJTree = this.frame.getClusterViewer().getTree();
    final String str = this.text.getText();
    final boolean bool = this.casesensitive.isSelected();
    final Object local1Integer1 = new Object()
    {
      private int value;

      public int getValue()
      {
        return this.value;
      }

      public void incremement()
      {
        this.value += 1;
      }

      public void decrement()
      {
        this.value -= 1;
      }
    };
    final Object local1Integer2 = new Object()
    {
      private int value;

      public int getValue()
      {
        return this.value;
      }

      public void incremement()
      {
        this.value += 1;
      }

      public void decrement()
      {
        this.value -= 1;
      }
    };
    final Object local1Boolean = new Object()
    {
      private boolean value;

      public boolean getValue()
      {
        return this.value;
      }

      public void setValue(boolean paramAnonymousBoolean)
      {
        this.value = paramAnonymousBoolean;
      }
    };
    localClustering.visit(new ClusterVisitor()
    {
      private boolean stop = false;
      private boolean collapsed = localJTree.isCollapsed(0);

      public void visit(ClusterSet paramAnonymousClusterSet)
      {
        Iterator localIterator;
        Clustering localClustering;
        if (!local1Boolean.getValue())
        {
          local1Integer1.incremement();
          if (localJTree.isCollapsed(local1Integer1.getValue()))
          {
            this.collapsed = true;
            local1Integer2.incremement();
          }
          analyze(paramAnonymousClusterSet);
          if ((local1Boolean.getValue()) && (localJTree.isCollapsed(local1Integer1.getValue())))
            local1Integer2.decrement();
          localIterator = paramAnonymousClusterSet.iterator();
          while (localIterator.hasNext())
          {
            localClustering = (Clustering)localIterator.next();
            if (!this.collapsed)
              localClustering.visit(this);
          }
          this.collapsed = false;
        }
        else if (!this.stop)
        {
          if (localJTree.isCollapsed(local1Integer1.getValue()))
          {
            this.collapsed = true;
            local1Integer2.incremement();
            this.stop = true;
          }
          else
          {
            localIterator = paramAnonymousClusterSet.iterator();
            while (localIterator.hasNext())
            {
              localClustering = (Clustering)localIterator.next();
              if (!this.collapsed)
                localClustering.visit(this);
            }
          }
        }
      }

      public void visit(ClusterItem paramAnonymousClusterItem)
      {
        if (!local1Boolean.getValue())
        {
          local1Integer1.incremement();
          local1Integer2.incremement();
          analyze(paramAnonymousClusterItem);
          if (local1Boolean.getValue())
            this.stop = true;
        }
        else if (!this.stop)
        {
          local1Integer2.incremement();
          this.stop = true;
        }
      }

      private void analyze(Clustering paramAnonymousClustering)
      {
        if (local1Integer1.getValue() > 0)
          if (bool)
            local1Boolean.setValue(paramAnonymousClustering.getName().contains(str));
          else
            local1Boolean.setValue(paramAnonymousClustering.getName().toLowerCase().contains(str.toLowerCase()));
      }
    });
    if (!local1Boolean.getValue())
    {
      JOptionPane.showMessageDialog(this.frame, "\"" + str + "\" not found");
      return;
    }
    this.frame.getClusterViewer().getTree().scrollRowToVisible(local1Integer1.getValue());
    this.frame.getClusterViewer().getTree().setSelectionRow(local1Integer1.getValue());
    int i = this.frame.getMatrixViewer().getRows().getSize();
    int j = this.frame.getMatrixViewer().getVerticalScrollBar().getMinimum();
    int k = this.frame.getMatrixViewer().getVerticalScrollBar().getMaximum();
    this.frame.getMatrixViewer().getVerticalScrollBar().setValue((int)(1.0D * local1Integer2.getValue() / i * (k - j) + j));
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.ui.Find
 * JD-Core Version:    0.6.2
 */