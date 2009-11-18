package com.amalto.workbench.actions;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.xsd.XSDElementDeclaration;

import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.utils.WorkbenchClipboard;

public class XSDCopyConceptAction extends Action {
	private DataModelMainPage page;

	public XSDCopyConceptAction(DataModelMainPage page) {
		super();
		this.page = page;
		setImageDescriptor(ImageCache.getImage(EImage.COPY.getPath()));
		setText("Copy Concept");
		setToolTipText("Copy Concept/Concepts");
	}

	//public IStatus doAction() {
	public void run() {
		try {
			WorkbenchClipboard.getWorkbenchClipboard().conceptsReset();
			IStructuredSelection selection = (IStructuredSelection)page.getTreeViewer().getSelection();
	 		for (Iterator<XSDElementDeclaration> iter = selection.iterator(); iter.hasNext(); ) {
				XSDElementDeclaration concept = iter.next();
				
				if (concept instanceof XSDElementDeclaration) 
					WorkbenchClipboard.getWorkbenchClipboard().add(concept);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(
					page.getSite().getShell(),
					"Error", 
					"An error occured trying to copy Concept: "+e.getLocalizedMessage()
			);

		}
		//return true;
	}
	public boolean checkInCopyType(Object[] selectedObjs){
		for (Object obj : selectedObjs) {
			if (obj instanceof XSDElementDeclaration)
				continue;
			else 
				return false;
		}

		return true;
	}
	
}