package com.careydevelopment.twitterautomation.service.impl;

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
import com.careydevelopment.twitterautomation.service.MajesticService;
import com.careydevelopment.twitterautomation.service.PageSpeedInsightsService;
import com.careydevelopment.twitterautomation.service.SEMRushService;
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
	DomainRankRepository domainRankRepository;
	
	@Autowired
	DomainSearchKeywordRepository domainSearchKeywordRepository;
	
	@Autowired
	CompetitorSearchRepository competitorSearchRepository;
	
	
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
		saveDomainSearchKeywords(url,SEMRushService.PAID);
		
		if (url.isDomain()) {
			saveCompetitorSearch(url,SEMRushService.ORGANIC);
			saveCompetitorSearch(url,SEMRushService.PAID);
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
					key.setProjectUrl(url);
					competitorSearchRepository.save(key);
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
			
			if (list != null) {
				for (DomainSearchKeyword key : list) {
					key.setProjectUrl(url);
					domainSearchKeywordRepository.save(key);
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
			domainRankRepository.save(info);
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
						IndexItemInfoRow res = IndexItemInfoRowParser.getIndexItemInfoRow(row);
						res.setProjectUrl(url);
						indexItemInfoRepository.save(res);
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
							LOGGER.info("Parsing " + row);
							BacklinkData bd = BacklinkDataParser.getBacklinkData(row);
							bd.setProjectUrl(url);
							backlinkDataRepository.save(bd);	
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
							LOGGER.info("Parsing " + row);
							AnchorTextData at = AnchorTextDataParser.getAnchorTextData(row);
							at.setProjectUrl(url);
							anchorTextDataRepository.save(at);	
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
			
			PageSpeedInsights insights = pageSpeedInsightsService.getPageSpeedInsights(url.getUrl());
			insights.setProjectUrl(url);
			
			pageSpeedInsightsRepository.save(insights);
		} catch (Exception e) {
			LOGGER.error("Problem saving page speed insights!",e);
		}
	}
}
