package com.company.actor;

import com.company.blackjack.Actor;
import com.company.blackjack.Hand;

public class Keith implements Actor {
    private int balance = 1000;

    @Override
    public String getName() {
        return "Keith";
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public int placeBet() {
        int bet = (int) (Math.random() * (balance * 0.33)) + 1;
        balance -= bet;
        System.out.println("Keith bets: " + bet);
        return bet;
    }

    @Override
    public byte getAction(Hand hand, Hand dealer) {
        int dealerValue = dealer.getValue();
        int playerValue = hand.getValue();
        System.out.println("Keith");
        if (playerValue == 21) {
            return STAND;
        }
        if (dealerValue >= 17 && playerValue < 17) {
            return HIT;
        }
        if (dealerValue <= 16 && playerValue >= 17) {
            return STAND;
        }
        if (playerValue >= 17 && dealerValue == 17) {
            return STAND;
        }
        if (playerValue >= 18 && dealerValue == 18) {
            return STAND;
        }
        if (playerValue >= 19 && dealerValue == 19) {
            return STAND;
        }
        if (playerValue >= 17 && playerValue < dealerValue) {
            return HIT;
        }
        if(dealerValue > 6 && dealerValue < 12 ){ //playerValue < 17
            return HIT;
        }
        return HIT;  // DEALER 4-6 and 12 -16 and player 4 -16
    }


    @Override
    public void addBalance(double amt) {
        balance += amt;
    }
}
