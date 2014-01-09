package com.cgi.bpm.service;

import org.activiti.engine.delegate.DelegateExecution;

public interface ValidationService {

	public DelegateExecution validate(DelegateExecution content);

	public String validateTask(String content);
}
