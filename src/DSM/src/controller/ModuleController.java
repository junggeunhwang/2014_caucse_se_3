package controller;

import model.*;
import view.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class ModuleController  implements ActionListener{

	private String backUPfilePath;
	private String[] currentClusterInfo;
	
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
		if(e.getSource()==Main_view.getInstance().getBtnCollapsAll())
		{
			Main_view.getInstance().setTreeView(1);
		}
		if(e.getSource()==Main_view.getInstance().getBtnExpandAll())
		{
			Main_view.getInstance().setTreeView(0);
		}
		if(e.getSource()==Main_view.getInstance().getBtnDelete())
		{
			TreeAction.getInstance().removeAction();
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnGroup())
		{
			TreeAction.getInstance().groupAction();
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnUngroup())
		{
			TreeAction.getInstance().removeAction();
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnLoadClustering())
		{
			String newFilePath = new String();
			newFilePath = Main_view.getInstance().setFilepath_load();
			ClusterModel.getInstance().readCluster(newFilePath, ModelInfo.getInstance().getModules().size());
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnMoveDown()) //horizontal split
		{
			TreeAction.getInstance().moveAction(1);
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnMoveUp()) //horizontal split
		{
			TreeAction.getInstance().moveAction(-1);
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnNewClustering())
		{
			ModelInfo.getInstance().setInstance(null);
			DsmModel.getInstance().readDsm(backUPfilePath);
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnOpenDsm())
		{
			ModelInfo.getInstance().setInstance(null);
			String newFilePath = new String();
			newFilePath = Main_view.getInstance().setFilepath_load();
			this.currentClusterInfo = new String[2];
			this.currentClusterInfo = null;
			this.backUPfilePath = new String();
			this.backUPfilePath = newFilePath;
			DsmModel.getInstance().readDsm(newFilePath);
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnRedraw())
		{
			Main_view.getInstance().setTable();
		}
		if(e.getSource()==Main_view.getInstance().getBtnSaveClustering())
		{
			if(this.currentClusterInfo==null)
			{
				String[] newFileInfo = new String[2];
				newFileInfo = Main_view.getInstance().setFilepath_save();
				this.currentClusterInfo = newFileInfo;
				ClusterModel.getInstance().writeCluster(newFileInfo[0], newFileInfo[1]);
			}
			else
				ClusterModel.getInstance().writeCluster(this.currentClusterInfo[0], this.currentClusterInfo[1]);
		}
		if(e.getSource()==Main_view.getInstance().getBtnSaveClusteringAs())
		{
			String[] newFileInfo = new String[2];
			newFileInfo = Main_view.getInstance().setFilepath_save();
			this.currentClusterInfo = newFileInfo;
			ClusterModel.getInstance().writeCluster(newFileInfo[0], newFileInfo[1]);
		}
	}
	
	public static void main(String[] args)
	{
		ModuleController.getInstance().eventUp();
	}
	
}