<?xml version="1.0" encoding="UTF-8"?>
<site-conf xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:noNamespaceSchemaLocation="http://ofbiz.apache.org/dtds/site-conf.xsd">
    <!-- The controller elements that are common to all OFBiz components
         can be found in the following xml file. A component can override the
         elements found in the common-controller.xml file. -->
    <include location="component://common/webcommon/WEB-INF/common-controller.xml"/>

    <description>${r"${${entityName}}"} Component Site Configuration File</description>
    <owner></owner>


    <!-- Events to run on every request before security (chains exempt) -->
    <!--
    <preprocessor>
    </preprocessor>
    -->
    <!-- Events to run on every request after all other processing (chains exempt) -->
    <!--
    <postprocessor>
        <event name="test" type="java" path="org.ofbiz.webapp.event.TestEvent" invoke="test"/>
    </postprocessor>
    -->

    <!-- Request Mappings -->
    <#list entityNames as name>
    	<request-map uri="${name}Create"><security https="false" auth="false"/>
    		<response name="success" type="view" value="${name}Create"/>
    	</request-map>
    	<request-map uri="${name}List"><security https="false" auth="false"/>
    		<response name="success" type="view" value="${name}List"/>
    	</request-map>
    	<request-map uri="${name}Update"><security https="false" auth="false"/>
    		<response name="success" type="view" value="${name}Update"/>
    	</request-map>
    	<request-map uri="${name}Delete"><security https="false" auth="false"/>
    		<response name="success" type="view" value="${name}List"/>
    	</request-map>
    	<request-map uri="${name}Detail"><security https="false" auth="false"/>
    		<response name="success" type="view" value="${name}Detail"/>
    	</request-map>
    	
    </#list>
    <!-- View Mappings -->
    <#list entityNames as name>
    	<view-map name="${name}Create" type="screen" page="component://${component.name}/widget/${component.resource.name}Screens.xml#${name}Create"/>
    	<view-map name="${name}List" type="screen" page="component://${component.name}/widget/${component.resource.name}Screens.xml#${name}List"/>
    	<view-map name="${name}Update" type="screen" page="component://${component.name}/widget/${component.resource.name}Screens.xml#${name}Update"/>
    	<view-map name="${name}Detail" type="screen" page="component://${component.name}/widget/${component.resource.name}Screens.xml#${name}Detail"/>
    </#list>
</site-conf>
