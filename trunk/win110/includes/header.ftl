<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->
<#if (requestAttributes.person)?exists><#assign person = requestAttributes.person></#if>
<#if (requestAttributes.partyGroup)?exists><#assign partyGroup = requestAttributes.partyGroup></#if>
<#assign docLangAttr = locale.toString()?replace("_", "-")>
<#assign langDir = "ltr">
<#if "ar.iw"?contains(docLangAttr?substring(0,2))>
    <#assign langDir = "rtl">
</#if>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${docLangAttr}" lang="${docLangAttr}" dir="${langDir}">
<head>
    <#include "meta.ftl">
    <#include "title.ftl">
    <#include "csslinks.ftl">
    <#include "extraHead.ftl">
    <#include "scripts.ftl">
</head>
<body>
<div id="wrap" class="ui-widget">
  <div id="header" class="ui-widget-content">
        <ul>
        	<li>
            	<a href="#">${uiLabelMap.CommonPreferences}</a>
            </li>
        </ul>
        <span id="prefBtn">
            <div id="preferences">
                <a href="#" rel="<@ofbizUrl>LookupLocales</@ofbizUrl>" id="language">${uiLabelMap.CommonLanguageTitle} - ${locale.getDisplayName(locale)}</a>
                <a href="#" rel="<@ofbizUrl>LookupTimezones</@ofbizUrl>" id="timezone">${nowTimestamp?datetime?string.short} - ${timeZone.getDisplayName(timeZone.useDaylightTime(), Static["java.util.TimeZone"].LONG, locale)}</a>
                <a href="#" rel="<@ofbizUrl>LookupVisualThemes</@ofbizUrl>" id="theme">${uiLabelMap.CommonVisualThemes}</a>
            </div>
        </span>
        <span>
        <#if person?has_content>
          ${uiLabelMap.CommonWelcome},  ${person.firstName?if_exists} ${person.lastName?if_exists} ( ${userLogin.userLoginId} )
        <#elseif partyGroup?has_content>
          ${uiLabelMap.CommonWelcome},  ${partyGroup.groupName?if_exists} ( ${userLogin.userLoginId} )
        <#else>
          ${uiLabelMap.CommonWelcome}
        </#if>
        </span>
        <#if person?has_content>
        <span><a href="<@ofbizUrl>logout</@ofbizUrl>">${uiLabelMap.CommonLogout}</a></span>
        </#if>
  </div>
  
  