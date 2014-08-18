package com.jivesoftware.addon.example.storage.file.storage.models;


/**
 * Created with IntelliJ IDEA.
 * User: varkel
 * Date: 7/2/13
 * Time: 1:29 PM
 */
public abstract class ExStorageResourcefulDTO
{
    private ResourceDTO[] resources;

    protected ExStorageResourcefulDTO()
    {
    }

    protected ExStorageResourcefulDTO(ExStorageResourcefulDTO toCopy)
    {
        if(toCopy.resources == null)
            this.resources = null;

        else
        {
            int numResources = toCopy.resources.length;
            this.resources = new ResourceDTO[numResources];
            for(int i = 0; i < numResources; i++)
            {
                ResourceDTO resourceToCopy = toCopy.resources[i];
                this.resources[i] = new ResourceDTO(resourceToCopy);
            }
        }
    }

    public ResourceDTO[] getResources()
    {
        return resources;
    }

    public void setResources(ResourceDTO[] resources)
    {
        this.resources = resources;
    }
}
