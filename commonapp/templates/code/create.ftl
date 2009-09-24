

<form name="${name}CreateForm" action="${name}Create" method="POST">
	<#list attributes as field>
		<#if field.name?ends_with('*')>
	    <#else>
			<div>
				<span>${r"$"}{uiLabelMap.${field.name}}</span>
				<input type="text" name="${field.name}" maxlength="${field.width!}"/>
			</div>		   	
        </#if>
	</#list>
	<div>
		<input type="submit" value="${r"$"}{uiLabelMap.create}" />
	</div>
</form>