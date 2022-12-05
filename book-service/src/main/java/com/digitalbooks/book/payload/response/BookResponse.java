package com.digitalbooks.book.payload.response;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name="TBL_BOOKS")
public class BookResponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private byte[] logo; 
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="CATEGORY")
	private String category;
	
	@Column(name="PRICE")
	private double price;
	
	@Column(name="AUTHOR")
	private int authorId;
	
	@Column(name="PUBLISHER")
	private String publisher;
	
	@Column(name="PUBLISHED_DATE")
	private LocalDate publishedDate; 
	
	@Column(name="ACTIVE")
	private boolean active;
	
	@Column(name="CONTENT")
	private String content;
	
	
	
	public BookResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BookResponse(byte[] logo, String title, String category, double price, int authorId, String publisher,
			LocalDate publishedDate, boolean active, String content) {
		super();
		this.logo = logo;
		this.title = title;
		this.category = category;
		this.price = price;
		this.authorId = authorId;
		this.publisher = publisher;
		this.publishedDate = publishedDate;
		this.active = active;
		this.content = content;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	
	public LocalDate getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Book [title=" + title + ", category=" + category + ", price=" + price
				+ ", authorId=" + authorId + ", publisher=" + publisher + ", publishedDate=" + publishedDate
				+ ", active=" + active + ", content=" + content + "]";
	}

	
	
}
