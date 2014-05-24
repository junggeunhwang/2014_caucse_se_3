package controller;
import java.util.ArrayList;

import model.*;
import view.*;

import java.awt.*;

import javax.swing.*; 
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.event.*;

public class ModuleController  implements ActionListener{

	private ClusterModel Cmodel;
	private Main_view Mview;
	private JTree tree;
	
	public ModuleController( ClusterModel cmodel, Main_view mview)
	{
		this.Cmodel = cmodel;
		this.Mview = mview;
		eventUp();
	}
	private void eventUp()
	{
		Mview.getBtnCollapsAll().addActionListener(this);
		Mview.getBtnDelete().addActionListener(this);
		Mview.getBtnExpandAll().addActionListener(this);
		Mview.getBtnGroup().addActionListener(this);
		Mview.getBtnLoadClustering().addActionListener(this);
		Mview.getBtnMoveDown().addActionListener(this);
		Mview.getBtnMoveUp().addActionListener(this);
		Mview.getBtnNewClustering().addActionListener(this);
		Mview.getBtnOpenDsm().addActionListener(this);
		Mview.getBtnRedraw().addActionListener(this);
		Mview.getBtnSaveClustering().addActionListener(this);
		Mview.getBtnSaveClusteringAs().addActionListener(this);
		Mview.getBtnUngroup().addActionListener(this);
		//Mview();
	}
	
	@Override
 	public void actionPerformed(ActionEvent e)
	{
 		
 		/*Mview.getBtnOpenDsm().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Mview.setFilepath();
				System.out.print(Mview.getFilePath());
			}
		});*/
 		
		if(e.getSource()==Mview.getBtnCollapsAll())
		{
			
		}
		if(e.getSource()==Mview.getBtnExpandAll())
		{
			
		}
		if(e.getSource()==Mview.getBtnDelete())
		{
			
		}
		if(e.getSource()==Mview.getBtnGroup())
		{
			
		}
		if(e.getSource()==Mview.getBtnLoadClustering())
		{
			
		}
		if(e.getSource()==Mview.getBtnMoveDown())
		{
			
		}
		if(e.getSource()==Mview.getBtnMoveUp())
		{
			
		}
		if(e.getSource()==Mview.getBtnNewClustering())
		{
			
		}
		if(e.getSource()==Mview.getBtnOpenDsm())
		{
			String newFilePath = new String();
			newFilePath = Mview.setFilepath();
			System.out.print(Mview.getFilePath()+"\n");
			Cmodel.readDsm(newFilePath);
			TreeAction ta = new TreeAction();
			ta.treeSetting(Cmodel);
			Mview.setJTree(ta.jtree);
			//DefaultMutableTreeNode treeModel = new DefaultMutableTreeNode(ta.root.getUserObject());
			//Mview.getClasstree().setModel(treeModel);
			//Mview.getfrmTitan().setVisible(true);
		//	Mview.getClasstree().get
			//eventUp();
			//Mview.setFilepath();
			//
		}
		if(e.getSource()==Mview.getBtnRedraw())
		{
			
		}
		if(e.getSource()==Mview.getBtnSaveClustering())
		{
			
		}
		if(e.getSource()==Mview.getBtnSaveClusteringAs())
		{
			
		}
		if(e.getSource()==Mview.getBtnUngroup())
		{
			
		}
	}
	
	public static void main(String[] args)
	{
		//DsmModel d = new DsmModel();
		ClusterModel c = new ClusterModel();
		Main_view v = new Main_view();
		new ModuleController(c,v);
	}
	
}
