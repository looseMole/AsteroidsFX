package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.springframework.stereotype.Component;

@Component
public class AsteroidProcessor implements IEntityProcessingService {

    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();

    /**
     * This method is called every frame. It updates the asteroid movement, and checks if they are out of bounds.
     * If they are, they are moved back in bounds.
     * @param gameData - The game data object
     * @param world - The game world object containing all entities
     */
    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            asteroid.setX(asteroid.getX() + changeX * 0.5);
            asteroid.setY(asteroid.getY() + changeY * 0.5);

            if (asteroid.getX() < 0) {
                asteroid.setX(asteroid.getX() - gameData.getDisplayWidth());
            }

            if (asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(asteroid.getX() % gameData.getDisplayWidth());
            }

            if (asteroid.getY() < 0) {
                asteroid.setY(asteroid.getY() - gameData.getDisplayHeight());
            }

            if (asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(asteroid.getY() % gameData.getDisplayHeight());
            }

        }

    }

    /**
     * This method is called whenever an entity collides with another entity.
     * If one of the entities in the collision is an asteroid, it calls the createSplitAsteroid method on the asteroidSplitter, for that entity.
     * @param world - The game world object containing all entities
     * @param collider - The entity which collided with another entity
     * @param collidee - The entity which was collided with
     */
    @Override
    public void collide(World world, Entity collider, Entity collidee) {
        // If neither, or both entities is asteroids, return:
        if(!((collider instanceof Asteroid) || (collidee instanceof Asteroid)) || ((collider instanceof Asteroid) && (collidee instanceof Asteroid))){
            return;
        }

        Asteroid asteroidToSplit;
        if(collider instanceof Asteroid){
            asteroidToSplit = (Asteroid) collider;
        } else { // Safe to cast, as we have already checked that there is an Asteroid, and whether it is the other entity.
            asteroidToSplit = (Asteroid) collidee;
        }

        asteroidSplitter.createSplitAsteroid(asteroidToSplit, world);
    }

    /**
     * Dependency Injection using OSGi Declarative Services
     */
    public void setAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = asteroidSplitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = null;
    }


}
