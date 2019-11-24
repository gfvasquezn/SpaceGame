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
import android.widget.Toast;

public class GameSurfaceView extends SurfaceView implements Runnable {

    private boolean isPlaying;
    private Ship ship;
    private Bullet bullet;
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
    boolean disparar=true;

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
        bullet = new Bullet(context, screenWith, screenHeight);
        paint = new Paint();
        paintStart= new Paint();
        paintStart.setTextSize(100);
        paint.setColor(Color.WHITE);
        holder = getHolder();
        isPlaying = true;


        for (int i=0;i<rocks.length;i=i+1){
            rocks[i]= new Rock(context, screenWith, screenHeight);
        }
        for (int i=0;i<shipsBad.length;i=i+1){
            shipsBad[i]= new ShipBad(context, screenWith, screenHeight);
        }
        for (int i=0;i<enemyBullet.length;i=i+1) {
            enemyBullet[i] = new EnemyBullet(context, screenWith, screenHeight);
        }

        planets1[0]= new Planet1(context, screenWith, screenHeight);
        planets2[0]= new Planet2(context, screenWith, screenHeight);
        planets3[0]= new Planet3(context, screenWith, screenHeight);
        planets4[0]= new Planet4(context, screenWith, screenHeight);

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
        if(level>2){
            level=3;
        }
        if(active){
            ship.updateInfo();
            bullet.updateInfo(ship.getPositionX(), ship.getPositionY(),level);
            for(int i=0; i < this.level ;i=i+1) {
                if(this.score<0){
                    active=false;
                    score=0;
                }
            }

            for(int i=0;i<this.level;i=i+1) {
                if( enemyBullet[i].getPositionX()<0){
                    enemyBullet[i].setPositionX(shipsBad[i].getPositionX());
                    enemyBullet[i].setPositionY(shipsBad[i].getPositionY());
                }
            }

            if( disparar ){
                bullet.setPositionY(ship.getPositionY()+50);
                bullet.setPositionX(200);
                disparar=false;
            }

            for(int i=0;i<this.level;i=i+1) {
                if((enemyBullet[i].getPositionY() +20> ship.getPositionY() && enemyBullet[i].getPositionY() -20 < ship.getPositionY() )&& (enemyBullet[i].getPositionX() +20> ship.getPositionX() && enemyBullet[i].getPositionX() -20 < ship.getPositionX()) ){
                    enemyBullet[i].setPositionX(shipsBad[i].getPositionX());
                    enemyBullet[i].setPositionY(shipsBad[i].getPositionY());
                }
            }

            for(int i=0; i < this.level ;i=i+1) {
                this.score+= rocks[i].updateInfo(ship.getPositionX(), ship.getPositionY(),level,(int)bullet.getPositionX(),(int)bullet.getPositionY());
            }
            for(int i=0; i < this.level ;i=i+1) {
                this.score+= shipsBad[i].updateInfo(ship.getPositionX(), ship.getPositionY(),level,(int)bullet.getPositionX(),(int)bullet.getPositionY());
            }
            for(int i=0; i < this.level ;i=i+1) {
                this.score+= enemyBullet[i].updateInfo(ship.getPositionX(), ship.getPositionY(),level);
            }

            planets1[0].updateInfo();

            planets2[0].updateInfo();

            planets3[0].updateInfo();

            planets4[0].updateInfo();

        }else {
            score=0;
            level=0;
            times=0;

            for (int i=0;i<rocks.length;i=i+1){
                rocks[i].setPositionX(screenWith);
            }
            for (int i=0;i<rocks.length;i=i+1){
                shipsBad[i].setPositionX(screenWith);
            }
            for (int i=0;i<rocks.length;i=i+1){
                enemyBullet[i].setPositionX(screenWith);
            }

        }
        if((score/20)+1>level){
            level=(score/500)+1;
        }
    }

    private void paintFrame() {
        if (holder.getSurface().isValid()){

            canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawText("Score "+this.score+"", 40,40,paint);
            canvas.drawText("Level "+this.level+"", this.screenWith-400,40,paint);
            if(!active){canvas.drawText("You lose", 650,400,paint);}
            canvas.drawText("S ", 20,250,paint);
            canvas.drawText("H ", 20,300,paint);
            canvas.drawText("O ", 20,350,paint);
            canvas.drawText("O ", 20,400,paint);
            canvas.drawText("T ", 20,450,paint);
            paint.setTextSize(40);
            canvas.drawBitmap(ship.getSpriteship(),ship.getPositionX(),ship.getPositionY(),paint);
            canvas.drawBitmap(bullet.getSpriteBullet(),bullet.getPositionX(),bullet.getPositionY(),paint);

            for(int i=0;i<this.level;i=i+1) {
                canvas.drawBitmap(rocks[i].getSpriterock(),rocks[i].getPositionX(),rocks[i].getPositionY(),paint);
            }
            for(int i=0;i<this.level;i=i+1) {
                canvas.drawBitmap(shipsBad[i].getSpriteShipBad(),shipsBad[i].getPositionX(),shipsBad[i].getPositionY(),paint);
            }
            for(int i=0;i<this.level;i=i+1) {
                canvas.drawBitmap(enemyBullet[i].getSpriteEnemyBullet(),enemyBullet[i].getPositionX(),enemyBullet[i].getPositionY(),paint);
            }

            canvas.drawBitmap(planets1[0].getSpritecloud(),planets1[0].getPositionX(),planets1[0].getPositionY(),paint);
            canvas.drawBitmap(planets2[0].getSpritecloud(),planets2[0].getPositionX(),planets2[0].getPositionY(),paint);
            canvas.drawBitmap(planets3[0].getSpritecloud(),planets3[0].getPositionX(),planets3[0].getPositionY(),paint);
            canvas.drawBitmap(planets4[0].getSpritecloud(),planets4[0].getPositionX(),planets4[0].getPositionY(),paint);
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

        float x = motionEvent.getX();
        float y = motionEvent.getY();

        if (x >= 0 && x <=100) {
            disparar=true;
            return true;
        }

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