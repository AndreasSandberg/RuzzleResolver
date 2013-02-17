package se.sandberg.ruzzleresolver;

import java.io.Serializable;

public class CharIndex implements Serializable{

	private static final long serialVersionUID = -8412176824278262404L;

	private int indexVertical;
	private int indexHorisontal;
	private int visitedNumber;
	
	public CharIndex(int indexVertical, int indexHorisontal, int visitedNumber) {
		super();
		this.indexVertical = indexVertical;
		this.indexHorisontal = indexHorisontal;
		this.visitedNumber = visitedNumber;
	}

	public int getIndexVertical() {
		return indexVertical;
	}

	public int getIndexHorisontal() {
		return indexHorisontal;
	}

	public int getVisitedNumber() {
		return visitedNumber;
	}
	
	@Override
	public boolean equals(Object o) {
		CharIndex charIndex = (CharIndex)o;
		return charIndex.getIndexHorisontal() == getIndexHorisontal() && 
				charIndex.getIndexVertical() == getIndexVertical();
	}



}
