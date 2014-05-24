package model;

import java.io.*;

import model.TreeNode;

import java.util.Vector;
import java.util.ArrayList;;
public class DsmModel {
	
	protected ArrayList<int[]> dependData;
	protected Vector<TreeNode> modules;
	protected TreeNode root;
	
	public DsmModel()
	{
		dependData = new ArrayList<int[]>();
		modules = new Vector<TreeNode>();
		root = new TreeNode();
		root.setKey("$root");
	}
	
	public TreeNode getRootNode()
	{
		return root;
	}
	public ArrayList<int[]> getDependData()
	{
		return dependData;
	}
	public Vector<TreeNode> getModules()
	{
		return modules;
	}
	public void setDependData(ArrayList<int[]> newDepend)
	{
		dependData = newDepend;
	}
	public void setRoot(TreeNode newRoot)
	{
		root = newRoot;
	}
	public void setModules(Vector<TreeNode> newModules)
	{
		modules = newModules;
	}
	
	public void readDsm(String filePath)
 	{
		 	try
			{
				File file = new File(filePath);
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr =  new InputStreamReader(fis, "UTF-8"); 
				BufferedReader br  = new BufferedReader(isr);
				
				String temp = "";
				temp = br.readLine();
				int pivot = Integer.parseInt(temp);
				int dependency[][] = new int [pivot+1][pivot];
				String module[] = new String [pivot];
				
				int index=0;
				while(((temp = br.readLine()) != null)) 
				{
					 if(index<pivot)
					 {
					    	for(int j=0; j<pivot; j++)
					    	{
					    		dependency[index][j] = Integer.parseInt(temp.substring(2*j, 2*j+1));				    		 
					    	}
					 }
					 else
					 {
						 module[index-pivot] = temp;
					 }
					    index++;
				}
				for(int i=0; i<pivot; i++)
					dependData.add(dependency[i]);
				
				String[] data = new String[2];
				for(int i=0; i<pivot; i++)
				{
					TreeNode newNode = new TreeNode();
					newNode.setKey(module[i]);
					root.setChild(newNode);
				}
				modules = root.getLeafNode(modules);
			}catch(FileNotFoundException fnfe)
			{
				System.out.println("파일을 찾을 수 없습니다.");
			}catch(IOException ioe){}
			
	}

	public void saveDsm(String filePath, String fileName)
	{
		try{
			File file = new File(filePath, fileName);
			file.createNewFile();
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			BufferedWriter buff_writer = new BufferedWriter(osw);
			PrintWriter print_writer = new PrintWriter(buff_writer,true);
			String printStr = "";
			printStr = Integer.toString(modules.size());
			print_writer.println(new String(printStr.getBytes("UTF-8"),"UTF-8"));
			Vector<String> printList = new Vector<String>();
			int dependSize = dependData.size();
			for(int i=0; i<dependSize; i++)
			{
				for(int j=0; j<dependSize; j++)
				{
					int dependent = dependData.get(i)[j];
					printStr = Integer.toString(dependent)+" ";
					print_writer.print(new String(printStr.getBytes("UTF-8"),"UTF-8"));
				}
				print_writer.println(new String("".getBytes("UTF-8"),"UTF-8"));
			}
			for(int i=0; i<modules.size(); i++)
			{
				print_writer.println(new String(modules.get(i).key.getBytes("UTF-8"),"UTF-8"));
			}
			osw.close();
			buff_writer.close();
			print_writer.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
