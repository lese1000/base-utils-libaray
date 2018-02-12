package com.base.utils.libaray.vo;

import java.io.Serializable;

public class WaybillMessage implements Serializable{

	private static final long serialVersionUID = 1L;

	private String key;

	private String waybill;

	private Long shipmentId;

	private Byte shipmentType;
	
	public WaybillMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WaybillMessage(String key, String waybill, Long shipmentId, Byte shipmentType) {
		super();
		this.key = key;
		this.waybill = waybill;
		this.shipmentId = shipmentId;
		this.shipmentType = shipmentType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWaybill() {
		return waybill;
	}

	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Byte getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(Byte shipmentType) {
		this.shipmentType = shipmentType;
	}

}
