package com.cgi.bpm.wf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@WebListener
public class ProcessEngineServletContextListener implements ServletContextListener {
	private ApplicationContext context = null;
	private Logger log = LoggerFactory.getLogger(ProcessEngineServletContextListener.class);	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log.info("Destroying process engines");
		ProcessEngines.destroy();
		context = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try
		{
		log.info("Initializing process engines.");
		ProcessEngines.init();
		log.info("Process Engines INIT Completed.");
		//context = new ClassPathXmlApplicationContext("conf/activiti.cfg.xml");
		context = new ClassPathXmlApplicationContext("camel/activiti-camel-application-context.xml");
		log.info("Got Context. Application Name:" + context.getDisplayName());
		ProcessEngine processEngine = (ProcessEngine) context.getBean("processEngine");
		log.info("Got processEngine Name:" + processEngine.getName());
		ProcessEngines.registerProcessEngine(processEngine);
		log.info("Registered Process Engine. processEngine Name:" + processEngine.getName());
		/*
		 * ProcessEngine processEngine = ProcessEngineConfiguration
		 * .createStandaloneProcessEngineConfiguration() .setJdbcUrl(
		 * "jdbc:mysql://localhost:3306/activiti?autoReconnect=true")
		 * .setJdbcDriver("com.mysql.jdbc.Driver")
		 * .setJdbcUsername("activiti").setJdbcPassword("test")
		 * .setJobExecutorActivate(true).buildProcessEngine();
		 * ProcessEngines.registerProcessEngine(processEngine);
		 */
		log.info("Check Initalization of Process Engines:" + ProcessEngines.getProcessEngines());
		createGroupsIfNotPresent();
		createAdminUserIfNotPresent();
		deployProcesses();
		}catch (Exception e)
		{
			log.info(e.getLocalizedMessage());
			log.info(e.getStackTrace().toString());
		}
	}

	private void createAdminUserIfNotPresent() {
		if (!isAdminUserPresent()) {
			createAdminUser();
		}
	}

	private void createGroupsIfNotPresent() {
		if (!isGroupPresent("staff")) {
			createGroup("staff", "Staff");
		}
		if (!isGroupPresent("managers")) {
			createGroup("managers", "Managers");
		}
	}

	private boolean isAdminUserPresent() {
		UserQuery query = getIdentityService().createUserQuery();
		query.userId("bpm");
		return query.count() > 0;
	}

	private void createAdminUser() {
		log.info("Creating an administration user with the username 'bpm' and password 'password'");
		User adminUser = getIdentityService().newUser("bpm");
		adminUser.setFirstName("Bpm");
		adminUser.setLastName("Manager");
		adminUser.setPassword("password");
		getIdentityService().saveUser(adminUser);
		assignAdminUserToGroups();
	}

	private void assignAdminUserToGroups() {
		getIdentityService().createMembership("bpm", "staff");
		getIdentityService().createMembership("bpm", "managers");
	}

	private boolean isGroupPresent(String groupId) {
		GroupQuery query = getIdentityService().createGroupQuery();
		query.groupId(groupId);
		return query.count() > 0;
	}

	private void createGroup(String groupId, String groupName) {
		log.info("Creating a group with the id '{1}' and name '{2}'", new Object[] { groupId, groupName });
		Group group = getIdentityService().newGroup(groupId);
		group.setName(groupName);
		getIdentityService().saveGroup(group);
	}

	private IdentityService getIdentityService() {
		IdentityService idService = ProcessEngines.getDefaultProcessEngine().getIdentityService();
		idService.setAuthenticatedUserId("bpm");
		return idService;
	}

	private void deployProcesses() {
		log.info("Deploying processes.");
		RepositoryService repositoryService = ProcessEngines.getDefaultProcessEngine().getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("processes/GenomicsWorkflow_v1.0.bpmn").deploy();
		log.info("Completed Deploying processes.");
	}
}
