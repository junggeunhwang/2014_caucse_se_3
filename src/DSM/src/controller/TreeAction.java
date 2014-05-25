package controller;

import javax.swing.JTree;

import model.ClusterModel;
import model.ModelInfo;
import model.TreeNode;

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import view.Main_view;

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
		DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)Main_view.getInstance().getClasstree().getLastSelectedPathComponent();
		if(selectedNode==null)
			return;
		String nodeName = new String();
		nodeName = (String) selectedNode.getUserObject();
		ClusterModel.getInstance().removeModule(nodeName);
	}
	
	public void groupAction()
	{
		DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		TreePath[] treepath = new TreePath[ModelInfo.getInstance().getModules().size()];
		treepath = Main_view.getInstance().getClasstree().getSelectionPaths();
		String[] members = new String[treepath.length];
		for(int i=0; i<treepath.length; i++)
		{
			String nodeName = treepath[i].getPathComponent(treepath[i].getPathCount()-1).toString();
			members[i] = nodeName;
		}
		String groupName = new String();
		groupName = Main_view.getInstance().setGroupName();
		ClusterModel.getInstance().grouping(groupName, members);
	}
	
	public void unGroupAction()
	{
		DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		TreePath[] treepath = new TreePath[ModelInfo.getInstance().getModules().size()];
		treepath = Main_view.getInstance().getClasstree().getSelectionPaths();
		String[] members = new String[treepath.length];
		for(int i=0; i<treepath.length; i++)
		{
			String nodeName = treepath[i].getPathComponent(treepath[i].getPathCount()-1).toString();
			members[i] = nodeName;
		}
		String groupName = new String();
		groupName = Main_view.getInstance().setGroupName();
		ClusterModel.getInstance().grouping(groupName, members);
	}
	
	
}