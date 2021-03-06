package model;

import java.io.*;

import model.TreeNode;

import java.util.ArrayList;
import java.util.Vector;

public class DsmModel {
	
	private static DsmModel instance;
	
	
	private DsmModel(){}
	
	public static DsmModel getInstance(){
		
		if(instance==null){
			instance = new DsmModel();
		}
		return instance;
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
					ModelInfo.getInstance().getDependData().add(dependency[i]);
				
				for(int i=0; i<pivot; i++)
				{
					TreeNode newNode = new TreeNode();
					newNode.setKey(module[i]);
					ModelInfo.getInstance().getRoot().setChild(newNode);
				}
				
				ModelInfo.getInstance()
				.setModules(ModelInfo.getInstance().getRoot()
				.getLeafNode(ModelInfo.getInstance().getModules()));
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
			printStr = Integer.toString(ModelInfo.getInstance().getModules().size());
			print_writer.println(new String(printStr.getBytes("UTF-8"),"UTF-8"));
			Vector<String> printList = new Vector<String>();
			int dependSize = ModelInfo.getInstance().getDependData().size();
			for(int i=0; i<dependSize; i++)
			{
				for(int j=0; j<dependSize; j++)
				{
					int dependent = ModelInfo.getInstance().getDependData().get(i)[j];
					printStr = Integer.toString(dependent)+" ";
					print_writer.print(new String(printStr.getBytes("UTF-8"),"UTF-8"));
				}
				print_writer.println(new String("".getBytes("UTF-8"),"UTF-8"));
			}
			for(int i=0; i<ModelInfo.getInstance().getModules().size(); i++)
			{
				print_writer.println(new String(ModelInfo.getInstance().getModules().get(i).key.getBytes("UTF-8"),"UTF-8"));
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
