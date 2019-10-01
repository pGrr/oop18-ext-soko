package view;

public interface View {
	
	void initialView();
	
	void craftLevelView();
	
	void playLevelView();
	
	void saveSucceded();
	
	void saveFailed();
	
	void loadSucceded();
	
	void loadFailed();
		
	void noInitialPointError();
	
	void tooManyInitialPointError();
	
	void noTargetError();
	
	void unequalBoxAndTargetError();
	
	void unForeSeenError(String message);

}
