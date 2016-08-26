package com.careydevelopment.twitterautomation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.careydevelopment.twitterautomation.domain.MajesticInfoTable;
import com.careydevelopment.twitterautomation.domain.MajesticInfoTables;
import com.careydevelopment.twitterautomation.domain.MajesticResults;
import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;
import com.careydevelopment.twitterautomation.jpa.entity.IndexItemInfoRow;
import com.careydevelopment.twitterautomation.jpa.entity.PageSpeedInsights;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.repository.BacklinkDataRepository;
import com.careydevelopment.twitterautomation.jpa.repository.IndexItemInfoRepository;
import com.careydevelopment.twitterautomation.jpa.repository.PageSpeedInsightsRepository;
import com.careydevelopment.twitterautomation.service.MajesticService;
import com.careydevelopment.twitterautomation.service.PageSpeedInsightsService;
import com.careydevelopment.twitterautomation.service.UrlMetricsService;
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

	
	@Override
	public void saveUrlMetrics(ProjectUrl url) {
		//savePageSpeedInsights(url);
		//saveMajesticInfo(url);
		saveSEMRushInfo(url);
		//saveUrlValue(url);
	}
	
	
	private void saveMajesticInfo(ProjectUrl url) {
		LOGGER.info("Retrieving Majestic info for url " + url.getUrl());			
		
		saveIndexInfo(url);	
		saveBacklinkData(url);
	}
	
	
	private void saveSEMRushInfo(ProjectUrl url) {
		LOGGER.info("Retrieving SEMRush info for url " + url.getUrl());			
		
		saveDomainRankInfo(url);
	}
	
	
	private void saveDomainRankInfo(ProjectUrl url) {
		
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
			LOGGER.error("Problem saving index item info!",e);
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
