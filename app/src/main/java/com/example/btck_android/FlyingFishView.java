package com.example.btck_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class FlyingFishView extends View {

    private Bitmap fish[] = new Bitmap[3];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private Bitmap smallFish;
    private int smallFishX, smallFishY, smallFishSpeed = 15;

    private Bitmap mediumFish;
    private int mediumFishX, mediumFishY, mediumFishSpeed = 20;

    private Bitmap largeFish;
    private int largeFishX, largeFishY, largeFishSpeed = 25;

    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    private int canavasWidth, canavasHeight;
    private boolean touch = false;
    private int score, lifeCounter;

    //    Phương thức khởi tạo view
    public FlyingFishView(Context context) {
        super(context);
//        Tạo hình cho nhân vật chính
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        fish[2] = BitmapFactory.decodeResource(getResources(), R.drawable.fish3);

//        Tạo hình nền cho game
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.a);

//        Tạo cá bé
        smallFish = BitmapFactory.decodeResource(getResources(),R.drawable.smallFish);

//        Tạo cá vừa
        mediumFish = BitmapFactory.decodeResource(getResources(),R.drawable.mediumFish);

//        Tạo cá lớn
        largeFish = BitmapFactory.decodeResource(getResources(),R.drawable.largeFish);

//        Tạo bảng điểm số
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);

//        Tạo hình mạng
        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.a);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.a);

//        Điểm bắt đầu trên trục Y của nhân vật
        fishY = 550;

//        Điểm số ban đầu
        score = 0;

//        Số mạng
        lifeCounter = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Lấy chiều rộng, cao của khung hình
        canavasWidth = canvas.getWidth();
        canavasHeight = canvas.getHeight();

//        Vẽ hình nền
        canvas.drawBitmap(backgroundImage,0,0,null);

//        Thiết lập biên độ di chuyển tối đa và tối thiểu của cá theo trục Y
        int minFishY = fish[0].getHeight();
        int maxFishY = canavasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;

        if(fishY < minFishY) {
            fishY = minFishY;
        }

        if(fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed += 2;
//        Làm nhân vật chuyển động khi chạm
        if(touch) {
            canvas.drawBitmap(fish[1],fishX, fishY, null);
            touch = false;
        }
        else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }

//        Thiết lập vị trí cho cá nhỏ
        smallFishX = smallFishX - smallFishSpeed;
        if(smallFishX < 0) {
            smallFishX = canavasWidth + 21;
            smallFishY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
//        Cộng điểm nếu ăn cá nhỏ
        if(hitFishChecker(smallFishX, smallFishY)) {
            score += 10;
            smallFishX = -1000;
        }
//        Vẽ cá nhỏ
        canvas.drawBitmap(smallFish, smallFishX, smallFishY,null);

//        Thiết lập vị trí cho cá vừa
        mediumFishX = mediumFishX - mediumFishSpeed;
        if(mediumFishX < 0) {
            mediumFishX = canavasWidth + 21;
            mediumFishY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
//        Cộng điểm nếu ăn cá vừa
        if(hitFishChecker(mediumFishX, mediumFishY)) {
            score += 20;
            mediumFishX = -1000;
        }
//        Vẽ cá vừa
        canvas.drawBitmap(mediumFish, mediumFishX, mediumFishY,null);

//         Thiết lập vị trí cho cá lớn
        largeFishX = largeFishX - largeFishSpeed;
        if(largeFishX < 0) {
            largeFishX = canavasWidth + 21;
            largeFishY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
//         Trừ mạng nếu ăn cá lớn
        if(hitFishChecker(largeFishX, largeFishY)) {
            largeFishX = -1000;
            lifeCounter--;

            if(lifeCounter == 0) {
                Toast.makeText(getContext(),"Game Over", Toast.LENGTH_LONG).show();
            }
//          Chuyển dữ liệu sang GameOverActivity
            Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
            gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getContext().startActivity(gameOverIntent);
        }
//        Vẽ cá lớn
        canvas.drawBitmap(largeFish, largeFishX, largeFishY,null);

//        Vẽ điểm số
        canvas.drawText("Score: ",20,60,scorePaint);

//        Vẽ số mạng còn và mất
        for(int i=0; i<3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 *i );
            int y =30;

            if(i < lifeCounter) {
                canvas.drawBitmap(life[0], x, y,null);
            }
            else {
                canvas.drawBitmap(life[1], x, y,null);
            }
        }
    }

    //      Phương thức kiểm tra có ăn được cá nhỏ hay không
    public boolean hitFishChecker(int x, int y) {
        if(fishX < x && x < (fishX + fish[0].getWidth()) &&
                fishY < y && y < (fishY + fish[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
        }
        return true;
    }
}
