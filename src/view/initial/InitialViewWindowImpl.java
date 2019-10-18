package view.initial;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import controller.ControllerFacade;
import model.sequence.LevelSequence;
import view.WindowAbstract;

import static view.GuiComponentFactoryImpl.*;
import static view.initial.InitialViewConstants.*;

public class InitialViewWindowImpl extends WindowAbstract implements InitialViewWindow {

	private final ControllerFacade controller;
	private final InitialViewList levels;
	private final InitialViewOptions options;
	
	public InitialViewWindowImpl(ControllerFacade controller) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.levels = new InitialViewList(this, this.controller, Optional.empty());
		this.options = new InitialViewOptions(this, this.controller, this.levels);
		this.getFrame().add(createMainPanel());
	}
	
	public InitialViewWindowImpl(ControllerFacade controller, LevelSequence levelSequence) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.levels = new InitialViewList(this, this.controller, Optional.of(levelSequence));
		this.options = new InitialViewOptions(this, this.controller, this.levels);
		this.getFrame().add(createMainPanel());
	}
	
	@Override
	public void showLevelNotValidDialog() {
		showErrorDialog(DIALOG_LEVEL_NOT_CORRECT_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT);
	}

	@Override
	protected JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
		mainPanel.add(welcomePanel(), BorderLayout.PAGE_START);
		mainPanel.add(levelSequencePanel(), BorderLayout.CENTER);
		mainPanel.add(this.options.createPanel(), BorderLayout.PAGE_END);
		return mainPanel;
	}
	
	@Override
	protected ActionListener errorAction(JDialog dialog) {
		return e -> {
			dialog.dispose();
			this.controller.backToInitialView();
		};	
	}

	private JPanel welcomePanel() {
		JPanel p = new JPanel();
		p.add(getComponentFactory().createLabel(LABEL_WELCOME_TEXT));
		return p;
	}

	private JPanel levelSequencePanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.setBorder(getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
		p.add(this.levels.getPanel());
		return p;
	}
}