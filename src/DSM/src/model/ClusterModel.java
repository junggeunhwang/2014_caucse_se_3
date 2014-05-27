package model;

import java.util.Vector;
import java.io.*;
import java.util.Stack;
import java.util.ArrayList;

public class ClusterModel {
	
	static private ClusterModel instance;
	
	private ClusterModel(){}

	public static ClusterModel getInstance(){
		
		if(instance==null){
			instance = new ClusterModel();
		}
		return instance;
	}
	
	public void newModule(String newModule, int[] newToExist, int[] existToNew)
	{
		TreeNode newNode = new TreeNode();
		newNode.setKey(newModule);
		newNode.parent = ModelInfo.getInstance().getRoot();
		int insertPoint=ModelInfo.getInstance().getRoot().childs.size();
		for(int i=0; i<ModelInfo.getInstance().getRoot().childs.size(); i++)
		{
			if(ModelInfo.getInstance().getRoot().childs.get(i).childs.size()!=0)
			{
				insertPoint = i;
				break;
			}
		}
		ModelInfo.getInstance().getRoot().childs.insertElementAt(newNode, insertPoint);
		ArrayList<int[]> tempDepend = new ArrayList<int[]>();
		int newSize = ModelInfo.getInstance().getDependData().size()+1;
		
		for(int i=0; i<newSize; i++)
		{
			int[] tempRow = new int[newSize];
			if(i<insertPoint)
			{
				for(int j=0; j<newSize; j++)
				{
					if(j<insertPoint)
						tempRow[j] =  ModelInfo.getInstance().getDependData().get(i)[j];
					else if(j==insertPoint)
						tempRow[j] = existToNew[i];
					else
						tempRow[j] =  ModelInfo.getInstance().getDependData().get(i)[j-1];
				}
				tempDepend.add(tempRow);
			}
			else if(i==insertPoint)
			{
				for(int j=0; j<newSize; j++)
				{
					if(j<insertPoint)
						tempRow[j] = newToExist[j];
					else if(j==insertPoint)
						tempRow[j] = 0;
					else
						tempRow[j] = newToExist[j-1];
				}
				tempDepend.add(tempRow);
			}
			else
			{
				for(int j=0; j<newSize; j++)
				{
					if(j<insertPoint)
						tempRow[j] =  ModelInfo.getInstance().getDependData().get(i-1)[j];
					else if(j==insertPoint)
						tempRow[j] = existToNew[i-1];
					else
						tempRow[j] =  ModelInfo.getInstance().getDependData().get(i-1)[j-1];
				}
				tempDepend.add(tempRow);
			}
		}
		ModelInfo.getInstance().setDependData(tempDepend);
		
	}

	public void grouping(String groupName, String[] members)
	{
		int memberSize = members.length;
		TreeNode groupNode = new TreeNode();
		groupNode.setKey(groupName);
		TreeNode[] membersNode = new TreeNode[memberSize];
		for(int i=0; i<members.length; i++)
		{
			membersNode[i] = ModelInfo.getInstance().getRoot().getNode(members[i]);
		}
		ModelInfo.getInstance().getRoot().insertNode(groupNode, membersNode);
		Vector<TreeNode> tempModules = new Vector<TreeNode>();
		Vector<TreeNode> tempModules2 = new Vector<TreeNode>();
		tempModules = ModelInfo.getInstance().getRoot().getLeafNode(tempModules2);
		this.updateDepend(tempModules);
		ModelInfo.getInstance().setModules(tempModules);
	}

	public void moveModule(String module, int dir)
	{
		TreeNode moduleNode = new TreeNode();
		moduleNode = ModelInfo.getInstance().getRoot().getNode(module);
		ModelInfo.getInstance().getRoot().reorderChild(moduleNode, dir);
		Vector<TreeNode> tempModules = new Vector<TreeNode>();
		Vector<TreeNode> tempModules2 = new Vector<TreeNode>();
		tempModules = ModelInfo.getInstance().getRoot().getLeafNode(tempModules2);
		this.updateDepend(tempModules);
		ModelInfo.getInstance().setModules(tempModules);
	}
	
	public void updateDepend(Vector<TreeNode> afterModules)
	{
		int parameter = 0;
		int dependSize = ModelInfo.getInstance().getRoot().getLeafNodeSize();
		//System.out.println(ModelInfo.getInstance().getRoot().getLeafNodeSize(parameter));
		//dependSize = 
		ArrayList<int[]> tempDepend = new ArrayList<int[]>(); //최신화 된 getDependData를 임시로 저장할 공간
		int[] afterOrderNo = new int[ModelInfo.getInstance().getModules().size()];
		//System.out.println(dependSize);
		for(int i=0; i<dependSize; i++)
		{
			for(int j=0; j<dependSize; j++)
				if(ModelInfo.getInstance().getModules().get(i).key.compareTo(afterModules.get(j).key)==0)
				{
					afterOrderNo[j] = i;
					break;
				}
		}		
		for(int i=0; i<dependSize; i++)
		{
			int[] row = new int[dependSize];
			for(int j=0; j<dependSize; j++)
				row[j] = ModelInfo.getInstance().getDependData().get(afterOrderNo[i])[afterOrderNo[j]];
			tempDepend.add(row);
		}
		ModelInfo.getInstance().setDependData(tempDepend);
		
	}
	
