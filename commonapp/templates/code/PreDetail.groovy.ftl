import java.math.BigDecimal;
import java.util.*;
import org.ofbiz.entity.*;
import org.ofbiz.entity.condition.*;
import org.ofbiz.entity.util.*;
import org.ofbiz.base.util.*;
import org.ofbiz.base.util.collections.*;
import javolution.util.FastMap;
keys=[];
<#list primaryKeyAttributes as pk>
	${pk}=paramenters.${pk};
	context.${pk}=${pk};
	keys.${pk}=${pk};
</#list>
if(keys){
	context.${name}=delegator.findByPrimaryKey("${name}",keys);
}
