package com.zillow.jenkins_aggregate.remote_job;

import hudson.model.ItemGroup;
import hudson.model.ViewJob;
import java.io.IOException;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 *
 * @author chrisste
 */
public class RemoteJob extends ViewJob {
    protected String remote_url;

    @DataBoundConstructor
    public RemoteJob(ItemGroup parent, String name) {
        super(parent, name);
        
        remote_url = "blank_url";
    }
    
    public RemoteJob(ItemGroup parent, String name, String job_url) {
        super(parent, name);
        remote_url = job_url;
    }
    
    /*@Override
    public String getName() {
        return this.name;
    }*/
    
    @Override
    public void save() throws IOException{
        super.save();
        
    }

    @Override
    protected void reload() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getRemoteUrl() {
        return this.remote_url;
    }
    
    public void setRemoteUrl(String url) {
        this.remote_url = url;
    }
    
}
