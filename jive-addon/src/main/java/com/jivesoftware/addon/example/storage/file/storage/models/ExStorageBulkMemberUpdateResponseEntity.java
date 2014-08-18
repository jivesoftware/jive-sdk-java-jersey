package com.jivesoftware.addon.example.storage.file.storage.models;

import java.util.List;

/**
 * <p>This entity is the response entity for container's bulkMembership resource call.</p>
 */
public class ExStorageBulkMemberUpdateResponseEntity {
    private List<ExStorageMembershipOperationResultEntity> operations;

    /**
     * A list of response objects to each of the operation specified in the {@link ExStorageBulkMemberUpdateRequestEntity#operations}
     */
    public List<ExStorageMembershipOperationResultEntity> getOperations() {
        return operations;
    }

    public void setOperations(List<ExStorageMembershipOperationResultEntity> operations) {
        this.operations = operations;
    }

}
