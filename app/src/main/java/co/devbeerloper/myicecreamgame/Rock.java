package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Rock {


    public static final float INIT_X =100;
    public static final float INIT_Y =100;
    public  int SPRITE_SIZE_WIDTH =70;
    public  int SPRITE_SIZE_HEIGTH=100;
    public static final float GRAVITY_FORCE=10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    int puntos=0;
    private float maxY;
    private float maxX;
    private int  orientation=1;
    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriterock;
    private boolean primeraVez;


    public Rock(Context context, float screenWidth, float screenHeigth){

        //Getting bitmap from resource
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.rock);
        spriterock  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriterock.getWidth()/2);
        this.maxY = screenHeigth - spriterock.getHeight();
        speed = 1;
        positionX = screenWidth-200;
        positionY = (float)Math.random()*maxY;
    }

    public Rock(Context context, float initialX, float initialY, float screenWidth, float screenHeigth){


        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.rock);
        spriterock  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriterock.getWidth()/2);
        this.maxY = screenHeigth - spriterock.getHeight();
        this.maxX = screenWidth - (spriterock.getWidth()/2);
        this.maxY = screenHeigth - spriterock.getHeight();
        speed = 1;
        positionX = screenWidth-200;
        positionY = (float)Math.random()*maxY;

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

    public Bitmap getSpriterock() {
        return spriterock;
    }

    public void setSpriterock(Bitmap spriterock) {
        this.spriterock = spriterock;
    }

    /**
     * Control the position and behaviour of the icecream car
     */
    public int updateInfo (float a, float b,int level,int x, int y) {

         this.positionX-=3*level;
         this.SPRITE_SIZE_HEIGTH*=(level );
         this.SPRITE_SIZE_WIDTH*=(level);
         speed+=5;

        if(level>2 ){
            this.positionY+=(orientation);
        }
        if(positionY<0){
            orientation=1;
        }
        if(positionY>maxY){
            orientation=-1;
        }

        if(this.positionX<0){
            this.positionX= this.maxX;
            this.positionY =  (float)Math.random()*maxY;
            return -1;
        }

        if(x+100>this.positionX && x-100<this.positionX ){
            if(y+100>this.positionY && y-100<this.positionY ){
                this.positionX= this.maxX;
                this.positionY = (float)Math.random()*maxY;
                return 5;
            }
        }
        if(a+60>this.positionX && a-60<this.positionX ){
            if(b+60>this.positionY && b-60<this.positionY ){
                this.positionX= this.maxX;
                this.positionY =  (float)Math.random()*maxY;
                return -10000;
            }
        }
        return 0;
    }
}

