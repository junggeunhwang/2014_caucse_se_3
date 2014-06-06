package controller;

import javax.swing.JOptionPane;
import javax.swing.JTree;

import model.ClusterModel;
import model.ModelInfo;
import model.TreeNode;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import view.Main_view;
import view.customtable.CustomJTable;
import view.customtable.CustomTableModel;

public class TreeAction {

	private static TreeAction instance;
	
	private int groupIndex;
	
	private TreeAction(){}
	
	public static TreeAction getInstance(){
		if(instance==null){
			instance = new TreeAction();
		}
		return instance;
	}
	
	Vector<DefaultMutableTreeNode> depthSearch(Vector<DefaultMutableTreeNode> nodes, TreeNode N)
	{
		DefaultMutableTreeNode tr = new DefaultMutableTreeNode(N.key);
		nodes.add(tr);
		nodes.get(groupIndex).add(tr);

		
		if(N.isGroup()==false)
		{
			return nodes;
		}
		else
		{
			int prevIndex = groupIndex;
			groupIndex = nodes.size()-1;
			for(int i=0; i<N.childs.size();  i++)
			{
				depthSearch(nodes,N.childs.get(i));	
			}
			groupIndex = prevIndex;
		}
		return nodes;
	}
	
	public JTree makeTree()
	{
		Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();
		groupIndex =0;
		int size = ModelInfo.getInstance().getRoot().childs.size();
		DefaultMutableTreeNode tr = new DefaultMutableTreeNode(ModelInfo.getInstance().getRoot().key);
		nodes.add(tr);
		for(int i=0; i<size; i++)
		{
			nodes = depthSearch(nodes, ModelInfo.getInstance().getRoot().childs.get(i));
		}
		JTree newTree = new JTree(nodes.get(0)); 
		return newTree;
	}
	
	public void moveAction(int dir)
	{
		DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)Main_view.getInstance().getClasstree().getLastSelectedPathComponent();
		if(selectedNode==null)
			return;
		String nodeName = new String();
		nodeName = (String) selectedNode.getUserObject();
		ClusterModel.getInstance().moveModule(nodeName, dir);
	}
	
	public void removeAction()
	{
		//DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		//DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)Main_view.getInstance().getClasstree().getLastSelectedPathComponent();
		//if(selectedNode==null)
		//	return;
		//String nodeName = new String();
		//nodeName = (String) selectedNode.getUserObject();
		//ClusterModel.getInstance().removeModule(nodeName,true);
		DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		TreePath[] treepath = new TreePath[ModelInfo.getInstance().getModules().size()];
		treepath = Main_view.getInstance().getClasstree().getSelectionPaths();
		if(treepath==null)
			return;
		String[] members = new String[treepath.length];
		for(int i=0; i<treepath.length; i++)
		{
			String nodeName = treepath[i].getPathComponent(treepath[i].getPathCount()-1).toString();
			members[i] = nodeName;
		}
		for(int i=0; i<members.length; i++)
		{
			ClusterModel.getInstance().removeModule(members[i],true);
		}
	}
	
	public void groupAction()
	{
		String groupName = new String();
		groupName = Main_view.getInstance().setGroupName();
		if(groupName==null)
			return;
		DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		TreePath[] treepath = new TreePath[ModelInfo.getInstance().getModules().size()];
		treepath = Main_view.getInstance().getClasstree().getSelectionPaths();
		String[] members = new String[treepath.length];
		for(int i=0; i<treepath.length; i++)
		{
			String nodeName = treepath[i].getPathComponent(treepath[i].getPathCount()-1).toString();
			members[i] = nodeName;
		}
		ClusterModel.getInstance().grouping(groupName, members);
	}
	
	public void unGroupAction()
	{
		DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)Main_view.getInstance().getClasstree().getLastSelectedPathComponent();
		if(selectedNode==null)
			return;
		String nodeName = new String();
		nodeName = (String) selectedNode.getUserObject();
		System.out.println("ungroup[");
		if(ModelInfo.getInstance().getRoot().getNode(nodeName).isGroup()==true)
			ClusterModel.getInstance().removeModule(nodeName,false);
	}
	
	public Vector<String> getTreeState()
	{
		Vector<String> printElement = new Vector<String>();
		for(int i=1; i<Main_view.getInstance().getClasstree().getRowCount(); i++)
		{
			boolean print = Main_view.getInstance().getClasstree().isExpanded(i);
			int height = Main_view.getInstance().getClasstree().getRowBounds(i).height;
			if(print==false)
			{
				TreePath tp = Main_view.getInstance().getClasstree().getPathForRow(i);
				String str = new String();
				str = tp.getPathComponent(tp.getPathCount()-1).toString();
				printElement.add(str);
			}
		}
		return printElement;
	}
	public void newDsmRow()
	{
		String newNodeName = new String();
		newNodeName = Main_view.getInstance().setNewName();
		if(newNodeName==null)
		{
			Main_view.getInstance().setEnableButton(true);
			return;
		}	
		for(int i=0; i<ModelInfo.getInstance().getModules().size(); i++)
		{
			if(ModelInfo.getInstance().getModules().get(i).key.compareTo(newNodeName)==0)
			{
				JOptionPane.showMessageDialog(Main_view.getInstance(),"Invalid module Name !");
				return;
			}
		}
		int[] newToExist = new int[ModelInfo.getInstance().getModules().size()+1];
		int[] existToNew = new int[ModelInfo.getInstance().getModules().size()+1];
		ClusterModel.getInstance().newModule(newNodeName, newToExist, existToNew);
		
		Vector<String> newModuleList = new Vector<String>();
		for(int i=0; i<ModelInfo.getInstance().getModules().size(); i++)
		{
			newModuleList.add(ModelInfo.getInstance().getModules().get(i).key);
		}
		Main_view.getInstance().drawTable(newModuleList);
		Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		
		Main_view.getInstance().getDependency_table().setEditable(true);
	}
	public void reNameNode()
	{
		DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)Main_view.getInstance().getClasstree().getLastSelectedPathComponent();
		if(selectedNode==null)
			return;
		String nodeName = new String();
		nodeName = (String) selectedNode.getUserObject();
		String newNodeName = new String();
		newNodeName = Main_view.getInstance().setNewName();
		if(newNodeName==null)
			return;
		ModelInfo.getInstance().getRoot().getNode(nodeName).key = newNodeName;
	}
	
	public void newDsm(int rowCount)
	{
		ModelInfo.getInstance().setInit(rowCount);
		Vector<String> newModuleList = new Vector<String>();
		for(int i=0; i<ModelInfo.getInstance().getModules().size(); i++)
		{
			newModuleList.add(ModelInfo.getInstance().getModules().get(i).key);
		}
		Main_view.getInstance().drawTable(newModuleList);
		Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		Main_view.getInstance().getDependency_table().setEditable(true);
	}
	
	public void setNewDepend(CustomJTable table)
	{
		CustomTableModel model = (CustomTableModel) table.getModel();
		ArrayList<int[]> newdp = new ArrayList<int[]>();
		int size = model.getRowCount();
		for(int i=0; i<size; i++)
		{
			int[] dp = new int[size];
			for(int j=1; j<size+1; j++)
			{
				if(model.getData()[i][j].toString().compareTo("��")==0)
					dp[j-1] = 0;
				else if(model.getData()[i][j].toString().compareTo(" ")==0)
					dp[j-1] = 0;
				else
					dp[j-1] = 1;
			}
			newdp.add(dp);
		}
		ModelInfo.getInstance().setDependData(newdp);
	}
	
	
	
}
