package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.SokobanController;
import model.Element.Type;
import model.Level;

class LevelCanvasImpl extends JPanel implements LevelCanvas {

	private static final long serialVersionUID = 1009850031284813715L;	
	private final SokobanController controller;
	private final JFrame ownerFrame;
	private final int elementHeight;
	private final int elementWidth;
	private final ViewElement user;
	private final List<ViewElement> targets;
	private final List<ViewElement> boxes;
	private final List<ViewElement> walls;
	private final List<List<Type>> schema;
	
	public LevelCanvasImpl(JFrame ownerFrame, SokobanController controller, List<List<Type>> schema) {
		this.controller = controller;
		this.schema = schema;
		this.ownerFrame = ownerFrame;
		this.setSize(this.getPreferredSize());
		this.elementHeight = computeElementHeight();
		this.elementWidth = computeElementWidth();
        this.user = this.createUser();
        this.targets = createViewElementList(Type.TARGET);
        this.boxes = createViewElementList(Type.MOVABLE);;
        this.walls = createViewElementList(Type.UNMOVABLE);
        this.addKeyListener(this.buttonPressed());
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
        g.drawImage(this.user.getImage(), this.user.getXposition(), this.user.getYposition(), this.user.getWidth(), this.user.getHeight(),  null);
        getAllElementStream().forEach(el -> g.drawImage(el.getImage(), el.getXposition(), el.getYposition(), el.getWidth(), el.getHeight(),  null));
		this.revalidate();
		this.repaint();
	}  
	
	private int computeElementHeight() {
		double panelHeight = this.getSize().getHeight();
		return (int) Math.round(panelHeight / Level.N_ROWS);
	}
	
	private int computeElementWidth() {
		double panelWidth = this.ownerFrame.getWidth();
		return (int) Math.round(panelWidth / Level.N_ROWS);
	}
	
	@Override
	public void moveElement(int fromX, int fromY, int toX, int toY) {
		ViewElement element = getAllElementStream().filter(el -> el.getXposition() == fromX && el.getYposition() == fromY).findFirst().get();
		element.setXposition(toX);
		element.setYposition(toY);
	}

	private Stream<ViewElement> getAllElementStream() {
		return Stream.concat(Stream.concat(targets.stream(), boxes.stream()), walls.stream());
	}

	private ViewElement createUser() {
		ViewElement user = null;
		for (int i=0; i<this.schema.size(); i++) {
			for (int j=0; j<this.schema.get(0).size(); j++) {
				if (this.schema.get(i).get(j) == Type.USER) {
			        user = new ViewElementImpl(Type.USER, elementHeight, elementWidth, j*elementWidth, i*elementHeight);				
			    }
			}
		}
		return user;	
	}
	
	private List<ViewElement> createViewElementList(Type type) {
		List<ViewElement> l = new ArrayList<>();
		for (int i=0; i<this.schema.size(); i++) {
			for (int j=0; j<this.schema.size(); j++) {
				if (this.schema.get(i).get(j) == type) {
					l.add(new ViewElementImpl(type, elementHeight, elementWidth, j*elementWidth, i*elementHeight));
				}
			}
		}
		return l;
	}
	
	KeyListener buttonPressed() {
		return new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				SwingUtilities.invokeLater(() -> {
					if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_KP_DOWN) {
						LevelCanvasImpl.this.controller.moveDown();
					} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP) {
						LevelCanvasImpl.this.controller.moveUp();
					} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_KP_LEFT) {
						LevelCanvasImpl.this.controller.moveLeft();
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_KP_RIGHT) {
						LevelCanvasImpl.this.controller.moveRight();
					}
				});				
			}
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
		};
	}
}