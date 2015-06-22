package ru.resolutionpoint.edu.animals.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import ru.resolutionpoint.edu.animals.model.Animal;
import ru.resolutionpoint.edu.animals.model.Entity;

/**
 * Class <code>EntityView</code> is View class for {@link Entity}
 *
 * @author Denis Murashev
 */
public class EntityView {

	static final int WIDTH = 30;
	static final int HEIGHT = 30;
    private Entity entity;
    private Image image;

    public Entity getEntity() {
        return entity;
    }

    /**
     * Constructs new view for given entity
     *
     * @param entity entity
     */
    public EntityView(Entity entity) {
		this.entity = entity;
        try {
            URL resource = getClass().getResource(entity.getImagePath());
            this.image = ImageIO.read(resource);
        } catch (IOException e) {
            this.image = null;
            e.printStackTrace();
        }
    }

    /**
     * Paints entity
     *
     * @param g Graphics object
     */
    protected void paint(Graphics g) {
		int x = entity.getX() * WIDTH + 1;
		int y = entity.getY() * HEIGHT + 1;
        g.drawImage(image, x, y, null);
	}
}
