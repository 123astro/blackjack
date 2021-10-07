package com.company;

import com.company.actor.Player;
import com.company.blackjack.Actor;
import com.company.blackjack.Hand;
import com.company.blackjack.Table;
import com.company.card.deck.CheatersDeck;
import com.company.card.deck.Deck;
import com.company.utils.Console;

public class Main {

    public static void main(String[] args) {
            Table table = new Table();
            table.playGame();
    }
}
//        Deck deck = new CheatersDeck();
//        deck.shuffle();
//
//        Actor dealer = new Player(Console.getString("Enter name", true));
//        Hand myHand = new Hand(dealer);
//        myHand.addCard(deck.draw());
//        myHand.addCard(deck.draw());
//
//        while (dealer.getAction(myHand) != Actor.STAND){
//            myHand.addCard(deck.draw());
//            System.out.println("HIT");
//
//        }
//        System.out.println(myHand.displayHand());
//        System.out.println("Done");

//        myhand.addCard(deck.deal());
//        myhand.addCard(deck.deal());
//        System.out.println(myhand.displayHand());
//        System.out.println(myhand.getValue());
//        myhand.addCard(deck.deal());
//        myhand.addCard(deck.deal());
//        System.out.println(myhand.displayHand());
//        System.out.println(myhand.getValue());
       // deck.printDeck();
       // System.out.println(deck.deal().display());
//        for(int i = 0; i < 5; i++){
//            System.out.println(deck.deal());

