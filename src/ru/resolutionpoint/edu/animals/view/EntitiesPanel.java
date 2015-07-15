package ru.resolutionpoint.edu.animals.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import ru.resolutionpoint.edu.animals.model.Entity;
import ru.resolutionpoint.edu.animals.model.Environment;

/**
 * Class <code>EntitiesPanel</code> represents main panel.
 */
public class EntitiesPanel extends JPanel implements Observer {

	private int width;
	private int height;
	private static List<Entity> deletedEntities = new ArrayList<>();
	private static List<EntityView> addedEntities = new ArrayList<>();
	private static List<EntityView> entities = new ArrayList<>();

	public static EntityView getEntityViewByEntity(Entity entity){
		for (EntityView entityView : getEntitiesList()){
			if (entityView.getEntity().equals(entity)) return entityView;
		}
		return null;
	}

    /**
     * Constructs new panel
     *
     * @param environment environment
     */
    public EntitiesPanel(Environment environment) {
		environment.addObserver(this);
		for (Entity entity : environment.getEntities()) {
			EntityView view = new EntityView(entity);
			entities.add(view);
		}
		width = Environment.WIDTH * EntityView.WIDTH;
		height = Environment.HEIGHT * EntityView.HEIGHT;
		setPreferredSize(new Dimension(width, height));
	}

	public static List<EntityView> getEntitiesList(){return entities;}

	public static void deleteEntityView(Entity entity){
		deletedEntities.add(entity);
	}

	public static void addEntityView(Entity entity){
		addedEntities.add(new EntityView(entity));
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.DARK_GRAY);
		for (int i = 1; i <= Environment.WIDTH; i++) {
			int x = i * EntityView.WIDTH;
			g.drawLine(x, 0, x, height);
		}
		for (int j = 1; j <= Environment.HEIGHT; j++) {
			int y = j * EntityView.HEIGHT;
			g.drawLine(0, y, width, y);
		}
		if (deletedEntities.size() != 0){
			for (Entity entity : deletedEntities){
				entities.remove(getEntityViewByEntity(entity));
			}
			deletedEntities.clear();
		}

		if (addedEntities.size() != 0) {
			entities.addAll(addedEntities);
			addedEntities.clear();
		}
		for (EntityView view : getEntitiesList()) {
			view.paint(g);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
}
