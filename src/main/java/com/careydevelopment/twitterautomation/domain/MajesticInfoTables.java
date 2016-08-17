package com.careydevelopment.twitterautomation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DataTables")
@XmlAccessorType(XmlAccessType.FIELD)
public class MajesticInfoTables {

	@XmlElement(name="DataTable")
	private MajesticInfoTable dataTable;

	public MajesticInfoTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(MajesticInfoTable dataTable) {
		this.dataTable = dataTable;
	}
	
}
