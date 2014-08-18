package com.jivesoftware.addon.example.storage.file.storage.models;


/**
 * Created with IntelliJ IDEA.
 * User: bmoshe
 * Date: 05/27/13
 * Time: 11:51
 */
public class ExStorageUserEntity extends ExStorageEntityDTO {
    private String userMappingOption;
    private String userMappingValue;
    private String displayName;

    public ExStorageUserEntity()
    {
        super();
    }

    public ExStorageUserEntity(ExStorageUserEntity toCopy) {
        super(toCopy);
        this.userMappingOption = toCopy.userMappingOption;
        this.userMappingValue = toCopy.userMappingValue;
        this.displayName = toCopy.displayName;
    }

    /**
     * <p>The user mapping option for matching users in Jive. This controls the method which Jive would use to match
     * users from its infrastructure to ESP users</p>
     * <p>
     *     Can be either:
     *     <ul>
     *         <li>{@link com.jivesoftware.community.integration.storage.usermapping.UserMappingOptionType#EMAIL}</li>
     *         <li>{@link com.jivesoftware.community.integration.storage.usermapping.UserMappingOptionType#USERNAME}</li>
     *         <li>{@link com.jivesoftware.community.integration.storage.usermapping.UserMappingOptionType#CUSTOM}</li>
     *     </ul>
     * </p>
     */
    public String getUserMappingOption()
    {
        return userMappingOption;
    }

    public void setUserMappingOption(String userMappingOption)
    {
        this.userMappingOption = userMappingOption;
    }

    /**
     * <p>The value for which user mapping will try to match a user. For exaple, if the
     * {@link com.jivesoftware.addon.example.storage.file.storage.models.ExStorageUserEntity#getUserMappingOption()} is set
     * to EMAIL, this value should be the user's email address.</p>
     */
    public String getUserMappingValue()
    {
        return userMappingValue;
    }

    public void setUserMappingValue(String userMappingValue)
    {
        this.userMappingValue = userMappingValue;
    }

    /**
     * <p>The user's display name. This value will be shown in case the user can't be mapped to a Jive user.</p>
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

    @Override
    public String toString() {
        return "ExStorageUserEntity{" +
                "userMappingOption='" + userMappingOption + '\'' +
                ", userMappingValue='" + userMappingValue + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
