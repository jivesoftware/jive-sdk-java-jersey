package com.jivesoftware.addon.example.storage.file.storage.models;

import java.io.Serializable;

/**
 * <p>This entity represents a single member operation (add member, remove, change permissions) that can occur on a container</p>
 */
public class ExStorageMembershipOperationEntity implements Serializable {
    private String action;
    private String id;
    private Integer operationId;
    private ExStorageMembershipEntity item;

    /**
     * <p>This specifies the action that needs to be done on the member. Possible values are: Add | Update | Delete</p>
     */
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    /**
     * This is the external ID that was given to the membership by ESP. New memberships will not have any, and the Jive
     * server expects that the value would be populated for the in the response.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * <p>This specifies {@link ExStorageMembershipEntity} in question for the operation. Delete actions will be sent
     * with this value empty. In that case, the membership needs to be removed by its external ID</p>
     */
    public ExStorageMembershipEntity getItem() {
        return item;
    }

    public void setItem(ExStorageMembershipEntity item) {
        this.item = item;
    }

    /**
     * <p>This is the action ID for the current operation. The ESP would need to return this ID in the response to the
     * bulk update call in order to let the Jive server know which answer correlates to what outgoing operation</p>
     */
    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    @Override
    public String toString() {
        return "ExStorageMembershipOperationDTO{" +
                "action='" + action + '\'' +
                ", id='" + id + '\'' +
                ", operationId=" + operationId +
                ", item=" + item +
                '}';
    }
}
