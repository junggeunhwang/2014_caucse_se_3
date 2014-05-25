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
	
	// input T�� group�� ��Ÿ���� node, members�� group���� ���� node��
	public void insertNode(TreeNode groupNode, TreeNode[] members)
	{
		int insertPosition=0;
		TreeNode parentNode = new TreeNode();
		parentNode = members[0].parent;
		
		for(int i=0; i<parentNode.childs.size(); i++)
		{
			if(parentNode.childs.get(i)==members[0])
				insertPosition = i;
			for(int j=0; j<members.length; j++)   // parent����� �ڽĿ��� member�� ���ϴ� ��� ����
			{
				if(parentNode.childs.get(i)==members[j])
					parentNode.childs.remove(i);
			}
		}
		parentNode.childs.insertElementAt(groupNode, parentNode.childs.size());// member���� parent�� group��� �߰�
		groupNode.parent = parentNode;
		
		for(int i=0; i<members.length; i++)  // member���� �׷��� �ƴѰ� ���� group�� �߰��Ѵ�.
		{
			if(members[i].childs.size()==0)
			{
				groupNode.setChild(members[i]);
			}
		}
		for(int i=0; i<members.length; i++)  // member���� �׷��� �͵��� ������� group�� �߰��Ѵ�.
		{
			if(members[i].childs.isEmpty()==false)
			{
				groupNode.setChild(members[i]);
			}
		}
		
	}
	
	public void removeNode(String deleteName)
	{
		TreeNode parentNode = new TreeNode();
		TreeNode deleteNode = new TreeNode();
		deleteNode = getNode(deleteName);
		parentNode = deleteNode.parent;
	
		//int deletePosition=0;
		int size = parentNode.childs.size();
		
		for(int i=0; i<size; i++)
		{
			if(parentNode.childs.get(i)==deleteNode) // parentNode���� groupNode ����
			{	
				parentNode.childs.remove(i);
				//deletePosition = i;
				break;
			}
		}
		if(deleteNode.childs.size()==0)
		{
			deleteNode = null;
			return;
		}
		
		/*for(int i=0; i<deleteNode.childs.size(); i++)
		{
			//parentNode.childs.insertElementAt(deleteNode.childs.get(i), deletePosition);
			//deleteNode.childs.get(i).parent = parentNode;
			//deletePosition++;
		}*/
		deleteNode = null;
		
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
		if(size==0)// leaf���� �߰�
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
	public int getLeafNodeSize(int retSize)
	{
		int size = childs.size();
		if(size==0)// leaf���� �߰�
		{
			retSize = retSize+1;
		}
		else
		{
			for(int i=0; i<size; i++)
				retSize = this.childs.get(i).getLeafNodeSize(retSize);
		}
		return retSize;
	}
	
}
