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

package com.jivesoftware.sdk.service;

import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.glassfish.jersey.server.ContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rrutan on 1/30/14.
 */
public abstract class BaseContainerRequestFilter implements ContainerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(BaseContainerRequestFilter.class);

    protected final static WebApplicationException VALIDATION_FAILED =
       new WebApplicationException(
           Response.status(Response.Status.BAD_REQUEST).build());

    protected final static WebApplicationException UNAUTHORIZED =
       new WebApplicationException(
           Response.status(Response.Status.UNAUTHORIZED).build());

    protected String getJsonBody(ContainerRequestContext containerRequestContext) {
        String body = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = containerRequestContext.getEntityStream();

        final StringBuilder b = new StringBuilder();
        try {
            if (in.available() > 0) {
                ReaderWriter.writeTo(in, out);
                containerRequestContext.setEntityStream(new ByteArrayInputStream(out.toByteArray()));
                body = new String(out.toByteArray());
            } // end if
        } catch (IOException ex) {
            throw new ContainerException(ex);
        } // end try/catch

        if (log.isDebugEnabled()) {
            if (MediaType.APPLICATION_JSON.equals(containerRequestContext.getHeaderString(HttpHeaders.CONTENT_TYPE))) {
                log.debug("\n["+containerRequestContext.getMethod()+"] : "+containerRequestContext.getUriInfo().getPath()+"\n"+ JiveSDKUtils.getJson(body));
            } else {
                log.debug("\n["+containerRequestContext.getMethod()+"] : "+containerRequestContext.getUriInfo().getPath()+"\n"+body);
            } // end if
        } // end if

        return body;
    } // end getJsonBody

} // end class
