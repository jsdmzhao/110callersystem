/**
 * 
 */
package org.ofbiz.appservers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

/**
 * @author pwang
 * 
 */
public class Entity2CodeContainer implements Container {

	public static final String module = Entity2CodeContainer.class.getName();
	public static final String source = "/framework/appserver/templates/";
	public static String target = "/setup/";

	protected String configFile = null;
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
		ofbizHome = System.getProperty("ofbiz.home");
		this.configFile = configFile;
		this.args = args;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ofbiz.base.container.Container#start()
	 */
	public boolean start() throws ContainerException {

		List<ModelEntity> entities=readEntity();
		if(entities!=null && entities.isEmpty()==false){
			File[] lsTemplates=getTemplates();
			if(lsTemplates!=null && lsTemplates.length>0){
				for(File template : lsTemplates){
					if (!(template.isDirectory() || template.isHidden())) {
						for(ModelEntity entity : entities){
							generateCode(template,entity);	
						}
					}
				}
			}
		}
		System.exit(1);
		return true;

	}

	private void generateCode(File template, ModelEntity entity) throws ContainerException {
		Debug.log("Parsing template : " + template.getAbsolutePath(), module);
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(template));
        } catch (FileNotFoundException e) {
            throw new ContainerException(e);
        }

        // create the target file/directory
        String targetDirectory = getTargetDirectory(entity);

        // write the template to the target directory
        Writer writer = null;
        try {
            writer = new FileWriter(targetDirectory + entity.getEntityName()+"_"+template.getName());
        } catch (IOException e) {
            throw new ContainerException(e);
        }
        try {
        	
        	FreeMarkerWorker.renderTemplate(UtilURL.fromFilename(template.getAbsolutePath()).toExternalForm(), dataMap, writer);
        } catch (Exception e) {
            throw new ContainerException(e);
        }

        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new ContainerException(e);
        }
	}

	private String getTargetDirectory(ModelEntity entity) throws ContainerException {
		// create the target file/directory
        String targetDirectoryName = args.length > 1 ? args[1] : null;
        if (targetDirectoryName == null) {
            targetDirectoryName = target;
        }
        String targetDirectory = null;
       
        targetDirectory = ofbizHome + targetDirectoryName;
        File targetDir = new File(targetDirectory);
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created) {
                throw new ContainerException("Unable to create target directory - " + targetDirectory);
            }
        }
        
        if (!targetDirectory.endsWith("/")) {
            targetDirectory = targetDirectory + "/";
        }
        String entityDirectory=targetDirectory+entity.getEntityName();
        File entityDir = new File(entityDirectory);
        if (!entityDir.exists()) {
            boolean created = entityDir.mkdirs();
            if (!created) {
                throw new ContainerException("Unable to create entity directory - " + entityDirectory);
            }
        }
        
        if (!entityDirectory.endsWith("/")) {
        	entityDirectory = entityDirectory + "/";
        }
        return entityDirectory;
	}

	private File[] getTemplates() throws ContainerException{
		if (args == null) {
            throw new ContainerException("Invalid application server type argument passed");
        }

        String templateLocation = args[0];
        if (templateLocation == null) {
            throw new ContainerException("Unable to locate Application Server template directory");
        }

        File parentDir = new File(ofbizHome + source + templateLocation+"/entity/");
        if (!parentDir.exists() || !parentDir.isDirectory()) {
            throw new ContainerException("Template location - " + templateLocation + " does not exist!");
        }

        return parentDir.listFiles();
	}

	private List<ModelEntity> readEntity() {
		GenericDelegator delegator= DelegatorFactory.getGenericDelegator(null);
        ModelReader mr= delegator.getModelReader();
        try {
			Iterator<String> itEntityNames= mr.getEntityNamesIterator();
			List<ModelEntity> entities=new ArrayList<ModelEntity>();
			while(itEntityNames.hasNext()){
				String strEntityName=itEntityNames.next();
				ModelEntity me= delegator.getModelEntity(strEntityName);
				entities.add(me);
				
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
