package me.kevinwalker.ui.controller;

import me.kevinwalker.ui.Container;

import java.util.ArrayList;
import java.util.List;

public class InterfaceManager {

    public static List<Container> containers  = new ArrayList<>();

    public static void addInterface(Container container) {
        containers.add(container);
    }

    public static void addAllButtons() {

    }
}
