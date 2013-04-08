// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.editors.xsdeditor;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyTitle;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xsd.ui.internal.adapters.CategoryAdapter;
import org.eclipse.wst.xsd.ui.internal.adapters.RedefineCategoryAdapter;
import org.eclipse.wst.xsd.ui.internal.adapters.XSDBaseAdapter;
import org.eclipse.wst.xsd.ui.internal.editor.InternalXSDMultiPageEditor;
import org.eclipse.wst.xsd.ui.internal.editor.XSDTabbedPropertySheetPage;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDDiagnostic;
import org.eclipse.xsd.XSDDiagnosticSeverity;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDIdentityConstraintDefinition;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDXPathDefinition;
import org.talend.mdm.commmon.util.core.CommonUtil;
import org.w3c.dom.Node;
import org.xml.sax.SAXParseException;

import com.amalto.workbench.dialogs.ErrorExceptionDialog;
import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.editors.IServerObjectEditorState;
import com.amalto.workbench.i18n.Messages;
import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.views.MDMPerspective;
import com.amalto.workbench.webservices.WSDataModel;

@SuppressWarnings("restriction")
public class XSDEditor extends InternalXSDMultiPageEditor implements IServerObjectEditorState {

    private static Log log = LogFactory.getLog(XSDEditor.class);

    public static final String CONTRUIBUTIONID_DATAMODELPAGE = "org.talend.mdm.workbench.propertyContributor.datamodel";//$NON-NLS-1$

    protected int preActivePageIndex = -1;

    IEditorInput xsdInput;

    protected TreeObject xobject;

    private XSDSelectionManagerSelectionListener fXSDSelectionListener;

    private byte[] fileContents = null;

    public void setXSDInput(IEditorInput input) {
        this.xsdInput = input;
    }

