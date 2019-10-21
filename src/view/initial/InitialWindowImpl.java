package view.initial;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import view.GuiComponentFactory;
import view.WindowAbstract;

import static view.GuiComponentFactoryImpl.*;
import static view.initial.InitialConstants.*;

public class InitialWindowImpl extends WindowAbstract implements InitialWindow {

	private final LevelList levelList;
	private final Choices choices;
	private final SaveLoadSequence saveLoadSequence;
	
	public InitialWindowImpl() {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.choices = new Choices(this);
		this.saveLoadSequence = new SaveLoadSequence(this);
		this.levelList = new LevelList(this);
		this.getFrame().add(createMainPanel());
	}
	
	@Override
	public void show() {
		this.levelList.loadDefaultLevelSequence();
		super.show();
	}

	@Override
	protected JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
		mainPanel.add(welcomePanel(), BorderLayout.PAGE_START);
		mainPanel.add(levelSequencePanel(), BorderLayout.CENTER);
		mainPanel.add(this.choices.createPanel(), BorderLayout.PAGE_END);
		return mainPanel;
	}

	protected LevelList getLevelList() {
		return this.levelList;
	}
	
	protected Choices getChoices() {
		return this.choices;
	}

	protected SaveLoadSequence getSaveLoadSequence() {
		return this.saveLoadSequence;
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
		p.add(this.levelList.getPanel());
		return p;
	}

	@Override
	public void showIOErrorDialog() {
		GuiComponentFactory.getDefaultInstance().createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT).setVisible(true);
	}

	@Override
	public void showClassNotFoundErrorDialog() {
		GuiComponentFactory.getDefaultInstance().createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT).setVisible(true);
	}

	@Override
	public void showLevelInvalidDialog(String cause) {
		GuiComponentFactory.getDefaultInstance().createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT + cause).setVisible(true);		
	}
}