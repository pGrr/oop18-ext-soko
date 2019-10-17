package model.sequence;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import model.level.LevelSchema;

public class LevelSequenceImpl implements LevelSequence, Serializable, Iterable<LevelSchema> {
	
	private static final long serialVersionUID = -6586784395736453900L;
	
	private final String name;
	private final List<LevelSchema> schemaList;
	
	public LevelSequenceImpl(String name, List<LevelSchema> schemaList) {
		this.name = name;
		this.schemaList = schemaList;
	}
	
	@Override
	public String getSequenceName() {
		return name;
	}

	@Override
	public List<String> getLevelNames() {
		return this.schemaList.stream().map(l -> l.getName() ).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return this.getSequenceName() + ": " + this.getLevelNames();
	}

	@Override
	public Iterator<LevelSchema> iterator() {
		return this.schemaList.iterator();
	}

}
