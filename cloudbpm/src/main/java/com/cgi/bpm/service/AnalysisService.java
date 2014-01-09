package com.cgi.bpm.service;

import org.activiti.engine.delegate.DelegateExecution;

public interface AnalysisService {

	public DelegateExecution performAnalysis(DelegateExecution analysisMessage);

}
