package controller;

import javax.swing.JTree;

import model.ClusterModel;
import model.ModelInfo;
import model.TreeNode;

import java.util.Enumeration;
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
	//	for(int i=0; i<members.length; i++)
	//		System.out.print(members[i]+" ");
//		System.out.println(members.length);
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
	
	public Vector<String> getTreeState()
	{
		//DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
		//TreePath root = new TreePath(Main_view.getInstance().getClasstree().getPathForRow(0));
		
		//Enumeration<TreePath> expandedPaths = Main_view.getInstance().getClasstree().getExpandedDescendants(root);
		//TreePath selectedPath =  Main_view.getInstance().getClasstree().getSelectionPath();
		
		//DefaultTreeModel model = (DefaultTreeModel)Main_view.getInstance().getClasstree().getModel();
	//	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)Main_view.getInstance().getClasstree().getLastSelectedPathComponent();
		//System.out.print(selectedNode.getPath().toString());
	/*	System.out.println(Main_view.getInstance().getClasstree().getRowCount());
		for(int i=0; i<Main_view.getInstance().getClasstree().getRowCount(); i++)
		{
			System.out.print(Main_view.getInstance().getClasstree().isExpanded(i)+"  ");
		}
		System.out.print("\n");
		for(int i=0; i<Main_view.getInstance().getClasstree().getRowCount(); i++)
		{
			System.out.print(Main_view.getInstance().getClasstree().getRowBounds(i).height+"  ");
		}
		System.out.print("\n");
		for(int i=0; i<Main_view.getInstance().getClasstree().getRowCount(); i++)
		{
			TreePath tp = Main_view.getInstance().getClasstree().getPathForRow(i);
			String str = new String();
			str = tp.getPathComponent(tp.getPathCount()-1).toString();
			System.out.print(str+" ");
		}
		System.out.print("\n");*/
		
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
		
		//System.out.println(Main_view.getInstance().getClasstree().isExpanded(0));
		//System.out.println(Main_view.getInstance().getClasstree().isExpanded(1));
	//	System.out.println(Main_view.getInstance().getClasstree().isExpanded(2));
		
		/*if(selectedNode.getPath()[selectedNode.getLevel()].)
			System.out.print("yes");
		else
			System.out.print("no");
		*//*while (expandedPaths != null && expandedPaths.hasMoreElements())
		{
		    TreePath path = expandedPaths.nextElement();
		    if (isPathValid(path))
		    {
		    	Main_view.getInstance().getClasstree().expandPath(path);
		    }
		}*/
	//	System.out.println();


	}
	
	
}
