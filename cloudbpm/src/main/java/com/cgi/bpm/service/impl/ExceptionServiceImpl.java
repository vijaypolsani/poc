package com.cgi.bpm.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.bpm.service.ExceptionService;

public class ExceptionServiceImpl implements ExceptionService {

	private Logger log = LoggerFactory.getLogger("ExceptionServiceImpl.class");

	private String businessKey = null;

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	@Override
	public DelegateExecution logExceptionMessage(DelegateExecution exceptionMessage) {
		log.info("Performed Exception Handling!.");
		businessKey = exceptionMessage.getProcessBusinessKey();
		exceptionMessage.setVariable("Validated", "Y");
		return exceptionMessage;
	}
}
