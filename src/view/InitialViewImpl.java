package view;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class InitialViewImpl extends AbstractView implements InitialView {

	private static final String TITLE = "SOKOBAN - InitialView";
	private static final double HEIGHT_TO_SCREENSIZE_RATIO = 0.8;
	private static final double WIDTH_TO_HEIGHT_RATIO = 1;
	private static final int DEFAULT_PADDING = 20;
	private static final String ICON_CRAFT = "craft.png";
	private static final String ICON_OK = "ok.png";
	private static final String ICON_UP = "arrow-up.png";
	private static final String ICON_DOWN = "arrow-down.png";
	private static final String ICON_RESET = "cross.png";
	private static final String ICON_DOWNLOAD = "download.png";
	private static final String ICON_UPLOAD = "upload.png";
	private static final String PANEL_LEVEL_SEQUENCE_TITLE = "Level sequence";
	private static final String PANEL_EDIT_LEVEL_SEQUENCE_TITLE = "Edit sequence elements";
	private static final String PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE = "Save your sequence or load one";
	private static final String LABEL_WELCOME_TEXT = "Welcome to Sokoban! What would you like to do?";
	private static final String BUTTON_CRAFT_TEXT = "CRAFT A LEVEL";
	private static final String BUTTON_PLAY_TEXT = "PLAY";
	
	public InitialViewImpl() {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		JFrame frame = this.getFrame();
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(this.getDefaultPaddingBorder(DEFAULT_PADDING));
		mainPanel.add(welcomePanel(), BorderLayout.PAGE_START);
		mainPanel.add(levelSequencePanel(), BorderLayout.CENTER);
		mainPanel.add(choicesPanel(), BorderLayout.PAGE_END);
		frame.add(mainPanel);
	}

	@Override
	public void show() {
		this.getFrame().setVisible(true);
	}

	@Override
	public void showSaveSuccessDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showSaveFailedDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLoadSuccessDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLoadFailedDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showLevelNotValidDialog() {
		// TODO Auto-generated method stub

	}
	
	private JPanel welcomePanel() {
		JPanel p = new JPanel();
		JLabel l = new JLabel(LABEL_WELCOME_TEXT);
		l.setBorder(this.getDefaultPaddingBorder(DEFAULT_PADDING));
		p.add(l);
		return p;
	}

	private JPanel levelSequencePanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.setBorder(this.getDefaultPaddingBorder(DEFAULT_PADDING));
		p.add(levelListPanel());
		p.add(editListPanel());
		p.add(saveOrLoadListPanel());
		return p;
	}
	
	private JPanel choicesPanel() {
		int littlePadding = Math.round(DEFAULT_PADDING / 5);
		JPanel p = new JPanel(new BorderLayout(littlePadding, littlePadding));
		p.setBorder(this.getDefaultPaddingBorder(DEFAULT_PADDING));
		// Craft new level and save
		JButton craftButton = new JButton(BUTTON_CRAFT_TEXT, 
				new ImageIcon(ClassLoader.getSystemResource(ICON_CRAFT)));
		craftButton.setBorder(this.getDefaultPaddingBorder(DEFAULT_PADDING));
		p.add(craftButton, BorderLayout.PAGE_START);
		// Big play button 
		JButton playButton = new JButton(BUTTON_PLAY_TEXT, 
				new ImageIcon(ClassLoader.getSystemResource(ICON_OK)));
		playButton.setBorder(this.getDefaultPaddingBorder(DEFAULT_PADDING));
		p.add(playButton, BorderLayout.PAGE_END);
		return p;
	}
	
	private JPanel levelListPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new CompoundBorder(new TitledBorder(PANEL_LEVEL_SEQUENCE_TITLE), 
				this.getDefaultPaddingBorder(DEFAULT_PADDING)));
		JList<String> l = new JList<String>();
		l.setFixedCellHeight(DEFAULT_PADDING * 2);
		// -------- for testing purposes --------
		String[] data = {"Prova1", "Prova2", "Prova3", "Prova1", "Prova2", "Prova3", "Prova1", "Prova2", "Prova3", "Prova1", "Prova2", "Prova3"};
		// --------------------------------------
		l.setListData(data);
		p.add(new JScrollPane(l));		
		return p;
	}
	
	private JPanel editListPanel() {
		JPanel p = new JPanel();
		JButton upButton = new JButton(
				new ImageIcon(ClassLoader.getSystemResource(ICON_UP)));
		p.add(upButton);
		JButton downButton = new JButton(
				new ImageIcon(ClassLoader.getSystemResource(ICON_DOWN)));
		p.add(downButton);
		JButton cancelButton = new JButton(
				new ImageIcon(ClassLoader.getSystemResource(ICON_RESET)));
		p.add(cancelButton);
		p.setBorder(new CompoundBorder(new TitledBorder(PANEL_EDIT_LEVEL_SEQUENCE_TITLE), 
				this.getDefaultPaddingBorder(DEFAULT_PADDING)));
		return p;
	}
	
	private JPanel saveOrLoadListPanel() {
		JPanel p = new JPanel();
		JButton saveButton = new JButton(
				new ImageIcon(ClassLoader.getSystemResource(ICON_DOWNLOAD)));
		p.add(saveButton);
		JButton loadButton = new JButton(
				new ImageIcon(ClassLoader.getSystemResource(ICON_UPLOAD)));
		p.add(loadButton);
		p.setBorder(new CompoundBorder(
				new TitledBorder(PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE), 
				this.getDefaultPaddingBorder(DEFAULT_PADDING)));
		return p;
	}
	
	public static void main(String ...args) {
		InitialView initialView = new InitialViewImpl();
		initialView.show();
	}
}
