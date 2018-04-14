package controller;

public abstract class Controller {
	
	protected AppController app;
	
	public void setApp(AppController app) {
		this.app = app;
	}
	
	public AppController getApp() {
		return app;
	}
}
