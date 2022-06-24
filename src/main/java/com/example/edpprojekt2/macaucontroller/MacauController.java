package com.example.edpprojekt2.macaucontroller;

import com.example.edpprojekt2.HelloApplication;
import com.example.edpprojekt2.currencyapi.CurrencyService;
import com.example.edpprojekt2.macaugame.MacauGame;
import com.example.edpprojekt2.mongodb.MongoAdapter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;


public class MacauController {
    private static final List<String> CURRENCIES = List.of("PLN", "EUR", "USD", "GBP");
    private static final List<String> TURNS = List.of("USER", "COMPUTER");

    @FXML
    private ImageView lastCardImage;

    @FXML
    private Label remainingCardsLabel;

    @FXML
    private Pane cardsHand;

    @FXML
    private ScrollPane scrollHand;

    @FXML
    private ScrollPane computerHand;

    @FXML
    private Pane computerHandPane;

    @FXML
    private Label tableCardsAmount;

    @FXML
    private TextField usersBet;

    @FXML
    private TextArea errorsHolder;

    @FXML
    private Label dollarsLabel;

    @FXML
    private Label euroLabel;

    @FXML
    private Label poundLabel;

    @FXML
    private Label usersBetLabel;

    @FXML
    private Label turnLabel;

    @FXML
    private ChoiceBox currencyChoice;

    private MacauGame macauGame = null;
    private MongoAdapter mongoAdapter = new MongoAdapter();
    private CurrencyService service = new CurrencyService();
    private String turn = TURNS.get(new Random().nextInt(2));

    @FXML
    protected void getCard() throws IOException {
        if (Objects.equals(this.turn, TURNS.get(0))) {
            this.macauGame.userGetCard();
            this.turn = TURNS.get(1);
            refresh();
        } else {
            this.errorsHolder.setText("You can not get card if it is not your turn");
        }
    }

