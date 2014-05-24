package controller;

import javax.swing.JTree;
import model.TreeNode;
import model.DsmModel;
import java.util.Vector;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeAction {

	JTree jtree = new JTree();
	DefaultMutableTreeNode root;
	int groupIndex;
	
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
	
	public void treeSetting( DsmModel m)
	{
		Vector<DefaultMutableTreeNode> nodes = new Vector();
		Vector<DefaultMutableTreeNode> tempNodes = new Vector();
		groupIndex =0;
		int size = m.getRootNode().childs.size();
		DefaultMutableTreeNode tr = new DefaultMutableTreeNode(m.getRootNode().key);
		nodes.add(tr);
		for(int i=0; i<size; i++)
		{
			nodes = depthSearch(nodes, m.getRootNode().childs.get(i));
		}
		//System.out.print(nodes.get(0).getChildCount()+"  ");
		JTree newTree = new JTree(nodes.get(0));
		jtree = newTree;
	}
	
	
	
	
	
}
