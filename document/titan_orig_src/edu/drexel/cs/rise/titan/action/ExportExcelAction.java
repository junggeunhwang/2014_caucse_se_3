package edu.drexel.cs.rise.titan.action;

import edu.drexel.cs.rise.civitas.spreadsheet.Excel;
import edu.drexel.cs.rise.civitas.spreadsheet.Excel.Type;
import edu.drexel.cs.rise.tellus.cluster.Clustering;
import edu.drexel.cs.rise.titan.Project;
import edu.drexel.cs.rise.titan.util.IconFactory;
import edu.drexel.cs.rise.util.Digraph;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExportExcelAction extends FileAction
{
  private static final long serialVersionUID = 10L;

  public ExportExcelAction(Component paramComponent)
  {
    super(paramComponent);
    initialize();
  }

  private void initialize()
  {
    putValue("Name", "Excel...");
    putValue("MnemonicKey", Integer.valueOf(120));
    putValue("ShortDescription", "Export Excel");
    putValue("SmallIcon", IconFactory.load("export.png"));
    FileNameExtensionFilter localFileNameExtensionFilter1 = new FileNameExtensionFilter("Microsoft Excel 97-2003 Workbook (*.xls)", new String[] { "." + Excel.Type.XLS.toString() });
    FileNameExtensionFilter localFileNameExtensionFilter2 = new FileNameExtensionFilter("Microsoft Excel 2007 Workbook (*.xlsx)", new String[] { "." + Excel.Type.XLSX.toString() });
    this.chooser.addChoosableFileFilter(localFileNameExtensionFilter1);
    this.chooser.addChoosableFileFilter(localFileNameExtensionFilter2);
    this.chooser.setFileFilter(localFileNameExtensionFilter1);
    this.chooser.setDialogTitle("Export Excel File");
    this.chooser.setMultiSelectionEnabled(false);
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    prepare();
    if (showSaveDialog() != 1)
    {
      File localFile = getPathWithExtension(this.chooser.getSelectedFile(), ((FileNameExtensionFilter)this.chooser.getFileFilter()).getExtensions()[0]);
      if (!confirmOverwrite(localFile))
        return;
      export(localFile);
    }
  }

  private void export(File paramFile)
  {
    Project localProject = Project.getInstance();
    Clustering localClustering = localProject.getCluster();
    Digraph localDigraph = localProject.getDependency();
    Set localSet = localProject.getCollapsed();
    if (paramFile.getName().endsWith(Excel.Type.XLSX.toString()))
      Excel.saveAsXLSX(localDigraph, localClustering, localSet, paramFile.getPath());
    else
      Excel.saveAsXLS(localDigraph, localClustering, localSet, paramFile.getPath());
  }
}

/* Location:           C:\Users\정근\Desktop\titan.jar
 * Qualified Name:     edu.drexel.cs.rise.titan.action.ExportExcelAction
 * JD-Core Version:    0.6.2
 */