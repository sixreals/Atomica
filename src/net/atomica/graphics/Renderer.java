package net.atomica.graphics;

import net.atomica.game.Game;

import java.awt.Frame;
import java.awt.Canvas;
import java.awt.Dimension;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Renderer {
    private static Frame frame;
    private static Canvas canvas;

    private static int canvasHeight;
    private static int canvasWidth;

    public static void init() {
        frame = new Frame();
        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(canvasHeight, canvasWidth));

        frame.add(canvas);
        frame.pack(); //sets frame to fit canvas
        frame.setResizable(false); //stops resizing of canvas
        frame.setVisible(true);

        //listen for window events
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Game.quit();
            }
        });
    }
}
