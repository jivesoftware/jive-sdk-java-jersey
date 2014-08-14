/*
 *
 *  * Copyright 2013 Jive Software
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package com.jivesoftware.addon.example.service.storage;

import com.jivesoftware.addon.example.service.storage.action.StorageRegisterAction;
import com.jivesoftware.addon.example.service.storage.action.StorageResolveAction;
import com.jivesoftware.addon.example.service.storage.action.StorageUnregisterAction;
import com.jivesoftware.sdk.service.filter.JiveSignatureValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by rrutan on 2/24/14.
 */
@Component
@Path("/storage")
@Singleton
public class MyExampleStorageService {
    private static final Logger log = LoggerFactory.getLogger(MyExampleStorageService.class);

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

    @POST
    @Path("/untrashFile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response untrashFile(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            StorageResolveAction storageResolve) {
        return Response.ok("untrashFile - unimplemented").build();
    } // end untrashFile


    @POST
    @Path("/trashFile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response trashFile(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            StorageResolveAction storageResolve) {
        return Response.ok("trashFile - unimplemented").build();
    } // end trashFile

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


}
