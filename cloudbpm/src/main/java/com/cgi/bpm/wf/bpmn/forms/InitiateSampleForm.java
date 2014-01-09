package com.cgi.bpm.wf.bpmn.forms;

import java.util.Map;


import com.cgi.bpm.wf.ui.util.AbstractUserTaskForm;
import com.vaadin.ui.TextField;

public class InitiateSampleForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -635257925992714870L;

	public static final String FORM_KEY = "initiateSampleForm";

	private TextField project;

	private TextField version;

	private TextField summary;

	private TextField targetVersion;

	private TextField priority;

	private TextField sampleData;

	@Override
	public String getDisplayName() {
		return "Initiate Sample";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("sampleData", (String) sampleData.getValue());
	}

	@Override
	protected void populateFormField(String propertyId, String propertyValue) {
		if (propertyId.equals("project")) {
			project.setValue(propertyValue);
		} else if (propertyId.equals("version")) {
			version.setValue(propertyValue);
		} else if (propertyId.equals("summary")) {
			summary.setValue(propertyValue);
		} else if (propertyId.equals("targetVersion")) {
			targetVersion.setValue(propertyValue);
		} else if (propertyId.equals("priority")) {
			priority.setValue(propertyValue);
		} else if (propertyId.equals("sampleData")) {
			sampleData.setValue(propertyValue);
		}
	}

	@Override
	protected void init() {
		project = new TextField("Project");
		project.setEnabled(false);
		addComponent(project);

		version = new TextField("Version");
		version.setEnabled(false);
		addComponent(version);

		summary = new TextField("Summary");
		summary.setEnabled(false);
		addComponent(summary);

		targetVersion = new TextField("Target Version");
		targetVersion.setEnabled(false);
		addComponent(targetVersion);

		priority = new TextField("Priority");
		priority.setEnabled(false);
		addComponent(priority);

		sampleData = new TextField("SampleData");
		addComponent(sampleData);
	}

}
