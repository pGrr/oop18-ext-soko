package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import model.Element.Type;
import model.LevelImpl.LevelNotValidException;

public class LevelSequenceImpl implements LevelSequence, Serializable {
	
	private static final long serialVersionUID = -6586784395736453900L;
	private final String name;
	private final List<Level> sequence;
	
	public LevelSequenceImpl() {
		this.name = new String();
		this.sequence = new ArrayList<>();
	}
	
	public LevelSequenceImpl(String name) {
		super();
		this.name = name;
		this.sequence = new ArrayList<>();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Level> getLevelSequence() {
		return sequence;
	}

	@Override
	public void add(Level level) {
		this.sequence.add(level);
	}

	@Override
	public void add(List<List<Type>> typeGrid, String name) throws LevelNotValidException {
		Level level = new LevelImpl(typeGrid, name);
		this.add(level);
	}

	@Override
	public List<String> getPathList() {
		return this.sequence.stream().map(l -> l.getName()).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return this.getName() + ": " + this.getPathList();
	}

	@Override
	public Iterator<Level> iterator() {
		return this.getLevelSequence().iterator();
	}

}
