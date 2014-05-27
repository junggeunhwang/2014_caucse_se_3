package view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import javax.swing.UIManager;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

import javax.swing.JSeparator;

import java.awt.Button;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JScrollPane;

import model.ModelInfo;


public class Main_view {

	static private Main_view instance;
	
	private JFrame frmTitan;
	
	/* Icon */
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
	private String filePath;
	private int showRowLabel; // if 0, do not show rowLabel / else if 1, show rowLabel
		
	/* Menu Item */
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
	
	/* Button */
	private JButton btnOpenDsm;
	private JButton btnRedraw;
	private JButton btnNewClustering;
	private JButton btnLoadClustering;
	private JButton btnSaveClustering;
	private JButton btnSaveClusteringAs;
	private JButton btnExpandAll;
	private JButton btnCollapsAll;
	private JButton btnGroup;
	private JButton btnUngroup;
	private JButton btnMoveUp;
	private JButton btnMoveDown;
	private JButton btnDelete;
	
	private JTree classtree;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JSeparator separator_5;

	private JPanel matrixTool;
	private JScrollPane matrix;
	private JOptionPane inputDlg;
	private JScrollPane scrollPane;
	private	JSplitPane splitPane_horizontal;
	private	JSplitPane splitPane_vertical;

	/**
	 * Create the application.
	 */
	private Main_view() {
		initialize();
		frmTitan.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmTitan = new JFrame();
		frmTitan.getContentPane().setBackground(SystemColor.menu);
		frmTitan.setTitle("Titan");
		frmTitan.setBounds(100, 100, 800, 500);
		frmTitan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showRowLabel=1;
		
		initialize_Menu();		
		initialize_HorizontalSplitPane();
		initialize_TopToolBar();
		
	}

