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

package com.jivesoftware.sdk.service.filter;

import com.jivesoftware.sdk.service.BaseContainerRequestFilter;
import com.jivesoftware.sdk.service.instance.action.InstanceRegisterAction;
import org.codehaus.jackson.map.ObjectMapper;
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
@JiveSignatureValidation
public class JiveSignatureValidationRequestFilter extends BaseContainerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JiveSignatureValidationRequestFilter.class);

    private ObjectMapper objectMapper = null;

    @Inject
    private JiveSignatureValidator jiveSignatureValidator;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (log.isTraceEnabled()) { log.trace("filter called"); }
        String jsonBody = getJsonBody(containerRequestContext);

        if (jsonBody == null || jsonBody.trim().length() < 1) {
            if (log.isDebugEnabled()) { log.debug("Invalid JSON Body, Unable to Validate Signtature"); }
            throw UNAUTHORIZED;
        } // end if

        //TODO: NEED TO GO THROUGH AND SEE IF THIS FILTER WILL BE USED ON ANY OTHER ACTION, IF SO .. ABSTRACT ACCORDINGLY

        InstanceRegisterAction instanceRegisterAction = new ObjectMapper().readValue(jsonBody, InstanceRegisterAction.class);

        if (instanceRegisterAction == null ||
            instanceRegisterAction.getJiveSignature() == null ||
            instanceRegisterAction.getJiveSignatureURL() == null) {
            throw UNAUTHORIZED;
        } // end if


        if (!jiveSignatureValidator.isValidSignature(instanceRegisterAction)) {
            log.error("Invalid Signature Validation");
            throw VALIDATION_FAILED;
        } // end if

    } // end filter

} // end filter