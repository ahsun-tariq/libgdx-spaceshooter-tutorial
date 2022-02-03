package com.ahsun.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Laser {



    //position and dimension
    Rectangle boundingBox;

    //laser characteristics
    float movementSpeed; //world units per second

    //graphics
    TextureRegion textureRegion;

    public Laser(float xPosition, float yPosition, float width, float height, float movementSpeed, TextureRegion textureRegion) {

        this.boundingBox = new Rectangle(xPosition- width/2,yPosition,width,height);
        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }


//    public Rectangle getBoundingBox(){
//        return boundingBox;
//    }


    public void draw(Batch batch){

        batch.draw(textureRegion, boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);

    }

}

