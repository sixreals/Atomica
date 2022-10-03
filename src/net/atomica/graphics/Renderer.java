package net.atomica.graphics;

import net.atomica.game.Game;

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
    private static Frame frame;
    private static Canvas canvas;

    private static int canvasHeight = 400;
    private static int canvasWidth = 400;

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

                while(true) {
                    //if volatile image is deleted, recreate it
                    if(vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
                        vImage = gc.createCompatibleVolatileImage(canvasWidth, canvasHeight);
                    }

                    Graphics g = vImage.getGraphics();

                    //create bg
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, canvasWidth, canvasHeight);

                    //render stuff

                    g.dispose();

                    g = canvas.getGraphics();
                    g.drawImage(vImage, 0, 0, canvasWidth, canvasHeight, null);

                    g.dispose();
                }
            }
        };

        thread.setName("Rendering Thread");
        thread.start();
    }
}
