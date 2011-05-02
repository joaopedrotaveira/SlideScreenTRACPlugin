package org.lustin.trac.xmlprc2;

public class ApiVersionImpl {
	private String epoch = null;
	private Number major = null;
	private Number minor = null;
	
	public ApiVersionImpl() {
	}

	public ApiVersionImpl(String epoch, Number major, Number minor) {
		super();
		this.epoch = epoch;
		this.major = major;
		this.minor = minor;
	}

	public String getEpoch() {
		return epoch;
	}

	public void setEpoch(String epoch) {
		this.epoch = epoch;
	}

	public Number getMajor() {
		return major;
	}

	public void setMajor(Number major) {
		this.major = major;
	}

	public Number getMinor() {
		return minor;
	}

	public void setMinor(Number minor) {
		this.minor = minor;
	}
	public String toString(){
		return String.format("%s: %d.%d",getEpoch(),getMajor(),getMinor());
	}
}
