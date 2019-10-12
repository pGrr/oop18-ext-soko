package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import model.Element.Type;

public class LevelInstanceImpl implements LevelInstance {
	
	private final LevelSchema schema;
	private final int width;
	private final int height;
	private final int elementHeight;
	private final int elementWidth;
	private final int deltaMovement;
	private final Element user;
	private final List<Element> targets;
	private final List<Element> boxes;
	private final List<Element> walls;

	public LevelInstanceImpl(LevelSchema schema, int width, int height) {
		this.schema = schema;
		this.width = width;
		this.height = height;
		this.elementWidth = computeElementWidth();
		this.elementHeight = computeElementHeight();
		this.deltaMovement = (elementWidth + elementHeight) / 2;
		this.user = createUser();
		this.targets = createElementList(Type.TARGET);
		this.boxes = createElementList(Type.MOVABLE);
		this.walls = createElementList(Type.UNMOVABLE);
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public List<Element> moveUserUp() {
		this.user.setYPosition(this.user.getYPosition() - deltaMovement);
		return this.getElements();
	}

	@Override
	public List<Element> moveUserDown() {
		this.user.setYPosition(this.user.getYPosition() + deltaMovement);
		return this.getElements();
	}

	@Override
	public List<Element> moveUserLeft() {
		this.user.setXPosition(this.user.getXPosition() - deltaMovement);
		return this.getElements();
	}

	@Override
	public List<Element> moveUserRight() {
		this.user.setXPosition(this.user.getXPosition() + deltaMovement);
		return this.getElements();
	}


	private int computeElementHeight() {
		return (int) Math.round(Math.ceil(this.height / LevelSchema.N_ROWS));
	}
	
	private int computeElementWidth() {
		return (int) Math.round(Math.ceil(this.width / LevelSchema.N_ROWS));
	}

	private Element createUser() {
		Element user = null;
		for (int i = 0; i < this.schema.getSchema().size(); i++) {
			for (int j = 0; j < this.schema.getSchema().get(0).size(); j++) {
				if (this.schema.getSchema().get(i).get(j) == Type.USER) {
					user = new ElementImpl(Type.USER, 
							elementHeight, elementWidth, 
							j * elementWidth, i * elementHeight);
				}
			}
		}
		return user;
	}
	
	private List<Element> createElementList(Type type) {
		List<Element> l = new ArrayList<>();
		IntStream.range(0, this.schema.getSchema().size()).forEach(i -> {
			 IntStream.range(0, this.schema.getSchema().size()).forEach(j -> {
					if (this.schema.getSchema().get(i).get(j) == type) {
						l.add(new ElementImpl(type, 
								elementHeight, elementWidth, 
								j*elementWidth, i*elementHeight));						
					}  
	 		  });
		 });
		return l;
	}

	@Override
	public List<Element> getElements() {
		return Stream.concat(Stream.concat(Stream.concat(
				Stream.of(this.user), this.targets.stream()), this.boxes.stream()), this.walls.stream())
				.collect(Collectors.toList());
	}
	
}
