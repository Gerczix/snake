package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private ImageIcon[] icons = new ImageIcon[8];
    private int length=3;
    private int numberOfObstacles;
    private int[] xLength = new int[1000];
    private int[] yLength = new int[1000];
    private int direction=6;
    private int oldDirection;
    private Timer timer;
    private int delay=120;
    private int xFruit, yFruit;
    private int[] xObstacle = new int[100];
    private int[] yObstacle = new int[100];
    private int size;
    private Random random = new Random();
    private boolean x=true;
    private int wynik=0;
    private boolean alive = true;

    void start(int l) {
        wynik=0;
        length=l;
        alive=true;

        //generowanie położenia węża
        int randomX=random.nextInt(24-length);
        int randomY=random.nextInt(9);
        for(int i=0;i<length;i++) {
            xLength[i]=12+(randomX+length-i-1)*size;
            //xLength[i]=12+(length-i-1)*size;
            yLength[i]=142+randomY*size;

        }

        //generowanie polożenia owocow
        x=true;
        while(x) {
            x = false;
            xFruit = random.nextInt(24) * size + 12;
            System.out.println(xFruit);
            yFruit = random.nextInt(9) * size + 142;
            for (int i = 0; i < length; i++) {
                if ((xFruit == xLength[i]) && (yFruit == yLength[i])) {
                    x = true;
                }
            }
        }

        //generowanie ilości i położenia przeszkod
        numberOfObstacles=random.nextInt(5)+10;
        for(int j=0;j<numberOfObstacles;j++) {
            x=true;
            while (x) {
                x = false;
                xObstacle[j] = random.nextInt(24) * size + 12;
                yObstacle[j] = random.nextInt(9) * size + 142;
                for (int i = 0; i < length; i++) {
                    if ( ((xObstacle[j] == xLength[i]) && (yObstacle[j] == yLength[i])) ||
                            ((xObstacle[j] == xFruit) && (yObstacle[j] == yFruit)) ) {
                        x = true;
                    }
                }
            }
        }
    }

    public Gameplay() {
        //inicjalizowanie ikon
        icons[0] = new ImageIcon("HeadUp.png");
        icons[1] = new ImageIcon("HeadDown.png");
        icons[2] = new ImageIcon("HeadRight.png");
        icons[3] = new ImageIcon("HeadLeft.png");
        icons[4] = new ImageIcon("body.png");
        icons[5] = new ImageIcon("apple.png");
        icons[6] = icons[2];
        icons[7] = new ImageIcon("Obstacle.png");
        size=icons[0].getIconHeight();

        //generowanie startowych pozycji węzą owoca i przeszkód
        start(length);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    //rysunek
    public void paint(Graphics g) {
        //góra
        g.setColor(Color.red);
        g.drawRect(10, 10, 804, 120);
        g.setColor(Color.green);
        g.fillRect(11, 11, 802, 118);
        g.setColor(Color.white);
        g.setFont(new Font("Dialog", Font.ITALIC, 50));
        FontMetrics fm = g.getFontMetrics(); //pobranie danych napisu
        g.drawString("SNAKE", 408 - fm.stringWidth("SNAKE") / 2, 85);

        //wynik
        g.setFont(new Font("Dialog", Font.ITALIC, 17));
        g.drawString("length: " + length, 718, 25);
        g.drawString("wynik: " + wynik, 718, 40);

        //dół
        g.setColor(Color.red);
        g.drawRect(10, 140, 804, 324);
        g.setColor(Color.green);
        g.fillRect(11, 141, 802, 322);


        //waz
        if (alive) {
            for (int i = 0; i < length; i++) {
                {
                    if (i == 0)
                        icons[direction].paintIcon(this, g, xLength[0], yLength[0]);
                    else
                        icons[4].paintIcon(this, g, xLength[i], yLength[i]);
                }

            }
        }
        else {
            for (int i = 1; i < length; i++) {
                {
                    if (i == 1)
                        icons[direction].paintIcon(this, g, xLength[1], yLength[1]);
                    else
                        icons[4].paintIcon(this, g, xLength[i], yLength[i]);
                }
                timer.stop();
            }
        }

        //owoc

        icons[5].paintIcon(this, g, xFruit, yFruit);

        //przeszkody

        for (int i = 0; i < numberOfObstacles; i++) {
            //System.out.println(xObstacle[i]);
            icons[7].paintIcon(this, g, xObstacle[i], yObstacle[i]);
        }

        //tablica wynikow

        if(!alive) {
            g.setFont(new Font("Dialog", Font.ITALIC, 30));
            direction=6;

            g.setColor(Color.white);
            g.drawString("dedasz leszczu",308,220);
            g.drawString("miałeś tylko "+wynik+" punktów",238,280);
            g.drawString("naciśnij spacje i popraw to chopie",163,340);
            System.out.println("game over");

        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(direction != 6) {
            for (int i = length - 1; i >= 0; i--) {
                xLength[i + 1] = xLength[i];
                yLength[i + 1] = yLength[i];
            }
        }
        switch (direction) {
            case 0: { //góra
                yLength[0] -= size;
                if (yLength[0] < 142)
                    yLength[0] = 430;
                break;
            }
            case 1: { //dół
                yLength[0] += size;
                if (yLength[0] > 446)
                    yLength[0] = 142;
                break;
            }
            case 2: { //prawo
                xLength[0] += size;
                if (xLength[0] > 780)
                    xLength[0] = 12;
                break;
            }
            case 3: { //lewo
                xLength[0] -= size;
                if (xLength[0] < 12)
                    xLength[0] = 780;
                break;
            }
        }
        if((xFruit == xLength[0]) && (yFruit == yLength[0])) { //jedzenie owoców
            length++;
            wynik=wynik+50;
            x=true;
            while (x) {
                x = false;
                xFruit = random.nextInt(24) * size + 12;
                yFruit = random.nextInt(9) * size + 142;
                for (int i = 0; i < length; i++) {
                    if ((xFruit == xLength[i]) && (yFruit == yLength[i])) {
                        x = true;
                    }
                }
                for (int i = 0; i < numberOfObstacles; i++) {
                    if ((xFruit == xObstacle[i]) && (yFruit == yObstacle[i])) {
                        x = true;
                    }
                }
            }

        }
        for (int i = 3; i < length; i++)
            if (((xLength[0] == xLength[i]) && (yLength[0] == yLength[i]))) {
                alive = false;
            }
        for (int i = 0; i < numberOfObstacles; i++)
            if (((xLength[0] == xObstacle[i]) && (yLength[0] == yObstacle[i]))) {
                alive = false;
            }

        oldDirection=direction;
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(alive) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT: {
                    if (oldDirection != 3)
                        direction = 2;
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    if ( (oldDirection != 2) && (oldDirection != 6) )
                        direction = 3;
                    break;
                }
                case KeyEvent.VK_UP: {
                    if (oldDirection != 1)
                        direction = 0;
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    if (oldDirection != 0)
                        direction = 1;
                    break;
                }
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            start(3);
            timer.start();
            repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
