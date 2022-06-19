package com.example.edpprojekt2.macaugame;

import com.example.edpprojekt2.mongodb.GameDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.example.edpprojekt2.macaugame.CardsList.AVAILABLE_STARTERS;
import static com.example.edpprojekt2.macaugame.CardsList.CARDS_PATHS;


public class MacauGame {
    private List<String> remainingCards;
    private List<String> userCards = new ArrayList<String>();
    private List<String> computerCards = new ArrayList<String>();
    private List<String> tableCards = new ArrayList<String>();
    private String topCard;
    private Date gameStart;
    private Date gameStop;
    private Result result;
    private String userBet;

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

    public Result getResult() {
        return result;
    }

    public MacauGame(String bet) {
        this.remainingCards = new ArrayList<>(CARDS_PATHS);
        this.topCard = "green_back";
        this.gameStart = new Date();
        System.out.println(this.gameStart);
        this.userBet = bet;
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
        this.tableCards.clear();
        this.tableCards.add(this.topCard);
    }

    private void getComputerCard() {
        Random random = new Random();
        if (this.remainingCards.size() > 0) {
            int idx = random.nextInt(this.remainingCards.size());
            String card = this.remainingCards.get(idx);
            this.computerCards.add(card);
            this.remainingCards.remove(card);
        } else {
            resetTable();
            if (this.remainingCards.size() > 0) {
                int idx = random.nextInt(this.remainingCards.size());
                String card = this.remainingCards.get(idx);
                this.computerCards.add(card);
                this.remainingCards.remove(card);
            }
        }
    }

    public void performComputerMove() {
        List<String> possibleHand = getPossibleTopCards(this.computerCards);

        if (possibleHand.size() > 0) {
            this.topCard = possibleHand.get(0);
            this.computerCards.remove(possibleHand.get(0));
            this.tableCards.add(possibleHand.get(0));
        } else {
            getComputerCard();
        }
    }

    public Boolean isGameOver(){
        if(this.computerCards.size() == 0){
            this.result = Result.COMPUTER_WON;
            this.gameStop = new Date();
            return true;
        }

        if(this.userCards.size() == 0){
            this.result = Result.USER_WON;
            this.gameStop = new Date();
            return true;
        }

        return false;
    }

    public GameDTO prepareGameDTO(){
        long duration = TimeUnit.MICROSECONDS.toMinutes(this.gameStop.getTime() - this.gameStart.getTime());
        return new GameDTO(this.gameStart.toString(), this.userBet, this.result.toString(), Long.toString(duration));
    }
}
