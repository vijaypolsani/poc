package com.cgi.bpm.service;

import org.activiti.engine.delegate.DelegateExecution;

public interface RetryService {

	public DelegateExecution performRetry(DelegateExecution retryMessage);
}
