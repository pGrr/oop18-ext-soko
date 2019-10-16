package view.play;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import controller.SokobanController;
import model.Element;
import model.Element.Type;

class PlayViewLevelPanel extends JPanel {

	private static final long serialVersionUID = 1009850031284813715L;	
	private static final int TIMER_DELAY_MS = 10;
	
	private final SokobanController controller;
	private final JFrame ownerFrame;
	private Optional<Graphics> graphics;
	private final Timer timer;
	private Optional<Integer> keyPressedCode;
	private final List<ViewElement> elements;
	private final Set<ViewElementBox> boxesOnTarget;
	
	public PlayViewLevelPanel(JFrame ownerFrame, SokobanController controller) {
		this.controller = controller;
		this.ownerFrame = ownerFrame;
		this.keyPressedCode = Optional.empty();
		this.timer = new Timer(TIMER_DELAY_MS, this.timerAction());
		this.timer.start();
		this.elements = new ArrayList<>();
		this.boxesOnTarget = new HashSet<>();
		this.graphics = Optional.empty();
		this.setSize(this.getPreferredSize());
		this.setFocusable(true);
		this.addKeyListener(buttonPressed());
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.graphics = Optional.of(g);
		this.elements.stream()
		   .forEach(ve -> {
			   Element e = ve.getElement();
			   g.drawImage(ve.getImage(), e.getX(), e.getY(), this);
		   });	
	}
	
	@Override
    public Dimension getPreferredSize() {
		int frameHeight = (int) Math.round(this.ownerFrame.getSize().getHeight()) - this.ownerFrame.getInsets().top;
		int frameWidth = (int) Math.round(this.ownerFrame.getSize().getWidth());
		return new Dimension(frameWidth, frameHeight);		
    }
		
	public void setElements(List<Element> elements) {
		if (elements == null || elements.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.elements.clear();
		this.elements.addAll(elements.stream().map(this::translateElement).collect(Collectors.toList()));
	}
	
	public void showElements(List<Element> elements) {
		List<ViewElement> drawElementList = elements.stream()
				.map(PlayViewLevelPanel.this::translateElement).collect(Collectors.toList());
		draw(drawElementList);
	}
	
	public void showBoxesOnTargets(List<Element> boxesOnTargets) {
		if (boxesOnTargets == null) {
			throw new IllegalArgumentException();
		}
		if (boxesOnTargets.isEmpty()) {
			this.boxesOnTarget.forEach(box -> {
				int index = this.elements.indexOf(box);
				((ViewElementBox)this.elements.get(index)).setOnTarget(false);
				this.boxesOnTarget.remove(box);
			});
		} else {
			List<ViewElementBox> newBoxes = boxesOnTargets.stream()
					  .map(this::translateElement)
					  .map(b -> (ViewElementBox)b)
					  .collect(Collectors.toList());
			newBoxes.forEach(box -> {
				int index = this.elements.indexOf(box);
				if (!this.boxesOnTarget.contains(box)) {
					((ViewElementBox)this.elements.get(index)).setOnTarget(true);
					this.boxesOnTarget.add(box);
				} 				
			});
			this.boxesOnTarget.forEach(box -> {
				int index = this.elements.indexOf(box);
				if (!newBoxes.contains(box)) {
					((ViewElementBox)this.elements.get(index)).setOnTarget(false);
					this.boxesOnTarget.remove(box);					
				}
			});
		}
		this.repaint(TIMER_DELAY_MS);
	}
	
	private void draw(List<ViewElement> viewElementList) {
		viewElementList.stream()
		   .forEach(ve -> {
			   Element e = ve.getElement();
			   this.graphics.get().drawImage(ve.getImage(), e.getX(), e.getY(), this);
			   this.repaint(TIMER_DELAY_MS);
		   });	
	}

	private ViewElement translateElement(Element element) {
		if (element.getType().equals(Type.USER)) {
			return new ViewElementUser(element);
		} else if (element.getType().equals(Type.TARGET)) {
			return new ViewElementTarget(element);
		} else if (element.getType().equals(Type.MOVABLE)) {
			return new ViewElementBox(element);
		} else if (element.getType().equals(Type.UNMOVABLE)) {
			return new ViewElementWall(element);
		}
		throw new IllegalArgumentException();
	}
	
	private ActionListener timerAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			if (this.keyPressedCode.isPresent()) {
				Integer key = keyPressedCode.get();
				if (key.equals(KeyEvent.VK_DOWN) || key.equals(KeyEvent.VK_KP_DOWN)) {
					PlayViewLevelPanel.this.controller.moveDown();
				} else if (key.equals(KeyEvent.VK_UP) || key.equals(KeyEvent.VK_KP_UP)) {
					PlayViewLevelPanel.this.controller.moveUp();
				} else if (key.equals(KeyEvent.VK_LEFT) || key.equals(KeyEvent.VK_KP_LEFT)) {
					PlayViewLevelPanel.this.controller.moveLeft();
				} else if (key.equals(KeyEvent.VK_RIGHT) || key.equals(KeyEvent.VK_KP_RIGHT)) {
					PlayViewLevelPanel.this.controller.moveRight();
				}					
				this.keyPressedCode = Optional.empty();
			}
		});
	}

	private KeyListener buttonPressed() {
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				SwingUtilities.invokeLater(() -> PlayViewLevelPanel.this.keyPressedCode = Optional.of(e.getKeyCode()));
			}
		};
	}	

}