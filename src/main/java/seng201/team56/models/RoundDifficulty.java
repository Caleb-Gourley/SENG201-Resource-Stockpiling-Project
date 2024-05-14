package seng201.team56.models;

public enum RoundDifficulty {
	EASY(1,1.5f),
	MEDIUM(2,3),
	HARD(3,5);
	
	private final float minBaseModifier;
	private final float maxBaseModifier;
	
	RoundDifficulty(float minBaseModifier, float maxBaseModifier) {
		this.maxBaseModifier = maxBaseModifier;
		this.minBaseModifier = minBaseModifier;
	}

}
