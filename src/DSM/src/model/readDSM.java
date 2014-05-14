package model;

import java.io.*;
import java.util.Vector;

public class readDSM {
	
	public void readDsm(String filePath)
	{
		 FileInputStream in = null;
			try
			{
				BufferedReader br = null;
				InputStreamReader isr = null;
				FileInputStream fis = null;
				File file = new File(filePath);
				fis = new FileInputStream(file);
				isr =  new InputStreamReader(fis, "UTF-8"); 
				br = new BufferedReader(isr);
				
				String temp = "";
				temp = br.readLine();
				int pivot = 0;
				pivot = Integer.parseInt(temp);
				int dependency[][] = new int [pivot+1][pivot];
				char module[] = new char [pivot];
				
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
						 module[index-pivot] = temp.charAt(0);
					 }
					    index++;
				}
			}catch(FileNotFoundException fnfe)
			{
				System.out.println("파일을 찾을 수 없습니다.");
			}catch(IOException ioe){}
			
			finally
			{
				try
				{
					in.close();
				}catch(Exception e){}
				
			}
	}

}
