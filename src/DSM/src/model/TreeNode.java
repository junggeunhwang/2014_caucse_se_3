package model;

import java.util.Vector;

public class TreeNode{
	
	
	public String key;
	public TreeNode parent;
	public Vector<TreeNode> childs;
	
	public TreeNode()
	{
		this.key = "$root";
		this.parent = null;
		this.childs = new Vector<TreeNode>();
	}

	public TreeNode(String keyName, TreeNode parentNode)
	{
		this.key = keyName;
		this.parent = parentNode;
		this.childs = new Vector<TreeNode>();
	}
	
	public void setKey(String keyName)
	{
		this.key = keyName;
		this.parent = null;
		this.childs = new Vector<TreeNode>();
	}
	
	public void setChild(TreeNode childNode)
	{
		childs.add(childNode);
		childNode.parent = this;
	}
	
	public TreeNode getNode(String nodeName)
	{
		TreeNode tempNode = new TreeNode();
		int size = childs.size();
		if(this.key.compareTo(nodeName)==0)
			return this;
		for(int i=0; i<childs.size(); i++)
		{
			TreeNode retNode = new TreeNode();
			if(childs.get(i).getNode(nodeName).key.compareTo("$root")!=0)
			{
				retNode = childs.get(i).getNode(nodeName);
				return retNode;
			}
		}
		return tempNode;
	}
	
	// input T는 group을 나타내는 node, members는 group으로 만들 node들
	public void insertNode(TreeNode groupNode, TreeNode[] members)
	{
		for(int i=1; i<members.length; i++)
		{
			if(members[i].parent.key.compareTo(members[0].parent.key)!=0)
				return;
		}
		int insertPosition=0;
		TreeNode parentNode = new TreeNode();
		parentNode = members[0].parent;
		
		int[] order = new int[members.length];
		for(int i=0; i<members.length; i++)
		{
			for(int j=0; j<parentNode.childs.size(); j++)   // parent노드의 자식에서 member에 속하는 노드 제거
			{
				if(parentNode.childs.get(j).key.compareTo(members[i].key)==0)
				{
					order[i] = j;
					parentNode.childs.remove(j);
				}
			}
		}
		int minOrder = 0;
		for(int i=0; i<order.length; i++)
		{
			if(i==0)
				minOrder = order[0];
			else
				minOrder = Math.min(minOrder, order[i]);
		}
		parentNode.childs.insertElementAt(groupNode, minOrder);
		//parentNode.childs.add(groupNode);
		groupNode.parent = parentNode;
		for(int i=0; i<members.length; i++)
		{
			groupNode.childs.add(members[i]);
			members[i].parent = groupNode;
		}
	}
	
	public void removeNode(String deleteName, boolean isDelete)
	{
		TreeNode parentNode = new TreeNode();
		TreeNode deleteNode = new TreeNode();
		deleteNode = getNode(deleteName);
		parentNode = deleteNode.parent;
	
		int deletePosition=0;
		int size = parentNode.childs.size();
		
		for(int i=0; i<size; i++)
		{
			if(parentNode.childs.get(i)==deleteNode) // parentNode에서 groupNode 제거
			{	
				parentNode.childs.remove(i);
				deletePosition = i;
				break;
			}
		}
		if(deleteNode.childs.size()==0)
		{
			deleteNode = null;
			return;
		}
		if(isDelete==false)
		{
			for(int i=0; i<deleteNode.childs.size(); i++)
			{
				parentNode.childs.insertElementAt(deleteNode.childs.get(i), deletePosition);
				deleteNode.childs.get(i).parent = parentNode;
				deletePosition++;
			}
			deleteNode = null;
		}
		
	}
	
	public void reorderChild(TreeNode moveNode, int dir)
	{
		for(int i=0; i<moveNode.parent.childs.size(); i++){
			if(moveNode.parent.childs.get(i)==moveNode)
			{
				if( 0<=(i+dir) && (i+dir)<moveNode.parent.childs.size())
				{
					moveNode.parent.childs.setElementAt(moveNode.parent.childs.get(dir+i), i);
					moveNode.parent.childs.setElementAt(moveNode, i+dir);
					break;
				}
			}
		}		
	}
	
	public boolean isGroup()
	{
		if(this.childs.size()==0)
			return false;
		else
			return true;
	}
	
	public Vector<TreeNode> getchildNode()
	{
		int size = childs.size();
		Vector<TreeNode> leafNodes = new Vector<TreeNode>();
		for(int i=0; i<size; i++)
		{
			leafNodes.add(childs.get(i));
		}
		return leafNodes;
	}
	public Vector<TreeNode> getLeafNode(Vector<TreeNode> leafNodes)
	{
		int size = childs.size();
		if(size==0)// leaf노드면 추가
		{
			leafNodes.add(this);
		}
		else
		{
			for(int i=0; i<size; i++)
				leafNodes = childs.get(i).getLeafNode(leafNodes);
		}
		return leafNodes;
	}
	
	public int getLeafNodeSize()
	{
		int size = childs.size();
		int retSize=0;
		if(size==0)// leaf노드면 추가
		{
			return 1;
		}
		else
		{
			for(int i=0; i<size; i++)
				retSize += this.childs.get(i).getLeafNodeSize();
		}
		//System.out.println(retSize);
		return retSize;
	}
	public boolean isEqualrootChild(TreeNode a, TreeNode b)
	{
		TreeNode temp = new TreeNode();
		temp = a;
		while(temp.parent.key.compareTo("$root")!=0)
		{
			temp = temp.parent;
		}
		TreeNode temp2 = new TreeNode();
		temp2 = b;
		while(temp2.parent.key.compareTo("$root")!=0)
		{
			temp2 = temp2.parent;
		}
		
		if(temp.key.compareTo(temp2.key)==0)
			return true;
		else
			return false;
	}
	
	public int getDepth(String a)
	{
		TreeNode temp = new TreeNode();
		temp = this.getNode(a);
		int ret=0;
		
		if(temp.key.compareTo("$root")==0)
			return 1;
		
		while(temp.parent.key.compareTo("$root")!=0)
		{
			temp = temp.parent;
			ret++;
		}
		return ret+1;
	}
	
}

