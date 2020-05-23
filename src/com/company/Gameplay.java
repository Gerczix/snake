package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private ImageIcon[] icons = new ImageIcon[7];
    private int length;
    private int[] xLength = new int[1000];
    private int[] yLength = new int[1000];
    private int direction=6;
    private Timer timer;
    private int delay=30;
    private int xFruit, yFruit;
    private int size;
    private Random random = new Random();
    private boolean x=true;
    private int wynik=0;
    private boolean alive = true;
    private JButton button = new JButton();

    void start(int l) {
        wynik=0;
        length=l;
        alive=true;
        for(int i=0;i<length;i++) {
            xLength[i]=12+(length-i-1)*size;
            yLength[i]=142;
        }
    }

    public Gameplay() {
        //inicjalizowanie ikon
        icons[0] = new ImageIcon("headUp.jpg");
        icons[1] = new ImageIcon("headDown.jpg");
        icons[2] = new ImageIcon("headRight.jpg");
        icons[3] = new ImageIcon("headLeft.jpg");
        icons[4] = new ImageIcon("Body.jpg");
        icons[5] = new ImageIcon("fruit.jpg");
        icons[6] = icons[2];
        size=icons[4].getIconHeight();


        //startowa pozycja węża dla dowolnej długości
        start(8);

        //generowanie polożenia owocow
        while(x) {
            x = false;
            xFruit = random.nextInt(48) * size + 12;
            System.out.println(xFruit);
            yFruit = random.nextInt(19) * size + 142;
            for (int i = 0; i < length; i++) {
                if ((xFruit == xLength[i]) && (yFruit == yLength[i])) {
                    x = true;
                }
            }
        }
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    //rysunek
    public void paint(Graphics g)
    {
        //góra
        g.setColor(Color.red);
        g.drawRect(10,10,788,120);
        g.setColor(Color.green);
        g.fillRect(11,11,786,118);
        g.setColor(Color.white);
        g.setFont(new Font("Dialog", Font.ITALIC, 50));
        FontMetrics fm = g.getFontMetrics(); //pobranie danych napisu
        g.drawString("SNAKE",400-fm.stringWidth("SNAKE")/2,85);

        //wynik
        g.setFont(new Font("Dialog", Font.ITALIC, 17));
        g.drawString("length: "+length,710,25);
        g.drawString("wynik: "+wynik,710,40);

        //dół
        g.setColor(Color.red);
        g.drawRect(10,140,788,324);
        g.setColor(Color.green);
        g.fillRect(11,141,786,322);

        //waz
        for(int i=0; i<length; i++)
        {
            if(i==0)
                icons[direction].paintIcon(this,g,xLength[0],yLength[0]);
            else
                icons[4].paintIcon(this,g,xLength[i],yLength[i]);
        }
        icons[5].paintIcon(this,g,xFruit,yFruit);


        //tablica wynikow
        for(int i=3; i<length; i++)
            if((xLength[0]==xLength[i]) && (yLength[0]==yLength[i])) {
                g.setFont(new Font("Dialog", Font.ITALIC, 30));
                direction=6;
                alive=false;
                System.out.println("dedasz");
                g.setColor(Color.white);
                g.drawString("dedasz leszczu",300,220);
                g.drawString("miałeś tylko "+wynik+" punktów",230,280);
                g.drawString("naciśnij spacje i popraw to chopie",155,340);
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
        if(direction < 2 ) {  //jeżeli ruch w górę, lub dół

            if(direction == 0) { //jeżeli góra
                yLength[0] -= size;
                if(yLength[0]<142)
                    yLength[0]=446;
            }
            else {
                yLength[0] += size;
                if (yLength[0] > 446)
                    yLength[0] = 142;
            }
        }
        else { //jeżeli prawo lub lewo
            if(direction == 2) {//jeżeli prawo
                xLength[0] += size;
                if(xLength[0]>780)
                    xLength[0]=12;
            }
            else if(direction == 3){
                xLength[0] -= size;
                if(xLength[0]<12)
                    xLength[0]=780;
            }

        }

        if((xFruit == xLength[0]) && (yFruit == yLength[0])) {
            length++;
            wynik=wynik+50;
            x=true;
            while (x) {
                x = false;
                xFruit = random.nextInt(48) * size + 12;
                System.out.println(xFruit);
                yFruit = random.nextInt(19) * size + 142;
                for (int i = 0; i < length; i++) {
                    if ((xFruit == xLength[i]) && (yFruit == yLength[i])) {
                        x = true;
                    }
                }
            }

        }


        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(alive) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != 3) {
                direction = 2;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != 2) {
                direction = 3;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP && direction != 1) {
                direction = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != 0) {
                direction = 1;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            for(int i=0;i<length;i++) {
                xLength[i]=12;
                yLength[i]=142;
            }
            start(8);
            repaint();

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
