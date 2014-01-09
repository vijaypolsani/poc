package com.cgi.bpm.service;

import org.activiti.engine.delegate.DelegateExecution;

public interface ExceptionService {

	public DelegateExecution logExceptionMessage(DelegateExecution exceptionMessage);
}