    private Boolean validUserBet(String bet, Object currency) {
        try {
            if (currency == null) return false;

            Float.parseFloat(bet);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    protected void startGame() throws IOException {
        this.errorsHolder.clear();

        if (this.macauGame == null && validUserBet(this.usersBet.getText(), this.currencyChoice.getValue())) {
            this.macauGame = new MacauGame(this.usersBet.getText() + " " + this.currencyChoice.getValue().toString());
            macauGame.initializeGame();
            String topCard;

            if (this.macauGame.getTopCard().charAt(0) == '0') {
                topCard = "1" + this.macauGame.getTopCard();
            } else {
                topCard = this.macauGame.getTopCard();
            }

            String path = "cards/" + topCard + ".png";
            Image img = new Image(HelloApplication.class.getResource(path).toString());
            lastCardImage.setImage(img);

            remainingCardsLabel.setText("Remaining Cards: " + macauGame.getRemainingCardsSize());

            setUserHand();
            setComputerHand();

            tableCardsAmount.setText("Table Cards: " + macauGame.getTableCardsSize());

            prepareCurrencyLabels();

            this.usersBetLabel.setText("User's bet: " + this.usersBet.getText() + " " + this.currencyChoice.getValue().toString());
            this.turnLabel.setText("Turn: " + this.turn);

        } else {
            this.errorsHolder.setText("You have to give your bet, even 0 PLN\n Value: " + this.usersBet.getText() + " is not valid\n");
        }
    }

    private void prepareCurrencyLabels() throws IOException {
        Map<String, String> currencyExchange = service.getTranslatedBet(this.usersBet.getText(), this.currencyChoice.getValue().toString());
        if (currencyExchange.containsKey("USD"))
            this.dollarsLabel.setText("In Dollars: " + currencyExchange.get("USD") + " USD");
        else this.dollarsLabel.setText("In Zloty: " + currencyExchange.get("PLN") + " PLN");

        if (currencyExchange.containsKey("EUR"))
            this.euroLabel.setText("In Euro: " + currencyExchange.get("EUR") + " EUR");
        else this.euroLabel.setText("In Zloty: " + currencyExchange.get("PLN") + " PLN");

        if (currencyExchange.containsKey("GBP"))
            this.poundLabel.setText("In Pounds: " + currencyExchange.get("GBP") + " GBP");
        else this.poundLabel.setText("In Zloty: " + currencyExchange.get("PLN") + " PLN");
    }

    private void setUserHand() {
        cardsHand.getChildren().clear();

        for (int i = 0; i < this.macauGame.getUserCards().size(); i++) {
            ImageView newImg = new ImageView();
            String userCard;
            if (this.macauGame.getUserCards().get(i).charAt(0) == '0') {
                userCard = "1" + this.macauGame.getUserCards().get(i);
            } else {
                userCard = this.macauGame.getUserCards().get(i);
            }
            String path = "cards/" + userCard + ".png";
            Image img = new Image(HelloApplication.class.getResource(path).toString());

            newImg.setImage(img);
            newImg.setFitHeight(140);

            if (this.macauGame.getUserCards().size() > 10) {
                double size = 100.00 / this.macauGame.getUserCards().size();
                size /= 100.00;
                double newWidth = this.cardsHand.getWidth() * size;
                newImg.setFitWidth(newWidth);
                newImg.relocate(i * newWidth, 0);
            } else {
                newImg.setFitWidth(95);
                newImg.relocate(i * 100, 0);
            }

            final int idx = i;
            newImg.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getSource() instanceof ImageView) {
                    ImageView clickedImage = (ImageView) mouseEvent.getSource();
                    String url = clickedImage.getImage().getUrl();
                    String card = url.length() > 5 ? url.substring(url.length() - 6, url.length() - 4) : url;
                    handleMouseClicked(card, idx);
                }
            });

            cardsHand.getChildren().add(i, newImg);
        }
        scrollHand.setContent(cardsHand);
    }

    private void setComputerHand() {
        computerHandPane.getChildren().clear();
        for (int i = 0; i < this.macauGame.getComputerCards().size(); i++) {
            ImageView newImg = new ImageView();
            String path = "cards/gray_back.png";
            Image img = new Image(HelloApplication.class.getResource(path).toString());
            newImg.setImage(img);
            newImg.setFitHeight(150);

            if (this.macauGame.getComputerCards().size() > 8) {
                double size = 100.00 / this.macauGame.getComputerCards().size();
                size /= 100.00;
                double newWidth = this.computerHandPane.getWidth() * size;
                newImg.setFitWidth(newWidth);
                newImg.relocate(i * newWidth, 0);
            } else {
                newImg.setFitWidth(100);
                newImg.relocate(i * 105, 0);
            }

            computerHandPane.getChildren().add(i, newImg);
        }

        computerHand.setContent(computerHandPane);
    }

    private void handleMouseClicked(String card, int idx) {
        if (Objects.equals(this.turn, TURNS.get(0))) {
            if (this.macauGame.handleCardClicked(card)) {
                this.turn = TURNS.get(1);
                refresh();
                if (this.macauGame.isGameOver()) {
                    handleGameOver();
                }
            }
        } else {
            this.errorsHolder.setText("You can not put card if it is not your turn");
        }
    }

    private void refresh() {
        setComputerHand();
        setUserHand();
        setTopCard();
        remainingCardsLabel.setText("Remaining Cards: " + macauGame.getRemainingCardsSize());
        tableCardsAmount.setText("Table Cards: " + macauGame.getTableCardsSize());
        turnLabel.setText("Turn: " + this.turn);
    }

    private void setTopCard() {
        String topCard;
        if (this.macauGame.getTopCard().charAt(0) == '0') {
            topCard = "1" + this.macauGame.getTopCard();
        } else {
            topCard = this.macauGame.getTopCard();
        }
        String path = "cards/" + topCard + ".png";
        Image img = new Image(HelloApplication.class.getResource(path).toString());
        lastCardImage.setImage(img);
    }

    @FXML
    private void performComputerMove() {
        if (Objects.equals(this.turn, TURNS.get(1))) {
            this.macauGame.performComputerMove();
            this.turn = TURNS.get(0);
            refresh();
            if (this.macauGame.isGameOver()) {
                handleGameOver();
            }
        } else {
            this.errorsHolder.setText("Now is Your turn, computer will be next!");
        }
    }

    private void handleGameOver() {
        mongoAdapter.insertGame(this.macauGame.prepareGameDTO().toDocument());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game is Over!");
        alert.setContentText("The winner is: " + this.macauGame.getResult().toString() + "\nCongratulations!");
        alert.showAndWait();
        Platform.exit();
    }
}
