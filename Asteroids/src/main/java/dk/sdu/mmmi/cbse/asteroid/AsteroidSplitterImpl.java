package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    /**
     * Splits an asteroid into two smaller asteroids. If the asteroid is too small, it is destroyed.
     * NOTE: Supports external, REST-based scoring service.
     *
     * @param e     - The asteroid entity to split.
     * @param world - The world in which the asteroid exists.
     */
    @Override
    public void createSplitAsteroid(Entity e, World world) {
        Entity asteroid1 = createHalfAsteroid(e);
        Entity asteroid2 = createHalfAsteroid(e);
        world.removeEntity(e);
        if (asteroid1 != null) {
            world.addEntity(asteroid1);
        }
        if (asteroid2 != null) {
            world.addEntity(asteroid2);
        } else { // If asteroid has been destroyed, increase score.
            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/addscore"))
                        .POST(HttpRequest.BodyPublishers.ofString("pointAmount=1")).build();
                try {
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException | InterruptedException ex) {
                    //                System.out.println("No response from scoring service");
                }
            }
        }
    }

    private Entity createHalfAsteroid(Entity originalAsteroid) {
        float originalSize = originalAsteroid.getRadius();

        if ((originalSize / 2) <= 4) { // Minimum size of asteroid. Destroyed if smaller.
            return null;
        }

        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        double size = rnd.nextDouble(originalSize / (2.5), originalSize / 2);
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(originalAsteroid.getX());
        asteroid.setY(originalAsteroid.getY());
        asteroid.setRadius((float) size);
        asteroid.setRotation((originalAsteroid.getRotation() + rnd.nextInt(180)));

        // Attempt at fixing asteroid colliding with itself infinitely.
        double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
        double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

        asteroid.setX(asteroid.getX() + changeX * 0.5);
        asteroid.setY(asteroid.getY() + changeY * 0.5);

        return asteroid;
    }

}
