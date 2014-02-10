package com.jivesoftware.sdk.service.instance.action;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;

/**
 * Created by rrutan on 1/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstanceRegisterAction {

    public final static String TENANT_ID = "tenantId";
    public final static String JIVE_SIGNATURE_URL = "jiveSignatureURL";
    public final static String TIMESTAMP = "timestamp";
    public final static String JIVE_URL = "jiveUrl";
    public final static String JIVE_SIGNATURE = "jiveSignature";
    public final static String SCOPE = "scope";
    public final static String CODE = "code";
    public final static String CLIENT_SECRET = "clientSecret";
    public final static String CLIENT_ID = "clientId";

    private String tenantId;
    private String jiveSignatureURL;
    private String timestamp;
    private String jiveUrl;
    private String jiveSignature;
    private String scope;
    private String code;
    private String clientSecret;
    private String clientId;

    @JsonCreator
    public InstanceRegisterAction( @JsonProperty(TENANT_ID) String tenantId,
                                   @JsonProperty(JIVE_SIGNATURE_URL) String jiveSignatureURL,
                                   @JsonProperty(TIMESTAMP) String timestamp,
                                   @JsonProperty(JIVE_URL) String jiveUrl,
                                   @JsonProperty(JIVE_SIGNATURE) String jiveSignature,
                                   @JsonProperty(SCOPE) String scope,
                                   @JsonProperty(CODE) String code,
                                   @JsonProperty(CLIENT_SECRET) String clientSecret,
                                   @JsonProperty(CLIENT_ID) String clientId) {
            this.tenantId = tenantId;
            this.jiveSignatureURL = jiveSignatureURL;
            this.timestamp = timestamp;
            this.jiveUrl = jiveUrl;
            this.jiveSignature = jiveSignature;
            this.scope = scope;
            this.code = code;
            this.clientSecret = clientSecret;
            this.clientId = clientId;
        }

        public String getClientId() {
            return clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public String getCode() {
            return code;
        }

        public String getScope() {
            return scope;
        }

        public String getJiveSignature() {
            return jiveSignature;
        }

        public String getJiveUrl() {
            return jiveUrl;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getJiveSignatureURL() {
            return jiveSignatureURL;
        }

        public String getTenantId() {
            return tenantId;
        }

        @Nonnull
        public SortedMap<String, String> toSortedMap() {
            // Encode the client-secret
            String encodedClientSecret = (clientSecret != null) ? DigestUtils.sha256Hex(clientSecret) : clientSecret;
            SortedMap<String, String> sortedMap = Maps.newTreeMap();
            sortedMap.put(CLIENT_ID, clientId);
            sortedMap.put(CLIENT_SECRET, encodedClientSecret);
            sortedMap.put(CODE, code);
            sortedMap.put(JIVE_SIGNATURE_URL, jiveSignatureURL);
            sortedMap.put(JIVE_URL, jiveUrl);
            sortedMap.put(SCOPE, scope);
            sortedMap.put(TENANT_ID, tenantId);
            sortedMap.put(TIMESTAMP, timestamp);
            return sortedMap;
        } // end toSortedMap

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add(TENANT_ID, tenantId)
                    .add(JIVE_SIGNATURE_URL, jiveSignatureURL)
                    .add(TIMESTAMP, timestamp)
                    .add(JIVE_URL, jiveUrl)
                    .add(JIVE_SIGNATURE, jiveSignature)
                    .add(SCOPE, scope)
                    .add(CODE, code)
                    .add(CLIENT_ID, clientId)
                    //TODO: COMMENT OUT
                    .add(CLIENT_SECRET, clientSecret)
                    .toString();
        } // end toString

} // end class
