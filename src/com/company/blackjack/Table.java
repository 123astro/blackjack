package com.company.blackjack;

import com.company.actor.Dealer;
import com.company.actor.Keith;
import com.company.actor.Player;
import com.company.card.deck.Deck;
import com.company.card.deck.StandardDeck;
import com.company.utils.Console;


import java.util.ArrayList;
import java.util.List;

public class Table {

    private List<Hand> hands = new ArrayList<>();
    //private List<Actor> players = new ArrayList<>();
    private Hand dealer = new Hand(new Dealer());
    private Deck deck;
    private int playerCount = 0; //6
    public static final int BUST_VALUE = 21;

    // to play game with multi players
    // ask how many players
    //  add players to list
    // figure out order
    // multi bets
    //  multi turns
    // multi wins


    public Table() {
        playerCount = Console.getInt("How many players?", 1, 6, "invalid player selection");
        for (int count = 0; count < playerCount; count++) {  // created loop at add the number of players
            Player newPlayer = new Player("Player" + (count + 1)); //create new player 1 thru 6
            newPlayer.addBalance(1000);
            hands.add(new Hand(newPlayer)); // instantiating a hand for the new player and adding to the list
        }
        hands.add(new Hand(new Keith()));
        playerCount++;

    }

    public void playGame(){
        while(true){
            playRound();
        }
    }


    // public void setupGame

    public void playRound() {
        deck = new StandardDeck();
        //deck = new CheatersDeck();
        deck.shuffle();
        getBets();
        deal();
        displayTable();
        playerTurn();
        while(turn(dealer));
        displayTable();
        endRound();
    }

    private void getBets(){
        for(Hand activeHand : hands){
            activeHand.placeBet();
        }
    }


    private void playerTurn() {
        for (int count = 0; count < hands.size(); count++) {
            Hand activeHand = hands.get(count);
            while (true) {
                if (!turn(activeHand)) break;
            }
            System.out.println(activeHand.displayHand());
            Console.getString("Enter to start next turn", false);
        }
    }

    private void endRound() {
        for (Hand player : hands) {
            determineWinner(player);
            System.out.println(player.getBalance());
            player.discardHand();

        }
        dealer.discardHand();
        while (hands.size() > playerCount){
            hands.remove(hands.size() - 1);
        }
    }


    private void displayTable() {
        StringBuilder output = new StringBuilder();
        output.append(dealer.getName()).append(" ").append(dealer.displayHand()).append("\n");
        for (Hand activeHand: hands) {
            output.append(activeHand.getName()).append(" ").append(activeHand.displayHand()).append(" | ");

        }
        System.out.println(output);
    }

    private void deal() {
        for (int count = 0; count < 2; count++) {
            dealer.addCard(deck.draw());
            for (Hand activeHand: hands) {
                activeHand.addCard(deck.draw());

            }
        }
    }

    private void determineWinner(Hand activeHand) {
        if (activeHand.getValue() > BUST_VALUE) {
            System.out.println(activeHand.getName() + " Busted");
            return;
        }
        if (activeHand.getValue() > dealer.getValue() || dealer.getValue() > BUST_VALUE) {
            System.out.println(activeHand.getName() + " Wins");
            activeHand.payout(Hand.NORMALPAY);
            return;
        }
        if (activeHand.getValue() == dealer.getValue()) {
            System.out.println("Push");
            activeHand.payout(Hand.PUSHPAY);
            return;
        }
        System.out.println("Dealer Wins Again");
    }

    private boolean turn(Hand activeHand) {
        System.out.println(dealer.getName() + " " + dealer.displayHand());
        byte action = activeHand.getAction(dealer);
        return switch (action) {
            case Actor.QUIT -> stand();
            case Actor.HIT -> hit(activeHand);
            case Actor.STAND -> stand();
            case Actor.DOUBLE -> doubleDown(activeHand);
            case Actor.SPLIT -> split(activeHand);
            default -> false;

        };
    }

    private boolean quit(){
        System.exit(0);
        return false;
    }

    private boolean hit(Hand activeHand) {
        activeHand.addCard(deck.draw());
        System.out.println("Hit");
        if (activeHand.getValue() > BUST_VALUE) {
            System.out.println("Busted");
            return false;
        }
        return true;
    }

    private boolean stand() {
        //TODO stand
        System.out.println("Stand");
        return false;
    }

    private boolean doubleDown(Hand activeHand) {
        //TODO :double
        activeHand.doubleBet();
        System.out.println("Bet Double");
        hit(activeHand);
        return false;
    }

    private boolean split(Hand activeHand) {
            /*
        take card
        make a second hand
        match the bet.
        draw 1 card for each hand
         */
        activeHand.doubleBet();
        Hand newHand = activeHand.splitHand();
        activeHand.addCard(deck.draw());
        newHand.addCard(deck.draw());
        hands.add(newHand);
        return true;

    }
}
