package com.cgi.bpm.wf;

import org.activiti.engine.ProcessEngines;

import com.cgi.bpm.wf.bpmn.forms.ChipLoadingForm;
import com.cgi.bpm.wf.bpmn.forms.ElusionForm;
import com.cgi.bpm.wf.bpmn.forms.EmulsionPcrForm;
import com.cgi.bpm.wf.bpmn.forms.GelQcForm;
import com.cgi.bpm.wf.bpmn.forms.InitiateSampleForm;
import com.cgi.bpm.wf.bpmn.forms.NanoDropForm;
import com.cgi.bpm.wf.bpmn.forms.PgmSequencerForm;
import com.cgi.bpm.wf.bpmn.forms.WashingForm;
import com.cgi.bpm.wf.ui.forms.UserFormView;
import com.cgi.bpm.wf.ui.forms.UserFormViewImpl;
import com.cgi.bpm.wf.ui.identity.IdentityManagementView;
import com.cgi.bpm.wf.ui.identity.IdentityManagementViewImpl;
import com.cgi.bpm.wf.ui.login.LoginViewImpl;
import com.cgi.bpm.wf.ui.login.UserLoggedInEvent;
import com.cgi.bpm.wf.ui.main.MainViewImpl;
import com.cgi.bpm.wf.ui.main.UserLoggedOutEvent;
import com.cgi.bpm.wf.ui.processes.ProcessView;
import com.cgi.bpm.wf.ui.processes.ProcessViewImpl;
import com.cgi.bpm.wf.ui.tasks.MyTasksView;
import com.cgi.bpm.wf.ui.tasks.MyTasksViewImpl;
import com.cgi.bpm.wf.ui.tasks.UnassignedTasksView;
import com.cgi.bpm.wf.ui.tasks.UnassignedTasksViewImpl;
import com.cgi.bpm.wf.ui.util.UserTaskFormContainer;
import com.github.peholmst.mvp4vaadin.ViewEvent;
import com.github.peholmst.mvp4vaadin.ViewListener;
import com.github.peholmst.mvp4vaadin.navigation.DefaultViewProvider;
import com.vaadin.Application;
import com.vaadin.ui.Window;

public class DemoApplication extends Application implements ViewListener {

	private static final long serialVersionUID = -9012126232401480280L;

	private LoginViewImpl loginView;

	private MainViewImpl mainView;

	private DefaultViewProvider viewProvider;

	private UserTaskFormContainer userTaskFormContainer;

	@Override
	public void init() {
		setTheme("bpm");
		createAndShowLoginWindow();
	}

	private void createAndShowLoginWindow() {
		loginView = new LoginViewImpl();
		loginView.addListener(this);
		Window loginWindow = new Window(loginView.getDisplayName(), loginView.getViewComponent());
		setMainWindow(loginWindow);
	}

	private void createAndShowMainWindow() {
		createAndInitViewProvider();
		loginView.removeListener(this);
		mainView = new MainViewImpl(this, viewProvider);
		mainView.addListener(this);
		// Remove old login window
		removeWindow(getMainWindow());
		// Set new main window
		Window mainWindow = new Window(mainView.getDisplayName(), mainView.getViewComponent());
		setMainWindow(mainWindow);
	}

	private void createAndInitViewProvider() {
		createAndInitUserTaskFormContainer();
		viewProvider = new DefaultViewProvider();
		viewProvider.addPreinitializedView(new MyTasksViewImpl(this), MyTasksView.VIEW_ID);
		viewProvider.addPreinitializedView(new UnassignedTasksViewImpl(this), UnassignedTasksView.VIEW_ID);
		viewProvider.addPreinitializedView(new ProcessViewImpl(), ProcessView.VIEW_ID);
		viewProvider.addPreinitializedView(new IdentityManagementViewImpl(), IdentityManagementView.VIEW_ID);
		viewProvider.addPreinitializedView(new UserFormViewImpl(userTaskFormContainer), UserFormView.VIEW_ID);
	}

	private void createAndInitUserTaskFormContainer() {
		userTaskFormContainer = new UserTaskFormContainer();
		userTaskFormContainer.registerForm(ChipLoadingForm.FORM_KEY, ChipLoadingForm.class);
		userTaskFormContainer.registerForm(ElusionForm.FORM_KEY, ElusionForm.class);
		userTaskFormContainer.registerForm(EmulsionPcrForm.FORM_KEY, EmulsionPcrForm.class);
		userTaskFormContainer.registerForm(GelQcForm.FORM_KEY, GelQcForm.class);
		userTaskFormContainer.registerForm(InitiateSampleForm.FORM_KEY, InitiateSampleForm.class);
		userTaskFormContainer.registerForm(NanoDropForm.FORM_KEY, NanoDropForm.class);
		userTaskFormContainer.registerForm(PgmSequencerForm.FORM_KEY, PgmSequencerForm.class);
		userTaskFormContainer.registerForm(WashingForm.FORM_KEY, WashingForm.class);
	}

	@Override
	public void close() {
		ProcessEngines.getDefaultProcessEngine().getIdentityService().setAuthenticatedUserId(null);
		super.close();
	}

	@Override
	public void handleViewEvent(ViewEvent event) {
		if (event instanceof UserLoggedInEvent) {
			String username = ((UserLoggedInEvent) event).getUsername();
			setUser(username);
			createAndShowMainWindow();
		} else if (event instanceof UserLoggedOutEvent) {
			close();
		}
	}

}
