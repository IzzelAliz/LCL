package me.kevinwalker.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public interface Container {

    Node getPane();

    Node getButton();

    static Container create(String fxmlName) throws IOException {
        return create(fxmlName, new Button(fxmlName));
    }

    static Container create(String fxmlName, Node button) throws IOException {
        Parent parent = FXMLLoader.load(Container.class.getResource("/fxml/" + fxmlName + ".fxml"));
        return new Impl(parent, button);
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
