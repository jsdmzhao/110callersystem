import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
 
${name}List= [];
exprList = [];
<#list attributes as attr>
	<#if attr.name?ends_with('*')==false>
		<#if attr.valueClassName == 'String'>
			if(parameters.${attr.name}){
				exprList.add(EntityCondition.makeCondition("${attr.name}", EntityOperator.LIKE,parameters.${attr.name}));				
			}	
		<#else>
			if(parameters.${attr.name}){
				exprList.add(EntityCondition.makeCondition("${attr.name}", EntityOperator.EQUALS,parameters.${attr.name}));				
			}
		</#if>
	</#if>
</#list>
${name}List = delegator.findList("${name}", EntityCondition.makeCondition(exprList, EntityOperator.AND), null, null, null, false);

context.${name}List=${name}List;