 	private void initialize_Menu()
	{
		JMenuBar menuBar = new JMenuBar();
		frmTitan.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		mnFile.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		mntmOpenDsm = new JMenuItem("Open DSM...");
		mntmOpenDsm.setMnemonic('O');
		mntmOpenDsm.setFont(new Font("���� ����", Font.PLAIN, 12));
				
		mntmOpenDsm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fldg = new FileDialog(frmTitan,"file open",FileDialog.LOAD);
				fldg.setVisible(true);		
			}
		});
		mntmOpenDsm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpenDsm);
		
		separator = new JSeparator();
		mnFile.add(separator);
		
		mntmNewMenuItem = new JMenuItem("New Clustering");
		mntmNewMenuItem.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmNewMenuItem.setMnemonic('N');
		mnFile.add(mntmNewMenuItem);
		
		mntmLoadClustering = new JMenuItem("Load Clustering");
		mntmLoadClustering.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmLoadClustering.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmLoadClustering.setMnemonic('L');
		mnFile.add(mntmLoadClustering);
		
		separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		mntmSaveClustering = new JMenuItem("Save Clustering");
		mntmSaveClustering.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmSaveClustering.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmSaveClustering.setMnemonic('S');
		mnFile.add(mntmSaveClustering);
		
		mntmSaveClusteringAs = new JMenuItem("Save Clustering as...");
		mntmSaveClusteringAs.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmSaveClusteringAs.setMnemonic('a');
		mnFile.add(mntmSaveClusteringAs);
		
		separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		JMenu mnExportAs = new JMenu("Export As");
		mnExportAs.setFont(new Font("���� ����", Font.PLAIN, 12));
		mnExportAs.setMnemonic('E');
		mnFile.add(mnExportAs);
		
		mntmDsm = new JMenuItem("DSM");
		mnExportAs.add(mntmDsm);
		
		mntmExcel = new JMenuItem("Excel");
		mnExportAs.add(mntmExcel);
		
		separator_3 = new JSeparator();
		mnFile.add(separator_3);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmExit.setMnemonic('x');
		mnFile.add(mntmExit);
		
		JMenu mnMetrics = new JMenu("Metrics");
		mnMetrics.setMnemonic('M');
		mnMetrics.setFont(new Font("���� ����", Font.PLAIN, 12));
		menuBar.add(mnMetrics);
		
		mntmPropagationCost = new JMenuItem("Propagation Cost");
		mntmPropagationCost.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmPropagationCost.setMnemonic('P');
		mnMetrics.add(mntmPropagationCost);
		
		JMenu mnView = new JMenu("View");
		mnView.setMnemonic('V');
		mnView.setFont(new Font("���� ����", Font.PLAIN, 12));
		menuBar.add(mnView);
		
		mntmRedraw = new JMenuItem("Redraw");
		mntmRedraw.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmRedraw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		mntmRedraw.setMnemonic('R');
		mnView.add(mntmRedraw);
		
		separator_4 = new JSeparator();
		mnView.add(separator_4);
		
		mntmFind = new JMenuItem("Find");
		mntmFind.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		mntmFind.setMnemonic('F');
		mnView.add(mntmFind);
		
		separator_5 = new JSeparator();
		mnView.add(separator_5);
		
		mntmShow_Row_Labels = new JMenuItem("Show Row Labels");
		mntmShow_Row_Labels.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmShow_Row_Labels.setMnemonic('L');
		mnView.add(mntmShow_Row_Labels);
		
		mntmShow_Dependency_Strength = new JMenuItem("Show Dependency Strength");
		mntmShow_Dependency_Strength.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmShow_Dependency_Strength.setMnemonic('D');
		mnView.add(mntmShow_Dependency_Strength);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic('H');
		mnHelp.setFont(new Font("���� ����", Font.PLAIN, 12));
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About...");
		mntmAbout.setMnemonic('A');
		mntmAbout.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About about_frme = new About();
				about_frme.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);
		frmTitan.getContentPane().setLayout(null);
	}	
	
	private void initialize_TopToolBar(){
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.menu);
		toolBar.setBounds(0, 0, 784, 30);
		frmTitan.getContentPane().add(toolBar);
				
		btnOpenDsm = new JButton("");
		btnOpenDsm.setBackground(SystemColor.menu);
		btnOpenDsm.setToolTipText("Open DSM");
		btnOpenDsm.setIcon(OpenDSMICon);
		toolBar.add(btnOpenDsm);
		
				
		btnRedraw = new JButton("");
		btnRedraw.setBackground(SystemColor.menu);
		btnRedraw.setToolTipText("Redraw");
		btnRedraw.setIcon(RedrawIcon);
		toolBar.add(btnRedraw);
		
		btnNewClustering = new JButton("");
		btnNewClustering.setBackground(SystemColor.menu);
		btnNewClustering.setToolTipText("New Clustering");
		btnNewClustering.setIcon(NewClusteringIcon);
		toolBar.add(btnNewClustering);
		
		btnLoadClustering = new JButton("");
		btnLoadClustering.setBackground(SystemColor.menu);
		btnLoadClustering.setToolTipText("Load Clustering");
		btnLoadClustering.setIcon(LoadClusteringIcon);
		toolBar.add(btnLoadClustering);
		
		btnSaveClustering = new JButton("");
		btnSaveClustering.setBackground(SystemColor.menu);
		btnSaveClustering.setToolTipText("Save Clustering");
		btnSaveClustering.setIcon(SaveClusteringIcon);
		toolBar.add(btnSaveClustering);
		
		btnSaveClusteringAs = new JButton("");
		btnSaveClusteringAs.setBackground(SystemColor.menu);
		btnSaveClusteringAs.setToolTipText("Save Clustering As");
		btnSaveClusteringAs.setIcon(SaveClusteringAsIcon);
		toolBar.add(btnSaveClusteringAs);
	}
	
	private void initialize_HorizontalSplitPane(){
		splitPane_horizontal = new JSplitPane();
		splitPane_horizontal.setBackground(SystemColor.menu);
		splitPane_horizontal.setResizeWeight(0.01);		//splitPane �¿� ���� ����
		splitPane_horizontal.setBounds(0, 30, 784, 412);   //splitPane ũ�� ����
		splitPane_horizontal.setDividerSize(12);			//splitPane ��輱 ũ�� ����
		splitPane_horizontal.setContinuousLayout(true);	//��� �̵��� �׸��� �̵� ����
		splitPane_horizontal.setOneTouchExpandable(true);	//���� ���̱� ��� Ȱ��ȭ
		frmTitan.getContentPane().add(splitPane_horizontal);
		
		table = new JTable();
		table.setBackground(SystemColor.menu);
		matrix = new JScrollPane(table);
		matrixTool = new JPanel();
		matrixTool.add(matrix);
		splitPane_horizontal.setRightComponent(matrixTool);
		
		splitPane_vertical = new JSplitPane();
		splitPane_vertical.setBackground(SystemColor.menu);
		splitPane_vertical.setEnabled(false);
		splitPane_vertical.setContinuousLayout(true);
		splitPane_vertical.setResizeWeight(0.01);
		splitPane_vertical.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_horizontal.setLeftComponent(splitPane_vertical);
		
		classtree = new JTree();
		classtree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("$root") {}
		));
		scrollPane = new JScrollPane(classtree);
		splitPane_vertical.setRightComponent(scrollPane);
		
		JToolBar treeToolBar = new JToolBar();
		treeToolBar.setBackground(SystemColor.menu);
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

	
	public static Main_view getInstance() {
		if(instance==null){
			instance = new Main_view();			
		}
		return instance;
	}
	
	public String setGroupName()
	{
		inputDlg = new JOptionPane();
		String groupName = new String();
		groupName = inputDlg.showInputDialog("Group Name");
		return groupName;
	}
	
	public void drawTable(Vector<String> printElement)
	{
		int matrixSize = printElement.size();
		Object data[][] = new Object[matrixSize][matrixSize+1];
		for(int i=0; i<matrixSize; i++)
		{
				for(int j=1; j<=matrixSize; j++)
				{
					if(i==j-1)
					{
						if(ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).childs.size()!=0)
							data[i][j] = "group";
						else data[i][j] = "��";
					}
					else
					{
						int dp=0;
						boolean from = ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).isGroup(); //group�̸� true
						boolean to = ModelInfo.getInstance().getRoot().getNode(printElement.get(j-1)).isGroup();
						
						if(from==true && to==false) //group To element
						{
							int indexFrom=0;
							int indexTo=0;
							for(int k=0; k< ModelInfo.getInstance().getModules().size(); k++)
								if(ModelInfo.getInstance().getRoot().getNode(printElement.get(j-1)).key.compareTo(ModelInfo.getInstance().getModules().get(k).key)==0)
									indexTo = k;
							for(int l=0; l<ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).childs.size(); l++)
							{
								for(int k=0; k< ModelInfo.getInstance().getModules().size(); k++)
									if(ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).childs.get(l).key.compareTo(ModelInfo.getInstance().getModules().get(k).key)==0)
										indexFrom = k;
								if(ModelInfo.getInstance().getDependData().get(indexFrom)[indexTo]==1)
									dp = 1;
							}
							
						}
						else if(from==false && to==true)  //element To group
						{
							int indexFrom=0;
							int indexTo=0;
							for(int k=0; k< ModelInfo.getInstance().getModules().size(); k++)
								if(ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).key.compareTo(ModelInfo.getInstance().getModules().get(k).key)==0)
									indexFrom = k;
							for(int l=0; l<ModelInfo.getInstance().getRoot().getNode(printElement.get(j-1)).childs.size(); l++)
							{
								for(int k=0; k< ModelInfo.getInstance().getModules().size(); k++)
									if(ModelInfo.getInstance().getRoot().getNode(printElement.get(j-1)).childs.get(l).key.compareTo(ModelInfo.getInstance().getModules().get(k).key)==0)
										indexTo = k; //index = child�� ���� ��ǥ
								if(ModelInfo.getInstance().getDependData().get(indexFrom)[indexTo]==1)
									dp = 1;
							}
						}
						else if(from==true && to==true)
						{
							int indexFrom=0;
							int indexTo=0;
							for(int l=0; l<ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).childs.size(); l++)
							{
								for(int k=0; k< ModelInfo.getInstance().getModules().size(); k++)
									if(ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).childs.get(l).key.compareTo(ModelInfo.getInstance().getModules().get(k).key)==0)
										indexFrom = k;
								for(int m=0; m<ModelInfo.getInstance().getRoot().getNode(printElement.get(j-1)).childs.size(); m++)
									for(int k=0; k< ModelInfo.getInstance().getModules().size(); k++)
										if(ModelInfo.getInstance().getRoot().getNode(printElement.get(j-1)).childs.get(m).key.compareTo(ModelInfo.getInstance().getModules().get(k).key)==0)
											indexTo = k; //index = child�� ���� ��ǥ
								if(ModelInfo.getInstance().getDependData().get(indexFrom)[indexTo]==1)
									dp = 1;
							}
						}
						else
						{
							
							int indexFrom=0;
							int indexTo=0;
							for(int k=0; k< ModelInfo.getInstance().getModules().size(); k++)
								if(ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).key.compareTo(ModelInfo.getInstance().getModules().get(k).key)==0)
									indexFrom = k;
							for(int k=0; k< ModelInfo.getInstance().getModules().size(); k++)
								if(ModelInfo.getInstance().getRoot().getNode(printElement.get(j-1)).key.compareTo(ModelInfo.getInstance().getModules().get(k).key)==0)
									indexTo = k;
							if(ModelInfo.getInstance().getDependData().get(indexFrom)[indexTo]==1)
								dp = 1;
						}
						
						if(dp==1)
							data[i][j] = 'x';
						else
							data[i][j] = ' ';
					}
				}
		}
		Object col[] = new Object[matrixSize+1];
		if(showRowLabel==1)
			for(int i=0; i<matrixSize; i++)
				data[i][0] =String.valueOf(i+1)+"  "+ printElement.get(i);
		else
			for(int i=0; i<matrixSize; i++)
				data[i][0] =String.valueOf(i+1);
		col[0] = "";
		for(int i=1; i<=matrixSize; i++)
			col[i] = i;
		
		
		DefaultTableModel model=new DefaultTableModel(data,col);
	   
		matrix.removeAll();
	    table = new JTable();
	    splitPane_horizontal.remove(matrixTool);
	    
	    JTable t = new JTable(model);
	    JScrollPane m = new JScrollPane(t);
	    JPanel mt = new JPanel();
	    mt.add(m);
		splitPane_horizontal.add(mt);
		
		table = t;
		matrix = m;
		matrixTool = mt;
	}
	
	public void setJTree(JTree tree)
	{
		scrollPane.removeAll();  // scrollPane�� �ʱ�ȭ
		classtree = new JTree(); // JTree�� �ʱ�ȭ
		this.splitPane_vertical.remove(scrollPane);
		JScrollPane newSp = new JScrollPane(tree);
		this.splitPane_vertical.add(newSp);
		scrollPane = newSp;
		classtree = tree;
	}
	
	public String setFilepath_load()
	{
		FileDialog fldg = new FileDialog(frmTitan,"file open",FileDialog.LOAD);
		fldg.setVisible(true);	
		String directoryName = new String();
		directoryName = fldg.getDirectory() + fldg.getFile();
		filePath = new String();
		filePath = directoryName;
		return filePath;
	}
	
	public String[] setFilepath_save()
	{
		FileDialog fldg = new FileDialog(frmTitan,"file open",FileDialog.SAVE);
		fldg.setVisible(true);	
		String[] fileInfo = new String[2];
		fileInfo[0] = fldg.getDirectory();
		fileInfo[1] = fldg.getFile();
		return fileInfo;
	}
	
	public void setTreeView(int how)
	{
		if(how==0)
		{	
			for(int i=0; i<classtree.getRowCount(); i++)
				classtree.expandRow(i);
		}
		else
		{	
			for(int i=0; i<classtree.getRowCount(); i++)
				classtree.collapseRow(i);
		}
	}
	
	public JFrame getfrmTitan()
	{
		return frmTitan;
	}
	
	public String getFilePath()
	{
		return filePath;
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

	public JSplitPane getSplitPane_horizontal() {
		return splitPane_horizontal;
	}

	public JSplitPane getSplitPane_vertical() {
		return splitPane_vertical;
	}
}
