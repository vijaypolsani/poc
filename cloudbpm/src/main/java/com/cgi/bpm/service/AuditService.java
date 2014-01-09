package com.cgi.bpm.service;

import org.activiti.engine.delegate.DelegateExecution;


public interface AuditService {

	public DelegateExecution logAudit(DelegateExecution auditMessage);
}
