package org.lustin.trac.xmlprc2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Hashtable;

public class TicketImpl {
	private long id = -1;
	private Calendar createdTime = null;
	private Calendar changedTime = null;
	
	private Hashtable<String, String> attributes = null;
	
	private Hashtable<String, TicketPropertyImpl> fields = null;
	private String summary;
	private String keywords;
	private StatusImpl status;
	private ResolutionImpl resolution;
	private TypeImpl type;
	private VersionImpl version;
	private MilestoneImpl milestone;
	private String reporter;
	private Calendar time;
	private PriorityImpl priority;
	private ComponentImpl component;
	private String description;
	private String owner;
	private Calendar changeTime;
	private String cc;
	private Calendar ts;
	
	public TicketImpl() {
	}

	public long getId() {
		return id;
	}

	public void setId(long returnedId) {
		this.id = returnedId;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public Calendar getChangedTime() {
		return changedTime;
	}

	public void setChangedTime(Calendar changedTime) {
		this.changedTime = changedTime;
	}

	public Hashtable<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Hashtable<String, String> attributes) {
		this.attributes = attributes;
	}

	public Hashtable<String, TicketPropertyImpl> getFields() {
		return fields;
	}

	public void setFields(Hashtable<String, TicketPropertyImpl> fields) {
		this.fields = fields;
	}

	public void setSummary(String stringValue) {	
		this.summary = stringValue;
	}

	public void setKeywords(String stringValue) {
		this.keywords = stringValue;
	}

	public void setStatus(StatusImpl statusImpl) {
		this.status = statusImpl;
	}

	public void setResolution(ResolutionImpl resolutionImpl) {
		this.resolution = resolutionImpl;
	}

	public void setType(TypeImpl typeImpl) {
		this.type = typeImpl;
	}

	public void setVersion(VersionImpl versionImpl) {
		this.version = versionImpl;
	}

	public void setMilestone(MilestoneImpl milestoneImpl) {
		this.milestone = milestoneImpl;
	}

	public void setReporter(String stringValue) {
		this.reporter = stringValue;
	}

	public void setTime(Calendar timeValue) {
		this.time = timeValue;
	}

	public void setComponent(ComponentImpl componentImpl) {
		this.component = componentImpl;
	}

	public void setPriority(PriorityImpl priorityImpl) {
		this.priority = priorityImpl;
	}

	public void setDescription(String stringValue) {
		this.description = stringValue;
	}

	public void setOwner(String stringValue) {
		this.owner = stringValue;
	}

	public void setChangeTime(Calendar timeValue) {
		this.changeTime = timeValue; 
	}

	public String getSummary() {
		return summary;
	}

	public String getKeywords() {
		return keywords;
	}

	public StatusImpl getStatus() {
		return status;
	}

	public ResolutionImpl getResolution() {
		return resolution;
	}

	public TypeImpl getType() {
		return type;
	}

	public VersionImpl getVersion() {
		return version;
	}

	public MilestoneImpl getMilestone() {
		return milestone;
	}

	public String getReporter() {
		return reporter;
	}

	public Calendar getTime() {
		return time;
	}

	public PriorityImpl getPriority() {
		return priority;
	}

	public ComponentImpl getComponent() {
		return component;
	}

	public String getDescription() {
		return description;
	}

	public String getOwner() {
		return owner;
	}

	public Calendar getChangeTime() {
		return changeTime;
	}

	public String getCc() {
		return cc;
	}

	public Calendar getTs() {
		return ts;
	}

	public void setCC(String stringValue) {
		this.cc = stringValue;
	}

	public void setTS(Calendar timeValue) {
		this.ts = timeValue;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof TicketImpl) {
			TicketImpl ticket = (TicketImpl) o;
			return getId() == ticket.getId() && 
			getSummary().equals(ticket.getSummary()) &&
			getKeywords().equals(ticket.getKeywords()) &&
			getStatus().equals(ticket.getStatus()) &&
			getType().equals(ticket.getType()) &&
			getVersion().equals(ticket.getVersion()) &&
			getMilestone().equals(ticket.getMilestone()) &&
			getReporter().equals(ticket.getReporter()) && 
			getTime().equals(ticket.getTime()) &&
			getPriority().equals(ticket.getPriority()) &&
			getComponent().equals(ticket.getComponent()) && 
			getDescription().equals(ticket.getDescription()) &&
			getOwner().equals(ticket.getOwner());
		}
		return false;
	}
	
	public static Comparator<TicketImpl> getPriorityOrderComparator(){
		return new Comparator<TicketImpl>() {			
			@Override
			public int compare(TicketImpl ticket1, TicketImpl ticket2) {
				int priority1=0;
				int priority2=0;
				String priorityString1 = ticket1.getPriority().getName();
				String priorityString2 = ticket2.getPriority().getName();
				if("blocker".equals(priorityString1)){
					priority1 = 1;
				}else if("critical".equals(priorityString1)){
					priority1 = 2;
				}else if("major".equals(priorityString1)){
					priority1 = 3;
				}else if("minor".equals(priorityString1)){
					priority1 = 4;
				}else if("trivial".equals(priorityString1)){
					priority1 = 5;
				}
				if("blocker".equals(priorityString2)){
					priority2 = 1;
				}else if("critical".equals(priorityString2)){
					priority2 = 2;
				}else if("major".equals(priorityString2)){
					priority2 = 3;
				}else if("minor".equals(priorityString2)){
					priority2 = 4;
				}else if("trivial".equals(priorityString2)){
					priority2 = 5;
				}
				if(priority1 == priority2) return 0;
				return (priority1<priority2)?-1:1;
			}
		};
	}
	
	public String toString(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return "Ticket: " + getId() +
		"\nSummary: " + getSummary() + 
		"\nKeywords: " + getKeywords() +
		"\nStatus: " + getStatus() +
		"\nType: " + getType() +
		"\nVersion: " + getVersion() +
		"\nMilestone:" + getMilestone() +
		"\nReporter: " + getReporter() + 
		"\nTime: " + format.format(getTime().getTime()) +
		"\nPriority: " + getPriority() +
		"\nComponent: " + getComponent() +
		"\nDescription: " + getDescription() +
		"\nOwner: " + getOwner() +
		"\nChangeTime: " + format.format(getChangeTime().getTime()) +
		"\nCC: " + getCc() +
		"\nTS: " + format.format(getTs().getTime());
	}
}
