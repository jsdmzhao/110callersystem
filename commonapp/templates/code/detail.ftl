
<form name="${name}UpdateForm" action="${name}Update" method="POST">
	<#list attributes as field>
		<#if field.name?ends_with('*')>
			<input type="hidden" name="${field.name}" value='${r"$"}{${name}.${field.name}}'/>
	    <#else>
			<div>
				<span>${r"$"}{uiLabelMap.${field.name}}</span>
				<input type="text" name="${field.name}" maxlength="${field.width!}" value='${r"$"}{${name}.${field.name}}'/>
			</div>		   	
        </#if>
	</#list>
	<div>
		<input type="submit" value="${r"$"}{uiLabelMap.update}" />
	</div>
</form>