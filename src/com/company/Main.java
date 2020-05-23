package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("xd");
        Gameplay game = new Gameplay(); //nowa gra
        JFrame obj = new JFrame(); //nowe okno
        obj.setTitle( "Snake.exe" );
        obj.setVisible(true); //widoczność okna
        obj.setSize(808,494);
        obj.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        obj.setResizable(false);
        obj.add(game);

    }
}

