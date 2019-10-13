package view.play;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import controller.SokobanController;
import model.Element;
import model.Element.Type;
import model.Pair;
import model.PairImpl;
import static view.Views.*;

class LevelPanel extends JPanel {

	private static final long serialVersionUID = 1009850031284813715L;	
	private static final String USER_IMAGE = "user-original.png";
	private static final String TARGET_IMAGE = "target-original.png";
	private static final String BOX_IMAGE = "box-original.png";
	private static final String WALL_IMAGE = "wall-original.png";
	private static final int TIMER_DELAY_MS = 1;
	
	private final SokobanController controller;
	private final JFrame ownerFrame;
	private Optional<List<Pair<Type,Image>>> scaledImages;
	private List<Element> elements;
	private Optional<Integer> keyPressedCode;
	private Timer timer;
	
	public LevelPanel(JFrame ownerFrame, SokobanController controller) {
		this.controller = controller;
		this.ownerFrame = ownerFrame;
		this.elements = new ArrayList<>();
		this.scaledImages = Optional.empty();
		this.setFocusable(true);
		this.keyPressedCode = Optional.empty();
		this.setSize(this.getPreferredSize());
		this.addKeyListener(buttonPressed());
		this.timer = new Timer(TIMER_DELAY_MS, this.timerAction());
		this.timer.start();
    }
	
	@Override
    public Dimension getPreferredSize() {
		int frameHeight = (int) Math.round(this.ownerFrame.getSize().getHeight()) - this.ownerFrame.getInsets().top;
		int frameWidth = (int) Math.round(this.ownerFrame.getSize().getWidth());
		return new Dimension(frameWidth, frameHeight);		
    }

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.elements.forEach(el -> g.drawImage(getImageByType(el.getType()), el.getX(), el.getY(), this));
	}  
	
	public void setElements(List<Element> elements) {
		List<Element> newElements = new ArrayList<>();
		elements.forEach(newElements::add);
		this.elements = newElements;
		Graphics g = getGraphics();
		this.paintComponent(g);
	}
	
	private KeyListener buttonPressed() {
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				LevelPanel.this.keyPressedCode = Optional.of(e.getKeyCode());
			}
		};
	}
	
	private ActionListener timerAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			if (this.keyPressedCode.isPresent()) {
				Integer key = LevelPanel.this.keyPressedCode.get();
				if (key.equals(KeyEvent.VK_DOWN) || key.equals(KeyEvent.VK_KP_DOWN)) {
					LevelPanel.this.controller.moveDown();
				} else if (key.equals(KeyEvent.VK_UP) || key.equals(KeyEvent.VK_KP_UP)) {
					LevelPanel.this.controller.moveUp();
				} else if (key.equals(KeyEvent.VK_LEFT) || key.equals(KeyEvent.VK_KP_LEFT)) {
					LevelPanel.this.controller.moveLeft();
				} else if (key.equals(KeyEvent.VK_RIGHT) || key.equals(KeyEvent.VK_KP_RIGHT)) {
					LevelPanel.this.controller.moveRight();
				}
				LevelPanel.this.keyPressedCode = Optional.empty();
			}
		});
	}
	
	private void createScaledImages() {
		if (this.elements.isEmpty()) {
			throw new IllegalStateException("Elements have not been set");
		}
		int width = this.elements.get(0).getWidth();
		int height = this.elements.get(0).getHeight();
		this.scaledImages = Optional.of(new ArrayList<>());
		for (Type type : Type.values()) {
			if (type.equals(Type.USER)) {
				Image image = createImageIcon(USER_IMAGE).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT); 
				
				this.scaledImages.get().add(new PairImpl<>(type, image));	
			} else if (type.equals(Type.TARGET)) {
				Image image = createImageIcon(TARGET_IMAGE).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT); 
				this.scaledImages.get().add(new PairImpl<>(type, image));	
			} else if (type.equals(Type.MOVABLE)) {
				Image image = createImageIcon(BOX_IMAGE).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT); 
				this.scaledImages.get().add(new PairImpl<>(type, image));	
			} else if (type.equals(Type.UNMOVABLE)) {
				Image image = createImageIcon(WALL_IMAGE).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT); 
				this.scaledImages.get().add(new PairImpl<>(type, image));	
			}
		}
	}
	
	private Image getImageByType(Type type) {
        if (!scaledImages.isPresent()) {
        	createScaledImages();
        }
        Image i = this.scaledImages.get().stream()
        							  .filter(pair -> pair.getX().equals(type))
        							  .map(Pair::getY)
        							  .findAny()
        							  .orElse(new ImageIcon().getImage());
        return i;
	}

}