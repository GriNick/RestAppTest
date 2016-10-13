package com.grinick.entity;

import java.util.ArrayList;

public class BookingResponse {
	private int statusCode;
	private String errMessage;
	private ArrayList<BookingDayAccepted> data;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public ArrayList<BookingDayAccepted> getData() {
		return data;
	}
	public void setData(ArrayList<BookingDayAccepted> data) {
		this.data = data;
}
}
