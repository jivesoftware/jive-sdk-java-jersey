package com.jivesoftware.addon.example.storage.file.storage.models;

import java.util.Collection;

/**
 * <p>The entity to send in the "register" container API request defined in {@link com.jivesoftware.community.extension.updater.dataobjects.StorageDefinitionDataEntity#registerPlace}</p>
 */
public class ExStorageContainerRegistrationRequestEntity
{
    private ExStorageContainerEntity container;

    private String config;

    private String oauthCode;
    private String userMapping;
    private String containerApiSuffix;
    private String jiveUrl;
    private String[] containerHierarchy;

    private Collection<ExStorageMembershipOperationEntity> memberOperations;

    /**
     * <p>The details of the place in Jive being registered.</p>
     */
    public ExStorageContainerEntity getContainer()
    {
        return container;
    }

    public void setContainer(ExStorageContainerEntity container)
    {
        this.container = container;
    }

    /**
     * <p>The JSON config that should be used in this register request.
     * This will be the JSON returned from {@link com.jivesoftware.community.extension.updater.dataobjects.StorageDefinitionDataEntity#configPlace} html page.
     * If not exist or user didn't click on the 'Configure' button, it will use the JSON from {@link com.jivesoftware.community.extension.updater.dataobjects.StorageDefinitionDataEntity#configInstance} html page.</p>
     */
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    /**
     * <p>An OAuth2 access-code that can be used to do ESP -to- Jive API calls in the context of this container (only for calls under the url prefix stated in {@link #containerApiSuffix}).
     * To get tokens for this access-code, please call JIVE-URL/oauth2/refresh (JIVE-URL is given in {@link #jiveUrl}</p>
     */
    public String getOauthCode() {
        return oauthCode;
    }

    public void setOauthCode(String oauthCode) {
        this.oauthCode = oauthCode;
    }

    /**
     * <p>The user mapping option to be used in communication of Jive -to/from- ESP for this container.
     * can be either: "EMAIL","USERNAME","CUSTOM"</p>
     */
    public String getUserMapping()
    {
        return userMapping;
    }

    public void setUserMapping(String userMapping)
    {
        this.userMapping = userMapping;
    }

    /**
     * <p>Suffix to the {@link #jiveUrl} for APIs against the jive place. Used by ESP to build the URL paths. Will be something like /api/jivelinks/v1/exstorage/containers/{id}</p>
     */
    public String getContainerApiSuffix()
    {
        return containerApiSuffix;
    }

    public void setContainerApiSuffix(String containerApiSuffix)
    {
        this.containerApiSuffix = containerApiSuffix;
    }


    /**
     * <p>The current state of members in the jive place being registerd. The membership should be synced to the associated container in the ESP.</p>
     */
    public Collection<ExStorageMembershipOperationEntity> getMemberOperations()
    {
        return memberOperations;
    }

    public void setMemberOperations(Collection<ExStorageMembershipOperationEntity> memberOperations)
    {
        this.memberOperations = memberOperations;
    }

    /**
     * <p>A url to the jive instance. To be used when doing calls back from ESP to Jive.</p>
     */
    public String getJiveUrl() {
        return jiveUrl;
    }

    public void setJiveUrl(String jiveUrl) {
        this.jiveUrl = jiveUrl;
    }

    /**
     * <p>An hierarchy of container-names if such exist for the registered Jive Place. Relevant only for spaces.</p>
     */
    public String[] getContainerHierarchy() {
        return containerHierarchy;
    }

    public void setContainerHierarchy(String[] containerHierarchy) {
        this.containerHierarchy = containerHierarchy;
    }
}
