<?xml version="1.0" encoding="UTF-8"?>
<screens xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:noNamespaceSchemaLocation="http://ofbiz.apache.org/dtds/widget-screen.xsd">
<#list entityNames as name>
    <screen name="${name}Create">
        <section>
            <actions>
            	<script location="component://${component.name}/webapp/${component.resource.name}/WEB-INF/actions/${name}PreCreate.groovy"/>
            </actions>
            <widgets>
                <decorator-screen name="${component.name}CommonDecorator" location="component://${component.name}/widget/CommonScreens.xml">
                    <decorator-section name="body">
                    	 <platform-specific>
                            <html>
                                <html-template location="component://${component.name}/webapp/includes/${name}Create.ftl"/>
                            </html>
                        </platform-specific>
                    </decorator-section>
                </decorator-screen>
            </widgets>
        </section>
    </screen>
    <screen name="${name}Update">
        <section>
            <actions>
            	<script location="component://${component.name}/webapp/${component.resource.name}/WEB-INF/actions/${name}PreUpdate.groovy"/>
            </actions>
            <widgets>
                <decorator-screen name="${component.name}CommonDecorator" location="component://${component.name}/widget/CommonScreens.xml">
                    <decorator-section name="body">
                    	 <platform-specific>
                            <html>
                                <html-template location="component://${component.name}/webapp/includes/${name}Update.ftl"/>
                            </html>
                        </platform-specific>
                    </decorator-section>
                </decorator-screen>
            </widgets>
        </section>
    </screen>
    <screen name="${name}List">
        <section>
            <actions>
            	<script location="component://${component.name}/webapp/${component.resource.name}/WEB-INF/actions/${name}PreList.groovy"/>
            </actions>
            <widgets>
                <decorator-screen name="${component.name}CommonDecorator" location="component://${component.name}/widget/CommonScreens.xml">
                    <decorator-section name="body">
                    	 <platform-specific>
                            <html>
                                <html-template location="component://${component.name}/webapp/includes/${name}List.ftl"/>
                            </html>
                        </platform-specific>
                    </decorator-section>
                </decorator-screen>
            </widgets>
        </section>
    </screen>
    <screen name="${name}Detail">
        <section>
            <actions>
            	<script location="component://${component.name}/webapp/${component.resource.name}/WEB-INF/actions/${name}PreDetail.groovy"/>
            </actions>
            <widgets>
                <decorator-screen name="${component.name}CommonDecorator" location="component://${component.name}/widget/CommonScreens.xml">
                    <decorator-section name="body">
                    	 <platform-specific>
                            <html>
                                <html-template location="component://${component.name}/webapp/includes/${name}Detail.ftl"/>
                            </html>
                        </platform-specific>
                    </decorator-section>
                </decorator-screen>
            </widgets>
        </section>
    </screen>
</#list>
</screens>
