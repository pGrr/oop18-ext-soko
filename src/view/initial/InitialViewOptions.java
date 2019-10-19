package view.initial;

import static view.GuiComponentFactoryImpl.*;
import static view.initial.InitialViewConstants.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.ControllerFacade;

public class InitialViewOptions {

	private final InitialViewWindowImpl owner;
	private final InitialViewList levels;
	
	public InitialViewOptions(InitialViewWindowImpl owner, InitialViewList levels) {
		this.owner = owner;
		this.levels = levels;
	}
	
	public JPanel createPanel() {
		int littlePadding = Math.round(DEFAULT_PADDING / 5);
		JPanel p = new JPanel(new BorderLayout(littlePadding, littlePadding));
		p.setBorder(owner.getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
		JButton craftButton = owner.getComponentFactory().createButton(BUTTON_CRAFT_TEXT, ICON_CRAFT, crafAction());
		p.add(craftButton, BorderLayout.PAGE_START);
		JButton playButton = owner.getComponentFactory().createButton(BUTTON_PLAY_TEXT, ICON_PLAY, playAction());
		p.add(playButton, BorderLayout.PAGE_END);
		return p;
	}
	
	public ActionListener crafAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			ControllerFacade.getInstance().craftLevel();
		});
	}
	
	public ActionListener playAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			ControllerFacade.getInstance().playLevelSequence(this.levels.getLevelSequence());
		});
	}

}
