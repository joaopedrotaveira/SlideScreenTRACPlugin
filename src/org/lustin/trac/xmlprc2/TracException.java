package org.lustin.trac.xmlprc2;

public class TracException extends Exception {

	private static final long serialVersionUID = 74477248326425989L;

	public TracException() {
		super();
	}

	public TracException(String detailMessage) {
		super(detailMessage);
	}

	public TracException(Throwable throwable) {
		super(throwable);
	}

	public TracException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
