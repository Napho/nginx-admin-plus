<%@include file="../app/taglibs.jsp"%>
<html:view title="{title}">

	<html:block>
		<html:alert state="success" label="{nginx.configure.update.success}" rendered="${ operation == 'UPDATE' }"/>
		<html:alert state="success" label="{nginx.configure.insert.success}" rendered="${ operation == 'INSERT' }"/>
		<html:alert state="danger" rendered="${ nginxConfigureResponse.error() }">
				${ nginxConfigureResponse.stackTrace }
		</html:alert>
		<html:alert state="danger" rendered="${ configuration == null  }" label="{nginx.configure.attention}"/>
	</html:block>
	
	<html:block>
		<html:form action="/configuration/saveOrUpdate" label="{nginx.configure.form}">
			<html:input name="id" value="${ configuration.id }" type="hidden"></html:input>
			<html:input name="idNginx" type="hidden" value="${ nginx.id }"></html:input>
			<html:formGroup>
				<html:listGroup>
					<html:listGroupItem>
						<html:input checked="${ configuration == null ? 1 : configuration.gzip }" name="gzip" value="1"
							type="checkbox"></html:input>
						<fmt:message key="nginx.configure.enable.gzip"></fmt:message>
					</html:listGroupItem>
				</html:listGroup>
			</html:formGroup>
			<html:formGroup label="{nginx.configure.max.post.size}" required="true">
				<html:input value="${ configuration == null ? 30 : configuration.maxPostSize }" name="maxPostSize"
					type="number" maxLength="9999"
					placeholder="{nginx.configure.max.post.size.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:formGroup label="{nginx.configure.root.listen.port}" required="true">
				<html:input value="${ configuration == null ? 80 : configuration.rootPort }" name="rootPort"
					type="number" maxLength="5"
					placeholder="{nginx.configure.root.listen.port.placeholder}" required="true"></html:input>
			</html:formGroup>
			<html:toolbar>
				<html:button state="primary" type="submit"
					label="{nginx.configure.apply.settings}"></html:button>
			</html:toolbar>
		</html:form>
	</html:block>
	
	<html:jsCode rendered="${ operation == 'INSERT' }">
		window.setTimeout(function(){
			window.parent.self.location = URL_BASE + '/nginx/tabs/${ nginx.id }';
		},5000);
	</html:jsCode>

</html:view>
