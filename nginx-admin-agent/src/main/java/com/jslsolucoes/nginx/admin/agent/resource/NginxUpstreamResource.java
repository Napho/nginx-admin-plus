package com.jslsolucoes.nginx.admin.agent.resource;

import com.jslsolucoes.nginx.admin.agent.auth.AuthHandler;
import com.jslsolucoes.nginx.admin.agent.error.ErrorHandler;
import com.jslsolucoes.nginx.admin.agent.model.FileObject;
import com.jslsolucoes.nginx.admin.agent.model.request.upstream.NginxUpstreamCreateRequest;
import com.jslsolucoes.nginx.admin.agent.model.request.upstream.NginxUpstreamUpdateRequest;
import com.jslsolucoes.nginx.admin.agent.model.response.upstream.NginxUpstreamCreateResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.upstream.NginxUpstreamDeleteResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.upstream.NginxUpstreamReadResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.upstream.NginxUpstreamUpdateResponse;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxOperationResult;
import com.jslsolucoes.nginx.admin.agent.resource.impl.NginxUpstreamResourceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;

@Path("upstream")
@ErrorHandler
@AuthHandler
@Produces(MediaType.APPLICATION_JSON)
public class NginxUpstreamResource {

	private NginxUpstreamResourceImpl nginxUpstreamResourceImpl;

	@Deprecated
	public NginxUpstreamResource() {

	}

	@Inject
	public NginxUpstreamResource(NginxUpstreamResourceImpl nginxUpstreamResourceImpl) {
		this.nginxUpstreamResourceImpl = nginxUpstreamResourceImpl;
	}

	@POST
	public void create(NginxUpstreamCreateRequest nginxUpstreamCreateRequest, @Suspended AsyncResponse asyncResponse,
					   @Context UriInfo uriInfo) {
		NginxOperationResult nginxOperationResult = nginxUpstreamResourceImpl.create(
				nginxUpstreamCreateRequest.getName(),
				nginxUpstreamCreateRequest.getAdditionalLines(),
				nginxUpstreamCreateRequest.getUuid(),
				nginxUpstreamCreateRequest.getStrategy(),
				nginxUpstreamCreateRequest.getEndpoints());
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		uriBuilder.path(nginxUpstreamCreateRequest.getUuid());
		asyncResponse.resume(Response.created(uriBuilder.build()).entity(
				new NginxUpstreamCreateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

	@PUT
	@Path("{uuid}")
	public void update(@PathParam("uuid") String uuid, NginxUpstreamUpdateRequest nginxUpstreamUpdateRequest,
					   @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxUpstreamResourceImpl.update(uuid,
				nginxUpstreamUpdateRequest.getName(),
				nginxUpstreamUpdateRequest.getAdditionalLines(),
				nginxUpstreamUpdateRequest.getStrategy(),
				nginxUpstreamUpdateRequest.getEndpoints());
		asyncResponse.resume(Response
				.ok(new NginxUpstreamUpdateResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

	@DELETE
	@Path("{uuid}")
	public void delete(@PathParam("uuid") String uuid, @Suspended AsyncResponse asyncResponse) {
		NginxOperationResult nginxOperationResult = nginxUpstreamResourceImpl.delete(uuid);
		asyncResponse.resume(Response
				.ok(new NginxUpstreamDeleteResponse(nginxOperationResult.getOutput(), nginxOperationResult.isSuccess()))
				.build());
	}

	@GET
	@Path("{uuid}")
	public void read(@PathParam("uuid") String uuid, @Suspended AsyncResponse asyncResponse) {
		FileObject fileObject = nginxUpstreamResourceImpl.read(uuid);
		asyncResponse.resume(Response.ok(new NginxUpstreamReadResponse(fileObject)).build());
	}
}