	public void removeModule(String nodeName)
	{
		int isGroup=0;
		if(ModelInfo.getInstance().getRoot().getNode(nodeName).childs.size()==0)
			isGroup=1;
		ModelInfo.getInstance().getRoot().removeNode(nodeName);
		
		Vector<TreeNode> tempModules = new Vector<TreeNode>();
		Vector<TreeNode> tempModules2 = new Vector<TreeNode>();
		tempModules = ModelInfo.getInstance().getRoot().getLeafNode(tempModules2);
		if(isGroup==0)
		{
			this.updateDepend(tempModules);
			ModelInfo.getInstance().setModules(tempModules);
		}
		else
		{
			int deletePoint=0;
			for(int i=0; i<ModelInfo.getInstance().getModules().size(); i++)
			{
				if(nodeName.compareTo(ModelInfo.getInstance().getModules().get(i).key)==0)
				{	
					deletePoint = i;
					break;
				}
			}
			ModelInfo.getInstance().removeDependElement(deletePoint);
			ModelInfo.getInstance().setModules(tempModules);
		}
	}
	
	public void readCluster(String filePath, int pivot)
	{
		String cluster[][] = new String [2][pivot];
		try
		 {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); 
			BufferedReader br = new BufferedReader(isr);
			
			String temp="";
			int groupNum=0;
			Stack<String> s = new Stack<String>();
			TreeNode newRoot = new TreeNode();
			while(((temp = br.readLine()) != null)) 
			{
				String[] str= temp.split("\"",3);
				if(str[0].compareTo("<group name=")==0)
				{
					TreeNode newNode = new TreeNode();
					newNode.setKey(str[1]);
					if(groupNum == 0)
					{
						newNode.parent=null;
						newRoot = newNode;
					}
					else
						newRoot.getNode(s.peek()).setChild(newNode);
					s.push(str[1]);
					groupNum++;
				}
				else if(str[0].compareTo("<item name=")==0)
				{
					TreeNode newNode = new TreeNode();
					newNode.setKey(str[1]);
					newRoot.getNode(s.peek()).setChild(newNode);
				}
				else if(str[0].compareTo("</group>")==0)
				{
					groupNum--;
					if(groupNum==0)	break;
					s.pop();
				}
			}
			Vector<TreeNode> tempModules = new Vector<TreeNode>();
			Vector<TreeNode> tempModules2 = new Vector<TreeNode>();
			tempModules = newRoot.getLeafNode(tempModules2);
			updateDepend(tempModules);
			ModelInfo.getInstance().setModules(tempModules);
			ModelInfo.getInstance().setRoot(newRoot);
		 }
		 catch(FileNotFoundException fnfe){
			 System.out.println("파일을 찾을 수 없습니다.");
			 }catch(IOException ioe){}
	}

	public Vector<String> makeStr(Vector<String> printList, TreeNode T)
	{
		String groupOpen = "<group name=\"";
		String groupClose = "</group>";
		String itemOpen = "<item name=\"";
		if(T.childs.size()==0)
		{
			printList.add(itemOpen+T.key+"\" />");
		}
		else
		{
			printList.add(groupOpen+T.key+"\">");
			for(int i=0; i<T.childs.size(); i++)
				makeStr(printList,T.childs.get(i));
			printList.add(groupClose);
		}
		return printList;
		
	}
	
	public void writeCluster(String filePath, String fileName)
	{
		try{
			File file = new File(filePath, fileName);
			file.createNewFile();
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			BufferedWriter buff_writer = new BufferedWriter(osw);
			PrintWriter print_writer = new PrintWriter(buff_writer,true);
			String clusterOpen = "<cluster>";
			String clusterClose = "</cluster>";
			print_writer.println(new String(clusterOpen.getBytes("UTF-8"),"UTF-8"));
			Vector<String> printList = new Vector<String>();
			printList = makeStr(printList,ModelInfo.getInstance().getRoot());
			for(int i=0; i<printList.size(); i++)
				print_writer.println(new String(printList.get(i).getBytes("UTF-8"),"UTF-8"));
			print_writer.println(new String(clusterClose.getBytes("UTF-8"),"UTF-8"));
			osw.close();
			buff_writer.close();
			print_writer.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
