package com.digitalbooks.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_SUBSCRIPTION")
public class Subscription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="USER_ID")
	private Long userId;
	
	@Column(name="BOOK_ID")
	private Long bookId;
	
	@Column(name="SUBSCRIPTON_ID")
	private String subscriptionId;
	
	@Column(name="SUBSCRIPTION_DATE")
	private Date dateOfSubscription;
	
	@Column(name="CANCELLED")
	private boolean isCancelled;
	
	@Column(name="DATE_OF_CANCEL")
	private Date dateOfCancellation;
	

	

	public Subscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subscription(Long id, Long userId, Long bookId, String subscriptionId, Date dateOfSubscription,
			boolean isCancelled, Date dateOfCancellation) {
		super();
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.subscriptionId = subscriptionId;
		this.dateOfSubscription = dateOfSubscription;
		this.isCancelled = isCancelled;
		this.dateOfCancellation = dateOfCancellation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}



	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}


	public Date getDateOfSubscription() {
		return dateOfSubscription;
	}

	public void setDateOfSubscription(Date dateOfSubscription) {
		this.dateOfSubscription = dateOfSubscription;
	}
	
	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public Date getDateOfCancellation() {
		return dateOfCancellation;
	}

	public void setDateOfCancellation(Date dateOfCancellation) {
		this.dateOfCancellation = dateOfCancellation;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", subscriptionId="
				+ subscriptionId + ", dateOfSubscription=" + dateOfSubscription + "]";
	}
	

}
