package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import controller.CraftObserver;

public final class CraftViewImpl extends AbstractView implements CraftView {
	
	private static final double HEIGHT_TO_SCREENSIZE_RATIO = 1;
	private static final double WIDTH_TO_HEIGHT_RATIO = 1;
	private static final int DEFAULT_PADDING = 20;
	private static final String TITLE = "SOKOBAN - Craft your level";
	private static final String PANEL_GRID_TITLE = "Level grid";
	private static final String PANEL_OPTIONS_TITLE = "Edit level options";
	private static final String LABEL_WELCOME_TEXT = "Welcome! Click on a element to select it and then the cell's grid to mark them.";
	private static final String BUTTON_SAVE_TEXT = "SAVE";
	private static final String BUTTON_RESET_TEXT = "RESET";
	private static final String BUTTON_BACK_TEXT = "BACK TO INITIAL VIEW";
	private static final String DIALOG_ERROR_TITLE = "ERROR";
	private static final String ERROR_INITIAL_POSITION_TEXT = "You must choose a singular initial point";
	private static final String ERROR_BOX_AND_TARGET_TEXT = "You must choose an equal number of boxes and targets";
	private static final String ICON_WALL_PATH = "wall-30px.png";
	private static final String ICON_BOX_PATH = "box-30px.png";
	private static final String ICON_TARGET_PATH = "target-30px.png";
	private static final String ICON_USER_PATH = "user-30px.png";
	private static final String ICON_SAVE_PATH = "download.png";
	private static final String ICON_CANCEL_PATH = "cross.png";
	private static final String ICON_BACK_PATH = "back.png";
	
	private static final int N_ROW_ELEMENTS = 15; // just for testing, is to be asked to the model
	private CraftObserver controller;
	private final List<JToggleButton> selectionList;
	private final List<List<JButton>> grid;
	
	public CraftViewImpl(int nRows, CraftObserver controller) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.setObserver(controller);
		this.selectionList = toggleButtons();
		this.grid = this.createGrid(nRows);
		this.getFrame().add(mainPanel());
		this.getFrame().setVisible(true);
	}
	
	@Override
	public void setObserver(CraftObserver controller) {
		this.controller = controller;
	}

	@Override
	public void show() {
		this.getFrame().setVisible(true);
	}

	@Override
	public void showNoInitialPointDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showTooManyInitialPointDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showNoTargetDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showUnequalBoxAndTargetDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showUnForeSeenErrorDialog(String message) {
		// TODO Auto-generated method stub

	}
	
	private List<List<JButton>> createGrid(int nRows) {
		List<List<JButton>> grid = new ArrayList<>();
		IntStream.range(0, nRows)
		 .forEach(i -> {
			grid.add(new ArrayList<>());
			IntStream.range(0, nRows)
					 .forEach(j -> {
						 grid.get(i).add(new JButton());
					 });
		 });
		return grid;
	}
	
	private JPanel mainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(this.createEmptyPaddingBorder(DEFAULT_PADDING));
		mainPanel.add(upperPanel(), BorderLayout.PAGE_START);
		mainPanel.add(gridPanel(), BorderLayout.CENTER);
		mainPanel.add(choicesPanel(), BorderLayout.PAGE_END);
		return mainPanel;
	}
	
	private List<JToggleButton> toggleButtons() {
		List<JToggleButton> l = new ArrayList<>();
		l.add(createToggleButton("", createImageIcon(ICON_WALL_PATH), toggleButtonActionListener()));
		l.add(createToggleButton("", createImageIcon(ICON_BOX_PATH), toggleButtonActionListener()));
		l.add(createToggleButton("", createImageIcon(ICON_TARGET_PATH), toggleButtonActionListener()));
		l.add(createToggleButton("", createImageIcon(ICON_USER_PATH), toggleButtonActionListener()));
		return l; 
	}
	
	private ActionListener toggleButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			CraftViewImpl.this.selectionList.forEach(b -> b.setSelected(false));
			((JToggleButton) e.getSource()).setSelected(true);
		});
	}
	
	private JToggleButton getSelectionElement() {
		return this.selectionList.stream()
								 .filter(e -> e.isSelected())
								 .findFirst()
								 .orElse(new JToggleButton());
	}
	
	private JPanel upperPanel() {
		JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel welcomeLabel = new JLabel(LABEL_WELCOME_TEXT);
		welcomeLabel.setBorder(this.createEmptyPaddingBorder(DEFAULT_PADDING));
		upperPanel.add(welcomeLabel);
		this.selectionList.stream().forEach(upperPanel::add);
		return upperPanel;
	}
	
	private final JPanel gridPanel() {
		JPanel p = new JPanel(new GridLayout(N_ROW_ELEMENTS, N_ROW_ELEMENTS));
		p.setBorder(createTitledPaddingBorder(PANEL_GRID_TITLE, DEFAULT_PADDING));
		this.grid.stream().flatMap(List::stream).forEach(b -> {
			b.addActionListener(gridButtonActionListener());
			p.add(b);
		});
		return p;
	}
	
	private ActionListener gridButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {

		});
	}
	
	private JPanel choicesPanel() {
		JPanel choicesPanel = new JPanel(new GridLayout(1,3, DEFAULT_PADDING, DEFAULT_PADDING));
		choicesPanel.setBorder(createTitledPaddingBorder(PANEL_OPTIONS_TITLE, DEFAULT_PADDING));
		choicesPanel.add(createButton(BUTTON_SAVE_TEXT, ICON_SAVE_PATH, saveButtonActionListener()));
		choicesPanel.add(createButton(BUTTON_RESET_TEXT, ICON_CANCEL_PATH, resetButtonActionListener()));
		choicesPanel.add(createButton(BUTTON_BACK_TEXT, ICON_BACK_PATH, backButtonActionListener()));
		return choicesPanel;
	}
	
	private ActionListener saveButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {

		});
	}
	
	private JDialog errorDialog(String message) {
		return createDialog(DIALOG_ERROR_TITLE, message);
	}
	
	private ActionListener resetButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			
		});
	}
	
	private ActionListener backButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			
		});
	}	
}
