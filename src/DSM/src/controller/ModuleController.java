package controller;

import model.*;
import view.*;
import java.awt.event.*;

public class ModuleController  implements ActionListener{

	private static ModuleController instance;
	
	private ModuleController(){}
	
	public static ModuleController getInstance(){
		
		if(instance==null){
			instance=new ModuleController();
		}
		return instance;
	}
	
	private void eventUp()
	{
		Main_view.getInstance().getBtnCollapsAll().addActionListener(this);
		Main_view.getInstance().getBtnDelete().addActionListener(this);
		Main_view.getInstance().getBtnExpandAll().addActionListener(this);
		Main_view.getInstance().getBtnGroup().addActionListener(this);
		Main_view.getInstance().getBtnLoadClustering().addActionListener(this);
		Main_view.getInstance().getBtnMoveDown().addActionListener(this);
		Main_view.getInstance().getBtnMoveUp().addActionListener(this);
		Main_view.getInstance().getBtnNewClustering().addActionListener(this);
		Main_view.getInstance().getBtnOpenDsm().addActionListener(this);
		Main_view.getInstance().getBtnRedraw().addActionListener(this);
		Main_view.getInstance().getBtnSaveClustering().addActionListener(this);
		Main_view.getInstance().getBtnSaveClusteringAs().addActionListener(this);
		Main_view.getInstance().getBtnUngroup().addActionListener(this);
		//
	}
	
	@Override
 	public void actionPerformed(ActionEvent e)
	{
 		
 		/*Main_view.getInstance().getBtnOpenDsm().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Main_view.getInstance().setFilepath();
				System.out.print(Main_view.getInstance().getFilePath());
			}
		});*/
 				
		if(e.getSource()==Main_view.getInstance().getBtnCollapsAll())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnExpandAll())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnDelete())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnGroup())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnLoadClustering())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnMoveDown())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnMoveUp())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnNewClustering())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnOpenDsm())
		{
			String newFilePath = new String();
			newFilePath = Main_view.getInstance().setFilepath();
			System.out.print(Main_view.getInstance().getFilePath()+"\n");
			DsmModel.getInstance().readDsm(newFilePath);
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnRedraw())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnSaveClustering())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnSaveClusteringAs())
		{
			
		}
		if(e.getSource()==Main_view.getInstance().getBtnUngroup())
		{
			
		}
	}
	
	public static void main(String[] args)
	{
		ModuleController.getInstance().eventUp();
	}
	
}
