package edu.drexel.cs.rise.titan;

import edu.drexel.cs.rise.titan.action.AboutAction;
import edu.drexel.cs.rise.titan.action.DependencyStrengthAction;
import edu.drexel.cs.rise.titan.action.ExportExcelAction;
import edu.drexel.cs.rise.titan.action.ExportMatrixAction;
import edu.drexel.cs.rise.titan.action.FindAction;
import edu.drexel.cs.rise.titan.action.OpenMatrixAction;
import edu.drexel.cs.rise.titan.action.PropagationCostAction;
import edu.drexel.cs.rise.titan.action.RedrawAction;
import edu.drexel.cs.rise.titan.action.RowLabelAction;
import edu.drexel.cs.rise.titan.action.cluster.LoadAction;
import edu.drexel.cs.rise.titan.action.cluster.NewAction;
import edu.drexel.cs.rise.titan.action.cluster.SaveAction;
import edu.drexel.cs.rise.titan.action.cluster.SaveAsAction;
import edu.drexel.cs.rise.titan.ui.ClusterViewer;
import edu.drexel.cs.rise.titan.ui.MatrixViewer;
import edu.drexel.cs.rise.titan.util.ActionUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;

public class Viewer extends JFrame
{
  private static final long serialVersionUID = 10L;
  public static final String version = "1.0";
  private final Map<String, Action> actions = new HashMap();
  protected ClusterViewer cluster;
  protected MatrixViewer matrix;

  public Viewer()
  {
    super("Titan");
    initialize();
  }

  public ClusterViewer getClusterViewer()
  {
    return this.cluster;
  }

  public MatrixViewer getMatrixViewer()
  {
    return this.matrix;
  }

