package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import controller.CraftObserver;
import model.ElementModel.Type;
import static view.Views.*;

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
	private final List<JToggleButton> toggleButtonSelectionList;
	private final List<Type> typeSelectionList;
	private final List<List<JButton>> buttonGrid;
	private final List<List<Type>> typeGrid;
	
	public CraftViewImpl(CraftObserver controller) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.setObserver(controller);
		this.toggleButtonSelectionList = createToggleButtonSelectionList();
		this.typeSelectionList = createTypeSelectionList();
		this.buttonGrid = createButtonGrid(N_ROW_ELEMENTS);
		this.typeGrid = createElementGrid(N_ROW_ELEMENTS);
		this.getFrame().add(createMainPanel());
	}
	
	@Override
	protected JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		mainPanel.add(upperPanel(), BorderLayout.PAGE_START);
		mainPanel.add(gridPanel(), BorderLayout.CENTER);
		mainPanel.add(choicesPanel(), BorderLayout.PAGE_END);
		return mainPanel;
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
	
	private List<List<JButton>> createButtonGrid(int nRows) {
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
	
	private List<List<Type>> createElementGrid(int nRows) {
		List<List<Type>> grid = new ArrayList<>();
		IntStream.range(0, nRows)
		 .forEach(i -> {
			grid.add(new ArrayList<>());
			IntStream.range(0, nRows)
					 .forEach(j -> {
						 grid.get(i).add(Type.EMPTY);
					 });
		 });
		return grid;
	}

	private List<JToggleButton> createToggleButtonSelectionList() {
		List<JToggleButton> l = new ArrayList<>();
		l.add(createToggleButton("", createImageIcon(ICON_WALL_PATH), toggleButtonActionListener()));
		l.add(createToggleButton("", createImageIcon(ICON_BOX_PATH), toggleButtonActionListener()));
		l.add(createToggleButton("", createImageIcon(ICON_TARGET_PATH), toggleButtonActionListener()));
		l.add(createToggleButton("", createImageIcon(ICON_USER_PATH), toggleButtonActionListener()));
		return l; 
	}
	
	private List<Type> createTypeSelectionList() {
		List<Type> l = new ArrayList<>();
		l.add(Type.UNMOVABLE);
		l.add(Type.MOVABLE);
		l.add(Type.TARGET);
		l.add(Type.USER);
		return l; 
	}
	
	private ActionListener toggleButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			CraftViewImpl.this.toggleButtonSelectionList.forEach(b -> b.setSelected(false));
			((JToggleButton) e.getSource()).setSelected(true);
		});
	}
	
	private JToggleButton getSelectedToggleButton() {
		return  this.toggleButtonSelectionList.stream()
				 .filter(e -> e.isSelected())
				 .findFirst()
				 .orElse(new JToggleButton());
	}
	
	private Type getSelectedType() {
		JToggleButton selectedButton = getSelectedToggleButton();
		int index = this.toggleButtonSelectionList.indexOf(selectedButton);
		return this.typeSelectionList.get(index);
	}
	
	private JPanel upperPanel() {
		JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel welcomeLabel = new JLabel(LABEL_WELCOME_TEXT);
		welcomeLabel.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		upperPanel.add(welcomeLabel);
		this.toggleButtonSelectionList.stream().forEach(upperPanel::add);
		return upperPanel;
	}
	
	private final JPanel gridPanel() {
		JPanel p = new JPanel(new GridLayout(N_ROW_ELEMENTS, N_ROW_ELEMENTS));
		p.setBorder(createTitledPaddingBorder(PANEL_GRID_TITLE, DEFAULT_PADDING));
		this.buttonGrid.stream().flatMap(List::stream).forEach(b -> {
			b.addActionListener(gridButtonActionListener());
			p.add(b);
		});
		return p;
	}
	
	private ActionListener gridButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JButton clickedButton = (JButton)e.getSource();
			for(int r=0; r<N_ROW_ELEMENTS; r++) {
				for(int c=0; c<N_ROW_ELEMENTS; c++) {
					if (this.buttonGrid.get(r).get(c) == clickedButton) {
						if (typeGrid.get(r).get(c).equals(getSelectedType())) {
							typeGrid.get(r).set(c, Type.EMPTY);
							buttonGrid.get(r).get(c).setIcon(new ImageIcon()); 
						} else {
							typeGrid.get(r).set(c, getSelectedType());
							buttonGrid.get(r).get(c).setIcon(getSelectedToggleButton().getIcon());
						}
					}
				}
			}
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
			JFileChooser fc = new JFileChooser();
			fc.showSaveDialog(getFrame());
			controller.saveLevel(typeGrid, fc.getSelectedFile().getAbsolutePath());
		});
	}
	
	private JDialog errorDialog(String message) {
		return createDialog(this.getFrame(), DIALOG_ERROR_TITLE, message);
	}
	
	private ActionListener resetButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.buttonGrid.stream().flatMap(List::stream).forEach(b -> b.setIcon(new ImageIcon()));
		});
	}
	
	private ActionListener backButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			controller.backToInitialView();
		});
	}	
}
