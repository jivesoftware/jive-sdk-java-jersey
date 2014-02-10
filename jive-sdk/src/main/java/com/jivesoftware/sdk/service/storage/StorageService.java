package com.jivesoftware.sdk.service.storage;

import com.jivesoftware.sdk.api.entity.StorageInstanceProvider;
import com.jivesoftware.sdk.event.StorageEvent;
import com.jivesoftware.sdk.service.filter.JiveSignatureValidation;
import com.jivesoftware.sdk.service.storage.action.StorageRegisterAction;
import com.jivesoftware.sdk.service.storage.action.StorageResolveAction;
import com.jivesoftware.sdk.service.storage.action.StorageUnregisterAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by rrutan on 1/29/14.
 */
@Path("/storage")
@Singleton
public class StorageService {
    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    @Inject
    private StorageInstanceProvider storageInstanceProvider;

    @Inject
    Event<StorageEvent> storageEventPublisher;

    private void fireStorageEvent(StorageEvent.Type type, Map<String, Object> data) {
        storageEventPublisher.fire(new StorageEvent(type,data));
    } // end fireStorageEvent
//
//    /**
//     * * {
//     * "tenantId": "e2e4edde-dab8-4e27-9a4d-3014d9d08059-memory",
//     * "jiveSignatureURL": "https://market.apps.jivesoftware.com/appsmarket/services/rest/jive/instance/validation/123549ef-8592-4002-af11-00954823d1de",
//     * "timestamp": "Wed Jul 17 15:28:27 IDT 2013",
//     * "jiveUrl": "http://localhost:8080",
//     * "jiveSignature": "WOgYuo1nb14v4JITWf5ed4/PXYNxLURiuCdK0gaihCI=",
//     * "scope": "uri:/tile",
//     * "code": "7ttbqy5k6vv4muizzk3sbdmeht9a0pji.c",
//     * "clientSecret": "ar684jxdzq2odfzbuurbqlrdd6kqodaf.s",
//     * "clientId": "mk2r0bp2ym43tmxehswpv8q3mma0e9xd.i"
//     * }
//     *
//     * @param - request The instance registration request
//     * @return 204 response if succeeded
//     */
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @JiveSignatureValidation
    public Response register(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            StorageRegisterAction storageRegister) {
        return Response.ok("register - unimplemented").build();
    } // end register

    @POST
    @Path("/unregister")
    @Produces(MediaType.APPLICATION_JSON)
    @JiveSignatureValidation
    public Response unregister(
                @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
                StorageUnregisterAction storageUnregister) {
        return Response.ok("unregister - unimplemented").build();
    } // end unregister

    @POST
    @Path("/resolveResources")
    @Produces(MediaType.APPLICATION_JSON)
    public Response resolveResources(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            StorageResolveAction storageResolve) {
        return Response.ok("resolveResources - unimplemented").build();
    } // end unregister

    @POST
    @Path("/ping")
    @JiveSignatureValidation
    public Response ping(
                @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,

                String configuration) {

        if (log.isDebugEnabled()) {
            log.debug("configuration: "+configuration);
        } // end if

        //TODO:  MAKE INTERFACE TO PASS THIS BACK TO THE STORAGE INSTANCE
        //TODO: NOTE NEED A STORAGE PLACE INSTANCe AS WELL APPARENTLY????

        return Response.status(200).entity("Alive!").build();
    } // end unregister

    @POST
    @Path("/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            StorageResolveAction storageResolve) {
        return Response.ok("upload - unimplemented").build();
    } // end upload

    @POST
    @Path("/uploadVersion")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadVersion(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            StorageResolveAction storageResolve) {
        return Response.ok("uploadVersion - unimplemented").build();
    } // end uploadVersion

    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_JSON)
    public Response download(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {
        return Response.ok("download - unimplemented").build();
    } // end download

    @DELETE
    @Path("/place")
    @Produces(MediaType.APPLICATION_JSON)
    public Response place(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            StorageResolveAction storageResolve) {
        return Response.ok("place - unimplemented").build();
    } // end place

    @DELETE
    @Path("/file")
    @Produces(MediaType.APPLICATION_JSON)
    public Response file(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            StorageResolveAction storageResolve) {
        return Response.ok("file - unimplemented").build();
    } // end file



} // end class JiveOAuth2Service