  private void initialize()
  {
    setDefaultCloseOperation(0);
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent paramAnonymousWindowEvent)
      {
        Viewer.this.queryClose();
      }
    });
    setLayout(new BorderLayout());
    this.cluster = createClusterViewer();
    this.matrix = createMatrixViewer();
    buildActions();
    setJMenuBar(buildMenus());
    JSplitPane localJSplitPane = new JSplitPane(1, this.cluster, this.matrix);
    add(localJSplitPane, "Center");
    localJSplitPane.setOneTouchExpandable(true);
    add(buildToolbar(), "First");
    Dimension localDimension = Toolkit.getDefaultToolkit().getScreenSize();
    int i = 3 * Math.min(localDimension.width, localDimension.height) / 4;
    this.matrix.setPreferredSize(new Dimension(i, i));
    this.cluster.setPreferredSize(new Dimension(i / 3, i));
    pack();
    Project localProject = Project.getInstance();
    localProject.addProjectListener(new ProjectListener()
    {
      public void clusterChanged(Project paramAnonymousProject)
      {
        Viewer.this.enableClusterButtons(paramAnonymousProject);
        Viewer.this.updateTitle(paramAnonymousProject);
      }

      public void dependencyChanged(Project paramAnonymousProject)
      {
        Viewer.this.enableMatrixButtons(paramAnonymousProject);
      }

      public void modified(Project paramAnonymousProject)
      {
        Viewer.this.updateTitle(paramAnonymousProject);
      }
    });
    enableClusterButtons(localProject);
    enableMatrixButtons(localProject);
  }

  private void buildActions()
  {
    this.actions.put("open-dsm", new OpenMatrixAction(this));
    this.actions.put("redraw", new RedrawAction(this.cluster, this.matrix));
    this.actions.put("new-clsx", new NewAction());
    this.actions.put("load-clsx", new LoadAction(this));
    this.actions.put("save-clsx", new SaveAction(this));
    this.actions.put("save-clsx-as", new SaveAsAction(this));
    this.actions.put("export-excel", new ExportExcelAction(this));
    this.actions.put("export-dsm", new ExportMatrixAction(this, this.matrix));
    this.actions.put("propagation-cost", new PropagationCostAction(this));
    this.actions.put("dep-mark", new DependencyStrengthAction());
    this.actions.put("row-label", new RowLabelAction());
    this.actions.put("about", new AboutAction(this));
    this.actions.put("find", new FindAction(this));
    ((Action)this.actions.get("dep-mark")).putValue("SwingSelectedKey", Boolean.FALSE);
    ((Action)this.actions.get("row-label")).putValue("SwingSelectedKey", Boolean.TRUE);
  }

  private JMenuBar buildMenus()
  {
    JMenuBar localJMenuBar = new JMenuBar();
    localJMenuBar.add(buildFileMenu());
    localJMenuBar.add(buildMetricsMenu());
    localJMenuBar.add(buildViewMenu());
    localJMenuBar.add(buildHelpMenu());
    return localJMenuBar;
  }

  private JMenu buildFileMenu()
  {
    JMenu localJMenu = new JMenu("File");
    localJMenu.setMnemonic('F');
    localJMenu.add(new JMenuItem((Action)this.actions.get("open-dsm")));
    localJMenu.addSeparator();
    localJMenu.add(new JMenuItem((Action)this.actions.get("new-clsx")));
    localJMenu.add(new JMenuItem((Action)this.actions.get("load-clsx")));
    localJMenu.addSeparator();
    localJMenu.add(new JMenuItem((Action)this.actions.get("save-clsx")));
    localJMenu.add(new JMenuItem((Action)this.actions.get("save-clsx-as")));
    localJMenu.addSeparator();
    localJMenu.add(buildExportMenu());
    localJMenu.addSeparator();
    JMenuItem localJMenuItem = new JMenuItem("Exit");
    localJMenuItem.setMnemonic('X');
    localJMenuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        Viewer.this.queryClose();
      }
    });
    localJMenu.add(localJMenuItem);
    return localJMenu;
  }

  private JMenu buildExportMenu()
  {
    JMenu localJMenu = new JMenu("Export As");
    localJMenu.setMnemonic('E');
    localJMenu.add(new JMenuItem((Action)this.actions.get("export-dsm")));
    localJMenu.add(new JMenuItem((Action)this.actions.get("export-excel")));
    return localJMenu;
  }

  private JMenu buildMetricsMenu()
  {
    JMenu localJMenu = new JMenu("Metrics");
    localJMenu.setMnemonic('M');
    localJMenu.add(new JMenuItem((Action)this.actions.get("propagation-cost")));
    return localJMenu;
  }

  private JMenu buildViewMenu()
  {
    JMenu localJMenu = new JMenu("View");
    localJMenu.setMnemonic('V');
    localJMenu.add(new JMenuItem((Action)this.actions.get("redraw")));
    localJMenu.addSeparator();
    localJMenu.add(new JMenuItem((Action)this.actions.get("find")));
    localJMenu.addSeparator();
    localJMenu.add(new JCheckBoxMenuItem((Action)this.actions.get("row-label")));
    localJMenu.add(new JCheckBoxMenuItem((Action)this.actions.get("dep-mark")));
    return localJMenu;
  }

  private JMenu buildHelpMenu()
  {
    JMenu localJMenu = new JMenu("Help");
    localJMenu.setMnemonic('H');
    localJMenu.add(new JMenuItem((Action)this.actions.get("about")));
    return localJMenu;
  }

  private JComponent buildToolbar()
  {
    JToolBar localJToolBar = new JToolBar();
    JButton localJButton = new JButton((Action)this.actions.get("open-dsm"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJButton = new JButton((Action)this.actions.get("redraw"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJToolBar.addSeparator();
    localJButton = new JButton((Action)this.actions.get("new-clsx"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJButton = new JButton((Action)this.actions.get("load-clsx"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJButton = new JButton((Action)this.actions.get("save-clsx"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    localJButton = new JButton((Action)this.actions.get("save-clsx-as"));
    localJButton.setText("");
    localJToolBar.add(localJButton);
    return localJToolBar;
  }

  protected ClusterViewer createClusterViewer()
  {
    return new ClusterViewer();
  }

  protected MatrixViewer createMatrixViewer()
  {
    return new MatrixViewer();
  }

  protected void enableClusterButtons(Project paramProject)
  {
    boolean bool = paramProject.getCluster() != null;
    ((Action)this.actions.get("redraw")).setEnabled(bool);
    ((Action)this.actions.get("new-clsx")).setEnabled(bool);
    ((Action)this.actions.get("load-clsx")).setEnabled(bool);
    ((Action)this.actions.get("save-clsx")).setEnabled(bool);
    ((Action)this.actions.get("save-clsx-as")).setEnabled(bool);
  }

  protected void enableMatrixButtons(Project paramProject)
  {
    boolean bool = paramProject.getDependency() != null;
    ((Action)this.actions.get("export-dsm")).setEnabled(bool);
    ((Action)this.actions.get("export-excel")).setEnabled(bool);
    ((Action)this.actions.get("find")).setEnabled(bool);
    ((Action)this.actions.get("propagation-cost")).setEnabled(bool);
  }

  protected void updateTitle(Project paramProject)
  {
    StringBuilder localStringBuilder = new StringBuilder("Titan - ");
    if (paramProject.getClusterPath() != null)
      localStringBuilder.append(paramProject.getClusterPath().getName());
    else
      localStringBuilder.append("untitled");
    if (paramProject.isModified())
      localStringBuilder.append("*");
    setTitle(localStringBuilder.toString());
  }

  protected void queryClose()
  {
    if (ActionUtilities.querySave(Project.getInstance()))
    {
      setVisible(false);
      dispose();
    }
  }

  public void open(File paramFile)
  {
    ((OpenMatrixAction)this.actions.get("open-dsm")).load(paramFile);
  }

  public void loadCluster(File paramFile)
  {
    ((LoadAction)this.actions.get("load-clsx")).load(paramFile);
  }

  private static void setLookAndFeel()
  {
    try
    {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    }
    catch (Exception localException)
    {
      setAuxilaryLookAndFeel();
    }
    finally
    {
      JFrame.setDefaultLookAndFeelDecorated(true);
    }
  }

  private static void setAuxilaryLookAndFeel()
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception localException)
    {
    }
  }

  public static void main(String[] paramArrayOfString)
  {
    setLookAndFeel();
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        Viewer localViewer = new Viewer();
        localViewer.setLocationRelativeTo(null);
        if (this.val$args.length > 0)
          localViewer.open(new File(this.val$args[0]));
        if (this.val$args.length > 1)
          localViewer.loadCluster(new File(this.val$args[1]));
        localViewer.setVisible(true);
      }
    });
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.Viewer
 * JD-Core Version:    0.6.2
 */