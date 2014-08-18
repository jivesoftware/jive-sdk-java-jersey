package com.jivesoftware.addon.example.storage.file.storage.models;


public class ExStorageMembershipEntity extends ExStorageResourcefulDTO
    implements ExternallyIdentifiable
{
    public enum Resources {
        /**
         * <p>Called when a comment has been updated in Jive. The ESP should edit its corresponding comment to match.</p>
         *
         * @takes {@link ExStorageCommentEntity} of the updated comment data
         * @returns {@link ExStorageCommentEntity} as was written to ESP
         */
        delete,
    }

    private String externalId;
    private String permission;
    private ExStorageUserEntity user;
    private ExStorageGroupEntity group;

    public ExStorageMembershipEntity() { }

    /**
     * <p>The member's permission for content in the container. Possible values are: none | read | write | full</p>
     */
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * <p>The external ID given to this object by the ESP. This is an arbitrary string that uniquely identifies the
     * object in the provider.</p>
     */
    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId(String externalId)
    {
        this.externalId = externalId;
    }

    /**
     * <p>The {@link ExStorageUserEntity} for this membership. Populate either this or {@link ExStorageMembershipEntity#group}</p>
     */
    public ExStorageUserEntity getUser() {
        return user;
    }

    public void setUser(ExStorageUserEntity user) {
        this.user = user;
    }

    /**
     * <p>The {@link ExStorageGroupEntity} for this membership. Populate either this or {@link ExStorageMembershipEntity#user}</p>
     */
    public ExStorageGroupEntity getGroup() {
        return group;
    }

    public void setGroup(ExStorageGroupEntity group) {
        this.group = group;
    }

    public void setExStorageEntity(ExStorageEntityDTO exStorageEntity) {
        if (exStorageEntity instanceof ExStorageUserEntity) {
            this.setUser((ExStorageUserEntity) exStorageEntity);
        } else {
            this.setGroup((ExStorageGroupEntity) exStorageEntity);
        }
    }

    public ExStorageEntityDTO getExStorageEntity() {
        if (user != null) {
            return user;
        }
        if (group != null) {
            return group;
        }
        return null;
    }

    @Override
    public String toString() {
        return "ExStorageMembershipEntity{" +
                "externalId='" + externalId + '\'' +
                ", permission='" + permission + '\'' +
                ", user=" + user +
                ", group=" + group +
                '}';
    }
}