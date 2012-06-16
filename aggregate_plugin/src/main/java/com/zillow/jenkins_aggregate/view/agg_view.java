package com.zillow.jenkins_aggregate.view;

import hudson.Extension;
import hudson.model.*;
import hudson.model.ViewDescriptor;
import hudson.model.Job;
import hudson.model.TopLevelItem;
import java.util.ArrayList;
import java.util.List;

import com.zillow.jenkins_aggregate.remote_job.RemoteJob;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 *
 * @author chrisste
 */
public class agg_view extends ListView {
    private ArrayList<RemoteJob> remoteJobList;
    // things here
    
    @DataBoundConstructor
    public agg_view(String name) {
        super(name);
    }
    
    public agg_view(String name, ViewGroup owner) {
        super(name, owner);
    }
    
    /*@Override
    public Collection<TopLevelItem> getItems() {
        //probably going to have to overload to get items stored
        return super.getItems();
    }*/
    
    public String getSampleString() {
        List<TopLevelItem> itemList = super.getItems();
        for (int i = 0; i < itemList.size(); i++) {
            Job job = (Job) itemList.get(i);
            
        }
        return "a returned string";
    }
    
    public String doMyString() {
        return "foobar";
    }
    
    @Override
    protected synchronized void submit(StaplerRequest req) throws IOException,
    ServletException, Descriptor.FormException {
        super.submit(req);
        
        // Remove all Unchecked Jobs
        ArrayList<RemoteJob> delJobs = new ArrayList<RemoteJob>();
        for(RemoteJob job : remoteJobList)
        {
            if (req.getParameter(job.getName()) == null ) {
                delJobs.add(job);
            }
                //remoteJobList.remove(job);
        }
        if (! delJobs.isEmpty())
            remoteJobList.removeAll(delJobs);
        
        // Add all new jobs
        String newJobUrls = req.getParameter("newjoburl");        
        remoteJobList.add(new RemoteJob(this.getOwner().getItemGroup(), "jobname", newJobUrls));
        
        //TODO - iterate through the checkboxes and remove jobs that aren't checked.
        //for each checkbox, etc...
        //look into the list view that does it more elegantly
        Collection<? extends TopLevelItem> items = getOwnerItemGroup().getItems();
        
    }
    
    public ArrayList getJobList() {
        if (remoteJobList == null)
        {
            remoteJobList = new ArrayList();
            //remoteJobList.add(new RemoteJob(this.getOwner().getItemGroup(), "name1"));
        }
        
        return remoteJobList;
    }
    
    @Override
    public void save() throws IOException {
        super.save();
        
        //String s = formData.optString("name", "default");
    }
    
    //TODO - overload the load() method to pull data from disk
    
    @Extension
    public static final class DescriptorImpl extends ViewDescriptor {
        
        @Override
        public boolean isInstantiable() {
            // TODO - if user is logged in.
            return true;
        }
        
        @Override
        public String getDisplayName() {
            return "A personal view of aggregated jobs.";
        }

    }
    
}
