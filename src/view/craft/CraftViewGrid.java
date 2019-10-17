package view.craft;

import static view.Components.DEFAULT_PADDING;
import static view.Components.createImageIcon;
import static view.Components.createResizedIcon;
import static view.Components.createTitledPaddingBorder;
import static view.craft.CraftViewConstants.GRIDBUTTON_RELATIVE_ICON_HEIGHT;
import static view.craft.CraftViewConstants.GRIDBUTTON_RELATIVE_ICON_WIDTH;
import static view.craft.CraftViewConstants.ICON_BOX;
import static view.craft.CraftViewConstants.ICON_TARGET;
import static view.craft.CraftViewConstants.ICON_USER;
import static view.craft.CraftViewConstants.ICON_WALL;
import static view.craft.CraftViewConstants.PANEL_GRID_TITLE;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Pair;
import model.PairImpl;
import model.element.Element.Type;
import model.level.LevelSchema;

public class CraftViewGrid {

	private final CraftViewSelection selection;
	private final List<List<Pair<JButton, Type>>> buttonGrid;
	private final List<Pair<Type,ImageIcon>> icons;
	
	public CraftViewGrid(CraftViewSelection selection) {
		this.selection = selection;
		this.buttonGrid = createButtonGrid(LevelSchema.N_ROWS);
		this.icons = createIcons();
	}
	
	public final JPanel createPanel() {
		JPanel panel = new JPanel(new GridLayout(LevelSchema.N_ROWS, LevelSchema.N_ROWS));
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
	
	public List<List<Type>> getCurrentTypeGrid() {
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
	
	public void acceptTypeGrid(List<List<Type>> typeGrid) {
		for(int i=0; i<LevelSchema.N_ROWS; i++) {
			for(int j=0; j<LevelSchema.N_ROWS; j++) {
				int w = (int) Math.round(this.buttonGrid.get(i).get(j).getX().getWidth() * GRIDBUTTON_RELATIVE_ICON_WIDTH);
				int h = (int) Math.round(this.buttonGrid.get(i).get(j).getX().getHeight() * GRIDBUTTON_RELATIVE_ICON_HEIGHT);
				ImageIcon icon = findTypeIcon(typeGrid.get(i).get(j));
				this.buttonGrid.get(i).get(j).getX().setIcon(createResizedIcon(icon, w, h));
				this.buttonGrid.get(i).get(j).setY(typeGrid.get(i).get(j));
			}
		}
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

	private List<Pair<Type, ImageIcon>> createIcons() {
		List<Pair<Type, ImageIcon>> l = new ArrayList<>();
		l.add(new PairImpl<>(Type.EMPTY, createImageIcon("")));
		l.add(new PairImpl<>(Type.BOX, ICON_BOX));
		l.add(new PairImpl<>(Type.WALL, ICON_WALL));
		l.add(new PairImpl<>(Type.TARGET, ICON_TARGET));
		l.add(new PairImpl<>(Type.USER, ICON_USER));
		return l;
	}
	
	private ActionListener gridButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JButton clickedButton = (JButton)e.getSource();
			Pair<JButton, Type> clickedPair = this.buttonGrid.stream()
												  .flatMap(List::stream)
												  .filter(pair -> pair.getX().equals(clickedButton))
												  .findFirst()
												  .orElse(new PairImpl<>(new JButton(), Type.EMPTY));
			if (clickedPair.getY().equals(this.selection.getSelectedType())) {
				clickedPair.getX().setIcon(new ImageIcon());
				clickedPair.setY(Type.EMPTY);
			} else {
				int w = (int) Math.round(clickedButton.getWidth() * GRIDBUTTON_RELATIVE_ICON_WIDTH);
				int h = (int) Math.round(clickedButton.getHeight() * GRIDBUTTON_RELATIVE_ICON_HEIGHT);
				Icon i = this.selection.getSelectedToggleButton().getIcon();
				if (i.getClass() != ImageIcon.class) {
					throw new IllegalStateException();
				}
				clickedPair.getX().setIcon(createResizedIcon((ImageIcon)i, w, h));
				clickedPair.setY(this.selection.getSelectedType());
			}
		});
	}
	
	public ActionListener resetButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.buttonGrid.stream()
				.flatMap(List::stream)
				.forEach(pair -> {
					pair.getX().setIcon(new ImageIcon());
					pair.setY(Type.EMPTY);
				});
		});
	}
	
	
	private ImageIcon findTypeIcon(Type type) {
		return this.icons.stream()
				   .filter(pair -> pair.getX().equals(type))
				   .map(Pair::getY)
				   .findFirst()
				   .orElse(new ImageIcon());
	}
	
}
