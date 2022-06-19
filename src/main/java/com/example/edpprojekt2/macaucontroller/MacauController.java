package com.example.edpprojekt2.macaucontroller;

import com.example.edpprojekt2.HelloApplication;
import com.example.edpprojekt2.currencyapi.CurrencyService;
import com.example.edpprojekt2.macaugame.MacauGame;
import com.example.edpprojekt2.mongodb.MongoAdapter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;


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

    private Boolean validUserBet(String bet) {
        try {
            List<String> splitted = Arrays.stream(bet.split(" ")).collect(Collectors.toList());

            if (!CURRENCIES.contains(splitted.get(1))) {
                return false;
            }

            Float value = Float.parseFloat(splitted.get(0));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    protected void startGame() throws IOException {
        this.errorsHolder.clear();

        if (this.macauGame == null && validUserBet(this.usersBet.getText())) {
            this.macauGame = new MacauGame();
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

            Map<String, String> currencyExchange = service.getTranslatedBet(this.usersBet.getText());

            this.dollarsLabel.setText("In dollars: " + currencyExchange.get("USD") + " USD");
            this.euroLabel.setText("In Euro: " + currencyExchange.get("EUR") + " EUR");
            this.poundLabel.setText("In Pounds: " + currencyExchange.get("GBP") + " GBP");
            this.usersBetLabel.setText("User's bet: " + this.usersBet.getText());
            this.turnLabel.setText("Turn: " + this.turn);

        } else {
            this.errorsHolder.setText("You have to give your bet, even 0 PLN\n Value: " + this.usersBet.getText() + " is not valid\n");
        }
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
            newImg.setFitHeight(120);
            double size = 100.00 / this.macauGame.getUserCards().size();
            size /= 100.00;
            double newWidth = this.cardsHand.getWidth() * size;
            newImg.setFitWidth(newWidth);
            newImg.relocate(i * newWidth, 0);

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
            newImg.setFitWidth(100);
            newImg.relocate(i * 105, 0);

            computerHandPane.getChildren().add(i, newImg);
        }

        computerHand.setContent(computerHandPane);
    }

    private void handleMouseClicked(String card, int idx) {
        if (Objects.equals(this.turn, TURNS.get(0))) {
            if (this.macauGame.handleCardClicked(card)) {
                this.turn = TURNS.get(1);
                refresh();
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
    private void performComputerMove(){
        if(Objects.equals(this.turn, TURNS.get(1))){
            this.macauGame.performComputerMove();
            this.turn = TURNS.get(0);
            refresh();
        }else {
            this.errorsHolder.setText("Now is Your turn, computer will be next!");
        }
    }
}
