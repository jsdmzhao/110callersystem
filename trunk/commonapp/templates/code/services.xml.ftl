<?xml version="1.0" encoding="UTF-8"?>
<services xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:noNamespaceSchemaLocation="http://ofbiz.apache.org/dtds/services.xsd">
    <description>@component-resource-name@ Services</description>
    <vendor></vendor>
    <version>1.0</version>
    <#list entityNames as name>
	<service name="${name}Create"  engine="entity-auto" default-entity-name="${name}" invoke="create"></service>
	<service name="${name}Update"  engine="entity-auto" default-entity-name="${name}" invoke="update"></service>
	<service name="${name}Delete"  engine="entity-auto" default-entity-name="${name}" invoke="delete"></service>
	</#list>
	
</services>
