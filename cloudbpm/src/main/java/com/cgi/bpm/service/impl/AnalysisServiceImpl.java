package com.cgi.bpm.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.bpm.service.AnalysisService;

public class AnalysisServiceImpl implements AnalysisService {
	private Logger log = LoggerFactory.getLogger("AnalysisServiceImpl.class");

	private String businessKey = null;

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Override
	public DelegateExecution performAnalysis(DelegateExecution analysisMessage) {
		log.info("Performed Analysis!.");
		businessKey = analysisMessage.getProcessBusinessKey();
		analysisMessage.setVariable("Validated", "Y");
		return analysisMessage;
	}

}
