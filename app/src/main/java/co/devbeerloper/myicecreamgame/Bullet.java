package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {


    public static final float INIT_X =100;
    public static final float INIT_Y =100;
    public  int SPRITE_SIZE_WIDTH =60;
    public  int SPRITE_SIZE_HEIGTH=20;
    public static final float GRAVITY_FORCE=10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    int puntos=0;
    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriteBullet;
    public boolean paint;


    public Bullet(Context context, float screenWidth, float screenHeigth){

        //Getting bitmap from resource
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        spriteBullet  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteBullet.getWidth()/2);
        this.maxY = screenHeigth - spriteBullet.getHeight();
        speed = 1;
        positionX = screenWidth-200;
        positionY = (float)Math.random()*maxY;
    }

    public Bullet(Context context, float initialX, float initialY, float screenWidth, float screenHeigth){


        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        spriteBullet  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteBullet.getWidth()/2);
        this.maxY = screenHeigth - spriteBullet.getHeight();
        this.maxX = screenWidth - (spriteBullet.getWidth()/2);
        this.maxY = screenHeigth - spriteBullet.getHeight();
        speed = 1;
        positionX = screenWidth-200;
        positionY = (float)Math.random()*maxY;

    }

    public int getMaxX(){
        return (int)this.maxX;
    }

    public static float getInitX() {
        return INIT_X;
    }

    public static float getInitY() {
        return INIT_Y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getSpriteBullet() {
        return spriteBullet;
    }

    public void setSpriteBullet(Bitmap spriteBullet) {
        this.spriteBullet = spriteBullet;
    }
        /**
     * Control the position and behaviour of the icecream car
     */
    public int updateInfo (float a, float b,int level) {

            this.positionX += 8;
            this.SPRITE_SIZE_HEIGTH *= (level);
            this.SPRITE_SIZE_WIDTH *= (level);

        /*
            if (a + 60 > this.positionX && a - 60 < this.positionX) {
                if (b + 60 > this.positionY && b - 60 < this.positionY) {
                    this.positionX = this.maxX;
                    this.positionY = (float) Math.random() * maxY;
                    return -10000;
                }
            }*/
            return 0;

    }

}

