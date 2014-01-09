package com.cgi.bpm.wf.ui.home;

import com.cgi.bpm.wf.ui.home.components.MainMenuItem;
import com.cgi.bpm.wf.ui.identity.IdentityManagementView;
import com.cgi.bpm.wf.ui.processes.ProcessView;
import com.github.peholmst.mvp4vaadin.VaadinView;
import com.github.peholmst.mvp4vaadin.navigation.AbstractControllableView;
import com.github.peholmst.mvp4vaadin.navigation.ControllableView;
import com.github.peholmst.mvp4vaadin.navigation.ViewProvider;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ChameleonTheme;;

public class HomeViewImpl extends
		AbstractControllableView<HomeView, HomePresenter> implements HomeView,
		VaadinView {

	private static final long serialVersionUID = -6441832004361841454L;

	private VerticalLayout viewLayout;

	private ViewProvider viewProvider;

	public HomeViewImpl(ViewProvider viewProvider) {
		this.viewProvider = viewProvider;
		init();
	}

	@Override
	public String getDisplayName() {
		return "Home";
	}

	@Override
	public String getDescription() {
		return "The starting point of Cloud BPM Application";
	}

	@Override
	protected HomePresenter createPresenter() {
		return new HomePresenter(this);
	}

	@Override
	public ComponentContainer getViewComponent() {
		return viewLayout;
	}

	@Override
	protected void initView() {
		viewLayout = new VerticalLayout();
		viewLayout.setMargin(true);
		viewLayout.setSpacing(true);

		addItemForView(viewProvider.getView(ProcessView.VIEW_ID));
		addItemForView(viewProvider.getView(IdentityManagementView.VIEW_ID));
	}

	@SuppressWarnings("serial")
	private void addItemForView(final ControllableView view) {
		MainMenuItem item = new MainMenuItem(view);
		item.addListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				getViewController().goToView(view);
			}
		});
		viewLayout.addComponent(item);
	}
}
