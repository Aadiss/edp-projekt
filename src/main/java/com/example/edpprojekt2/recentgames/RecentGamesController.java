package com.example.edpprojekt2.recentgames;

import com.example.edpprojekt2.mongodb.GameDTO;
import com.example.edpprojekt2.mongodb.MongoAdapter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;


public class RecentGamesController {

    MongoAdapter mongoAdapter = new MongoAdapter();

    @FXML
    private TableView table;

    public void initTable() throws IOException {
        List<GameDTO> games = mongoAdapter.getALlGames();

        TableColumn<GameDTO, ObjectId> idColumn = new TableColumn<>("Id");
        TableColumn<GameDTO, String> dateColumn = new TableColumn<>("Date");
        TableColumn<GameDTO, String> prizeColumn = new TableColumn<>("Prize");
        TableColumn<GameDTO, String> resultColumn = new TableColumn<>("Result");
        TableColumn<GameDTO, String> timeColumn = new TableColumn<>("Time [sec]");
        TableColumn<GameDTO, String> userIdColumn = new TableColumn<>("Username");

        idColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.16667));
        dateColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.16667));
        prizeColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.16667));
        resultColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.16667));
        timeColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.16667));
        userIdColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.16667));

        idColumn.setCellValueFactory(new PropertyValueFactory<GameDTO, ObjectId>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<GameDTO, String>("date"));
        prizeColumn.setCellValueFactory(new PropertyValueFactory<GameDTO, String>("prize"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<GameDTO, String>("result"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<GameDTO, String>("time"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<GameDTO, String>("username"));
        this.table.setEditable(true);

        this.table.getColumns().add(idColumn);
        this.table.getColumns().add(dateColumn);
        this.table.getColumns().add(prizeColumn);
        this.table.getColumns().add(resultColumn);
        this.table.getColumns().add(timeColumn);
        this.table.getColumns().add(userIdColumn);
        this.table.setItems(FXCollections.observableArrayList(games));
    }
}
