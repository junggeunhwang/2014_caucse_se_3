package model;

import java.util.ArrayList;
import java.util.Vector;

public class ModelInfo {
	
	static private ModelInfo instance;
	
	private ArrayList<int[]> dependData;
	private Vector<TreeNode> modules;
	private TreeNode root;
	
	ModelInfo(){
		dependData = new ArrayList<int[]>();
		modules = new Vector<TreeNode>();
		root = new TreeNode();
		root.setKey("$root");
	}
	
	static public ModelInfo getInstance(){
		
		if(instance==null){
			instance=new ModelInfo();
		}
		return instance;
	}

	public ArrayList<int[]> getDependData() {
		return dependData;
	}

	public Vector<TreeNode> getModules() {
		return modules;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setDependData(ArrayList<int[]> dependData) {
		this.dependData = dependData;
	}

	public void setModules(Vector<TreeNode> modules) {
		this.modules = modules;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
}
