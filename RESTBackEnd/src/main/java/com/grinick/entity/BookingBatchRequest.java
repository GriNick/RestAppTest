package com.grinick.entity;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grinick.serializers.JsonTimeSerializer;
import com.grinick.serializers.JsonTimeDeserializer;

public class BookingBatchRequest {
	private Date startWorkingDay;
	private Date endWorkingDay;
	private List<BookingRequest> requestList;	
	
	@JsonSerialize(using = JsonTimeSerializer.class)
	public Date getStartWorkingDay() {
		return startWorkingDay;
	}
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public void setStartWorkingDay(Date startWorkingDay) {
		this.startWorkingDay = startWorkingDay;
	}
	@JsonSerialize(using = JsonTimeSerializer.class)
	public Date getEndWorkingDay() {
		return endWorkingDay;
	}
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public void setendWorkingDay(Date endWorkingDay) {
		this.endWorkingDay = endWorkingDay;
	}
	public List<BookingRequest> getRequest() {
		return requestList;
	}
	public void setRequest(List<BookingRequest> request) {
		this.requestList = request;
	}
}
