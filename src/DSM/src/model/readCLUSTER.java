package model;

import java.io.*;
import java.util.Stack;

public class readCLUSTER {
	
	public String[][] readCluster(String filePath, int pivot)
	{
		FileInputStream in = null;
		String cluster[][] = new String [2][pivot];
		try
		 {
			BufferedReader br = null;
			InputStreamReader isr = null;
			FileInputStream fis = null;
			File file = new File("after_partitioning_simple.clsx");
			fis = new FileInputStream(file);
			isr =  new InputStreamReader(fis, "UTF-8"); 
			br = new BufferedReader(isr);
				
			
			String temp="";
			int index=0;
			Stack s = new Stack();
			
			while(((temp = br.readLine()) != null)) 
			{
				String[] str= temp.split("\"",3);
				if(str[0].compareTo("<group name=")==0)
				{
					cluster[1][index] = str[1];
					s.push(str[1]);
				}
				else if(str[0].compareTo("<item name=")==0)
				{
					cluster[0][index] = str[1];
					cluster[1][index] = (String)s.peek();
					index++;
				}
				else if(str[0].compareTo("</group>")==0)
				{
					s.pop();
				}
			}
			
		 }
		 catch(FileNotFoundException fnfe){
			 System.out.println("파일을 찾을 수 없습니다.");
			 }catch(IOException ioe){}
		 finally
		 {
			 try{
				 in.close();
				 }
			 catch(Exception e){}
		 }
		 return cluster;
	}
}
