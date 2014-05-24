package controller;

import javax.swing.JTree;

import model.ModelInfo;
import model.TreeNode;

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

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
		//System.out.print(nodes.get(0).getChildCount()+"  ");
		JTree newTree = new JTree(nodes.get(0)); 
		return newTree;
	}
	
}
