package com.cgi.bpm.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.bpm.service.ValidationService;

public class ValidationServiceImpl implements ValidationService {
	private Logger log = LoggerFactory.getLogger("ValidationServiceImpl.class");

	private String businessKey = null;

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Override
	public DelegateExecution validate(DelegateExecution content) {
		log.info("Performed Validation!.");
		businessKey = content.getProcessBusinessKey();
		content.setVariable("Vlidated", "Y");
		return content;
	}

	@Override
	public String validateTask(String content) {
		log.info("Performed Task Validation!.");
		return "Performed Task Validation!." + content;
	}

}
