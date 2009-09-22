/**
 * 
 */
package com.easylink.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.container.Container;
import org.ofbiz.base.container.ContainerException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilURL;
import org.ofbiz.base.util.template.FreeMarkerWorker;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelReader;

import freemarker.template.TemplateException;

/**
 * @author pwang
 * 
 */
public class Entity2CodeContainer implements Container {

	public static final String module = Entity2CodeContainer.class.getName();
	public static final String source = "/hot-deploy/commonapp/templates/";
	private static final int TT_SERCIVES = 0;
	private static final int TT_ACTIONS = 1;
	private static final int TT_FTLHTML = 2;
	private static final int TT_CONTROLER = 3;
	private static final int TT_SCREENS = 4;

	protected Properties props = null;
	protected String ofbizHome = null;
	protected String args[] = null;

	/**
	 * 
	 */
	public Entity2CodeContainer() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ofbiz.base.container.Container#init(java.lang.String[],
	 * java.lang.String)
	 */
	public void init(String[] args, String configFile)
			throws ContainerException {
		ofbizHome = System.getProperty("ofbiz.home")+"/hot-deploy/";
		props=new Properties();
		try {
			props.load(new FileInputStream(configFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.args = args;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ofbiz.base.container.Container#start()
	 */
	public boolean start() throws ContainerException {

		String packageFilter=null;
		
		if(args!=null && args.length>1){
			packageFilter=args[1];
		}
		List<ModelEntity> entities=readEntity(packageFilter);
		if(entities!=null && entities.isEmpty()==false){
			
			generateServices(entities);
			
			generateActions(entities);
			
			generateUILabel(entities);
			
			generateControler(entities);
			
			generateScreens(entities);
			
			generateHtml(entities);
			
			
		}
		System.exit(1);
		return true;

	}

	
	private void generateActions(List<ModelEntity> entities) throws ContainerException {
		File preCreate=getActionsTemplate("create");
		File preDetail=getActionsTemplate("detail");
		File preList=getActionsTemplate("list");
		File preUpdate=getActionsTemplate("update");
		for(ModelEntity ent : entities){
			Map<String,Object> data=createEntityModelData(ent);
			generateCode(preCreate, data, TT_ACTIONS);
			generateCode(preDetail, data, TT_ACTIONS);
			generateCode(preList, data, TT_ACTIONS);
			generateCode(preUpdate, data, TT_ACTIONS);
		}
	}

	private void generateHtml(List<ModelEntity> entities) throws ContainerException {

		for(ModelEntity entity : entities){
			generateCreate(entity);
			generateList(entity);
			generateDetail(entity);
			generateUpdate(entity);
		}
			
	}

	private void generateUpdate(ModelEntity entity) throws ContainerException {
		File template=getHTMLTemplate("update");
		Map<String,Object> data=createEntityModelData(entity);
		
		Map<String,Object> component=FastMap.newInstance();
		data.put("component", component);
		component.put("name",getComponentName());

		Map<String,Object> resource=FastMap.newInstance();
		component.put("resource", resource);
		resource.put("name",getResourceName());
		
		generateCode(template, data,TT_FTLHTML); 

	}

	private void generateDetail(ModelEntity entity) throws ContainerException {
		File template=getHTMLTemplate("detail");
		Map<String,Object> data=createEntityModelData(entity);
		
		Map<String,Object> component=FastMap.newInstance();
		data.put("component", component);
		component.put("name",getComponentName());

		Map<String,Object> resource=FastMap.newInstance();
		component.put("resource", resource);
		resource.put("name",getResourceName());
		
		generateCode(template, data,TT_FTLHTML); 
		
	}

	private void generateList(ModelEntity entity) throws ContainerException {
		File template=getHTMLTemplate("list");
		Map<String,Object> data=createEntityModelData(entity);
		
		Map<String,Object> component=FastMap.newInstance();
		data.put("component", component);
		component.put("name",getComponentName());

		Map<String,Object> resource=FastMap.newInstance();
		component.put("resource", resource);
		resource.put("name",getResourceName());
		
		generateCode(template, data,TT_FTLHTML); 
		
	}

	private void generateCreate(ModelEntity entity) throws ContainerException {
		File template=getHTMLTemplate("create");
		Map<String,Object> data=createEntityModelData(entity);
		
		Map<String,Object> component=FastMap.newInstance();
		data.put("component", component);
		component.put("name",getComponentName());

		Map<String,Object> resource=FastMap.newInstance();
		component.put("resource", resource);
		resource.put("name",getResourceName());
		
		generateCode(template, data,TT_FTLHTML); 
	}

	private void generateServices(List<ModelEntity> entities) throws ContainerException {
		File template=getServiceTemplate();
		Map<String,Object> data=getEntityNames(entities);
		
		Map<String,Object> component=FastMap.newInstance();
		data.put("component", component);
		component.put("name",getComponentName());

		Map<String,Object> resource=FastMap.newInstance();
		component.put("resource", resource);
		resource.put("name",getResourceName());
		
		generateCode(template, data,TT_SERCIVES); 
	}

	private Map<String, Object> getEntityNames(List<ModelEntity> entities) {
		Map<String, Object> data=FastMap.newInstance();
		List<String> names=new ArrayList<String>();
		for(ModelEntity ent : entities){
			names.add(ent.getEntityName());
		}
		data.put("entityNames", names);
		return data;
	}

	private File getServiceTemplate() {
		String fileName=ofbizHome;
		fileName+=getComponentName()+"/";
		fileName+=props.getProperty("com.easylink.code.template.root", "templates")+"/code/";
		fileName+=props.getProperty("com.easylink.code.template.services", "services.xml.ftl");
		
		return new File(fileName);
		
	}
	private File getControllerTemplate() {
		String fileName=ofbizHome;
		fileName+=getComponentName()+"/";
		fileName+=props.getProperty("com.easylink.code.template.root", "templates")+"/code/";
		fileName+=props.getProperty("com.easylink.code.template.controller", "controller.xml.ftl");
		
		return new File(fileName);
		
	}
	private File getScreensTemplate() {
		String fileName=ofbizHome;
		fileName+=getComponentName()+"/";
		fileName+=props.getProperty("com.easylink.code.template.root", "templates")+"/code/";
		fileName+=props.getProperty("com.easylink.code.template.screens", "Screens.xml.ftl");
		
		return new File(fileName);
		
	}
	private File getActionsTemplate(String action) {
		String fileName=ofbizHome;
		fileName+=getComponentName()+"/";
		fileName+=props.getProperty("com.easylink.code.template.root", "templates")+"/code/";
		fileName+=props.getProperty("com.easylink.code.template.actions."+action, "Pre"+action+".groovy.ftl");
		
		return new File(fileName);
		
	}
	private File getHTMLTemplate(String action) {
		String fileName=ofbizHome;
		fileName+=getComponentName()+"/";
		fileName+=props.getProperty("com.easylink.code.template.root", "templates")+"/code/";
		fileName+=props.getProperty("com.easylink.code.template.view."+action, action+".ftl");
		
		return new File(fileName);
		
	}

	private void generateScreens(List<ModelEntity> entities) throws ContainerException {
		File template=getScreensTemplate();
		Map<String,Object> data=getEntityNames(entities);
		
		Map<String,Object> component=FastMap.newInstance();
		data.put("component", component);
		component.put("name",getComponentName());

		Map<String,Object> resource=FastMap.newInstance();
		component.put("resource", resource);
		resource.put("name",getResourceName());
		
		generateCode(template, data,TT_SCREENS); 
	}

	private void generateControler(List<ModelEntity> entities) throws ContainerException {
		File template=getControllerTemplate();
		Map<String,Object> data=getEntityNames(entities);
		
		Map<String,Object> component=FastMap.newInstance();
		data.put("component", component);
		component.put("name",getComponentName());

		Map<String,Object> resource=FastMap.newInstance();
		component.put("resource", resource);
		resource.put("name",getResourceName());
		
		generateCode(template, data,TT_CONTROLER); 
	}

	private void generateUILabel(List<ModelEntity> entities) {
		// TODO Auto-generated method stub
		
	}

	private void generateCode(File template, Map<String,Object> data,int targetType) throws ContainerException {
		Debug.log("Parsing template : " + template.getAbsolutePath(), module);
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(template));
        } catch (FileNotFoundException e) {
            throw new ContainerException(e);
        }
        // create the target file/directory
        String targetDirectory = getTargetDirectory(targetType);
        
        // write the template to the target directory
        String  outFileName = createOutputFileName(data.get("name")+"",targetDirectory,template,targetType);
        FileWriter writer=null;
        try {
			writer = new FileWriter(outFileName);
			
			FreeMarkerWorker.renderTemplate(UtilURL.fromFilename(template.getAbsolutePath()).toExternalForm(), data, writer);
			writer.flush();
			
			
		} catch (IOException e) {
			throw new ContainerException(e);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Map<String,Object> createEntityModelData(ModelEntity entity){
		Set<String> entityNameIncludeSet=new TreeSet<String>(); 
    	Map<String, Object> dataMap=FastMap.newInstance();
		try {
			dataMap = entity.createEoModelMap(null, "localderby", entityNameIncludeSet, entity.getModelReader());
			Writer ww=new StringWriter();
			PrintWriter wr=new PrintWriter(ww);
			entity.writeEoModelText(wr, null, "localderby", entityNameIncludeSet, entity.getModelReader());
	    	dataMap.put("entityText",ww.toString());
		} catch (GenericEntityException e) {
			e.printStackTrace();
		}
		return dataMap;
	}
	
	private String createOutputFileName(String entityName,String targetDirectory, File template,
			int targetType) {
		String name = template.getName();
		if (name.endsWith(".ftl") && name.substring(0, name.indexOf(".ftl")).indexOf(".") >0) {
			name = name.substring(0, name.indexOf(".ftl"));
		}

		switch (targetType) {
		case TT_ACTIONS:
			name = entityName + name;
			break;
		case TT_FTLHTML:
			name = entityName + name;
			break;
		case TT_SCREENS:
			name = getResourceName() + name;
			break;
		default:

			break;
		}

		return targetDirectory+name;
	}

	private String getTargetDirectory(int tagetType) throws ContainerException {
		// create the target file/directory
        String targetDirectoryName = System.getProperty("ofbiz.home");
        targetDirectoryName=getExitingSubDirectory(targetDirectoryName,props.getProperty("com.easylink.code.output", "output"));
        String componentName=getComponentName();
        if(componentName!=null){
        	targetDirectoryName=getExitingSubDirectory(targetDirectoryName,componentName);
        }
		switch (tagetType) {
		case TT_SERCIVES:
			targetDirectoryName=getExitingSubDirectory(targetDirectoryName,"servicedef");
			break;
		case TT_ACTIONS:
			targetDirectoryName=getExitingSubDirectory(targetDirectoryName,"webapp");
			if(getResourceName()!=null){
				targetDirectoryName=getExitingSubDirectory(targetDirectoryName,getResourceName());
			}
			targetDirectoryName=getExitingSubDirectory(targetDirectoryName,"WEB-INF");
			targetDirectoryName=getExitingSubDirectory(targetDirectoryName,"actions");
			break;
		case TT_FTLHTML:
			targetDirectoryName=getExitingSubDirectory(targetDirectoryName,"webapp");
			targetDirectoryName=getExitingSubDirectory(targetDirectoryName,"includes");
			break;
		case TT_CONTROLER:
			targetDirectoryName=getExitingSubDirectory(targetDirectoryName,"webapp");
			if(getResourceName()!=null){
				targetDirectoryName=getExitingSubDirectory(targetDirectoryName,getResourceName());
			}
			targetDirectoryName=getExitingSubDirectory(targetDirectoryName,"WEB-INF");
			break;
		case TT_SCREENS:
			targetDirectoryName=getExitingSubDirectory(targetDirectoryName,"widget");
			break;
		default:
			
			break;

		}
        return targetDirectoryName;
	}

	private String getActionsDirectory(String targetDirectoryName) throws ContainerException {
		File targetDir = new File(targetDirectoryName+"webapp");
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created) {
                throw new ContainerException("Unable to create target directory - " + targetDirectoryName);
            }
            targetDirectoryName+="/";
        }
        
		return null;
	}
	
	private String getExitingSubDirectory(String dir,String subDir) throws ContainerException{
		if(dir.endsWith("/")==false){
			dir+="/";
		}
		if(subDir.startsWith("/")==true){
			subDir=subDir.substring(1);
		}
		
		File targetDir = new File(dir+subDir);
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created) {
                throw new ContainerException("Unable to create target directory - " + dir+subDir);
            }
        }
        return dir+subDir+"/";
	}

	private String getComponentName() {
		if(args!=null && args.length>0)
		{
			return args[0];
		}
		return null;
	}
	private String getResourceName() {
		if(args!=null && args.length>1)
		{
			return args[1];
		}
		return null;
	}



	private List<ModelEntity> readEntity(String packageFilter) {
		GenericDelegator delegator= DelegatorFactory.getGenericDelegator(null);
        ModelReader mr= delegator.getModelReader();
        try {
        	
        	Iterator<String> itEntityNames= mr.getEntityNamesIterator();
			List<ModelEntity> entities=new ArrayList<ModelEntity>();
			while(itEntityNames.hasNext()){
				String strEntityName=itEntityNames.next();
				ModelEntity me= delegator.getModelEntity(strEntityName);
				if(packageFilter==null || me.getPackageName().indexOf(packageFilter)>0){
					entities.add(me);
				}
			}
			return entities;
		} catch (GenericEntityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ofbiz.base.container.Container#stop()
	 */
	public void stop() throws ContainerException {

	}

}
