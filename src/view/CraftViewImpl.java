package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import controller.SokobanController;
import model.PairImpl;
import model.Element.Type;
import model.LevelImpl.LevelNotValidException;
import model.Pair;

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
	private static final String BUTTON_LOAD_TEXT = "LOAD";
	private static final String BUTTON_RESET_TEXT = "RESET";
	private static final String BUTTON_BACK_TEXT = "BACK TO INITIAL VIEW";
	private static final String ICON_WALL_PATH = "wall-30px.png";
	private static final String ICON_BOX_PATH = "box-30px.png";
	private static final String ICON_TARGET_PATH = "target-30px.png";
	private static final String ICON_USER_PATH = "user-30px.png";
	private static final String ICON_SAVE_PATH = "download.png";
	private static final String ICON_LOAD_PATH = "upload.png";
	private static final String ICON_CANCEL_PATH = "cross.png";
	private static final String ICON_BACK_PATH = "back.png";
	private static final String DIALOG_ERROR_TITLE = "ERROR";
	private static final String DIALOG_LEVEL_NOT_CORRECT_TITLE = "LEVEL NOT SAVED";
	private static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "Oops! One or more levels in the sequence seems to be incorrect!";
	private static final String DIALOG_IOERROR_TEXT = "An error occurred during input / output operation";

	
	private static final int N_ROW_ELEMENTS = 15; // TODO just for testing, is to be asked to the model
	
	private SokobanController controller;
	private final List<Pair<Type,JToggleButton>> toggleButtons;
	private final List<List<Pair<JButton, Type>>> buttonGrid;
	private final List<Pair<Type,ImageIcon>> icons;
	
	public CraftViewImpl(SokobanController controller) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.icons = createIcons();
		this.toggleButtons = createToggleButtonSelectionList();
		this.buttonGrid = createButtonGrid(N_ROW_ELEMENTS);
		this.getFrame().add(createMainPanel());
	}
	
	@Override
	public void showLevelNotValidDialog() {
		showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT);
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
	public void show() {
		this.getFrame().setVisible(true);
	}
	
	private List<Pair<Type, ImageIcon>> createIcons() {
		List<Pair<Type, ImageIcon>> l = new ArrayList<>();
		l.add(new PairImpl<>(Type.EMPTY, createImageIcon("")));
		l.add(new PairImpl<>(Type.MOVABLE, createImageIcon(ICON_BOX_PATH)));
		l.add(new PairImpl<>(Type.UNMOVABLE, createImageIcon(ICON_WALL_PATH)));
		l.add(new PairImpl<>(Type.TARGET, createImageIcon(ICON_TARGET_PATH)));
		l.add(new PairImpl<>(Type.USER, createImageIcon(ICON_USER_PATH)));
		return l;
	}

	private List<Pair<Type,JToggleButton>> createToggleButtonSelectionList() {
		List<Pair<Type,JToggleButton>> l = new ArrayList<>();
		l.add(new PairImpl<>(Type.MOVABLE, createToggleButton("", createImageIcon(ICON_BOX_PATH), toggleButtonActionListener())));
		l.add(new PairImpl<>(Type.UNMOVABLE, createToggleButton("", createImageIcon(ICON_WALL_PATH), toggleButtonActionListener())));
		l.add(new PairImpl<>(Type.TARGET, createToggleButton("", createImageIcon(ICON_TARGET_PATH), toggleButtonActionListener())));
		l.add(new PairImpl<>(Type.USER, createToggleButton("", createImageIcon(ICON_USER_PATH), toggleButtonActionListener())));
		return l; 
	}
	
	private List<List<Pair<JButton,Type>>> createButtonGrid(int nRows) {
		List<List<Pair<JButton,Type>>> grid = new ArrayList<>();
		IntStream.range(0, nRows)
		 .forEach(i -> {
			grid.add(new ArrayList<>());
			IntStream.range(0, nRows)
					 .forEach(j -> {
						 grid.get(i).add(new PairImpl<JButton,Type>(new JButton(), Type.EMPTY));
					 });
		 });
		return grid;
	}
	
	private JPanel upperPanel() {
		JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel welcomeLabel = new JLabel(LABEL_WELCOME_TEXT);
		welcomeLabel.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		upperPanel.add(welcomeLabel);
		this.toggleButtons.stream()
						  .map(Pair::getY)
						  .forEach(upperPanel::add);
		return upperPanel;
	}
	
	private final JPanel gridPanel() {
		JPanel panel = new JPanel(new GridLayout(N_ROW_ELEMENTS, N_ROW_ELEMENTS));
		panel.setBorder(createTitledPaddingBorder(PANEL_GRID_TITLE, DEFAULT_PADDING));
		this.buttonGrid.stream()
					   .flatMap(List::stream)
					   .map(Pair::getX)
					   .forEach(button -> {
							button.addActionListener(gridButtonActionListener());
							panel.add(button);
						});
		return panel;
	}
	
	private JPanel choicesPanel() {
		JPanel choicesPanel = new JPanel(new GridLayout(1,4, DEFAULT_PADDING, DEFAULT_PADDING));
		choicesPanel.setBorder(createTitledPaddingBorder(PANEL_OPTIONS_TITLE, DEFAULT_PADDING));
		choicesPanel.add(createButton(BUTTON_SAVE_TEXT, ICON_SAVE_PATH, saveButtonActionListener()));
		choicesPanel.add(createButton(BUTTON_LOAD_TEXT, ICON_LOAD_PATH, loadButtonActionListener()));
		choicesPanel.add(createButton(BUTTON_RESET_TEXT, ICON_CANCEL_PATH, resetButtonActionListener()));
		choicesPanel.add(createButton(BUTTON_BACK_TEXT, ICON_BACK_PATH, backButtonActionListener()));
		return choicesPanel;
	}

	private ActionListener toggleButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			CraftViewImpl.this.toggleButtons.forEach(b -> b.getY().setSelected(false));
			((JToggleButton) e.getSource()).setSelected(true);
		});
	}
	
	private ActionListener gridButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JButton clickedButton = (JButton)e.getSource();
			Pair<JButton, Type> clickedPair = this.buttonGrid.stream()
												  .flatMap(List::stream)
												  .filter(pair -> pair.getX().equals(clickedButton))
												  .findFirst()
												  .orElse(new PairImpl<>(new JButton(), Type.EMPTY));
			if (clickedPair.getY().equals(getSelectedType())) {
				clickedPair.getX().setIcon(new ImageIcon());
				clickedPair.setY(Type.EMPTY);
			} else {
				clickedPair.getX().setIcon(getSelectedToggleButton().getIcon());
				clickedPair.setY(getSelectedType());
			}
		});
	}

	private ActionListener saveButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelFileDescription(), controller.getLevelFileExtension());
			fc.showSaveDialog(getFrame());
			List<List<Type>> typeGrid = getCurrentTypeGrid();
			try {
				controller.saveLevel(typeGrid, fc.getSelectedFile().getAbsolutePath() + controller.getLevelFileExtension());
			} catch (IOException ioException) {
				showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				ioException.printStackTrace();
			} catch (LevelNotValidException levelNotValidException) {
				showErrorDialog(DIALOG_LEVEL_NOT_CORRECT_TITLE, levelNotValidException.toString());
			}
		});
	}
	
	private ActionListener loadButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelFileDescription(), controller.getLevelFileExtension());
			fc.showOpenDialog(getFrame());
			List<List<Type>> typeGrid;
			try {
				typeGrid = controller.loadLevel(fc.getSelectedFile().getAbsolutePath()).getTypeGrid();
				CraftViewImpl.this.acceptTypeGrid(typeGrid);
			} catch (ClassNotFoundException | IOException  inputError) {
				showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(inputError.toString());
			} catch (LevelNotValidException levelNotValidException) {
				showErrorDialog(DIALOG_LEVEL_NOT_CORRECT_TITLE, levelNotValidException.toString());
			}			
		});
	}
	
	private List<List<Type>> getCurrentTypeGrid() {
		List<List<Type>> typeGrid = new ArrayList<>();
		this.buttonGrid.stream()
					   .forEach(row -> {
						   List<Type> newRow = new ArrayList<>();
						   row.stream()
						   	  .map(Pair::getY)
						   	  .forEach(newRow::add);
						   typeGrid.add(newRow);
					   });
		return typeGrid;
	}
	
	private void acceptTypeGrid(List<List<Type>> typeGrid) {
		for(int i=0; i<N_ROW_ELEMENTS; i++) {
			for(int j=0; j<N_ROW_ELEMENTS; j++) {
				this.buttonGrid.get(i).get(j).getX().setIcon(findTypeIcon(typeGrid.get(i).get(j)));
				this.buttonGrid.get(i).get(j).setY(typeGrid.get(i).get(j));
			}
		}
	}
	
	private ActionListener resetButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.buttonGrid.stream()
				.flatMap(List::stream)
				.forEach(pair -> {
					pair.getX().setIcon(new ImageIcon());
					pair.setY(Type.EMPTY);
				});
		});
	}
	
	private ActionListener backButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.controller.backToInitialViewButtonPressed();
		});
	}
	
	private JToggleButton getSelectedToggleButton() {
		return  this.toggleButtons.stream()
				  				  .map(Pair::getY)
								  .filter(e -> e.isSelected())
								  .findFirst()
								  .orElse(new JToggleButton());
	}
	
	private Type getSelectedType() {
		return  this.toggleButtons.stream()
				  .filter(e -> e.getY().isSelected())
				  .map(Pair::getX)
				  .findFirst()
				  .orElse(Type.EMPTY);
	}
	
	
	private ImageIcon findTypeIcon(Type type) {
		return this.icons.stream()
				   .filter(pair -> pair.getX().equals(type))
				   .map(Pair::getY)
				   .findFirst()
				   .orElse(new ImageIcon());
	}
}