    public void setXObject(TreeObject xobject) {
        this.xobject = xobject;
        InputStreamReader reader = null;
        try {// temporarily store the file data for restore
            IFile file = getXSDFile(xobject);
            reader = new InputStreamReader(file.getContents());
            fileContents = IOUtils.toByteArray(reader, "utf-8"); //$NON-NLS-1$
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }
        }
    }

    @Override
    public String getPartName() {
        String part = super.getPartName();
        if (part.endsWith(".xsd")) {//$NON-NLS-1$
            return part.substring(0, part.length() - 4);
        } else {
            int start = part.lastIndexOf('_');
            int end = part.lastIndexOf(' ');
            if (start != -1 && end != -1) {
                return part.substring(0, start) + part.substring(end);
            }
        }
        return part;
    }

    protected void superDoSave(IProgressMonitor monitor) {
        super.doSave(monitor);
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        InputStreamReader reader = null;
        try {
            IFile file = getXSDFile(xobject);
            if (getSelectedPage() instanceof DataModelMainPage) {// save DataModelMainPage's contents to file
                DataModelMainPage mainPage = (DataModelMainPage) getSelectedPage();
                String xsd = mainPage.getXSDSchemaString();
                WSDataModel wsDataModel = (WSDataModel) xobject.getWsObject();
                wsDataModel.setXsdSchema(xsd);
                //
                file.setCharset("utf-8", null);//$NON-NLS-1$
                ByteArrayInputStream inputStream = new ByteArrayInputStream(xsd.getBytes("utf-8"));//$NON-NLS-1$
                file.setContents(inputStream, IFile.FORCE, null);

                if (mainPage != null) {
                    mainPage.save(xsd);
                }
                fileContents = xsd.getBytes("utf-8"); //$NON-NLS-1$

            }

            getDataModelEditorPage().setDirty(false);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            super.doSave(monitor);
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }
            monitor.done();
        }
    }

    protected IFile getXSDFile(TreeObject object) throws Exception {
        return XSDEditorUtil.createFile(object);
    }

    boolean doUpdateSourceLocation = false;

    @Override
    protected void pageChange(int newPageIndex) {
        resetTreeSelection();

        super.pageChange(newPageIndex);
        doUpdateSourceLocation = newPageIndex == SOURCE_PAGE_INDEX;
        if (doUpdateSourceLocation && fXSDSelectionListener != null) {
            fXSDSelectionListener.doSetSelection();
        }
        doPageChanged();
        refreshPropertyView();
        setFocus();
    }

    private void doPageChanged() {
        if (xobject == null) {
            return;
        }
        try {
            if (getSelectedPage() instanceof DataModelMainPage) {// save the file's contents to
                DataModelMainPage mainPage = getDataModelEditorPage();
                if (mainPage != null) {
                    getEditorSite().setSelectionProvider(mainPage.getSelectionProvider());

                    String xsd = getTextEditor().getTextViewer().getDocument().get();
                    XSDSchema schema = Util.createXsdSchema(xsd, xobject);
                    mainPage.setXsdSchema(schema);
                    mainPage.getTypeContentProvider().setXsdSchema(schema);
                    mainPage.getSchemaContentProvider().setXsdSchema(schema);
                    mainPage.refresh();
                }
            } else {
                // save DataModelMainPage's contents to file
                getEditorSite().setSelectionProvider(getSelectionManager());
                DataModelMainPage mainPage = getDataModelEditorPage();
                if (mainPage != null && mainPage.isDirty()) {
                    String xsd = mainPage.getXSDSchemaString();
                    xsd = Util.formatXsdSource(xsd);
                    WSDataModel wsDataModel = (WSDataModel) xobject.getWsObject();
                    wsDataModel.setXsdSchema(xsd);
                    IFile file = getXSDFile(xobject);
                    file.setCharset("utf-8", null);//$NON-NLS-1$
                    file.setContents(new ByteArrayInputStream(xsd.getBytes("utf-8")), IFile.FORCE, null);//$NON-NLS-1$

                    initializeGraphicalViewer();
                }
            }
        } catch (SAXParseException ex) {
            // do nothing
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private DataModelMainPage getDataModelEditorPage() {
        IEditorPart[] editors = findEditors(xsdInput);
        if (editors.length == 1 && editors[0] instanceof DataModelMainPage) {
            return (DataModelMainPage) editors[0];
        }
        return null;
    }

    private class XSDSelectionManagerSelectionListener implements ISelectionChangedListener {

        /**
         * Determines DOM node based on object (xsd node)
         * 
         * @param object
         * @return
         */
        private Object getObjectForOtherModel(Object object) {
            Node node = null;
            if (object instanceof Node) {
                node = (Node) object;
            } else if (object instanceof XSDComponent) {
                if (object instanceof XSDElementDeclaration) {
                    String name = ((XSDElementDeclaration) object).getName();
                    for (XSDElementDeclaration elem : getXSDSchema().getElementDeclarations()) {
                        if (elem.getName().equals(name)) {
                            node = elem.getElement();
                        }
                    }
                } else {
                    node = ((XSDComponent) object).getElement();
                }
            } else if (object instanceof RedefineCategoryAdapter) {
                RedefineCategoryAdapter category = (RedefineCategoryAdapter) object;
                node = category.getXSDRedefine().getElement();
            } else if (object instanceof CategoryAdapter) {
                node = ((CategoryAdapter) object).getXSDSchema().getElement();
            } else if (object instanceof XSDBaseAdapter) {
                if (((XSDBaseAdapter) object).getTarget() instanceof XSDConcreteComponent) {
                    node = ((XSDConcreteComponent) ((XSDBaseAdapter) object).getTarget()).getElement();
                }
            } else if (object instanceof String) {
                // This case was added to make the F3/hyperlink navigation work when an
                // inline schema from a WSDL document is opened in the schema editor.
                // The string is expected to be a URI fragment used to identify an XSD
                // component in the context of the enclosing WSDL resource.

                String uriFragment = (String) object;
                Resource resource = getXSDSchema().eResource();
                EObject modelObject = resource.getEObject(uriFragment);

                if (modelObject != null && modelObject instanceof XSDComponent) {
                    XSDComponent component = (XSDComponent) modelObject;
                    node = component.getElement();
                }
            }

            // the text editor can only accept sed nodes!
            //
            if (!(node instanceof IDOMNode)) {
                node = null;
            }
            return node;
        }

        public void selectionChanged(SelectionChangedEvent event) {
            // do not fire selection in source editor if the current active page is the InternalXSDMultiPageEditor
            // (source)
            // We only want to make source selections if the active page is either the outline or properties (a modify
            // has been done via the outline or properties and not the source view). We don't want to be selecting
            // and unselecting things in the source when editing in the source!!
            boolean makeSelection = true;
            if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                if (page.getActivePart() instanceof InternalXSDMultiPageEditor) {
                    if (getActiveEditor() instanceof StructuredTextEditor) {
                        makeSelection = false;
                    }
                }
            }

            // do not fire selection in source editor if selection event came
            // from source editor
            if (event.getSource() != getTextEditor().getSelectionProvider() && makeSelection) {
                ISelection selection = event.getSelection();
                if (selection instanceof IStructuredSelection) {
                    List otherModelObjectList = new ArrayList();
                    for (Iterator i = ((IStructuredSelection) selection).iterator(); i.hasNext();) {
                        Object modelObject = i.next();
                        Object otherModelObject = getObjectForOtherModel(modelObject);
                        if (otherModelObject != null) {
                            otherModelObjectList.add(otherModelObject);
                        }
                    }
                    if (!otherModelObjectList.isEmpty()) {
                        if (getActivePage() == SOURCE_PAGE_INDEX) {
                            StructuredSelection nodeSelection = new StructuredSelection(otherModelObjectList);
                            getTextEditor().getSelectionProvider().setSelection(nodeSelection);
                        }
                    }
                }
            }
        }

        public void doSetSelection() {
            ISelection iSelection = getSelectionManager().getSelection();
            if (iSelection != null) {
                Object firstElement = ((StructuredSelection) iSelection).getFirstElement();
                Object otherModelObject = getObjectForOtherModel(firstElement);
                if (otherModelObject != null) {
                    StructuredSelection nodeSelection = new StructuredSelection(otherModelObject);
                    getTextEditor().getSelectionProvider().setSelection(nodeSelection);
                }
            }
        }
    }

    @Override
    protected void createPages() {
        super.createPages();
        fXSDSelectionListener = new XSDSelectionManagerSelectionListener();
        getSelectionManager().addSelectionChangedListener(fXSDSelectionListener);

        ((CTabFolder) getContainer()).addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (preActivePageIndex == SOURCE_PAGE_INDEX) {
                    Exception exc = validateXsdSourceEditor();
                    if (exc != null) {
                        setActivePage(preActivePageIndex);
                        ErrorExceptionDialog.openError(getSite().getShell(), Messages.XSDEditor_ChangedPageErrorTitle,
                                CommonUtil.getErrMsgFromException(exc));
                        return;
                    }
                }
                preActivePageIndex = getActivePage();
            }
        });
    }

    @Override
    protected void createSourcePage() {
        this.structuredTextEditor = new StructuredTextEditor() {

            @Override
            public boolean isEditable() {
                if (isReadOnly()) {
                    return false;
                }
                return super.isEditable();
            }
        };
        try {
            int index = addPage(this.structuredTextEditor, getEditorInput());
            setPageText(index, org.eclipse.wst.xsd.ui.internal.adt.editor.Messages._UI_LABEL_SOURCE);
            this.structuredTextEditor.update();
            this.structuredTextEditor.setEditorPart(this);
            this.structuredTextEditor.addPropertyListener(this);
            firePropertyChange(1);
        } catch (PartInitException e) {
            ErrorDialog.openError(getSite().getShell(), Messages.XSDEditor_ErrorMessage, null, e.getStatus());
        }
    }

    private Exception validateXsdSourceEditor() {
        String xsd = getTextEditor().getTextViewer().getDocument().get();
        try {
            XSDSchema xsdSchema = Util.createXsdSchema(xsd, xobject);
            String error = validateDiagnoses(xsdSchema);
            if (!"".equals(error)) { //$NON-NLS-1$
                return new IllegalAccessException(error);
            }
            return null;
        } catch (SAXParseException e) {
            return e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return e;
        }
    }

    /**
     * DOC talend-mdm Comment method "validateDiagnoses".
     * 
     * @param xsdSchema
     * @return
     */
    private String validateDiagnoses(XSDSchema xsdSchema) {
        String msg_omit[] = { Messages.XsdOmit1, Messages.XsdOmit2, Messages.XsdOmit3, Messages.XsdOmit4, Messages.XsdOmit5 };
        xsdSchema.clearDiagnostics();
        xsdSchema.getAllDiagnostics().clear();
        xsdSchema.validate();
        EList<XSDDiagnostic> diagnoses = xsdSchema.getAllDiagnostics();
        String error = "";//$NON-NLS-1$
        Set<String> errors = new HashSet<String>();
        for (int i = 0; i < diagnoses.size(); i++) {
            XSDDiagnostic dia = diagnoses.get(i);
            XSDDiagnosticSeverity servity = dia.getSeverity();
            if (servity == XSDDiagnosticSeverity.ERROR_LITERAL || servity == XSDDiagnosticSeverity.FATAL_LITERAL) {
                boolean omit = false;
                for (String msg : msg_omit) {
                    if (dia.getMessage().indexOf(msg) != -1) {
                        omit = true;
                        break;
                    }
                }
                if (!omit && !errors.contains(dia.getMessage())) {
                    error += dia.getMessage() + "\n";//$NON-NLS-1$
                    errors.add(dia.getMessage());
                }
            }
        }
        return error;
    }

    @Override
    public String getContributorId() {
        if (getActiveEditor() instanceof DataModelMainPage) {
            return CONTRUIBUTIONID_DATAMODELPAGE;
        }
        return super.getContributorId();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object getAdapter(Class type) {
        if (type == IPropertySheetPage.class) {
            int activePageIndex = getActivePage();
            if (activePageIndex == DESIGN_PAGE_INDEX || activePageIndex == SOURCE_PAGE_INDEX) {
                return new XSDTabbedPropertySheetPage(this);
            }
            return new TabbedPropertySheetPage(this) {

                @Override
                protected void refreshTitleBar() {
                    TabbedPropertyTitle title = ((TabbedPropertyComposite) getControl()).getTitle();
                    if (getCurrentTab() == null) {
                        title.setTitle(null, null);
                        return;
                    }
                    String text = registry.getLabelProvider().getText(currentSelection);
                    Image image = registry.getLabelProvider().getImage(currentSelection);
                    String label = isReadOnly() ? NLS.bind(Messages.XSDEditor_SheetPageTitle, text) : text;
                    title.setTitle(label, image);
                }
            };
        }

        if (type == DataModelMainPage.class) {
            for (int i = 0; i < getPageCount(); i++) {
                if (getEditor(i) instanceof DataModelMainPage) {
                    return getEditor(i);
                }
            }
        }
        if (Boolean.class == type) {
            return new Boolean(isReadOnly());
        }
        // Add this for enable false the properties view while this editor is readOnly.
        if (XSDSchema.class == type && isReadOnly()) {
            return null;
        }
        return super.getAdapter(type);

    }

    private void refreshPropertyView() {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IViewPart propView = page.findView(MDMPerspective.VIEWID_PROPERTYVIEW);
        if (propView != null) {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(propView);
        }
        try {
            page.showView(MDMPerspective.VIEWID_PROPERTYVIEW);
        } catch (PartInitException e) {
            e.printStackTrace();
        }
    }

    private void resetTreeSelection() {
        DataModelMainPage dataModelEditorPage = getDataModelEditorPage();
        if (dataModelEditorPage != null) {
            TreeViewer treeViewer = dataModelEditorPage.getTreeViewer();
            IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
            if (!selection.isEmpty()) {
                Object firstElement = selection.getFirstElement();
                if ((firstElement instanceof XSDIdentityConstraintDefinition)) {
                    XSDIdentityConstraintDefinition cdf = (XSDIdentityConstraintDefinition) firstElement;
                    XSDConcreteComponent container = cdf.getContainer();
                    treeViewer.setSelection(new StructuredSelection(container));
                } else if ((firstElement instanceof XSDXPathDefinition)) {
                    XSDXPathDefinition pathdef = (XSDXPathDefinition) firstElement;
                    XSDConcreteComponent container = pathdef.getContainer().getContainer();
                    treeViewer.setSelection(new StructuredSelection(container));
                }
            }
        }
    }

    @Override
    public void setFocus() {
        Object selectedPage = getSelectedPage();
        if (selectedPage instanceof IEditorPart) {
            ((IEditorPart) selectedPage).setFocus();
        } else {
            super.setFocus();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.amalto.workbench.editors.IServerObjectEditorState#isLocalInput()
     */
    public boolean isLocalInput() {
        return false;
    }

    public void setName(String name) {
        setPartName(name);
    }

    @Override
    public void dispose() {
        try {
            if (isDirty()) {
                IFile file = getXSDFile(xobject);
                file.setCharset("utf-8", null);//$NON-NLS-1$
                file.setContents(new ByteArrayInputStream(fileContents), IFile.FORCE, null);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {

                IEditorInput input = getEditorInput();
                if (input != null && input instanceof XSDEditorInput) {
                    XSDEditorInput editorInput = (XSDEditorInput) input;
                    editorInput.dispose();
                }
                System.gc();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }

        super.dispose();
    }

    public DataModelMainPage getdMainPage() {
        return null;
    }

}
