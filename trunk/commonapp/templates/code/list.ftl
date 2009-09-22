
<form name="${name}DeleteForm" action="${name}Delete" method="POST">
	<${r"#"}list ${name}List as record>
	<#list attributes as field>
		<#if field.name?ends_with('*')>
			<input type="hidden" name="${field.name}" value='${r"$"}{${name}.${field.name}}'/>
	    <#else>
			<div>
				<span>${r"$"}{uiLabels.${field.name}}</span>
				<span  name="${field.name}">
					${r"$"}{record.${field.name}}
				</span>
			</div>		   	
        </#if>
	</#list>
	</${r"#"}list>
	<div>
		<input type="submit" value="${r"$"}{uiLabels.update}" />
	</div>
</form>