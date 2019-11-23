package co.devbeerloper.myicecreamgame;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable {

    private boolean isPlaying;
    private Ship ship;

    private Paint paint;
    private Paint paintStart;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private  int times=0;
    private int score=0;
    private Rock[] rocks= new Rock[3];
    private ShipBad[] shipsBad= new ShipBad[3];
    private EnemyBullet[] enemyBullet= new EnemyBullet[3];
    boolean active=false;
    boolean rockTouched=false;
    int screenWith=0;
    int level=1;


    private Planet1[] planets1= new Planet1[1];
    private Planet2[] planets2= new Planet2[1];
    private Planet3[] planets3= new Planet3[1];
    private Planet4[] planets4= new Planet4[1];

    /**
     * Contructor
     * @param context
     */
    public GameSurfaceView(Context context, float screenWith, float screenHeight) {
        super(context);
        this.screenWith=(int)screenWith;
        ship = new Ship(context, screenWith, screenHeight);
        paint = new Paint();
        paintStart= new Paint();
        paintStart.setTextSize(100);
        paint.setColor(Color.WHITE);
        holder = getHolder();
        isPlaying = true;


        for (int i=0;i<3;i=i+1){
            rocks[i]= new Rock(context, screenWith, screenHeight);
        }
        for (int i=0;i<3;i=i+1){
            shipsBad[i]= new ShipBad(context, screenWith, screenHeight);
        }
        for (int i=0;i<3;i=i+1){
            enemyBullet[i]= new EnemyBullet(context, screenWith, screenHeight);
        }

        for (int i=0;i<1;i=i+1){
            planets1[i]= new Planet1(context, screenWith, screenHeight);
        }

        for (int i=0;i<1;i=i+1){
            planets2[i]= new Planet2(context, screenWith, screenHeight);
        }

        for (int i=0;i<1;i=i+1){
            planets3[i]= new Planet3(context, screenWith, screenHeight);
        }

        for (int i=0;i<1;i=i+1){
            planets4[i]= new Planet4(context, screenWith, screenHeight);
        }

    }

    /**
     * Method implerockted from runnable interface
     */
    @Override
    public void run() {
        while (isPlaying) {
            updateInfo();
            paintFrame();
        }

    }

    private void updateInfo() {
        if(active){
            ship.updateInfo();
            for(int i=0; i < times/100 ;i=i+1) {
                if(this.score<0){
                    active=false;
                    score=0;
                }
            }

            for(int i=0;i<times/200;i=i+1) {
                if(shipsBad[i].getPositionY()==ship.getPositionY()){
                    enemyBullet[i].paint=true;
                    enemyBullet[i].setPositionY(shipsBad[i].getPositionY());
                }
            }


            for(int i=0; i < times/200 ;i=i+1) {
                this.score+= rocks[i].updateInfo(ship.getPositionX(), ship.getPositionY(),level);
            }
            for(int i=0; i < times/200 ;i=i+1) {
                this.score+= shipsBad[i].updateInfo(ship.getPositionX(), ship.getPositionY(),level);
            }
            for(int i=0; i < times/200 ;i=i+1) {
                this.score+= enemyBullet[i].updateInfo(ship.getPositionX(), ship.getPositionY(),level);
            }

            for(int i=0; i < times/700 ;i=i+1) {
                planets1[i].updateInfo();
            }

            for(int i=0; i < times/700 ;i=i+1) {
                planets2[i].updateInfo();
            }

            for(int i=0; i < times/700 ;i=i+1) {
                planets3[i].updateInfo();
            }

            for(int i=0; i < times/700 ;i=i+1) {
                planets4[i].updateInfo();
            }

        }else {
            score=0;
            level=0;
            times=0;

            for (int i=0;i<3;i=i+1){
                rocks[i].setPositionX(screenWith);
            }
            for (int i=0;i<3;i=i+1){
                shipsBad[i].setPositionX(screenWith);
            }
            for (int i=0;i<3;i=i+1){
                enemyBullet[i].setPositionX(screenWith);
            }

        }
        if((score/20)+1>level){
            level=(score/20)+1;
        }
    }

    private void paintFrame() {
        if (holder.getSurface().isValid()){
            if(times<700){
                times=times+1;
            }
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawText("Score "+this.score+"", 40,40,paint);
            canvas.drawText("Level "+this.level+"", this.screenWith-400,40,paint);
            if(!active){canvas.drawText("You lose", 650,400,paint);}
            paint.setTextSize(40);
            canvas.drawBitmap(ship.getSpriteship(),ship.getPositionX(),ship.getPositionY(),paint);


            for(int i=0;i<times/200;i=i+1) {
                canvas.drawBitmap(rocks[i].getSpriterock(),rocks[i].getPositionX(),rocks[i].getPositionY(),paint);
            }
            for(int i=0;i<times/200;i=i+1) {
                canvas.drawBitmap(shipsBad[i].getSpriteShipBad(),shipsBad[i].getPositionX(),shipsBad[i].getPositionY(),paint);
            }
            for(int i=0;i<times/200;i=i+1) {
                canvas.drawBitmap(enemyBullet[i].getSpriteEnemyBullet(),enemyBullet[i].getPositionX(),enemyBullet[i].getPositionY(),paint);
            }

            for(int i=0;i<times/700;i=i+1) {
                canvas.drawBitmap(planets1[i].getSpritecloud(),planets1[i].getPositionX(),planets1[i].getPositionY(),paint);
            }

            for(int i=0;i<times/700;i=i+1) {
                canvas.drawBitmap(planets2[i].getSpritecloud(),planets2[i].getPositionX(),planets2[i].getPositionY(),paint);
            }

            for(int i=0;i<times/700;i=i+1) {
                canvas.drawBitmap(planets3[i].getSpritecloud(),planets3[i].getPositionX(),planets3[i].getPositionY(),paint);
            }

            for(int i=0;i<times/700;i=i+1) {
                canvas.drawBitmap(planets4[i].getSpritecloud(),planets4[i].getPositionX(),planets4[i].getPositionY(),paint);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {

        isPlaying = true;
        gameplayThread = new Thread(this);
        gameplayThread.start();
    }

    /**
     * Detect the action of the touch event
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.active=true;
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                ship.setJumping(false);
                break;
            case MotionEvent.ACTION_DOWN:
                ship.setJumping(true);
                break;
        }
        return true;
    }


}