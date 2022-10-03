package net.atomica.particles;

import java.awt.Color;
import javax.swing.text.Position;


public class Particle {
    public Color color;
    
    public Coordinate position;
    public Coordinate velocity;

    public Particle(Color c, Coordinate p, Coordinate v) {
        color = c;
        position = p;
        velocity = v;
    }

    public void refresh() {
        // add velocity onto position
        position.x += velocity.x;
        position.y += velocity.y;

        System.out.println(position.x + " " + position.y);
    }
}
