package org.lustin.trac.xmlprc2;

import java.util.Calendar;

public class SearchResultImpl {
	private String href = null;
	private String title = null;
	private Calendar date = null;
	private String author = null;
	private String excerpt = null;
	
	public SearchResultImpl() {

	}
	
	public SearchResultImpl(String href, String title, Calendar date,
			String author, String excerpt) {
		super();
		this.href = href;
		this.title = title;
		this.date = date;
		this.author = author;
		this.excerpt = excerpt;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}	
}
