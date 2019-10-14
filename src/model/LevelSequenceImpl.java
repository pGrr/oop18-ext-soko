package model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class LevelSequenceImpl implements LevelSequence, Serializable, Iterable<LevelSchema> {
	
	private static final long serialVersionUID = -6586784395736453900L;
	
	private final String name;
	private final List<LevelSchema> schemaList;
	
	public LevelSequenceImpl(String name, List<LevelSchema> schemaList) {
		this.name = name;
		this.schemaList = schemaList;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getNames() {
		return this.schemaList.stream().map(l -> l.getName() ).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return this.getName() + ": " + this.getNames();
	}

	@Override
	public Iterator<LevelSchema> iterator() {
		return this.schemaList.iterator();
	}

}
