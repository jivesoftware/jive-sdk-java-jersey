package com.jivesoftware.addon.example.storage.file.storage.models;

import java.io.Serializable;

/**
 * <p>This entity is the response object for {@link ExStorageMembershipOperationEntity} which is recieved by the
 * endpoint for container's bulkMembership resource</p>
 */
public class ExStorageMembershipOperationResultEntity implements Serializable {
    private boolean success;
    private String action;
    private Integer operationId;
    private ExStorageMembershipEntity item;

    /**
     * <p>This specifies {@link ExStorageMembershipEntity} in question for the operation.</p>
     */
    public ExStorageMembershipEntity getItem() {
        return item;
    }

    public void setItem(ExStorageMembershipEntity item) {
        this.item = item;
    }

    /**
     * <p>This is the action ID for the current operation. Should match the operation's {@link ExStorageMembershipOperationEntity#operationId}</p>
     */
    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    /**
     * Returns true if this operation succeeded
     */
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * <p>This specifies the action done on the member. Possible values are: Add | Update | Delete</p>
     */
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
