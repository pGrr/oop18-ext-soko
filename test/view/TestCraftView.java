package view;

import controller.CraftObserver;
import model.Level;

public class TestCraftView {

	private TestCraftView() {}
	
	public static void main(String... args) {
		CraftView craftView = new CraftViewImpl(15, new CraftObserver() {
			
			@Override
			public void saveLevel(Level level) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void backToInitialView() {
				// TODO Auto-generated method stub
				
			}
		});
		craftView.show();
	}

}
