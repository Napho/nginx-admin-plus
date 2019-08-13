<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">



	<html:form action="/nginx/saveOrUpdate" validation="/nginx/validate" label="{nginx.agent.form}">
		<html:input name="id" value="${ nginx.id }" type="hidden"></html:input>
		
		<html:formGroup label="{nginx.agent.name}" required="true">
			<html:input name="name" value="${ nginx.name }"
				placeholder="{nginx.agent.name.placeholder}" required="true"></html:input>
		</html:formGroup>
		<html:formGroup label="{nginx.agent.endpoint}" required="true">
			<html:input name="endpoint" value="${ nginx.endpoint }"
				placeholder="{nginx.agent.endpoint.placeholder}" required="true"></html:input>
		</html:formGroup>
        <html:formGroup label="{nginx.agent.serviceName}" required="true">
			<html:input name="serviceName" value="${ nginx.serviceName }"
				placeholder="{nginx.agent.serviceName.placeholder}" required="true"></html:input>
		</html:formGroup>
		<html:formGroup label="{nginx.agent.settingsPath}" required="true">
			<html:input name="settingsPath" value="${ nginx.settingsPath }"
				placeholder="{nginx.agent.settingsPath.placeholder}" required="true"></html:input>
		</html:formGroup>
		<html:formGroup label="{nginx.agent.authorization.key}" required="true">
			<html:input name="authorizationKey" value="${ nginx.authorizationKey }"
				placeholder="{nginx.agent.authorization.key.placeholder}" required="true"></html:input>
		</html:formGroup>
		<html:toolbar>
			<html:buttonGroup spaced="true">
				<html:button state="primary" type="submit" label="{nginx.agent.save.settings}"></html:button>
			</html:buttonGroup>		
		</html:toolbar>
	</html:form>
	
	<html:block align="center">
		<html:link url="/nginx/list" label="{back}" target="_parent"></html:link>
	</html:block>
	
</html:view>
