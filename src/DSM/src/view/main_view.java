package view;

import java.awt.EventQueue;
import java.awt.FileDialog;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Color;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class main_view {

	private JFrame frmTitan;
	
	private Icon OpenDSMICon = new ImageIcon("resource\\open-dsm.png");
	private Icon RedrawIcon  = new ImageIcon("resource\\redraw.png");
	private Icon NewClusteringIcon = new ImageIcon("resource\\new-clsx.png");
	private Icon LoadClusteringIcon = new ImageIcon("resource\\open-clsx.png");
	private Icon SaveClusteringIcon = new ImageIcon("resource\\save-clsx.png");
	private Icon SaveClusteringAsIcon = new ImageIcon("resource\\save-clsx-as.png");
	private Icon ExpandAllIcon = new ImageIcon("resource\\expand.png");
	private Icon CollapsAllIcon = new ImageIcon("resource\\collapse.png");
	private Icon GroupIcon = new ImageIcon("resource\\group.png");
	private Icon UnGroupIcon = new ImageIcon("resource\\ungroup.png");
	private Icon MoveUpIcon = new ImageIcon("resource\\up.png");
	private Icon MoveDownIcon = new ImageIcon("resource\\down.png");
	private Icon DeleteIcon = new ImageIcon("resource\\delete.png");
	
	private JTable table;
		
	private JMenuItem mntmOpenDsm;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmLoadClustering;
	private JMenuItem mntmSaveClustering;
	private JMenuItem mntmSaveClusteringAs;
	private JMenuItem mntmDsm;
	private JMenuItem mntmExcel;
	private JMenuItem mntmExit;
	private JMenuItem mntmPropagationCost;
	private JMenuItem mntmRedraw;
	private JMenuItem mntmFind;
	private JMenuItem mntmShow_Row_Labels;
	private JMenuItem mntmShow_Dependency_Strength;
	private JMenuItem mntmAbout;
	private JButton btnOpenDsm;
	private JButton btnRedraw;
	private JButton btnNewClustering;
	private JButton btnLoadClustering;
	private JButton btnSaveClustering;
	private JButton btnSaveClusteringAs;
	private JTree classtree;
	private JButton btnExpandAll;
	private JButton btnCollapsAll;
	private JButton btnGroup;
	private JButton btnUngroup;
	private JButton btnMoveUp;
	private JButton btnMoveDown;
	private JButton btnDelete;


	
	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main_view window = new main_view();
					window.frmTitan.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main_view() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTitan = new JFrame();
		frmTitan.setTitle("Titan");
		frmTitan.setBounds(100, 100, 800, 500);
		frmTitan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initialize_Menu();		
		initialize_HorizontalSplitPane();
		initialize_TopToolBar();
	}

	private void initialize_Menu()
	{
		JMenuBar menuBar = new JMenuBar();
		frmTitan.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmOpenDsm = new JMenuItem("Open DSM...");
				
		mntmOpenDsm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fldg = new FileDialog(frmTitan,"file open",FileDialog.LOAD);
				fldg.setVisible(true);		
			}
		});
		mntmOpenDsm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpenDsm);
		
		mntmNewMenuItem = new JMenuItem("New Clustering");
		mnFile.add(mntmNewMenuItem);
		
		mntmLoadClustering = new JMenuItem("Load Clustering");
		mntmLoadClustering.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmLoadClustering);
		
		mntmSaveClustering = new JMenuItem("Save Clustering");
		mntmSaveClustering.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmSaveClustering);
		
		mntmSaveClusteringAs = new JMenuItem("Save Clustering as...");
		mnFile.add(mntmSaveClusteringAs);
		
		JMenu mnExportAs = new JMenu("Export As");
		mnFile.add(mnExportAs);
		
		mntmDsm = new JMenuItem("DSM");
		mnExportAs.add(mntmDsm);
		
		mntmExcel = new JMenuItem("Excel");
		mnExportAs.add(mntmExcel);
		
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnMetrics = new JMenu("Metrics");
		menuBar.add(mnMetrics);
		
		mntmPropagationCost = new JMenuItem("Propagation Cost");
		mnMetrics.add(mntmPropagationCost);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		mntmRedraw = new JMenuItem("Redraw");
		mntmRedraw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		mnView.add(mntmRedraw);
		
		mntmFind = new JMenuItem("Find");
		mntmFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		mnView.add(mntmFind);
		
		mntmShow_Row_Labels = new JMenuItem("Show Row Labels");
		mnView.add(mntmShow_Row_Labels);
		
		mntmShow_Dependency_Strength = new JMenuItem("Show Dependency Strength");
		mnView.add(mntmShow_Dependency_Strength);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About...");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about about_frme = new about();
				about_frme.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);
		frmTitan.getContentPane().setLayout(null);
	}	
	private void initialize_TopToolBar(){
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(Color.WHITE);
		toolBar.setBounds(0, -3, 784, 26);
		frmTitan.getContentPane().add(toolBar);
		

		
		btnOpenDsm = new JButton("");
		btnOpenDsm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fldg = new FileDialog(frmTitan,"file open",FileDialog.LOAD);
				fldg.setVisible(true);		
			}
		});
		btnOpenDsm.setToolTipText("Open DSM");
		btnOpenDsm.setIcon(OpenDSMICon);
		toolBar.add(btnOpenDsm);
		
				
		btnRedraw = new JButton("");
		btnRedraw.setToolTipText("Redraw");
		btnRedraw.setIcon(RedrawIcon);
		toolBar.add(btnRedraw);
		
		btnNewClustering = new JButton("");
		btnNewClustering.setToolTipText("New Clustering");
		btnNewClustering.setIcon(NewClusteringIcon);
		toolBar.add(btnNewClustering);
		
		btnLoadClustering = new JButton("");
		btnLoadClustering.setToolTipText("Load Clustering");
		btnLoadClustering.setIcon(LoadClusteringIcon);
		toolBar.add(btnLoadClustering);
		
		btnSaveClustering = new JButton("");
		btnSaveClustering.setToolTipText("Save Clustering");
		btnSaveClustering.setIcon(SaveClusteringIcon);
		toolBar.add(btnSaveClustering);
		
		btnSaveClusteringAs = new JButton("");
		btnSaveClusteringAs.setToolTipText("Save Clustering As");
		btnSaveClusteringAs.setIcon(SaveClusteringAsIcon);
		toolBar.add(btnSaveClusteringAs);
	}
	private void initialize_HorizontalSplitPane(){
		JSplitPane splitPane_horizontal = new JSplitPane();
		splitPane_horizontal.setBackground(Color.LIGHT_GRAY);
		splitPane_horizontal.setResizeWeight(0.01);		//splitPane 좌우 비율 지정
		splitPane_horizontal.setBounds(0, 29, 784, 412);   //splitPane 크기 지정
		splitPane_horizontal.setDividerSize(12);			//splitPane 경계선 크기 지정
		splitPane_horizontal.setContinuousLayout(true);	//경계 이동시 그림자 이동 해제
		splitPane_horizontal.setOneTouchExpandable(true);	//벽에 붙이기 기능 활성화
		frmTitan.getContentPane().add(splitPane_horizontal);
		
		table = new JTable();
		splitPane_horizontal.setRightComponent(table);
		
		JSplitPane splitPane_vertical = new JSplitPane();
		splitPane_vertical.setEnabled(false);
		splitPane_vertical.setContinuousLayout(true);
		splitPane_vertical.setResizeWeight(0.01);
		splitPane_vertical.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_horizontal.setLeftComponent(splitPane_vertical);
		
		classtree = new JTree();
		classtree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("$root") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					add(new DefaultMutableTreeNode("A"));
					add(new DefaultMutableTreeNode("B"));
					add(new DefaultMutableTreeNode("C"));
					add(new DefaultMutableTreeNode("D"));
					add(new DefaultMutableTreeNode("E"));
					add(new DefaultMutableTreeNode("F"));
					add(new DefaultMutableTreeNode("G"));
				}
			}
		));
		splitPane_vertical.setRightComponent(classtree);
		
		JToolBar treeToolBar = new JToolBar();
		treeToolBar.setEnabled(false);
		treeToolBar.setFloatable(false);
		splitPane_vertical.setLeftComponent(treeToolBar);
		
		btnExpandAll = new JButton("");
		btnExpandAll.setToolTipText("Expand All");
		btnExpandAll.setIcon(ExpandAllIcon);
		treeToolBar.add(btnExpandAll);
		
		btnCollapsAll = new JButton("");
		btnCollapsAll.setToolTipText("Collaps All");
		btnCollapsAll.setIcon(CollapsAllIcon);
		treeToolBar.add(btnCollapsAll);
		
		btnGroup = new JButton("");
		btnGroup.setToolTipText("Group");
		btnGroup.setIcon(GroupIcon);
		treeToolBar.add(btnGroup);
		
		btnUngroup = new JButton("");
		btnUngroup.setToolTipText("Ungroup");
		btnUngroup.setIcon(UnGroupIcon);
		treeToolBar.add(btnUngroup);
		
		btnMoveUp = new JButton("");
		btnMoveUp.setToolTipText("Move Up");
		btnMoveUp.setIcon(MoveUpIcon);
		treeToolBar.add(btnMoveUp);
		
		btnMoveDown = new JButton("");
		btnMoveDown.setToolTipText("Move Down");
		btnMoveDown.setIcon(MoveDownIcon);
		treeToolBar.add(btnMoveDown);
		
		btnDelete = new JButton("");
		btnDelete.setToolTipText("Delete");
		btnDelete.setIcon(DeleteIcon);
		treeToolBar.add(btnDelete);	
	}

	public JTable getTable() {
		return table;
	}

	public JMenuItem getMntmOpenDsm() {
		return mntmOpenDsm;
	}

	public JMenuItem getMntmNewMenuItem() {
		return mntmNewMenuItem;
	}

	public JMenuItem getMntmLoadClustering() {
		return mntmLoadClustering;
	}

	public JMenuItem getMntmSaveClustering() {
		return mntmSaveClustering;
	}

	public JMenuItem getMntmSaveClusteringAs() {
		return mntmSaveClusteringAs;
	}

	public JMenuItem getMntmDsm() {
		return mntmDsm;
	}

	public JMenuItem getMntmExcel() {
		return mntmExcel;
	}

	public JMenuItem getMntmExit() {
		return mntmExit;
	}

	public JMenuItem getMntmPropagationCost() {
		return mntmPropagationCost;
	}

	public JMenuItem getMntmRedraw() {
		return mntmRedraw;
	}

	public JMenuItem getMntmFind() {
		return mntmFind;
	}

	public JMenuItem getMntmShow_Row_Labels() {
		return mntmShow_Row_Labels;
	}

	public JMenuItem getMntmShow_Dependency_Strength() {
		return mntmShow_Dependency_Strength;
	}

	public JMenuItem getMntmAbout() {
		return mntmAbout;
	}

	public JButton getBtnOpenDsm() {
		return btnOpenDsm;
	}

	public JButton getBtnRedraw() {
		return btnRedraw;
	}

	public JButton getBtnNewClustering() {
		return btnNewClustering;
	}

	public JButton getBtnLoadClustering() {
		return btnLoadClustering;
	}

	public JButton getBtnSaveClustering() {
		return btnSaveClustering;
	}

	public JButton getBtnSaveClusteringAs() {
		return btnSaveClusteringAs;
	}

	public JTree getClasstree() {
		return classtree;
	}

	public JButton getBtnExpandAll() {
		return btnExpandAll;
	}

	public JButton getBtnCollapsAll() {
		return btnCollapsAll;
	}

	public JButton getBtnGroup() {
		return btnGroup;
	}

	public JButton getBtnUngroup() {
		return btnUngroup;
	}

	public JButton getBtnMoveUp() {
		return btnMoveUp;
	}

	public JButton getBtnMoveDown() {
		return btnMoveDown;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}
}
