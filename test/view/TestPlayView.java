package view;

import view.play.GameWindowImpl;

public class TestPlayView {

	private TestPlayView() {}
	
	public static void main(String ...args) {
		new GameWindowImpl("").show();
	}

}
