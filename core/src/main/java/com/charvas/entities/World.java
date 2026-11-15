package com.charvas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.charvas.main.Main;
import com.charvas.items.Item;

public class World {
    private int hiddenEventCounter = 0;

    private TextureAtlas mainTexturesRef = null;

    EntityManager entManager;
    private Player playerRef = null;

    // map and cameras to view the map
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer terrainLayer;
    private ShapeRenderer shapeRenderer = null;

    private boolean drawHitboxes = false;

    public World(TextureAtlas mainTextures, String mapFilePath) {
        this.mainTexturesRef = mainTextures;
        // Initialising the TiledMap and its renderer, as well as the camera
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapFilePath);
        terrainLayer = (TiledMapTileLayer) map.getLayers().get("water");

        // renderers for the map and entities
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f); // Assuming your tiles are 32x32 pixels
        camera = new OrthographicCamera();

        shapeRenderer = new ShapeRenderer();

        entManager = new EntityManager();
        spawnEntities();

    }

    public final void spawnEntities() {
        // handles all entity spawns

        initialiseRCH(new Vector2(15, 7));

        spawnPlayer(new Vector2(5, 5));
        playerRef.setSize(1, 1);

        for (int i = 0; i <= 20; i++) {
            spawnGoose(new Vector2((int) (Math.random() * 40), (int) (Math.random() * 30)));
        }

        spawnMedKit(new Vector2(7, 6));
    }

    public final Player getPlayer() {
        return playerRef;
    }

    public final TiledMap getMap() {
        return map;
    }

    public final OrthographicCamera getCamera() {
        return camera;
    }

    public final void processInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            drawHitboxes = !drawHitboxes;
        }

        playerRef.handleInput();
    }

    public final void update() {
        entManager.update();
    }

    public final void draw(SpriteBatch pBatch) {
        if (!isInitialised()) {
            throw new IllegalStateException(
                    "World is not initialised. Ensure that mainTexturesRef, entManager, camera, map, and mapRenderer are all set.");
        }

        camera.position.set(playerRef.getX() + playerRef.getWidth() / 2,
                playerRef.getY() + playerRef.getHeight() / 2, 0);
        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2,
                Main.V_WIDTH - camera.viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2,
                Main.V_HEIGHT - camera.viewportHeight / 2);
        camera.update();

        mapRenderer.setView(camera.combined, 0, 0, Main.V_WIDTH, Main.V_HEIGHT); // Set mapRenderer to use the camera
        mapRenderer.render();

        pBatch.setProjectionMatrix(camera.combined);
        pBatch.begin();

        entManager.draw(pBatch);

        pBatch.end();

        // draw hitboxes if enabled
        if (drawHitboxes) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.setColor(1, 0, 0, 1); // Red color for hitboxes

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            entManager.drawHitboxes(shapeRenderer);
            shapeRenderer.end();
        }

    }

    public final void initialiseRCH(Vector2 spawnPos) {
        RCHPoopEventZone rch = new RCHPoopEventZone(this);
        rch.setPosition(spawnPos);
        rch.updateHitbox();
        rch.setSize(6f, 7f);

        entManager.addEntity(rch);
    }

    public final void spawnPoop(Vector2 spawnPos) {
        RCHPoopEntity rchPoop = new RCHPoopEntity(new Texture("Items/goosePoopImg.png"), this);
        rchPoop.setPosition(spawnPos);

        entManager.addEntity(rchPoop);
    }

    public final void spawnGoose(Vector2 spawnPos) {
        Goose gooseEntity = new Goose(mainTexturesRef.findRegions("gooseFrame"),
                mainTexturesRef.findRegion("gooseFrame"), this);

        gooseEntity.setPosition(spawnPos);
        gooseEntity.updateHitbox();
        gooseEntity.setSize(0.5f, 0.5f);
        gooseEntity.setMaxSpeed(0.5f);
        gooseEntity.target = playerRef;

        entManager.addEntity(gooseEntity);

    }

    public final void spawnMedKit(Vector2 spawnPos) {
        MedKitEntity medKitEntity = new MedKitEntity(new Texture("Items/medkitImg.png"));

        medKitEntity.setPosition(spawnPos);
        medKitEntity.updateHitbox();
        medKitEntity.setSize(0.5f, 0.5f);

        entManager.addEntity(medKitEntity);
    }

    public final void spawnSeedBag(Vector2 spawnPos) {
        SeedBagEntity seedBagEntity = new SeedBagEntity(5.0f);

        seedBagEntity.setPosition(spawnPos);
        seedBagEntity.updateHitbox();

        entManager.addEntity(seedBagEntity);
    }

    /**
     * Creates a player entity and adds it to entitymanager.
     * Should be called after mainTextures is set
     * 
     * @param spawnPos position to spawn player at
     */
    public final void spawnPlayer(Vector2 spawnPos) {
        if (mainTexturesRef == null)
            throw new IllegalStateException("Main textures must be set before spawning the player.");

        playerRef = new Player(mainTexturesRef.findRegions("playerRunning"), mainTexturesRef.findRegions("playerIdle"),
                this);

        playerRef.setMaxHealth(100);
        playerRef.setHealth(100);
        playerRef.setMaxSpeed(1.5f);
        playerRef.setPosition(spawnPos);
        playerRef.updateHitbox();

        entManager.addEntity(playerRef);
    }

    public final void givePlayer(Item newItem) {
        if (playerRef == null)
            return;
        playerRef.getInventory().addItem(newItem);
    }

    public final boolean isInitialised() {
        return mainTexturesRef != null &&
                entManager != null &&
                camera != null &&
                map != null &&
                mapRenderer != null;
    }

    public void incrementEventCounter() {
        hiddenEventCounter++;
    }

    public int getEventCount() {
        return hiddenEventCounter;
    }

    public boolean isTileWater(float x, float y) {

        if (terrainLayer == null) {
            return false;
        }

        // adds 0.5f to get the center of the player
        int cellX = (int) (x + 0.5f);
        int cellY = (int) (y + 0.5f);

        Cell cell = terrainLayer.getCell(cellX, cellY); // based on tile position

        if (cell != null && cell.getTile() != null) {
            // returns true if the cell is a water tile
            return cell.getTile().getProperties().get("IsWater", false, Boolean.class);
        }

        return false;
    }
}
