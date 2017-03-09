package com.careydevelopment.twitterautomation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.careydevelopment.twitterautomation.domain.MajesticInfoTable;
import com.careydevelopment.twitterautomation.domain.MajesticInfoTables;
import com.careydevelopment.twitterautomation.domain.MajesticResults;
import com.careydevelopment.twitterautomation.jpa.entity.AnchorTextData;
import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;
import com.careydevelopment.twitterautomation.jpa.entity.CompetitorSearch;
import com.careydevelopment.twitterautomation.jpa.entity.DomainRank;
import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.IndexItemInfoRow;
import com.careydevelopment.twitterautomation.jpa.entity.PageSpeedInsights;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.repository.AnchorTextDataRepository;
import com.careydevelopment.twitterautomation.jpa.repository.BacklinkDataRepository;
import com.careydevelopment.twitterautomation.jpa.repository.CompetitorSearchRepository;
import com.careydevelopment.twitterautomation.jpa.repository.DomainRankRepository;
import com.careydevelopment.twitterautomation.jpa.repository.DomainSearchKeywordRepository;
import com.careydevelopment.twitterautomation.jpa.repository.IndexItemInfoRepository;
import com.careydevelopment.twitterautomation.jpa.repository.PageSpeedInsightsRepository;
import com.careydevelopment.twitterautomation.model.SerpBookKeyword;
import com.careydevelopment.twitterautomation.service.MajesticService;
import com.careydevelopment.twitterautomation.service.PageSpeedInsightsService;
import com.careydevelopment.twitterautomation.service.SEMRushService;
import com.careydevelopment.twitterautomation.service.SerpBookService;
import com.careydevelopment.twitterautomation.service.UrlMetricsService;
import com.careydevelopment.twitterautomation.util.AnchorTextDataParser;
import com.careydevelopment.twitterautomation.util.BacklinkDataParser;
import com.careydevelopment.twitterautomation.util.IndexItemInfoRowParser;

