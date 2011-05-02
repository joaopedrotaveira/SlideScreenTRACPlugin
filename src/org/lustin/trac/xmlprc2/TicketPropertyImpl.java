package org.lustin.trac.xmlprc2;

public abstract class TicketPropertyImpl {
	private String name = null;
	
	public TicketPropertyImpl(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return String.format("%s: %s", getClass().getSimpleName(),getName());
	}
}
