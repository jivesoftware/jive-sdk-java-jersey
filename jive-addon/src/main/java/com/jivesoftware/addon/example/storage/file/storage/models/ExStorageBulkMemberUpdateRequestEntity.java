package com.jivesoftware.addon.example.storage.file.storage.models;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * This entity is used for bulk update of container's memberships. When container members have changed, the server would
 * invoke a call to the container's bulkMembership resource with this entity. The ESP in turn would need to execute the
 * same operations on its side and return an answer for each of the operations.
 */
public class ExStorageBulkMemberUpdateRequestEntity implements Serializable {
    private List<ExStorageMembershipOperationEntity> operations = Lists.newArrayList();

    /**
     * A list of operations that need to be done on memberships. This
     */
    public List<ExStorageMembershipOperationEntity> getOperations() {
        return operations;
    }

    public void setOperations(List<ExStorageMembershipOperationEntity> operations) {
        this.operations = operations;
    }
}
