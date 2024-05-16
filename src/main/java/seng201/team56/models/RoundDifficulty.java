package seng201.team56.models;

public enum RoundDifficulty {
	EASY(1,1.5),
	MEDIUM(2,3),
	HARD(3,5);
	
	private final double minBaseModifier;
	private final double maxBaseModifier;
	
	RoundDifficulty(double minBaseModifier, double maxBaseModifier) {
		this.maxBaseModifier = maxBaseModifier;
		this.minBaseModifier = minBaseModifier;
	}

}
