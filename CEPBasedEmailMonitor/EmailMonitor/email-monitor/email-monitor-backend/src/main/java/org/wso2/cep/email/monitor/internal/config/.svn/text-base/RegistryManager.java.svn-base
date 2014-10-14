/**
 * Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.cep.email.monitor.internal.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.registry.common.services.RegistryAbstractAdmin;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;
import org.wso2.cep.email.monitor.internal.ds.EmailMonitorValueHolder;

import java.nio.charset.Charset;

/**
 * Does Configuration Registry operations required to store/fetch email monitor configurations
 */
public class RegistryManager extends RegistryAbstractAdmin {

    private static final Log log = LogFactory.getLog(RegistryManager.class);
    private Registry registry;
    private Resource resource;

    public RegistryManager(){
        EmailMonitorValueHolder emailMonitorValueHolder = EmailMonitorValueHolder.getInstance();
        RegistryService registryService = emailMonitorValueHolder.getRegistryService();
        try {
            registry = registryService.getConfigSystemRegistry(MultitenantUtils.getTenantId(super.getConfigContext()));
        } catch (RegistryException e) {
            String errorMsg = "Error while getting the Registry Service. " + e.getMessage();
            log.error(errorMsg, e);
        }

    }

    public void saveResourceString(String resourceString, String resourcePath){
        try {
            resource = registry.newResource();
            resource.setContent(resourceString);
            resource.setMediaType("application/xml");
            registry.put(resourcePath, resource);
        } catch (RegistryException e) {
            String errorMsg = "Error while saving resource string from Registry. " + e.getMessage();
            log.error(errorMsg, e);
        }
    }
    
    public boolean resourceAlreadyExists(String path){
        try {
            return registry.resourceExists(path);
        } catch (RegistryException e) {
            String errorMsg = "Error while checking resource string from Registry. " + e.getMessage();
            log.error(errorMsg, e);
        }
        return true;
    }

    public boolean removeResource(String path){
        try {
            registry.delete(path);
            return true;
        } catch (RegistryException e) {
            String errorMsg = "Error while removing the resource from Registry. " + e.getMessage();
            log.error(errorMsg, e);
        }
        return false;
    }

    public String getResourceString(String path){
        try {
            resource = registry.get(path);
            return new String((byte[])resource.getContent(), Charset.forName("UTF-8"));
        } catch (RegistryException e) {
            String errorMsg = "Error while getting the resource from Registry. " + e.getMessage();
            log.error(errorMsg, e);
        }
        return null;
    }
    
    public boolean addCollection(String collectionPath){
        try {
            resource = registry.newCollection();
            registry.put(collectionPath, resource);
            return true;
        } catch (RegistryException e) {
            String errorMsg = "Error while adding the collection to the Registry. " + e.getMessage();
            log.error(errorMsg, e);
        }
        return false;
    }

    public String[] getEmailMonitorResources(String emailMonitorCollectionLocation){
        try {
            return (String[])registry.get(emailMonitorCollectionLocation).getContent();
        } catch (RegistryException e) {
            String errorMsg = "Error while getting resources from the Registry. " + e.getMessage();
            log.error(errorMsg, e);
        }
        return new String[0];
    }


}
