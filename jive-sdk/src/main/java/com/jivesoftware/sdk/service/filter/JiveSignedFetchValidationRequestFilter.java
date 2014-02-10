package com.jivesoftware.sdk.service.filter;

import com.jivesoftware.sdk.service.BaseContainerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by rrutan on 1/30/14.
 */
@Provider
@JiveSignedFetchValidation
public class JiveSignedFetchValidationRequestFilter extends BaseContainerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JiveSignedFetchValidationRequestFilter.class);

    @Inject
    private JiveSignedFetchValidator jiveSignedFetchValidator;
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (log.isTraceEnabled()) { log.trace("filter called"); }
        if (log.isDebugEnabled()) {
            log.debug(getJsonBody(containerRequestContext));
        } // end if

        jiveSignedFetchValidator.authenticate(containerRequestContext);

    } // end filter

} // end class
