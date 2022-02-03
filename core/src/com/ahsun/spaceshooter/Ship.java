package com.ahsun.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

abstract public class Ship {

    //ship characteristics
    float movementSpeed; //world units per second
    int shield;


    //position & dimension

    Rectangle boundingBox;

    //laser information
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    //graphics
    TextureRegion shipTextureRegion, shieldTextureRegion, laserTextureRegion;

    public Ship(float movementSpeed, int shield, float width, float height, float xCentre, float yCentre, float laserWidth, float  laserHeight, float laserMovementSpeed,
               float timeBetweenShots, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,TextureRegion laserTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;

        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;
        this.boundingBox = new Rectangle(xCentre - width/2,yCentre - width/2,width,height);
        this.shipTextureRegion = shipTextureRegion;
        this.shieldTextureRegion = shieldTextureRegion;
        this.laserTextureRegion = laserTextureRegion;
    }

    public void update(float deltaTime){

        timeSinceLastShot += deltaTime;
    }

    public boolean canFireLaser(){
        return(timeSinceLastShot - timeBetweenShots >=0);
    }

    public boolean intersects(Rectangle otherRectangle){

        return boundingBox.overlaps(otherRectangle);
    }

    public void hit(Laser laser){
        if (this.shield > 0){
            this.shield --;
        }
    }


    public abstract Laser[] fireLasers();

    public void draw(Batch batch){

        batch.draw(shipTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        if(shield >0){
            batch.draw(shieldTextureRegion, boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        }
    }
}
