package net.atomica.graphics;

import net.atomica.game.Game;
import net.atomica.particles.*;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.VolatileImage;


public class Renderer {
    //Graphics Variables
    private static Frame frame;
    private static Canvas canvas;

    //Canvas & Margins
    private static int canvasHeight = 400;
    private static int canvasWidth = 400;

    private static int margin = 10;

    //Ticker  
    private static int ticker = 0; //counts frame rate
    private static int refreshRate = 100; //rate at which ticker triggers a new cycle

    //FPS Variables
    private static long lastFPSCheck = 0;
    private static long nextFPSCheck = 0;
    private static int currentFPS = 0;
    private static int totalFrames = 0;


    //TEST Particle
    private static Particle p = new Particle(Color.PINK, new Coordinate(40, 20), new Coordinate(1, 2));

    public static void init() {
        frame = new Frame();
        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(canvasHeight, canvasWidth));

        frame.add(canvas);
        frame.pack(); //sets frame to fit canvas
        frame.setResizable(false); //stops resizing of canvas

        //listen for window events
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Game.quit();
            }
        });

        frame.setVisible(true);

        startRendering();
    }

    private static void startRendering() {
        Thread thread = new Thread() {
            public void run() {
                GraphicsConfiguration gc = canvas.getGraphicsConfiguration();
                VolatileImage vImage = gc.createCompatibleVolatileImage(canvasWidth, canvasHeight);
                nextFPSCheck = lastFPSCheck + 1000000000;

                while(true) {
                    //FPS Tracker
                    totalFrames++; 

                    if(System.nanoTime() > nextFPSCheck) {
                        //set next fps check
                        nextFPSCheck = System.nanoTime() + 1000000000;
                        currentFPS = totalFrames; //update FPS
                        totalFrames = 0; //reset the number of frames counted

                        //SYSTEM Output
                        System.out.println("FPS: " + currentFPS);
                    }

                    //if volatile image is deleted, recreate it
                    if(vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                        vImage = gc.createCompatibleVolatileImage(canvasWidth, canvasHeight);
                    }

                    Graphics g = vImage.getGraphics();

                    //create bg
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, canvasWidth, canvasHeight);

                    g.setColor(Color.GRAY); //create border
                    g.drawRect(margin, margin, canvasWidth - 2*margin, canvasHeight - 2*margin);

                    //test draw
                    drawParticle(g, p);

                    g = canvas.getGraphics();
                    g.drawImage(vImage, 0, 0, canvasWidth, canvasHeight, null);

                    g.dispose();

                    ticker++;
                }
            }
        };

        thread.setName("Rendering Thread");
        thread.start();
    }

    private static void drawParticle(Graphics g, Particle p) {
        g.setColor(p.color);
        g.drawRect(p.position.x + margin, p.position.y + margin, 0, 0);
        
        if(ticker % refreshRate == 0) {
            p.refresh();
        }
    }
}
