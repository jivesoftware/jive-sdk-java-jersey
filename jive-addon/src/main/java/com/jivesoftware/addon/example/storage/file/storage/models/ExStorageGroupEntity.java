package com.jivesoftware.addon.example.storage.file.storage.models;


/**
 * This entity represents a group of users in Jive.
 */
public class ExStorageGroupEntity extends ExStorageEntityDTO {
    private String displayName;
    private String delegationId;
    private String specialGroupId;

    public ExStorageGroupEntity()
    {
        super();
    }

    public ExStorageGroupEntity(ExStorageGroupEntity toCopy) {
        super(toCopy);
        this.displayName = toCopy.displayName;
    }

    /**
     * <p>The group's display name</p>
     */
    @Override
    public String getDisplayName()
    {
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * If the group is a delegated Active Directory group, this property will contain the ID for this group in AD.
     */
    public String getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(String delegationId) {
        this.delegationId = delegationId;
    }

    /**
     * @return possible values are: null (when delegationId!=null) | Everyone | AnonymousUsersGroup | RegisteredUsersGroup | AnyUsersGroup
     */
    public String getSpecialGroupId() {
        return specialGroupId;
    }

    public void setSpecialGroupId(String specialGroupId) {
        this.specialGroupId = specialGroupId;
    }

    @Override
    public String toString() {
        return "ExStorageGroupDTO{" +
                "displayName='" + displayName + '\'' +
                ", delegationId='" + delegationId + '\'' +
                ", specialGroupId='" + specialGroupId + '\'' +
                '}';
    }
}
