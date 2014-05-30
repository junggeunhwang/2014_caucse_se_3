package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import java.awt.SystemColor;

import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JScrollPane;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JCheckBoxMenuItem;

import view.customtable.CustomJTable;
import view.customtable.CustomTableCellRenderer;
import model.ModelInfo;

public class Main_view extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JFrame frmTitan;
	private static Main_view instance;

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
	private Icon NewDsmIcon = new ImageIcon("resource\\newDsmRow.png");
	private Icon RenameIcon = new ImageIcon("resource\\rename.png");
	
	private String filePath;
	private int showRowLabel; // if 0, do not show rowLabel / else if 1, show rowLabel
		
	/* Menu Item */
	private JMenuItem mntmOpenDsm;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmLoadClustering;
	private JMenuItem mntmSaveClustering;
	private JMenuItem mntmSaveClusteringAs;
	//private JMenuItem mntmDsm;
	private JMenuItem mntmExit;
	private JMenuItem mntmRedraw;
	private JMenuItem mntmAbout;
	private JMenuItem mntmNewDsm;
	private JMenuItem mntmSaveDsm;
	private JMenuItem mntmSaveAsDsm;//
	
	private JCheckBoxMenuItem mntmShow_Row_Labels;
	
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
	private JButton btnNewDsmRow;
	private JButton btnRename;
	
	private JTree classtree;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;


	private JPanel matrixTool;
	private JScrollPane dependency_matrix_scrollPane;
	private JOptionPane inputDlg;
	private JScrollPane scrollPane;
	private	JSplitPane splitPane_horizontal;
	private	JSplitPane splitPane_vertical;
	private JSplitPane splitPane_top_main;
	private CustomJTable dependency_table;


	/**
	 * Create the frame.
	 */
	public Main_view() {
		instance = this;
		frmTitan = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 826, 600);
		initialize();
		this.setVisible(true);
	}
	
	private void initialize()
	{
		initialize_Menu();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		splitPane_top_main = new JSplitPane();
		splitPane_top_main.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane_top_main, BorderLayout.CENTER);
		
		initialize_TopToolBar();
		initialize_HorizontalSplitPane();
		showRowLabel=1;
		setEnableButton(false);
	}
	
 	private void initialize_Menu()
	{
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		mnFile.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
		menuBar.add(mnFile);
		mntmNewDsm = new JMenuItem("New DSM...");
		mntmNewDsm.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
		mntmNewDsm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNewDsm);
		
		mntmOpenDsm = new JMenuItem("Open DSM...");
		mntmOpenDsm.setMnemonic('O');
		mntmOpenDsm.setFont(new Font("���� ����", Font.PLAIN, 12));
				
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
		mntmSaveDsm = new JMenuItem("Save DSM");
		mntmSaveDsm.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
		mnFile.add(mntmSaveDsm);
		mntmSaveAsDsm = new JMenuItem("Save DSM as..");
		mntmSaveAsDsm.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
		mnFile.add(mntmSaveAsDsm);
