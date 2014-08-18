package com.jivesoftware.addon.example.storage.file.storage.models;


/**
 * Entity representing an external-storage container.
 */
public class ExStorageContainerEntity
implements CoreIdentifiable, ExternallyIdentifiable
{
    /**
     * Enumerates the different types of container permissions
     */
    public enum PermissionType {
        SPACE("space"),
        GROUP_PRIVATE("private-group"),
        GROUP_MEMBER_ONLY("members-only-group"),
        GROUP_OPEN("open-group"),
        SECRET("secret-group"),
        PROJECT("project");

        private final String name;

        PermissionType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Long id;
    private String externalId;
    private String name;
    private String description;
    private String permissionType;
    private String nonMemberPermission;
    private String userMapping;

    public ExStorageContainerEntity() {

    }

    /**
     * <p>The core-v3 Id of the place in Jive.</p>
     */
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId(String externalId)
    {
        this.externalId = externalId;
    }

    /**
     * <p>The name of the place in Jive.</p>
     */
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * <p>The description of the place in Jive.</p>
     */
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * <p>The permission-type of the place in Jive. The permissions should be synced to ESP container during register.
     * Can be either:  "space","private-group","members-only-group","open-group","secret-group","project"</p>
     */
    public String getPermissionType()
    {
        return permissionType;
    }

    public void setPermissionType(String permissionType)
    {
        this.permissionType = permissionType;
    }

    /**
     * <p>The permission-type for non-member users of the place in Jive. . The permissions should be synced to ESP container during register.
     * Can be either: "none","read","write","full"</p>
     */
    public String getNonMemberPermission() {
        return nonMemberPermission;
    }

    public void setNonMemberPermission(String nonMemberPermission) {
        this.nonMemberPermission = nonMemberPermission;
    }

    public String getUserMapping() {
        return userMapping;
    }

    public void setUserMapping(String userMapping) {
        this.userMapping = userMapping;
    }
}