package se.sandberg.ruzzleresolver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Class GamePlan is necessary since the String[][] is not Serializable, 
 * in order to send the game plan between activities it needs to be placed in a 
 * Parceable object.
 * 
 * @author Andreas Sandberg
 */
public class GamePlan implements Parcelable{

	/** The game. */
	private String[][] game = new String[4][4];
	
	/** The Constant CREATOR is a mandatory field for Parceables. */
	public static final Parcelable.Creator<GamePlan> CREATOR = new Parcelable.Creator<GamePlan>() {
		public GamePlan createFromParcel(Parcel in) {
			return new GamePlan(in);
		}

		@Override
		public GamePlan[] newArray(int arg0) {
			return null;
		}
	};
	
	/**
	 * Instantiates a new game plan.
	 *
	 * @param in the parcel from which the game plan can be created.
	 */
	public GamePlan(Parcel in) {
		in.readStringArray(game[0]);
		in.readStringArray(game[1]);
		in.readStringArray(game[2]);
		in.readStringArray(game[3]);
	}

	/**
	 * Instantiates a new game plan.
	 *
	 * @param game the game plan
	 */
	public GamePlan(String[][] game){
		this.game = game;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public String[][] getGame() {
		return game;
	}


	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {

		return 0;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		for(int i = 0; i < game[0].length; i++){
			arg0.writeStringArray(game[i]);
		}

	}

	

}
