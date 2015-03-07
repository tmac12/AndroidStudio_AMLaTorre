package it.tmac12.amlatorre;

import java.io.Serializable;

/**
 * A team game.
 */
public final class Game implements Serializable {
    private static final long serialVersionUID = -211632489881026293L;
    private static final String HOME_LOCATION = "Crespano del Grappa";

    public final String mOpponent;
    public final String mDate;
    public final String mTime;
    public final String mLocation;
    public final String mWinner;

    private Game(Builder gameBuilder) {
        mOpponent = gameBuilder.mOpponent;
        mDate = gameBuilder.mDate;
        mTime = gameBuilder.mTime;
        mLocation = gameBuilder.mLocation;
        mWinner = gameBuilder.mWinner;
    }

    public boolean isHomeGame() {
        return HOME_LOCATION.equalsIgnoreCase(mLocation);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Game))
            return false;
        Game game = (Game) obj;
        return mOpponent.equals(game.mOpponent) && mDate.equals(game.mDate);
    }

    @Override
    public int hashCode() {
        int hashCode = 91;
        hashCode += 31 * mOpponent.hashCode();
        hashCode += 31 * mDate.hashCode();
        return hashCode;
    }

    /**
     * Builder for a {@link Game}.
     */
    public static class Builder {
        private String mOpponent;
        private String mDate;
        private String mTime;
        private String mLocation;
        private String mWinner;

        public Builder setOpponent(String opponent) {
            mOpponent = opponent;
            return this;
        }

        public Builder setDate(String date) {
            mDate = date;
            return this;
        }

        public Builder setTime(String time) {
            mTime = time;
            return this;
        }

        public Builder setLocation(String location) {
            mLocation = location;
            return this;
        }

        public Builder setWinner(String winner) {
            mWinner = winner;
            return this;
        }

        public Game build() throws NullPointerException {
            if (mOpponent == null || mDate == null)
                throw new NullPointerException("Missing required data");
            return new Game(this);
        }
    }
}