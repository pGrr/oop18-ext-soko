package view.initial;

import static view.GuiComponentFactoryImpl.*;
import static view.initial.InitialViewConstants.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.ControllerFacade;
import model.level.LevelSchemaImpl.LevelNotValidException;

public class InitialViewOptions {

	private final InitialViewContainer owner;
	private final InitialViewList levels;
	private final ControllerFacade controller;
	
	public InitialViewOptions(InitialViewContainer owner, ControllerFacade controller, InitialViewList levels) {
		this.controller = controller;
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
			this.controller.craftLevel();
		});
	}
	
	public ActionListener playAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			try {
				this.controller.playLevelSequence(this.levels.getLevelSequence());
			} catch (LevelNotValidException levelNotValidException) {
				this.owner.showLevelNotValidDialog();
			} catch (IOException ioException) {
				this.owner.showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(ioException);
			} catch (ClassNotFoundException classNotFoundException) {
				this.owner.showErrorDialog(DIALOG_CLASS_NOT_FOUND_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT);
				System.err.println(classNotFoundException);
			}
		});
	}

}
