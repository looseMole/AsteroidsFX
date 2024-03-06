package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

/**
 *
 * @author corfixen
 */
public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        Entity asteroid1 = createHalfAsteroid(e);
        Entity asteroid2 = createHalfAsteroid(e);
        world.removeEntity(e);
        if(asteroid1 != null){world.addEntity(asteroid1);}
        if(asteroid2 != null){world.addEntity(asteroid2);}
    }

    private Entity createHalfAsteroid(Entity originalAsteroid) {
        float originalSize = originalAsteroid.getRadius();

        if((originalSize/2) <= 4) {
            return null;
        }

        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        double size = rnd.nextDouble(originalSize/(2.5), originalSize/2);
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(originalAsteroid.getX());
        asteroid.setY(originalAsteroid.getY());
        asteroid.setRadius((float)size);
        asteroid.setRotation((originalAsteroid.getRotation()+rnd.nextInt(180)));

        // Attempt at fixing asteroid colliding with itself infinitely.
        double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
        double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

        asteroid.setX(asteroid.getX() + changeX * 0.5);
        asteroid.setY(asteroid.getY() + changeY * 0.5);

        return asteroid;
    }

}
