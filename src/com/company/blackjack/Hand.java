package com.company.blackjack;
import com.company.card.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards = new ArrayList<>();
    private int bet = 0;
    private Actor activeHand;

    public static final byte PUSHPAY = 0;
    public static final byte NORMALPAY = 1;
    public static final byte BLACKJACKPAY = 2;

    public Hand(Actor activeHand){
        this.activeHand = activeHand;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public String displayHand() {
        StringBuilder output = new StringBuilder();
        for (Card card : cards) {
            output.append(card.display()).append(" ");
        }
        return output.toString().trim();
    }


     public int getValue() {
        int score = 0;
        boolean haveAce11 = false;
        for (Card card : cards){
            int value = card.getRank();
            switch (value) {
                case 1 -> {
                    value = score + 11 > 21 ? 1 : 11;
                    if(value ==11) {
                        haveAce11 = true;
                    }
                    score += value;
                }
                case 11, 12, 13 -> score += 10;
                default -> score += value;
            }
            if (score > 21 && haveAce11 ) {
                score -= 10;
                haveAce11 = false;
            }
        }
        return score;
     }

     // getting composition methods
    // getter with no setter
    // pass through method

    public byte getAction(Hand dealer) {
        return activeHand.getAction(this, dealer);
    }

    public int size(){return cards.size();}

    public int getBet(){ return bet; }

    public void placeBet() {
        bet = activeHand.placeBet();
    }

    public int getBalance(){ return activeHand.getBalance();}

    public String getName() { return activeHand.getName();}

    //hasPair -. returns true if at least 1 pair is in hand
    //isPair -> takes 2 cards and determine if pair
    // countPair -> count all instances of pairs in a hand
    // checkPair -> check first two cards for pair

    public boolean canSplit(){
        return cards.get(0).getRank() == cards.get(1).getRank();
    }

    public void doubleBet() {
        activeHand.addBalance(-bet);
        bet *= 2;
    }


    public void payout(byte type){
        switch(type) {
            case PUSHPAY -> activeHand.addBalance(bet);
            case NORMALPAY ->  activeHand.addBalance(bet * 2);
            case BLACKJACKPAY -> activeHand.addBalance(bet * 2.5);
        }
    }

    // remove card
    public Card removeCard(int index){
        // take card at index out of hand and return to table
        return cards.remove(index);
    }

    public Hand splitHand(){
        bet = bet / 2;
        Hand hand = new Hand(activeHand);
        hand.addCard(cards.remove(1));
        hand.bet = bet;
        return hand;
    }



//    public void revealHand() {
//        for( Card card : cards) {
//            if (card.getIsFaceDown()){
//                card.flip();
//            }
//        }
//    }

    public void discardHand(){
        cards.clear();
    }
}