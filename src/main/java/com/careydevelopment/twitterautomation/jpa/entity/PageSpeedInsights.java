package com.careydevelopment.twitterautomation.jpa.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "page_speed_insights")
public class PageSpeedInsights {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="mobile_speed")
	private Integer mobileSpeed = 0;
	
	@Column(name="mobile_usability")
	private Integer mobileUsability = 0;
	
	@Column(name="desktop_speed")
	private Integer desktopSpeed = 0;
	
	//a url belongs to one and only one psi
	//and a psi has only one url
	@OneToOne
	@JoinColumn(name = "url_id")
	private ProjectUrl projectUrl;
	
	
	public Integer getMobileSpeed() {
		return (mobileSpeed == null) ? 0 : mobileSpeed;
	}
	public void setMobileSpeed(Integer mobileSpeed) {
		this.mobileSpeed = mobileSpeed;
	}
	public Integer getMobileUsability() {
		return (mobileUsability == null) ? 0 : mobileUsability;
	}
	public void setMobileUsability(Integer mobileUsability) {
		this.mobileUsability = mobileUsability;
	}
	public Integer getDesktopSpeed() {
		return (desktopSpeed == null) ? 0 : desktopSpeed;
	}
	public void setDesktopSpeed(Integer desktopSpeed) {
		this.desktopSpeed = desktopSpeed;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProjectUrl getProjectUrl() {
		return projectUrl;
	}
	public void setProjectUrl(ProjectUrl projectUrl) {
		this.projectUrl = projectUrl;
	}
}
