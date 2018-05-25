package me.kevinwalker.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import me.kevinwalker.main.Main;

public interface Container {

    Node getPane();

    Node getButton();

    static Container create(String fxmlName){
        Button button = new Button(fxmlName);
        button.setPrefSize(200, 50);
        return create(fxmlName, button);
    }

    static Container create(String fxmlName, Node button) {
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
