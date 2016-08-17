package com.careydevelopment.twitterautomation.jpa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ad_company")
public class AdCompany {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="name")
	private String name;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy="adCompany")
	//private List<NativeAd> nativeAds;
	
	public AdCompany() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public List<NativeAd> getNativeAds() {
		return nativeAds;
	}

	public void setNativeAds(List<NativeAd> nativeAds) {
		this.nativeAds = nativeAds;
	}*/
	
}
