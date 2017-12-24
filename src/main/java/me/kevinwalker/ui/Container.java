package me.kevinwalker.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import me.kevinwalker.main.Main;

import java.io.IOException;

public interface Container {

    Node getPane();

    Node getButton();

    static Container create(String fxmlName) throws IOException {
        Button button = new Button(fxmlName);
        button.setPrefSize(200, 50);
        return create(fxmlName, button);
    }

    static Container create(String fxmlName, Node button) throws IOException {
        return new Impl(Main.getInstance(fxmlName), button);
    }

    class Impl implements Container {

        private Node pane, button;

        Impl(Node pane, Node button) {
            this.pane = pane;
            this.button = button;
        }

        @Override
        public Node getPane() {
            return pane;
        }

        @Override
        public Node getButton() {
            return button;
        }
    }

}
