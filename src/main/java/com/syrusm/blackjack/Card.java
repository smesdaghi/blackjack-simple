package com.syrusm.blackjack;

/**
 *  represents a single card which has a Suit and a Rank as well as whether the face is hidden or not.
 */

// TODO: make sure params are final!!!

public class Card {

    // setup Suit enum such that toString prints out the Suit symbol if the font in use supports them
    public static enum Suit {
        SPADES("\u2660"),
        HEARTS("\u2764"),
        DIAMONDS("\u2666"),
        CLUBS("\u2663");

        private final String string;

        private Suit(final String string){
            this.string = string;
        }

        @Override
        public String toString(){
            return string;
        }
    }

    // setup Rank enum such that toString prints out the short hand Rank. ex "A" for ACE
    public static enum Rank {
        ACE("A"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("10"),
        JACK("J"),
        QUEEN("Q"),
        KING("K");

        private final String string;

        private Rank(final String string) {
            this.string = string;
        }

        public int getValue(){
            return ordinal() + 1;
        }

        @Override
        public String toString(){
            return string;
        }
    }


     // Even though suit is not critical for some card games, it is important if there is any sort of UI
     // and it also does affect user experience. Many people remember cards by their rank + suit as opposed
     // to counting how many of each rank they have seen.
    private Suit suit;
    private Rank rank;
    private boolean hidden = false;

    // a full block meaning the card face is "hidden"
    private static final String SYMBOL_HIDDEN = "\u2588";

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean value) {
        hidden = value;
    }

    /**
     * @return String representing the card where the string contains suit symbols and ranks if the card is not hidden
     */
    @Override
    public String toString() {
        String string;
        if (hidden) {
            string = SYMBOL_HIDDEN;
        } else {
            string = getRank().toString() + getSuit().toString();
        }
        return string;
    }

}
