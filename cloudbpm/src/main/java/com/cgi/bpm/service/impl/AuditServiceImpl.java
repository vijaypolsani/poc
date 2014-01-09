package com.cgi.bpm.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.bpm.service.AuditService;

public class AuditServiceImpl implements AuditService {

	private Logger log = LoggerFactory.getLogger("AuditServiceImpl.class");


	private String businessKey = null;

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Override
	public DelegateExecution logAudit(DelegateExecution auditMessage) {
		log.info("Performed Audit!.");
		businessKey = auditMessage.getProcessBusinessKey();
		auditMessage.setVariable("Validated", "Y");
		return auditMessage;

	}

}
