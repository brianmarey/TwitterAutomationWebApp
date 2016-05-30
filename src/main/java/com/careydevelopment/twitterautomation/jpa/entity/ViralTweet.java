package com.careydevelopment.twitterautomation.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "viral_tweet")
public class ViralTweet {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="html")
	private String html;

	@Column(name="date_seen")
	private Date dateSeen;
	
	@Column(name="retweets")
	private int retweets;
	
	@Column(name="tweet_id")
	private long tweetId;
	
	@Column(name="category")
	private String category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Date getDateSeen() {
		return dateSeen;
	}

	public void setDateSeen(Date dateSeen) {
		this.dateSeen = dateSeen;
	}

	public int getRetweets() {
		return retweets;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}

	public long getTweetId() {
		return tweetId;
	}

	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
