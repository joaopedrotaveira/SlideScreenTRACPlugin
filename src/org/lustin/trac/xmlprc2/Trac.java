package org.lustin.trac.xmlprc2;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.System;

import android.content.Context;


import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;

public class Trac {
	private JSONRPC2Session session = null;
	private TrackerDynamicProxy tracker = null;
	private Context context = null;
	
	public Trac(Context context, String url, String username, String password) {
		try {
			this.context = context;
			this.session = new JSONRPC2Session(new URL(url), username, password);
			this.tracker = new TrackerDynamicProxy(this.session);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<FilterImpl> getSearchFilters(){
		List<FilterImpl> retObj = new ArrayList<FilterImpl>();
		Search search = (Search) tracker.newInstance(this.context.getClassLoader(), Search.class);
        JSONArray filters = search.getSearchFilters();
        for(int i = 0 ; i<filters.size(); i++){
        	JSONArray returned =  (JSONArray) filters.get(i);
        	String name = (String) returned.get(0);
        	String description = (String) returned.get(1);
        	FilterImpl filter = new FilterImpl(name, description);
        	System.out.println(filter);
        	retObj.add(filter);
        }
        return retObj;
	}
	
//	public List<SearchResultImpl> performSearch(String query, List<FilterImpl> filters){}
//	
	public ApiVersionImpl systemGetAPIVersion(){
		org.lustin.trac.xmlprc2.System system = (org.lustin.trac.xmlprc2.System) tracker.newInstance(this.context.getClassLoader(), org.lustin.trac.xmlprc2.System.class);
		JSONArray versions = system.getAPIVersion();
		ApiVersionImpl apiVersion = new ApiVersionImpl();
		String epoch = null;
		Number epochNumber = null;
		Number major = null;
		Number minor = null;
		
		epochNumber = (Long) versions.get(0);
		major = (Long) versions.get(1);
		minor = (Long) versions.get(2);
		
		switch(epochNumber.intValue()){
		case 0:
			epoch = "Trac 0.10";
			break;
		case 1:
			epoch = "Trac 0.11 or Higher";
			break;
		}
		apiVersion.setEpoch(epoch);
		apiVersion.setMajor(major);
		apiVersion.setMinor(minor);
		return apiVersion;
	}
//
//	public List<String> systemListMethods(){}
//	
//	public String systemMethodHelp(String method){}
//	
//	//public List<List<String>> systemMethodSignature(String method){}
//	
//	//public ? systemMultiCall(Signatures);
//	
	public TicketImpl getTicket(long id){
		Ticket ticket = (Ticket) tracker.newInstance(this.context.getClassLoader(),Ticket.class);
		TicketImpl ticketImpl = new TicketImpl();
		
		JSONArray result = ticket.get(id);
		Long returnedId = (Long) result.get(0);
//		JSONObject time_created = (JSONObject) result.get(1);
//		JSONObject time_changed = (JSONObject) result.get(2);
		JSONObject attributes = (JSONObject) result.get(3);
		ticketImpl.setId(returnedId);
		
		for(Object key: attributes.keySet()){
			String stringValue = null;
			Calendar timeValue = Calendar.getInstance();
			Object value = attributes.get(key);
			
			if(value instanceof String){
				stringValue = (String) value;
			}else{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				JSONObject object = (JSONObject) value;
				String date = (String)((JSONArray)(object.get("__jsonclass__"))).get(1);
				try {
					timeValue.setTime(format.parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if("summary".equals(key)){
				ticketImpl.setSummary(stringValue);
			}else if("keywords".equals(key)){
				ticketImpl.setKeywords(stringValue);
			}else if("status".equals(key)){
				ticketImpl.setStatus(new StatusImpl(stringValue));
			}else if("resolution".equals(key)){
				ticketImpl.setResolution(new ResolutionImpl(stringValue));
			}else if("type".equals(key)){
				ticketImpl.setType(new TypeImpl(stringValue));
			}else if("version".equals(key)){
				ticketImpl.setVersion(new VersionImpl(stringValue));
			}else if("milestone".equals(key)){
				ticketImpl.setMilestone(new MilestoneImpl(stringValue));
			}else if("reporter".equals(key)){
				ticketImpl.setReporter(stringValue);
			}else if("time".equals(key)){
				ticketImpl.setTime(timeValue);
			}else if("component".equals(key)){
				ticketImpl.setComponent(new ComponentImpl(stringValue));
			}else if("priority".equals(key)){				
				ticketImpl.setPriority(new PriorityImpl(stringValue));
			}else if("description".equals(key)){
				ticketImpl.setDescription(stringValue);
			}else if("owner".equals(key)){
				ticketImpl.setOwner(stringValue);
			}else if("changetime".equals(key)){
				ticketImpl.setChangeTime(timeValue);
			}else if("cc".equals(key)){
				ticketImpl.setCC(stringValue);
			}else if("_ts".equals(key)){
				ticketImpl.setTS(timeValue);
			}
		}
		return ticketImpl;
	}
	
	public List<ComponentImpl> getAllComponents(){
		Ticket.Component component = (Ticket.Component) tracker.newInstance(this.context.getClassLoader(), Ticket.Component.class);
		List<ComponentImpl> components = new ArrayList<ComponentImpl>();
		JSONArray result = component.getAll();
		for(int i = 0; i<result.size();i++){
			ComponentImpl componentImpl = new ComponentImpl((String)result.get(i));
			components.add(componentImpl);			
		}
		return components;
	}

	public List<MilestoneImpl> getAllMilestones(){
		Ticket.Milestone milestone = (Ticket.Milestone) tracker.newInstance(this.context.getClassLoader(), Ticket.Milestone.class);
		List<MilestoneImpl> milestones = new ArrayList<MilestoneImpl>();
		JSONArray result = milestone.getAll();
		for(int i = 0; i<result.size();i++){
			MilestoneImpl milestoneImpl = new MilestoneImpl((String)result.get(i));
			milestones.add(milestoneImpl);			
		}
		return milestones;
	}

	public List<PriorityImpl> getAllPriorities(){
		Ticket.Priority priority = (Ticket.Priority) tracker.newInstance(this.context.getClassLoader(), Ticket.Priority.class);
		List<PriorityImpl> priorities = new ArrayList<PriorityImpl>();
		JSONArray result = priority.getAll();
		for(int i = 0; i<result.size();i++){
			PriorityImpl priorityImpl = new PriorityImpl((String)result.get(i));
			priorities.add(priorityImpl);			
		}
		return priorities;
	}

	public List<ResolutionImpl> getAllResolutions(){
		Ticket.Resolution resolution = (Ticket.Resolution) tracker.newInstance(this.context.getClassLoader(), Ticket.Resolution.class);
		List<ResolutionImpl> resolutions = new ArrayList<ResolutionImpl>();
		JSONArray result = resolution.getAll();
		for(int i = 0; i<result.size();i++){
			ResolutionImpl resolutionImpl = new ResolutionImpl((String)result.get(i));
			resolutions.add(resolutionImpl);			
		}
		return resolutions;
	}
	
	public List<SeverityImpl> getAllSeverities(){
		Ticket.Severity severity = (Ticket.Severity) tracker.newInstance(this.context.getClassLoader(), Ticket.Severity.class);
		List<SeverityImpl> severities = new ArrayList<SeverityImpl>();
		JSONArray result = severity.getAll();
		for(int i = 0; i<result.size();i++){
			SeverityImpl severityImpl = new SeverityImpl((String)result.get(i));
			severities.add(severityImpl);			
		}
		return severities;
	}
	
	public List<TypeImpl> getAllTypes(){
		Ticket.Type type = (Ticket.Type) tracker.newInstance(this.context.getClassLoader(), Ticket.Type.class);
		List<TypeImpl> types = new ArrayList<TypeImpl>();
		JSONArray result = type.getAll();
		for(int i = 0; i<result.size();i++){
			TypeImpl typeImpl = new TypeImpl((String)result.get(i));
			types.add(typeImpl);			
		}
		return types;
	}
	
	public List<VersionImpl> getAllVersions(){
		Ticket.Version version = (Ticket.Version) tracker.newInstance(this.context.getClassLoader(), Ticket.Version.class);
		List<VersionImpl> versions = new ArrayList<VersionImpl>();
		JSONArray result = version.getAll();
		for(int i = 0; i<result.size();i++){
			VersionImpl versionImpl = new VersionImpl((String)result.get(i));
			versions.add(versionImpl);			
		}
		return versions;
	}

	public List<StatusImpl> getAllStatus(){
		Ticket.Status status = (Ticket.Status) tracker.newInstance(this.context.getClassLoader(), Ticket.Status.class);
		List<StatusImpl> statuss = new ArrayList<StatusImpl>();
		JSONArray result = status.getAll();
		for(int i = 0; i<result.size();i++){
			StatusImpl statusImpl = new StatusImpl((String)result.get(i));
			statuss.add(statusImpl);			
		}
		return statuss;
	}

	
		
	public List<TicketImpl> query(String query) throws TracException {
		Ticket ticketQuery = (Ticket) tracker.newInstance(this.context.getClassLoader(), Ticket.class);
		List<TicketImpl> tickets = new ArrayList<TicketImpl>();
		
		JSONArray result = null;
//		if(query == null || "".equals(query)){
//			result = ticketQuery.query();
//		}else{
//			result = ticketQuery.query(query);
//		}
		result = ticketQuery.query(query);
		for(int i = 0; i<result.size();i++){
			long id = (Long) result.get(i);
			TicketImpl ticketImpl = getTicket(id);
			tickets.add(ticketImpl);
		}
		
		return tickets;
	}
//
//	public List<TicketImpl> getRecentChanges(Calendar since){}
}