//		mntmExcel = new JMenuItem("Excel");
//		mnExportAs.add(mntmExcel);
		
		separator_3 = new JSeparator();
		mnFile.add(separator_3);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmExit.setMnemonic('x');
		mnFile.add(mntmExit);
		
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
		
		mntmShow_Row_Labels = new JCheckBoxMenuItem("Show Row Labels");
		
		mntmShow_Row_Labels.setFont(new Font("���� ����", Font.PLAIN, 12));
		mntmShow_Row_Labels.setMnemonic('L');
		mntmShow_Row_Labels.setSelected(true);
		mnView.add(mntmShow_Row_Labels);
		
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
		this.getContentPane().setLayout(null);
	}	

 	private void initialize_TopToolBar(){
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.menu);
		splitPane_top_main.setLeftComponent(toolBar);
				
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
		splitPane_horizontal.setForeground(Color.WHITE);
		splitPane_horizontal.setBackground(SystemColor.menu);
		splitPane_horizontal.setResizeWeight(0.01);		//splitPane �¿� ���� ����
		splitPane_horizontal.setBounds(0, 30, 784, 412);   //splitPane ũ�� ����
		splitPane_horizontal.setDividerSize(12);			//splitPane ��輱 ũ�� ����
		splitPane_horizontal.setContinuousLayout(true);	//��� �̵��� �׸��� �̵� ����
		splitPane_horizontal.setOneTouchExpandable(true);	//���� ���̱� ��� Ȱ��ȭ
		splitPane_top_main.setRightComponent(splitPane_horizontal);
		
		
		
		dependency_table = new CustomJTable();
		dependency_table.setRowSelectionAllowed(false);
		dependency_table.setEnabled(false);
		dependency_table.setBackground(SystemColor.white);
		
		
		dependency_matrix_scrollPane = new JScrollPane(dependency_table);
		dependency_matrix_scrollPane.setEnabled(false);
		dependency_matrix_scrollPane.setViewportView(dependency_table);
		
		matrixTool = new JPanel();
		matrixTool.setForeground(Color.WHITE);
		matrixTool.setLayout(new BorderLayout(0, 0));
		matrixTool.add(dependency_matrix_scrollPane);
				
		splitPane_horizontal.setRightComponent(matrixTool);
		
		splitPane_vertical = new JSplitPane();
		splitPane_vertical.setForeground(Color.WHITE);
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
		
		btnNewDsmRow = new JButton("");
		btnNewDsmRow.setToolTipText("NewDsmRow");
		btnNewDsmRow.setIcon(NewDsmIcon);
		treeToolBar.add(btnNewDsmRow);
		
		btnRename = new JButton("");
		btnRename.setToolTipText("Rename");
		btnRename.setIcon(RenameIcon);
		treeToolBar.add(btnRename);
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
	
	public String setNewName()
	{
		inputDlg = new JOptionPane();
		String groupName = new String();
		groupName = inputDlg.showInputDialog("New Module Name");
		return groupName;
	}

	public int[][] setNewModule()
	{
		int[][] newDepend = new int[2][ModelInfo.getInstance().getModules().size()];
		
		return newDepend;
	}
	
	public void drawTable(Vector<String> printElement)
	{
		int matrixSize = printElement.size();
		Object data[][];
		Object col[];
		Vector<int[]> colored;
		if(matrixSize==0)
		{
			colored = new Vector<int[]>();
			colored = null;
			data = new Object[1][2];
			col = new Object[2];
			if(showRowLabel==1)
				data[0][0] = String.valueOf(1) + " $root";
			else
				data[0][0] = String.valueOf(1);
			data[0][1] = "��";
			col[0] = "";
			col[1] = String.valueOf(1);
		}
		else
		{
			colored = new Vector<int[]>();
			data = new Object[matrixSize][matrixSize+1];
			for(int i=0; i<matrixSize; i++)
			{
					for(int j=1; j<=matrixSize; j++)
					{
						int[] groupPoint = new int[2];
						if(i==j-1)
						{
							if(ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).childs.size()!=0)
							{
								data[i][j] = 1;
								groupPoint[0] = i;
								groupPoint[1] = j;
								colored.add(groupPoint);
							}
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
						String parentName = new String();
						parentName = ModelInfo.getInstance().getRoot().getNode(printElement.get(i)).parent.key;
						if(parentName.compareTo("$root")!=0
								&& ModelInfo.getInstance().getRoot().getNode(printElement.get(j-1)).parent.key.compareTo(parentName)==0)
							{
								groupPoint[0] = i;
								groupPoint[1] = j;
								colored.add(groupPoint);
							}
					}
			}
		
			col = new Object[matrixSize+1];
			if(showRowLabel==1)
				for(int i=0; i<matrixSize; i++)
					data[i][0] =String.valueOf(i+1)+"  "+ printElement.get(i);
			else
				for(int i=0; i<matrixSize; i++)
					data[i][0] =String.valueOf(i+1);
			col[0] = "";
			for(int i=1; i<=matrixSize; i++)
				col[i] = i;
		}
		
		
		DefaultTableModel model=new DefaultTableModel(data,col);
		dependency_table = new CustomJTable(model);
		dependency_table.setRowSelectionAllowed(false);
		dependency_table.setEnabled(false);
		dependency_table.setBackground(SystemColor.white);
		dependency_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //�ڵ� �������� ����
		
		for(int i = 0 ; i < model.getRowCount();i++){
			for(int j = 0;j<model.getColumnCount();j++){
				if(j==0) dependency_table.setAlignment(i, j, CustomTableCellRenderer.left_alignment);
				else dependency_table.setAlignment(i, j, CustomTableCellRenderer.center_alignment);
			}
		}
		
		for(int i=0; i<colored.size(); i++)
			dependency_table.setColor(colored.get(i)[0], colored.get(i)[1], Color.green);
		
	
		//dependency_table.setColor(2, 2, Color.red);
		
		TableColumnModel col_model = dependency_table.getColumnModel();
		
		for(int i=1; i<col_model.getColumnCount(); i++)
		{
			col_model.getColumn(i).setPreferredWidth(10);
		}
		
		int max_length=0;
		if(printElement.size()==0)
			max_length=8;
		else
		{
			for(int i=0; i<matrixSize; i++)
			{
				data[i][0] = String.valueOf(i+1)+"  "+ printElement.get(i);
				if(max_length < (String.valueOf(i+1)+"  "+ printElement.get(i)).length());
					max_length = (String.valueOf(i+1)+"  "+ printElement.get(i)).length();
			}
		}	
		col_model.getColumn(0).setPreferredWidth(max_length*7+3);		
		col_model.setColumnSelectionAllowed(false);
		
		
		dependency_matrix_scrollPane = new JScrollPane(dependency_table);
		dependency_matrix_scrollPane.setEnabled(false);
		dependency_matrix_scrollPane.setViewportView(dependency_table);
				
		matrixTool = new JPanel();
		matrixTool.setForeground(Color.WHITE);
		matrixTool.setLayout(new BorderLayout(0, 0));
		matrixTool.add(dependency_matrix_scrollPane, BorderLayout.CENTER);
				
		splitPane_horizontal.setRightComponent(matrixTool);
	}
	
	public void showMsg(String msg)
	{
		JOptionPane newMsg = new JOptionPane();
		newMsg.showMessageDialog(this,msg);
	}
	
	public void setEnableButton(boolean set)
	{
		mntmNewMenuItem.setEnabled(set);
		mntmLoadClustering.setEnabled(set);
		mntmSaveClustering.setEnabled(set);
		mntmSaveClusteringAs.setEnabled(set);
		mntmExit.setEnabled(set);
		mntmRedraw.setEnabled(set);
		mntmAbout.setEnabled(set);
		mntmNewDsm.setEnabled(set);
		mntmSaveDsm.setEnabled(set);
		mntmSaveAsDsm.setEnabled(set);
		btnRedraw.setEnabled(set);
		btnNewClustering.setEnabled(set);
		btnLoadClustering.setEnabled(set);
		btnSaveClustering.setEnabled(set);
		btnSaveClusteringAs.setEnabled(set);
		btnExpandAll.setEnabled(set);
		btnCollapsAll.setEnabled(set);
		btnGroup.setEnabled(set);
		btnUngroup.setEnabled(set);
		btnMoveUp.setEnabled(set);
		btnMoveDown.setEnabled(set);
		btnDelete.setEnabled(set);
		btnNewDsmRow.setEnabled(set);
		btnRename.setEnabled(set);
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
	
	public String[] setFilepath_load()
	{
		FileDialog fldg = new FileDialog(frmTitan,"file open",FileDialog.LOAD);
		fldg.setVisible(true);	
		try{
			String[] pathName = new String[2];
			pathName[0] = fldg.getDirectory();
			pathName[1] = fldg.getFile();
			filePath = new String();
			filePath = pathName[0]+pathName[1];
			return pathName;
		}catch(Exception e)
		{
			return null;
		}
	}
	
	public String[] setFilepath_save()
	{
		FileDialog fldg = new FileDialog(frmTitan,"file open",FileDialog.SAVE);
		fldg.setVisible(true);	
		try{
			String[] fileInfo = new String[2];
			fileInfo[0] = fldg.getDirectory();
			fileInfo[1] = fldg.getFile();
			return fileInfo;
		}catch(Exception e)
		{
			return null;
		}
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
	
	public void setShowRowLabel(int setting)
	{
		this.showRowLabel = setting;
	}
	
	public int getShowRowLabel()
	{
		return showRowLabel;
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

	public JMenuItem getMntmSaveDsm() {
		return mntmSaveDsm;
	}

	public JMenuItem getMntmSaveAsDsm() {
		return mntmSaveAsDsm;
	}
	
	public JMenuItem getMntmExit() {
		return mntmExit;
	}

	public JMenuItem getMntmRedraw() {
		return mntmRedraw;
	}

	public JMenuItem getMntmShow_Row_Labels() {
		return mntmShow_Row_Labels;
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
 
	public JButton getBtnNewDsmRow()
	{
		return btnNewDsmRow;
	}
	public JButton getBtnRename()
	{
		return btnRename;
	}
	
	public JMenuItem getMntmNewDsm() {
		return mntmNewDsm;
	}
}
