package com.careydevelopment.twitterautomation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DataTables")
@XmlAccessorType(XmlAccessType.FIELD)
public class IndexItemInfoTables {

	@XmlElement(name="DataTable")
	private IndexItemInfoTable dataTable;

	public IndexItemInfoTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(IndexItemInfoTable dataTable) {
		this.dataTable = dataTable;
	}
	
}
