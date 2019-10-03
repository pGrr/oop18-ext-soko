package view;

import java.util.Optional;

import model.ElementModel;

public interface ElementView {

	ElementModel.Type getType();
		
	Optional<String> getIconPath();
		
}
