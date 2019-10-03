package view;

import java.util.List;

import controller.CraftObserver;
import model.ElementModel.Type;

public class TestCraftView {

	private TestCraftView() {}
	
	public static void main(String... args) {
		CraftView craftView = new CraftViewImpl(new CraftObserver() {

			@Override
			public void saveLevel(List<List<Type>> level, String path) {
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
