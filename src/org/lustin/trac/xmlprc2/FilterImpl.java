package org.lustin.trac.xmlprc2;

public class FilterImpl {
	private String name = null;
	private String description = null;
	public FilterImpl(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return getClass().getSimpleName() + ": Name: " + getName() + " Description: " + getDescription();
	}
}
