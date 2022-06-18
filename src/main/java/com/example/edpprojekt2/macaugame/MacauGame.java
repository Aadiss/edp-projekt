package com.example.edpprojekt2.macaugame;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.edpprojekt2.macaugame.CardsList.AVAILABLE_STARTERS;
import static com.example.edpprojekt2.macaugame.CardsList.CARDS_PATHS;

public class MacauGame {
    private List<String> remainingCards;
    private List<String> userCards = new ArrayList<String>();
    private List<String> computerCards = new ArrayList<String>();
    private List<String> tableCards = new ArrayList<String>();
    private String topCard;

    public String getTopCard() {
        return topCard;
    }

    public List<String> getComputerCards() {
        return computerCards;
    }

    public int getRemainingCardsSize() {
        return this.remainingCards.size();
    }

    public int getTableCardsSize() {
        return this.tableCards.size();
    }

    public List<String> getUserCards() {
        return this.userCards;
    }

    public MacauGame() {
        this.remainingCards = new ArrayList<>(CARDS_PATHS);
        this.topCard = "green_back";
    }

    public void initializeGame() {
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            int idx = random.nextInt(this.remainingCards.size());
            String card = this.remainingCards.get(idx);
            this.computerCards.add(card);
            this.remainingCards.remove(card);
        }

        for (int i = 0; i < 11; i++) {
            int idx = random.nextInt(this.remainingCards.size());
            String card = this.remainingCards.get(idx);
            this.userCards.add(card);
            this.remainingCards.remove(card);
        }

        this.topCard = retrieveAvailable();
        this.tableCards.add(this.topCard);
    }

    private String retrieveAvailable() {
        for (String card : this.remainingCards) {
            if (AVAILABLE_STARTERS.contains(card)) return card;
        }

        return "green_back";
    }

    public Boolean handleCardClicked(String card) {
        if (getPossibleTopCards(this.userCards).contains(card)) {
            this.topCard = card;
            this.userCards.remove(card);
            this.tableCards.add(card);
            return true;
        }
        return false;
    }

    private List<String> getPossibleTopCards(List<String> hand) {
        List<String> result = new ArrayList<>();
        for (String card_ : hand) {
            if (card_.charAt(0) == this.topCard.charAt(0) || card_.charAt(1) == this.topCard.charAt(this.topCard.length() - 1))
                result.add(card_);
        }
        return result;
    }

    public void userGetCard() {
        Random random = new Random();
        if (this.remainingCards.size() > 0) {
            int idx = random.nextInt(this.remainingCards.size());
            String card = this.remainingCards.get(idx);
            this.userCards.add(card);
            this.remainingCards.remove(card);
        } else {
            resetTable();
            if (this.remainingCards.size() > 0) {
                int idx = random.nextInt(this.remainingCards.size());
                String card = this.remainingCards.get(idx);
                this.userCards.add(card);
                this.remainingCards.remove(card);
            }
        }
    }

    private void resetTable() {
        this.remainingCards.addAll(this.tableCards);
        this.remainingCards.remove(this.topCard);
        this.tableCards.add(this.topCard);
    }
}
