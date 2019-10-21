package view.play;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import controller.ControllerFacade;
import model.element.Element;

import static view.play.GameConstants.*;

class GameArea extends JPanel {

	private static final long serialVersionUID = 1009850031284813715L;	
	
	private final GameWindowImpl owner;
	private final Timer timer;
	private Optional<Integer> keyPressedCode;
	private Optional<Graphics> graphics;
	
	public GameArea(GameWindowImpl owner) {
		this.owner = owner;
		this.timer = new Timer(TIMER_DELAY_MS, this.timerAction());
		this.keyPressedCode = Optional.empty();
		this.graphics = Optional.empty();
		this.setSize(this.getPreferredSize());
		this.setFocusable(true);
		this.addKeyListener(buttonPressed());
		this.timer.start();
		this.owner.getFrame().repaint();
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.graphics = Optional.of(g);
		this.owner.getState().getAllElements()
							 .stream()
							 .forEach(ve -> g.drawImage(ve.getImage(), ve.getElement().getX(), ve.getElement().getY(), this));	
	}
	
	@Override
    public Dimension getPreferredSize() {
		int frameHeight = (int) Math.round(owner.getFrame().getSize().getHeight()) - owner.getFrame().getInsets().top;
		int frameWidth = (int) Math.round(owner.getFrame().getSize().getWidth());
		return new Dimension(frameWidth, frameHeight);		
    }
	
	public void drawElements(List<Element> elements) {
		if (graphics.isPresent()) {
			elements.stream()
					.map(this.owner.getState()::translateElement)
					.forEach(ve -> graphics.get().drawImage(ve.getImage(), ve.getElement().getX(), ve.getElement().getY(), this));			
		}
		this.repaint(TIMER_DELAY_MS);
	}
	
	public void drawDarkerBoxes(List<Element> newBoxesOnTarget) {
		Objects.requireNonNull(newBoxesOnTarget);
		this.owner.getState().updateBoxesOnTarget(newBoxesOnTarget);
		this.repaint(TIMER_DELAY_MS);
	}
	
	private KeyListener buttonPressed() {
		return new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				SwingUtilities.invokeLater(() -> GameArea.this.keyPressedCode = Optional.of(e.getKeyCode()));
			}
		};
		
	}	

	private ActionListener timerAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			if (this.keyPressedCode.isPresent()) {
				Integer key = keyPressedCode.get();
				if (key.equals(KeyEvent.VK_DOWN) || key.equals(KeyEvent.VK_KP_DOWN)) {
					ControllerFacade.getInstance().getGameController().moveDown();
				} else if (key.equals(KeyEvent.VK_UP) || key.equals(KeyEvent.VK_KP_UP)) {
					ControllerFacade.getInstance().getGameController().moveUp();
				} else if (key.equals(KeyEvent.VK_LEFT) || key.equals(KeyEvent.VK_KP_LEFT)) {
					ControllerFacade.getInstance().getGameController().moveLeft();
				} else if (key.equals(KeyEvent.VK_RIGHT) || key.equals(KeyEvent.VK_KP_RIGHT)) {
					ControllerFacade.getInstance().getGameController().moveRight();
				}					
				this.keyPressedCode = Optional.empty();
			}
		});
	}
}