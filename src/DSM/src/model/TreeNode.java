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
		TreeNode retNode = new TreeNode();
		int size = childs.size();
		if(this.key.compareTo(nodeName)==0)
			return this;
		for(int i=0; i<childs.size(); i++)
		{
			if(childs.get(i).key.compareTo(nodeName)==0)
				return childs.get(i);
		}
		for(int i=0; i<childs.size(); i++)
		{
			if(childs.get(i).getNode(nodeName).key!=null)
				retNode = childs.get(i).getNode(nodeName);
		}
		return retNode;
	}
	
	// input T는 group을 나타내는 node, members는 group으로 만들 node들
	public void insertNode(TreeNode groupNode, TreeNode[] members)
	{
		int insertPosition=0;
		TreeNode parentNode = new TreeNode();
		parentNode = members[0].parent;
		
		for(int i=0; i<parentNode.childs.size(); i++)
		{
			if(parentNode.childs.get(i)==members[0])
				insertPosition = i;
			for(int j=0; j<members.length; j++)   // parent노드의 자식에서 member에 속하는 노드 제거
			{
				if(parentNode.childs.get(i)==members[j])
					parentNode.childs.remove(i);
			}
		}
		parentNode.childs.insertElementAt(groupNode, parentNode.childs.size());// member들의 parent에 group노드 추가
		groupNode.parent = parentNode;
		
		for(int i=0; i<members.length; i++)  // member에서 그룹이 아닌것 부터 group에 추가한다.
		{
			if(members[i].childs.size()==0)
			{
				groupNode.setChild(members[i]);
			}
		}
		for(int i=0; i<members.length; i++)  // member에서 그룹인 것들을 순서대로 group에 추가한다.
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
		
		for(int i=0; i<deleteNode.childs.size(); i++)
		{
			parentNode.childs.insertElementAt(deleteNode.childs.get(i), deletePosition);
			deleteNode.childs.get(i).parent = parentNode;
			deletePosition++;
		}
		deleteNode = null;
	}
	
	public void reorderChild(TreeNode moveNode, int dir)
	{
		TreeNode parentNode = new TreeNode();
		TreeNode tempNode = new TreeNode();
		parentNode = moveNode.parent;
		
		for(int i=0; i<parentNode.childs.size(); i++){
			if(parentNode.childs.get(i)==moveNode)
			{
				if( 0<=(i+dir) && (i+dir)<parentNode.childs.size())
				{
					tempNode = parentNode.childs.get(i+dir);
					parentNode.childs.remove(i);
					parentNode.childs.remove(i+dir);
					parentNode.childs.insertElementAt(tempNode,i);
					parentNode.childs.insertElementAt(moveNode,i+dir);
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
	
	
}

