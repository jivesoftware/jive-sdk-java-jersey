package com.jivesoftware.addon.example.storage.file.storage.models;


/**
 * Created with IntelliJ IDEA.
 * User: lev.waisberg
 * Date: 8/26/13
 * Time: 2:33 PM
 */
public class ResourceDTO {
    private String name;
    private String url;
    private String[] verbs;

    public ResourceDTO()
    {

    }

    public ResourceDTO(String name, String url, String... verbsArg) {
        this.name = name;
        this.url = url;
        this.verbs = verbsArg;
    }

    public ResourceDTO(ResourceDTO other) {
        this.name = other.name;
        this.url = other.url;
        this.verbs = other.verbs;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getVerbs() {
        return verbs;
    }

    public void setVerbs(String[] verbs) {
        this.verbs = verbs;
    }
}
