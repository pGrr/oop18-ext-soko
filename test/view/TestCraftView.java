package view;

public class TestCraftView {

	private TestCraftView() {}
	
	public static void main(String... args) {
		CraftView craftView = new CraftViewImpl();
		craftView.show();
	}

}
