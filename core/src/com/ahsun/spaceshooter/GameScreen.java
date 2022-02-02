package com.ahsun.spaceshooter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;




    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;

    private TextureRegion[] backgrounds;
    private float backgroundHeight;

    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion, enemyShipTextureRegion, enemyShieldTextureRegion,
    playerLaserTextureRegion, enemyLaserTextureRegion;




    //timing
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;

    //world parameters;
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    //game objects
    private Ship playerShip;
    private Ship enemyShip;
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;

    GameScreen(){

        camera = new OrthographicCamera();
        viewport  = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("images.atlas");

        backgrounds =  new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) WORLD_HEIGHT/4;

        //initialize texture regions
        playerShipTextureRegion = textureAtlas.findRegion("playerShip2_blue");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyRed3");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(false,true);
        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed03");



        //Set up game objects
        playerShip = new PlayerShip(2,3, 10,10, WORLD_WIDTH/2,WORLD_HEIGHT/4, 0.4f, 4, 45,0.5f,
                playerShipTextureRegion,playerShieldTextureRegion,playerLaserTextureRegion);
        enemyShip = new EnemyShip(2,3, 10,10, WORLD_WIDTH/2,WORLD_HEIGHT*3/4, 0.3f, 5, 50,0.5f,enemyShipTextureRegion,
               enemyShieldTextureRegion,enemyLaserTextureRegion);

        playerLaserList =  new LinkedList<>();
        enemyLaserList = new LinkedList<>();

        batch = new SpriteBatch();

    }


    @Override
    public void render(float deltaTime) {
        batch.begin();

        playerShip.update(deltaTime);
        enemyShip.update(deltaTime);

        //scrolling background
        renderBackground(deltaTime);


        //enemy ships
        enemyShip.draw(batch);

        //player ship
        playerShip.draw(batch);

        //lasers

        //create new lasers

        //player lasers
        if(playerShip.canFireLaser()){
            Laser[] lasers = playerShip.fireLasers();
            for (Laser laser: lasers){
                playerLaserList.add(laser);
            }
        }

        //enemyLasers
        if(enemyShip.canFireLaser()){
            Laser[] lasers = enemyShip.fireLasers();
            for (Laser laser: lasers){
                enemyLaserList.add(laser);
            }
        }



        //draw lasers
        ListIterator<Laser> iterator =  playerLaserList.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.yPosition += laser.movementSpeed*deltaTime;
            //remove old lasers
            if(laser.yPosition > WORLD_HEIGHT){
                iterator.remove();
            }
        }

        //draw lasers
        iterator =  enemyLaserList.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.yPosition -= laser.movementSpeed*deltaTime;
            //remove old lasers
            if(laser.yPosition + laser.height < 0){
                iterator.remove();
            }
        }


        //explosions


        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    @Override
    public void show() {

    }

    private void renderBackground(float deltaTime){

        backgroundOffsets[0] += deltaTime*backgroundMaxScrollingSpeed/8;
        backgroundOffsets[1] += deltaTime*backgroundMaxScrollingSpeed/4;
        backgroundOffsets[2] += deltaTime*backgroundMaxScrollingSpeed/2;
        backgroundOffsets[3] += deltaTime*backgroundMaxScrollingSpeed;

        for (int layer = 0; layer<backgroundOffsets.length; layer++){
            if(backgroundOffsets[layer]>WORLD_HEIGHT){
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer],0,-backgroundOffsets[layer],WORLD_WIDTH,WORLD_HEIGHT);
            batch.draw(backgrounds[layer],0,-backgroundOffsets[layer]+WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT);
        }

    }
}
