package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Gameplay game = new Gameplay(); //nowa gra
        JFrame obj = new JFrame(); //nowe okno

        obj.setTitle( "Snake.exe" );
        obj.setVisible(true); //widoczność okna
        obj.setSize(826,494);
        obj.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        obj.setResizable(false); //stała wartość
        obj.add(game);

    }
}

