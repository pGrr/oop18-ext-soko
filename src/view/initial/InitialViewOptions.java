package view.initial;

import static view.Components.*;
import static view.initial.InitialViewConstants.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.SokobanController;
import model.LevelSchemaImpl.LevelNotValidException;

public class InitialViewOptions {

	private final InitialViewContainer view;
	private final InitialViewList levels;
	private final SokobanController controller;
	
	public InitialViewOptions(SokobanController controller, InitialViewContainer view, InitialViewList levels) {
		this.controller = controller;
		this.view = view;
		this.levels = levels;
	}
	
	public JPanel createPanel() {
		int littlePadding = Math.round(DEFAULT_PADDING / 5);
		JPanel p = new JPanel(new BorderLayout(littlePadding, littlePadding));
		p.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		JButton craftButton = createButton(BUTTON_CRAFT_TEXT, ICON_CRAFT, crafAction());
		p.add(craftButton, BorderLayout.PAGE_START);
		JButton playButton = createButton(BUTTON_PLAY_TEXT, ICON_PLAY, playAction());
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
				this.view.showLevelNotValidDialog();
			} catch (IOException ioException) {
				this.view.showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(ioException);
			} catch (ClassNotFoundException classNotFoundException) {
				this.view.showErrorDialog(DIALOG_CLASS_NOT_FOUND_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT);
				System.err.println(classNotFoundException);
			}
		});
	}

}
