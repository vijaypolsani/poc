package com.cgi.bpm.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.bpm.service.RetryService;

public class RetryServiceImpl implements RetryService {
	private Logger log = LoggerFactory.getLogger("RetryServiceImpl.class");

	private String businessKey = null;

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Override
	public DelegateExecution performRetry(DelegateExecution retryMessage) {
		log.info("Performed Retry!.");
		businessKey = retryMessage.getProcessBusinessKey();
		retryMessage.setVariable("Validated", "Y");
		return retryMessage;
	}
}
