package com.jivesoftware.sdk.client.filter;

import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.glassfish.jersey.server.ContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.*;

/**
 * Created by rrutan on 2/7/14.
 */

@Provider
public class DebugClientResponseFilter implements ClientResponseFilter {
    private static final Logger log = LoggerFactory.getLogger(DebugClientResponseFilter.class);

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        if (!log.isDebugEnabled()) {
            return;
        } // end if

        logRequest(requestContext);
        logResponse(responseContext);

    } // end getJsonBody

    private void logRequest(ClientRequestContext requestContext) throws IOException {

        Object requestBody = requestContext.getEntity();

        if (log.isTraceEnabled()) {
            StringBuffer sbuf = new StringBuffer("\nHeaders:");
            sbuf.append("\n--- BEGIN ---");
            MultivaluedMap<String,String> headers = requestContext.getStringHeaders();
            for (String key : headers.keySet()) {
                sbuf.append("\n").append(key).append("=").append(headers.get(key));
            } // end for key
            sbuf.append("\n--- END ---");
            log.trace(sbuf.toString());
            sbuf = null;
        } // end if

        if (MediaType.APPLICATION_JSON.equals(requestContext.getHeaderString(HttpHeaders.CONTENT_TYPE))) {
            log.debug("\n["+requestContext.getMethod()+"] : "+requestContext.getUri().toString()+"\n"+JiveSDKUtils.getJson(requestBody));
        } else {
            log.debug("\n["+requestContext.getMethod()+"] : "+requestContext.getUri().toString()+"\n"+requestBody);
        } // end if

    } // end logResponse

    private void logResponse(ClientResponseContext responseContext) throws IOException {

        String responseBody = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = responseContext.getEntityStream();

        final StringBuilder b = new StringBuilder();
        try {
            if (in.available() > 0) {
                ReaderWriter.writeTo(in, out);
                responseContext.setEntityStream(new ByteArrayInputStream(out.toByteArray()));
                responseBody = new String(out.toByteArray());
            } // end if
        } catch (IOException ex) {
            throw new ContainerException(ex);
        } // end try/catch

        responseBody = (responseBody == null) ? "" : "\n"+responseBody;

        log.debug("\nClient Response:\nStatus: "+responseContext.getStatus()+ responseBody);

    } // end logResponse

} // end class