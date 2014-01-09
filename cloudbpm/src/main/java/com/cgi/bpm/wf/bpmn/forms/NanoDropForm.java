package com.cgi.bpm.wf.bpmn.forms;

import java.util.Map;


import com.cgi.bpm.wf.ui.util.AbstractUserTaskForm;
import com.vaadin.ui.TextField;

public class NanoDropForm extends AbstractUserTaskForm {

	private static final long serialVersionUID = -6355792599239114870L;

	public static final String FORM_KEY = "nanoDropForm";

	private TextField project;

	private TextField version;

	private TextField summary;

	private TextField targetVersion;

	private TextField priority;

	private TextField nanoDropData;

	@Override
	public String getDisplayName() {
		return "Nano Drop Form";
	}

	@Override
	public String getFormKey() {
		return FORM_KEY;
	}

	@Override
	public void copyFormProperties(Map<String, String> destination) {
		destination.put("nanoDropData", (String) nanoDropData.getValue());
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
		} else if (propertyId.equals("nanoDropData")) {
			nanoDropData.setValue(propertyValue);
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

		nanoDropData = new TextField("NanoDropData");
		addComponent(nanoDropData);
	}

}
