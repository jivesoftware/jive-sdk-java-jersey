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
@JiveAuthorizationValidation
public class JiveAuthorizationValidationRequestFilter extends BaseContainerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JiveAuthorizationValidationRequestFilter.class);

    @Inject
    private JiveAuthorizationValidator jiveAuthorizationValidator;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (log.isTraceEnabled()) { log.trace("filter called"); }
        if (log.isDebugEnabled()) {
            getJsonBody(containerRequestContext);
        } // end if

        jiveAuthorizationValidator.authenticate(containerRequestContext);
    } // end filter

} // end class
