package view.play;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import controller.ControllerFacade;
import model.element.Element;

import static view.play.PlayViewConstants.*;

class PlayViewGameArea extends JPanel {

	private static final long serialVersionUID = 1009850031284813715L;	
	
	private final ControllerFacade controller;
	private final PlayViewContainer owner;
	private final PlayViewState state;
	private final Timer timer;
	private Optional<Integer> keyPressedCode;
	private Optional<Graphics> graphics;
	
	public PlayViewGameArea(PlayViewContainer owner, PlayViewState state, ControllerFacade controller) {
		this.controller = controller;
		this.owner = owner;
		this.state = state;
		this.keyPressedCode = Optional.empty();
		this.timer = new Timer(TIMER_DELAY_MS, this.timerAction());
		this.timer.start();
		this.graphics = Optional.empty();
		this.setSize(this.getPreferredSize());
		this.setFocusable(true);
		this.addKeyListener(buttonPressed());
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.graphics = Optional.of(g);
		this.state.getAllElements().stream()
		   .forEach(ve -> g.drawImage(ve.getImage(), ve.getElement().getX(), ve.getElement().getY(), this));	
	}
	
	@Override
    public Dimension getPreferredSize() {
		int frameHeight = (int) Math.round(owner.getFrame().getSize().getHeight()) - owner.getFrame().getInsets().top;
		int frameWidth = (int) Math.round(owner.getFrame().getSize().getWidth());
		return new Dimension(frameWidth, frameHeight);		
    }
	
	public void drawElements(List<Element> elements) {
		elements.stream()
				.map(state::translateElement)
				.forEach(ve -> graphics.get().drawImage(ve.getImage(), ve.getElement().getX(), ve.getElement().getY(), this));
		this.repaint(TIMER_DELAY_MS);
	}
	
	public void drawDarkerBoxes(List<Element> newBoxesOnTarget) {
		if (newBoxesOnTarget == null) {
			throw new IllegalArgumentException();
		}
		state.updateBoxesOnTarget(newBoxesOnTarget);
		this.repaint(TIMER_DELAY_MS);
	}
	
	private KeyListener buttonPressed() {
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				SwingUtilities.invokeLater(() -> PlayViewGameArea.this.keyPressedCode = Optional.of(e.getKeyCode()));
			}
		};
	}	

	private ActionListener timerAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			if (this.keyPressedCode.isPresent()) {
				Integer key = keyPressedCode.get();
				if (key.equals(KeyEvent.VK_DOWN) || key.equals(KeyEvent.VK_KP_DOWN)) {
					PlayViewGameArea.this.controller.moveDown();
				} else if (key.equals(KeyEvent.VK_UP) || key.equals(KeyEvent.VK_KP_UP)) {
					PlayViewGameArea.this.controller.moveUp();
				} else if (key.equals(KeyEvent.VK_LEFT) || key.equals(KeyEvent.VK_KP_LEFT)) {
					PlayViewGameArea.this.controller.moveLeft();
				} else if (key.equals(KeyEvent.VK_RIGHT) || key.equals(KeyEvent.VK_KP_RIGHT)) {
					PlayViewGameArea.this.controller.moveRight();
				}					
				this.keyPressedCode = Optional.empty();
			}
		});
	}
}