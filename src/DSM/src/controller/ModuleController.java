package controller;

import model.*;
import view.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class ModuleController  implements ActionListener{

	private String[] DsmfilePath;
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
		Main_view.getInstance().getMntmSaveDsm().addActionListener(this);
		Main_view.getInstance().getMntmSaveAsDsm().addActionListener(this);
		Main_view.getInstance().getMntmAbout().addActionListener(this);//
		Main_view.getInstance().getMntmExit().addActionListener(this);//
		Main_view.getInstance().getMntmShow_Row_Labels().addActionListener(this);//
		Main_view.getInstance().getMntmNewMenuItem().addActionListener(this);
		Main_view.getInstance().getMntmOpenDsm().addActionListener(this);//
		Main_view.getInstance().getMntmRedraw().addActionListener(this);//
		Main_view.getInstance().getMntmSaveClustering().addActionListener(this);//
		Main_view.getInstance().getMntmSaveClusteringAs().addActionListener(this);//
		Main_view.getInstance().getMntmLoadClustering().addActionListener(this);//
		Main_view.getInstance().getMntmNewDsm().addActionListener(this);
		Main_view.getInstance().getBtnRename().addActionListener(this);//
		Main_view.getInstance().getBtnNewDsmRow().addActionListener(this);
		DsmfilePath = new String[2];
		//
	}
	
	@Override
 	public void actionPerformed(ActionEvent e)
	{	
		if(e.getSource()==Main_view.getInstance().getBtnNewDsmRow())
		{
			Main_view.getInstance().setEnableButton(false);
			Main_view.getInstance().setEnableSave(true);
			TreeAction.getInstance().newDsmRow();
		}
		if(e.getSource()==Main_view.getInstance().getMntmNewDsm())
		{
			int rowcount = new NewDsmDialog(Main_view.getInstance(),true).getRow_count();//입력받은 줄 수
			Main_view.getInstance().setEnableSave(true);
			TreeAction.getInstance().newDsm(rowcount);
		}
		if(e.getSource()==Main_view.getInstance().getMntmSaveDsm())  
		{
			if(this.DsmfilePath[0]!=null)
				DsmModel.getInstance().saveDsm(DsmfilePath[0], DsmfilePath[1]);
			else
			{
				String[] newDsmFileInfo = new String[2];
				newDsmFileInfo = Main_view.getInstance().setFilepath_save();
				if(newDsmFileInfo[0]!=null)
				{
					String newDsmfilePath = new String();
					newDsmfilePath = newDsmFileInfo[0]+newDsmFileInfo[1];
					DsmModel.getInstance().saveDsm(newDsmFileInfo[0], newDsmFileInfo[1]);
				}
			}
		}
		if(e.getSource()==Main_view.getInstance().getMntmSaveAsDsm()) 
		{
			String[] newDsmFileInfo = new String[2];
			DsmfilePath = new String[2];
			newDsmFileInfo = Main_view.getInstance().setFilepath_save();
			if(newDsmFileInfo[0]!=null)
			{
				String newDsmfilePath = new String();
				newDsmfilePath = newDsmFileInfo[0]+newDsmFileInfo[1];
				DsmModel.getInstance().saveDsm(newDsmFileInfo[0], newDsmFileInfo[1]);
				DsmfilePath[0] = newDsmFileInfo[0];
				DsmfilePath[1] = newDsmFileInfo[1];
			}
			if(Main_view.getInstance().getDependency_table().isEditable())
			{
				Main_view.getInstance().getDependency_table().setEditable(false);
				Main_view.getInstance().setEnableButton(true);
				TreeAction.getInstance().setNewDepend(Main_view.getInstance().getDependency_table());
			}
		}
		if(e.getSource()==Main_view.getInstance().getBtnRename())
		{
			TreeAction.getInstance().reNameNode();
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		
		if(e.getSource()==Main_view.getInstance().getMntmExit())
		{
			System.exit(0);
		}
		if(e.getSource()==Main_view.getInstance().getMntmShow_Row_Labels())
		{
			if(Main_view.getInstance().getShowRowLabel()==1)
				Main_view.getInstance().setShowRowLabel(0);
			else
				Main_view.getInstance().setShowRowLabel(1);
		}
		
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
			TreeAction.getInstance().unGroupAction();
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnLoadClustering() || e.getSource()==Main_view.getInstance().getMntmLoadClustering())
		{
			String[] newFilePath = new String[2];
			newFilePath = Main_view.getInstance().setFilepath_load();
			
			if(newFilePath[0]!=null)
			{	
				if(newFilePath[1].contains(".clsx")==false)
				{
					Main_view.getInstance().showMsg("Please load clsx file.");
					return;
				}
				this.currentClusterInfo = newFilePath;
				ClusterModel.getInstance().readCluster(newFilePath[0]+newFilePath[1], ModelInfo.getInstance().getModules().size());
				Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
			}
			else
				return;
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
		if(e.getSource()==Main_view.getInstance().getBtnNewClustering() || e.getSource()==Main_view.getInstance().getMntmNewMenuItem())
		{
			ModelInfo.getInstance().setInstance(null);
			DsmModel.getInstance().readDsm(DsmfilePath[0]+DsmfilePath[1]);
			Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
		}
		if(e.getSource()==Main_view.getInstance().getBtnOpenDsm() || e.getSource()==Main_view.getInstance().getMntmOpenDsm())
		{
			ModelInfo.getInstance().setInstance(null);
			String[] newFilePath = new String[2];
			newFilePath = Main_view.getInstance().setFilepath_load();
			
			if(newFilePath[0]!=null)
			{
				if(newFilePath[1].contains(".dsm")==false)
				{
					Main_view.getInstance().showMsg("Please load DSM file.");
					return;
				}
				this.currentClusterInfo = new String[2];
				this.currentClusterInfo = null;
				this.DsmfilePath = new String[2];
				this.DsmfilePath[0] = newFilePath[0];
				this.DsmfilePath[1] = newFilePath[1];
				DsmModel.getInstance().readDsm(newFilePath[0]+newFilePath[1]);
				Main_view.getInstance().setJTree(TreeAction.getInstance().makeTree());
				Main_view.getInstance().setEnableButton(true);
			}
			else{
				return;
			}
		}
		if(e.getSource()==Main_view.getInstance().getBtnRedraw() || e.getSource()==Main_view.getInstance().getMntmRedraw())
		{
				Vector<String> printElement = new Vector<String>();
				printElement = TreeAction.getInstance().getTreeState();
				Main_view.getInstance().drawTable(printElement);
		}
		if(e.getSource()==Main_view.getInstance().getBtnSaveClustering() || e.getSource()==Main_view.getInstance().getMntmSaveClustering())
		{
			if(this.currentClusterInfo==null)
			{
				String[] newFileInfo = new String[2];
				newFileInfo = Main_view.getInstance().setFilepath_save();
				if(newFileInfo[0]!=null)
				{	
					this.currentClusterInfo = newFileInfo;
					ClusterModel.getInstance().writeCluster(newFileInfo[0], newFileInfo[1]);
				}
			}
			else
				ClusterModel.getInstance().writeCluster(this.currentClusterInfo[0], this.currentClusterInfo[1]);
		}
		if(e.getSource()==Main_view.getInstance().getBtnSaveClusteringAs() || e.getSource()==Main_view.getInstance().getMntmSaveClusteringAs())
		{
			String[] newFileInfo = new String[2];
			newFileInfo = Main_view.getInstance().setFilepath_save();
			if(newFileInfo[0]!=null)
			{
				this.currentClusterInfo = newFileInfo;
				ClusterModel.getInstance().writeCluster(newFileInfo[0], newFileInfo[1]);
			}
		}
	}
	
	public static void main(String[] args)
	{
		ModuleController.getInstance().eventUp();
	}
	
}
