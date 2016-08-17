package com.careydevelopment.twitterautomation.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DataTable")
@XmlAccessorType(XmlAccessType.FIELD)
public class MajesticInfoTable {

	@XmlElements(
			@XmlElement(name="Row",type=String.class)
	)
	private List<String> rows;

	public List<String> getRows() {
		return rows;
	}

	public void setRows(List<String> rows) {
		this.rows = rows;
	}
}