@Component
public class UrlMetricsServiceImpl implements UrlMetricsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlMetricsServiceImpl.class);

	@Autowired
	PageSpeedInsightsService pageSpeedInsightsService;
	
	@Autowired
	PageSpeedInsightsRepository pageSpeedInsightsRepository;
	
	@Autowired
	MajesticService majesticService;
	
	@Autowired
	IndexItemInfoRepository indexItemInfoRepository;

	@Autowired
	BacklinkDataRepository backlinkDataRepository;

	@Autowired
	AnchorTextDataRepository anchorTextDataRepository;
	
	@Autowired
	SEMRushService semRushService;
	
	@Autowired
	SerpBookService serpBookService;
	
	@Autowired
	DomainRankRepository domainRankRepository;
	
	@Autowired
	DomainSearchKeywordRepository domainSearchKeywordRepository;
	
	@Autowired
	CompetitorSearchRepository competitorSearchRepository;
	
	int keywordCount = 0;
	
	@Override
	public void saveUrlMetrics(ProjectUrl url) {
		savePageSpeedInsights(url);
		saveMajesticInfo(url);
		saveSEMRushInfo(url);
		//saveUrlValue(url);
	}
	
	
	private void saveMajesticInfo(ProjectUrl url) {
		LOGGER.info("Retrieving Majestic info for url " + url.getUrl());			
		
		saveIndexInfo(url);	
		saveBacklinkData(url);
		saveAnchorTextData(url);
	}
	
	
	private void saveSEMRushInfo(ProjectUrl url) {
		LOGGER.info("Retrieving SEMRush info for url " + url.getUrl());			
		
		saveDomainRankInfo(url);
		saveDomainSearchKeywords(url,SEMRushService.ORGANIC);
		saveDomainSearchKeywords(url,SEMRushService.ORGANIC_MOBILE);
		//saveDomainSearchKeywords(url,SEMRushService.PAID);
		
		if (url.isDomain()) {
			saveCompetitorSearch(url,SEMRushService.ORGANIC);
			//saveCompetitorSearch(url,SEMRushService.PAID);
		} else {
			LOGGER.info("Skipping competitor analysis because " + url.getUrl() + " is not a domain");
		}
	}

	
	private void saveCompetitorSearch(ProjectUrl url,String type) {
		LOGGER.info("Saving competitor search");
		
		try {
			List<CompetitorSearch> list = semRushService.getCompetitorSearch(url, type);
			
			if (list != null) {
				for (CompetitorSearch key : list) {
					try {
						key.setProjectUrl(url);
						
						CompetitorSearch existingCompetitor = competitorSearchRepository.findSpecific(url, key.getDomain(), type);
	
						if (existingCompetitor == null) {
							LOGGER.info("adding new competitor");
							competitorSearchRepository.save(key);	
						} else {
							LOGGER.info("updating competitor");
							existingCompetitor.setCommonKeywords(key.getCommonKeywords());
							existingCompetitor.setCompetitorRelevance(key.getCompetitorRelevance());
							existingCompetitor.setTypeCost(key.getTypeCost());
							existingCompetitor.setTypeKeywords(key.getTypeKeywords());
							existingCompetitor.setTypeTraffic(key.getTypeTraffic());
							competitorSearchRepository.save(existingCompetitor);
						}
					} catch (Exception e) {
						LOGGER.error("Problem saving competitor " + key.getDomain() + " " + key.getId(),e);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Problem saving competitor search info!",e);
		}
	}

	
	private void saveDomainSearchKeywords(ProjectUrl url,String type) {
		LOGGER.info("Saving domain search keywords");
		
		try {
			List<DomainSearchKeyword> list = semRushService.getDomainSearchKeywords(url, type);
			List<String> theseKeys = new ArrayList<String>();
			
			if (list != null) {
				for (DomainSearchKeyword key : list) {
					try {
						key.setProjectUrl(url);
						
						DomainSearchKeyword existingKeyword = domainSearchKeywordRepository.findSpecific(url, key.getKeyword(), type);
	
						if (existingKeyword == null) {
							LOGGER.info("adding new keyword " + key.getKeyword());
							domainSearchKeywordRepository.save(key);	
							theseKeys.add(key.getKeyword());
						} else {
							if (!theseKeys.contains(key.getKeyword())) {
								LOGGER.info("updating keyword " + key.getKeyword());
								
								existingKeyword.setCompetition(key.getCompetition());
								existingKeyword.setCpc(key.getCpc());
								existingKeyword.setNumberOfResults(key.getNumberOfResults());
								existingKeyword.setPosition(key.getPosition());
								existingKeyword.setPositionDifference(key.getPositionDifference());
								existingKeyword.setPreviousPosition(key.getPreviousPosition());
								existingKeyword.setSearchVolume(key.getSearchVolume());
								existingKeyword.setTrafficCostPercent(key.getTrafficCostPercent());
								existingKeyword.setTrafficPercent(key.getTrafficPercent());

								if (existingKeyword.getDailyRank() == 1) {
									//if we get here, it's an seo strategy keyword and we need a daily rank update
									List<SerpBookKeyword> serpKeywords = serpBookService.fetchKeywordsByCategory(url);
									
									for (SerpBookKeyword serpKeyword : serpKeywords) {
										LOGGER.info("comparing serp " + serpKeyword.getKeyword() + " to " + existingKeyword.getKeyword());
										
										if (serpKeyword.getKeyword().equals(existingKeyword.getKeyword())) {
											LOGGER.info("MATCH: Setting rank to " + serpKeyword.getGrank());
											existingKeyword.setPosition(serpKeyword.getGrank());
											existingKeyword.setPreviousPosition(serpKeyword.getStart());
											existingKeyword.setPosition(serpKeyword.getGrank() - serpKeyword.getStart());
											existingKeyword.setDailyRank(1);
											break;
										}
									}
								}
								
								domainSearchKeywordRepository.save(existingKeyword);
								theseKeys.add(key.getKeyword());
							} else {
								LOGGER.info("Skipping " + key.getKeyword() + " because we've already added it");
							}
						}	
					} catch (Exception e) {
						LOGGER.error("Problem saving keyword " + key.getKeyword() + " " + key.getId(),e);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Problem saving domain search info!",e);
		}
	}

	
	private void saveDomainRankInfo(ProjectUrl url) {
		LOGGER.info("Saving domain rank info");
		
		try {
			DomainRank info = semRushService.getDomainRank(url);
			info.setProjectUrl(url);
			
			DomainRank oldInfo = domainRankRepository.findByUrl(url);
			
			if (oldInfo == null) {
				LOGGER.info("adding new domain rank info");
				domainRankRepository.save(info);	
			} else {
				LOGGER.info("updating domain rank info");
				oldInfo.setAdwordsCost(info.getAdwordsCost());
				oldInfo.setAdwordsKeywords(info.getAdwordsKeywords());
				oldInfo.setAdwordsTraffic(info.getAdwordsTraffic());
				oldInfo.setOrganicCost(info.getOrganicCost());
				oldInfo.setOrganicKeywords(info.getOrganicKeywords());
				oldInfo.setOrganicTraffic(info.getOrganicTraffic());
				oldInfo.setRank(info.getRank());
				domainRankRepository.save(oldInfo);
			}
		} catch (Exception e) {
			LOGGER.error("Problem saving rank info!",e);
		}
	}
	
	
	private void saveIndexInfo(ProjectUrl url) {
		LOGGER.info("Saving index info");
		
		try {
			MajesticResults indexInfoResults = majesticService.getIndexItemInfo(url.getUrl());
			if (indexInfoResults != null && indexInfoResults.getTables() != null) {
				MajesticInfoTables tables = indexInfoResults.getTables();
				
				if (tables.getDataTable() != null) {
					MajesticInfoTable table = tables.getDataTable();
					
					if (table.getRows() != null && table.getRows().size() > 0) {
						String row = table.getRows().get(0);
						LOGGER.info("Parsing " + row);
						IndexItemInfoRow res = IndexItemInfoRowParser.getIndexItemInfoRow(row,table.getHeaders());
						res.setProjectUrl(url);
						
						IndexItemInfoRow oldRes = indexItemInfoRepository.findByUrl(url);
						
						if (oldRes == null) {
							LOGGER.info("adding new index item info");
							indexItemInfoRepository.save(res);	
						} else {
							LOGGER.info("updating index item info");
							oldRes.setCitationFlow(res.getCitationFlow());
							oldRes.setExtBacklinks(res.getExtBacklinks());
							oldRes.setExtBacklinksEdu(res.getExtBacklinksEdu());
							oldRes.setExtBacklinksEduExact(res.getExtBacklinksEduExact());
							oldRes.setExtBacklinksGov(res.getExtBacklinksGov());
							oldRes.setExtBacklinksGovExact(res.getExtBacklinksGovExact());
							oldRes.setItem(res.getItem());
							oldRes.setItemType(res.getItemType());
							oldRes.setRefDomains(res.getRefDomains());
							oldRes.setRefDomainsEduExact(res.getRefDomainsEdu());
							oldRes.setRefDomainsEduExact(res.getRefDomainsEduExact());
							oldRes.setRefDomainsGov(res.getRefDomainsGov());
							oldRes.setRefDomainsGovExact(res.getRefDomainsGovExact());
							oldRes.setRefIps(res.getRefIps());
							
							indexItemInfoRepository.save(oldRes);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Problem saving index item info!",e);
		}
	}
	

	private void saveBacklinkData(ProjectUrl url) {
		LOGGER.info("Saving backlink info");
		
		try {
			MajesticResults backlinkDataResults = majesticService.getBacklinkData(url.getUrl());
			
			if (backlinkDataResults != null && backlinkDataResults.getTables() != null) {
				MajesticInfoTables tables = backlinkDataResults.getTables();
				
				if (tables.getDataTable() != null) {
					MajesticInfoTable table = tables.getDataTable();
					
					if (table.getRows() != null && table.getRows().size() > 0) {
						for (String row : table.getRows()) {
							try {
								LOGGER.info("Parsing " + row);
								BacklinkData bd = BacklinkDataParser.getBacklinkData(row,table.getHeaders());
								bd.setProjectUrl(url);
								
								BacklinkData oldBd = backlinkDataRepository.findSpecific(url, bd.getSourceUrl());
								
								if (oldBd == null) {
									LOGGER.info("adding new backlink");
									backlinkDataRepository.save(bd);		
								} else {
									LOGGER.info("updating backlink");
									oldBd.setAnchorText(bd.getAnchorText());
									oldBd.setDateLost(bd.getDateLost());
									oldBd.setFirstIndexedDate(bd.getFirstIndexedDate());
									oldBd.setFlagAltText(bd.getFlagAltText());
									oldBd.setFlagDeleted(bd.getFlagDeleted());
									oldBd.setFlagNoFollow(bd.getFlagNoFollow());
									oldBd.setLastSeenDate(bd.getLastSeenDate());
									oldBd.setLinkSubtype(bd.getLinkSubtype());
									oldBd.setLinkType(bd.getLinkType());
									oldBd.setReasonLost(bd.getReasonLost());
									oldBd.setSourceCitationFlow(bd.getSourceCitationFlow());
									oldBd.setSourceTrustFlow(bd.getSourceTrustFlow());
									oldBd.setTargetCitationFlow(bd.getTargetCitationFlow());
									oldBd.setTargetTrustFlow(bd.getTargetTrustFlow());
									oldBd.setTargetUrl(bd.getTargetUrl());
									
									backlinkDataRepository.save(oldBd);
								}
							} catch (Exception e) {
								LOGGER.error("Problem saving backlink data " + row,e);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Problem saving backlink info!",e);
		}
	}
	

	private void saveAnchorTextData(ProjectUrl url) {
		LOGGER.info("Saving anchor text info");
		
		try {
			MajesticResults anchorTextResults = majesticService.getAnchorTextData(url.getUrl());
			
			if (anchorTextResults != null && anchorTextResults.getTables() != null) {
				MajesticInfoTables tables = anchorTextResults.getTables();
				
				if (tables.getDataTable() != null) {
					MajesticInfoTable table = tables.getDataTable();
					
					if (table.getRows() != null && table.getRows().size() > 0) {
						for (String row : table.getRows()) {
							try {
								LOGGER.info("Parsing " + row);
								AnchorTextData at = AnchorTextDataParser.getAnchorTextData(row,table.getHeaders());
								if (at.getAnchorText() != null && at.getAnchorText().trim().length()> 0) {
									at.setProjectUrl(url);
									
									LOGGER.debug("Searching for anchor text " + at.getAnchorText());
									AnchorTextData oldAt = anchorTextDataRepository.findSpecific(url, at.getAnchorText());
									
									if (oldAt == null) {
										LOGGER.info("adding new anchor text");
										anchorTextDataRepository.save(at);		
									} else {
										LOGGER.info("updating anchor text");
										oldAt.setCitationFlow(at.getCitationFlow());
										oldAt.setDeletedLinks(at.getDeletedLinks());
										oldAt.setNofollowLinks(at.getNofollowLinks());
										oldAt.setReferringDomains(at.getReferringDomains());
										oldAt.setTotalLinks(at.getTotalLinks());
										oldAt.setTrustFlow(at.getTrustFlow());
										
										anchorTextDataRepository.save(oldAt);
									}
								}
							} catch (Exception e) {
								LOGGER.error("Problem saving anchor text " + row,e);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Problem saving anchor text info!",e);
		}
	}
	
	
	private void savePageSpeedInsights(ProjectUrl url) {
		try {
			LOGGER.info("Retrieving insights for url " + url.getUrl());
			
			PageSpeedInsights insights = pageSpeedInsightsRepository.findByProjectUrl(url);
			
			PageSpeedInsights newInsights = pageSpeedInsightsService.getPageSpeedInsights(url);
			newInsights.setProjectUrl(url);	
			
			if (insights != null) {
				LOGGER.info("updating page speed insights");
				insights.setDesktopSpeed(newInsights.getDesktopSpeed());
				insights.setMobileSpeed(newInsights.getMobileSpeed());
				insights.setMobileUsability(newInsights.getMobileUsability());
				pageSpeedInsightsRepository.save(insights);	
			} else {
				LOGGER.info("adding new page speed insights");
				pageSpeedInsightsRepository.save(newInsights);
			}
		} catch (Exception e) {
			LOGGER.error("Problem saving page speed insights!",e);
		}
	}
}
