package com.game.combob;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class BoardView extends View
{

    public static final int BOARD_MARGIN = 20;
    public static final int BOARD_SIZE_X = 11;
    public static final int BOARD_SIZE_Y = 9;
    public static final int GRID_SIZE = 2;
    private static final int SPACE_LETTER=75;
    private static final int SPACE=0;

    private static final int MSG_ANIMATE = 0;

    private Bitmap mBitmap;
    private Bitmap mBitmapScaled;

    private Bitmap bmpHelpScaled,bmpStart,bmpPause;

    private boolean paused;


    private final Handler animationHandler = new Handler(
            new AnimationMessageHandler());

    private MoveStageListener moveStageListener;


    public interface MoveStageListener
    {
        void userClick(int i, int j);
        void animationComplete();
    }

    public void setMoveStageListener(MoveStageListener selectionListener) {
        this.moveStageListener = selectionListener;
    }


    public interface Animation {

        void animate(Canvas canvas);


        boolean isFinish();


        boolean skip(int i, int j);


        int fps();
    }

    private Animation animation = new NullAnimation();


    private State[][] positions;
    private Node[][] nodes;

    public void setPositions(State[][] positions)
    {
        this.positions = positions;
    }

    public void setNodes(Node[][] nodes)
    {
        this.nodes = nodes;
    }


    private Paint boardLinePaint;

    //Game Status
    private int waktu;
    private int curlevel;
    private int helpcount;
    private int maxsoal;
    private int progressoal;
    private int targetsoal;

    private float boardWidth;
    private float boardHeight;


    private float maxRadius;


    private float cellSizeX;
    private float cellSizeY;


    public boolean IsPaused()
    {
        return paused;
    }

    public void setNTargetSoal(int target)
    {
        this.targetsoal = target;
    }

    public int getNTargetsoal()
    {
        return targetsoal;
    }

    private void SetMaxSoal(int maxsoal)
    {
        this.maxsoal = maxsoal;
    }

    private String ConvertTime(int waktu ) {
        int menit,detik ;
        String strwaktu;
        menit = waktu / 60;
        detik = waktu % 60;
        strwaktu = String.valueOf(menit)  + ":" + String.valueOf(detik);
        return strwaktu;
    }


    public void SetWaktu(int waktu)
    {
        this.waktu = waktu;
    }

    public void SetHelpCount(int helpc)
    {
        helpcount = helpc;
    }

    public void SetProgressSoal(int progressoal)
    {
        this.progressoal = progressoal;
    }

    public void SetCurLevel(int level) {
        this.curlevel = level;
    }

    public BoardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        requestFocus();
        boardLinePaint = new Paint();
        boardLinePaint.setColor(0xFFFFFFFF);
        boardLinePaint.setStrokeWidth(GRID_SIZE);
        boardLinePaint.setStyle(Style.STROKE);
        paused=false;


    }



    public void Paused()
    {
        paused =true;
    }

    public void reStartGame()
    {
        paused =false;
    }

    private   void setTextSizeForWidth(Paint paint, float desiredWidth,
                                       String text) {


        final float testTextSize = desiredWidth;
        paint.setTextSize(testTextSize);
        //   Rect bounds = new Rect();
        //  paint.getTextBounds(text, 0, text.length(), bounds);
        //  float desiredTextSize = testTextSize * desiredWidth / bounds.width();
        //  paint.setTextSize(desiredTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        float offsetBoardWidth = boardWidth - BOARD_MARGIN;
        float offsetBoardHeight = boardHeight - BOARD_MARGIN;
        canvas.drawRect(BOARD_MARGIN, BOARD_MARGIN, offsetBoardWidth,
                offsetBoardHeight, boardLinePaint);

        for (int i = 0; i < BOARD_SIZE_X; i++)
        {
            float cellStep = BOARD_MARGIN + (i * cellSizeX);
            canvas.drawLine(cellStep, BOARD_MARGIN, cellStep, offsetBoardHeight,
                    boardLinePaint);
            // canvas.drawLine(BOARD_MARGIN, cellStep, offsetBoardWidth, cellStep,boardLinePaint);
        }

        for (int i = 0; i < BOARD_SIZE_Y; i++)
        {
            float cellStep = BOARD_MARGIN + (i * cellSizeY);
            canvas.drawLine(BOARD_MARGIN, cellStep, offsetBoardWidth, cellStep,boardLinePaint);
        }

        Paint pt = new Paint();
        pt.setColor(Color.YELLOW);
        setTextSizeForWidth(pt,35,"Level");
        canvas.drawText("Level",offsetBoardWidth + BOARD_MARGIN,BOARD_MARGIN+(SPACE_LETTER*3) +SPACE,pt);

        pt.setColor(Color.WHITE);
        setTextSizeForWidth(pt,35,String.valueOf(curlevel));
        canvas.drawText(String.valueOf(curlevel),offsetBoardWidth + (BOARD_MARGIN*6) + (bmpHelpScaled.getWidth()/2),BOARD_MARGIN+(SPACE_LETTER*3),pt);

        pt.setColor(Color.YELLOW);
        setTextSizeForWidth(pt,35,"Sisa Soal");
        canvas.drawText("Sisa Soal",offsetBoardWidth + BOARD_MARGIN,BOARD_MARGIN+(SPACE_LETTER*4) +SPACE,pt);

        pt.setColor(Color.WHITE);
        setTextSizeForWidth(pt,35,String.valueOf(targetsoal - progressoal));
        canvas.drawText(String.valueOf( targetsoal -progressoal),offsetBoardWidth + (BOARD_MARGIN*6) + (bmpHelpScaled.getWidth()/2),BOARD_MARGIN+(SPACE_LETTER*4),pt);

        pt.setColor(Color.YELLOW);
        setTextSizeForWidth(pt,35,"Waktu");
        canvas.drawText("Waktu",offsetBoardWidth + BOARD_MARGIN,BOARD_MARGIN+(SPACE_LETTER*5) +SPACE,pt);

        pt.setColor(Color.WHITE);
        setTextSizeForWidth(pt,35,ConvertTime(waktu));
        canvas.drawText(ConvertTime(waktu),offsetBoardWidth + (BOARD_MARGIN*6) + (bmpHelpScaled.getWidth()/2),BOARD_MARGIN+(SPACE_LETTER*5),pt);
		/*
		pt.setColor(Color.YELLOW);
		setTextSizeForWidth(pt,35,"Soal");
		canvas.drawText("Soal",offsetBoardWidth + BOARD_MARGIN,BOARD_MARGIN+(SPACE_LETTER*3)+SPACE,pt);

		pt.setColor(Color.WHITE);
		setTextSizeForWidth(pt,35,String.valueOf(progressoal));
		canvas.drawText(String.valueOf(progressoal),offsetBoardWidth + BOARD_MARGIN,BOARD_MARGIN+(SPACE_LETTER*4),pt);

		pt.setColor(Color.YELLOW);
		setTextSizeForWidth(pt,35,"Waktu");
		canvas.drawText("Waktu",offsetBoardWidth + BOARD_MARGIN,BOARD_MARGIN+(SPACE_LETTER*5)+SPACE,pt);
		pt.setColor(Color.WHITE);
		setTextSizeForWidth(pt,35,ConvertTime(waktu));
		canvas.drawText(ConvertTime(waktu),offsetBoardWidth + BOARD_MARGIN,BOARD_MARGIN+(SPACE_LETTER*6),pt);
		*/
        if (bmpHelpScaled!=null)
        {
            canvas.drawBitmap(bmpHelpScaled, offsetBoardWidth + (BOARD_MARGIN*6), BOARD_MARGIN, null);
            pt.setColor(Color.RED);
            setTextSizeForWidth(pt,60,String.valueOf(helpcount));
            canvas.drawText(String.valueOf(helpcount),offsetBoardWidth + (BOARD_MARGIN*6) + (bmpHelpScaled.getWidth()/2)-20 ,BOARD_MARGIN+  SPACE_LETTER+20,pt);
        }

        if (bmpStart!=null)
        {
            canvas.drawBitmap(bmpStart, offsetBoardWidth + (BOARD_MARGIN),offsetBoardHeight -(BOARD_MARGIN*4), null);
        }

        if (bmpPause!=null)
        {
            canvas.drawBitmap(bmpPause, offsetBoardWidth + (BOARD_MARGIN*6),offsetBoardHeight- (BOARD_MARGIN*4), null);
        }

        if (paused)
        {
            pt.setColor(Color.WHITE);
            setTextSizeForWidth(pt,45,"PAUSED");
            canvas.drawText("PAUSED",offsetBoardWidth + (BOARD_MARGIN*5),BOARD_MARGIN+(SPACE_LETTER*6)+SPACE,pt);
        }


        setValuesFromDatas(canvas);
        animation.animate(canvas);

    }


    private void setValuesFromDatas(Canvas canvas) {
        for (int i = 1; i < BOARD_SIZE_X + 1; i++)
        {
            for (int j = 1; j < BOARD_SIZE_Y + 1; j++)
            {

                if (!paused)
                {
                    if (positions[i - 1][j - 1]!=State.EMPTY)
                    {
                        drawBgSquare(canvas,i,j);
                        drawSolidSquare(canvas, i, j, positions[i - 1][j - 1]);
                        drawIcon(i, j, canvas);
                    }
                }
            }
        }
    }


    private void drawWhiteSquare(Canvas canvas, int i, int j, int alpha) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Style.FILL);
        paint.setAlpha(alpha);
        drawCustomRect(i, j, canvas, paint, 0);
    }

    private void drawCustomRect(int i, int j, Canvas canvas, Paint paint,
                                float shrink) {

        canvas.drawRect(i * cellSizeX + GRID_SIZE + BOARD_MARGIN + shrink, j
                * cellSizeY + GRID_SIZE + BOARD_MARGIN + shrink, (i + 1)
                * cellSizeX - GRID_SIZE + BOARD_MARGIN - shrink, (j + 1)
                * cellSizeY + BOARD_MARGIN - GRID_SIZE - shrink, paint);
    }

    private void drawBgSquare(Canvas canvas, int i, int j )
    {
        Paint paintBigger = new Paint();
        paintBigger.setColor(0xFFAEEAF9);
        paintBigger.setStyle(Style.FILL);
        drawCustomRect(i - 1, j - 1, canvas, paintBigger, 0);
    }

    private void drawSolidSquare(Canvas canvas, int i, int j, State who) {

        if (who == State.SELECTED)
        {
            Paint paintBigger = new Paint();
            paintBigger.setColor(0xFFA800A8);
            paintBigger.setStyle(Style.FILL);

            drawCustomRect(i - 1, j - 1, canvas, paintBigger, 0);

            Paint paintSmaller = new Paint();
            paintSmaller.setColor(0xFFFC54FC);
            paintSmaller.setStyle(Style.FILL);

            float shrink = cellSizeX * 0.15f;

            drawCustomRect(i - 1, j - 1, canvas, paintSmaller, shrink);

            canvas.drawLine((i - 1) * cellSizeX + GRID_SIZE + BOARD_MARGIN,
                    (j - 1) * cellSizeX + GRID_SIZE + BOARD_MARGIN, (i - 1)
                            * cellSizeX + GRID_SIZE + BOARD_MARGIN + shrink,
                    (j - 1) * cellSizeX + GRID_SIZE + BOARD_MARGIN + shrink,
                    paintSmaller);

            canvas.drawLine(i * cellSizeX - GRID_SIZE + BOARD_MARGIN, (j - 1)
                    * cellSizeX + GRID_SIZE + BOARD_MARGIN, i * cellSizeX
                    - GRID_SIZE + BOARD_MARGIN - shrink, (j - 1) * cellSizeX
                    + GRID_SIZE + BOARD_MARGIN + shrink, paintSmaller);

            canvas.drawLine(i * cellSizeX - GRID_SIZE + BOARD_MARGIN, j
                    * cellSizeX - GRID_SIZE + BOARD_MARGIN, i * cellSizeX
                    - GRID_SIZE + BOARD_MARGIN - shrink, j * cellSizeX
                    - GRID_SIZE + BOARD_MARGIN - shrink, paintSmaller);

            canvas.drawLine((i - 1) * cellSizeX + GRID_SIZE + BOARD_MARGIN, j
                    * cellSizeX - GRID_SIZE + BOARD_MARGIN, (i - 1) * cellSizeX
                    + GRID_SIZE + BOARD_MARGIN + shrink, j * cellSizeX
                    - GRID_SIZE + BOARD_MARGIN - shrink, paintSmaller);
        }

    }

    public void initBitmap()
    {
        for (int i = 0; i < BoardView.BOARD_SIZE_X ; i++)
        {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y ; j++)
            {
                InputStream is = getContext().getResources().openRawResource(nodes[i][j].getGambar());
                nodes[i][j].createBitmap(is,(int)cellSizeX,(int) cellSizeY);
            }
        }
        //mBitmap = BitmapFactory.decodeStream(is);
        // mBitmapScaled = Bitmap.createScaledBitmap(mBitmap,(int)cellSizeX,(int)cellSizeY, false);
        //mBitmapScaled = Bitmap.createBitmap(mBitmapScaled);
        InputStream is = getContext().getResources().openRawResource(R.drawable.help);
        mBitmap = BitmapFactory.decodeStream(is);
        bmpHelpScaled = Bitmap.createScaledBitmap(mBitmap,(int)(cellSizeX*2),(int)(cellSizeX*2), false);

        is = getContext().getResources().openRawResource(R.drawable.play_32);
        mBitmap = BitmapFactory.decodeStream(is);
        bmpStart= Bitmap.createScaledBitmap(mBitmap,(int)(cellSizeX),(int)(cellSizeX), false);

        is = getContext().getResources().openRawResource(R.drawable.pause_32);
        mBitmap = BitmapFactory.decodeStream(is);
        bmpPause= Bitmap.createScaledBitmap(mBitmap,(int)(cellSizeX),(int)(cellSizeX), false);
    }


    private void drawIcon(int i, int j,Canvas canvas)
    {
        float x = cellSizeX * (i - 1) + BOARD_MARGIN;
        float y = cellSizeY * (j - 1)  + BOARD_MARGIN;
        mBitmapScaled = nodes[i-1][j-1].getBitmapScaled();
        if (mBitmapScaled!=null)
        {
            canvas.drawBitmap(mBitmapScaled, x, y, null);
        }
    }




    public void error(int i, int j) {
        animation = new FillSquareAnimation();
        FillSquareAnimation fillSquareAnimation = (FillSquareAnimation) animation;
        fillSquareAnimation.i = i;
        fillSquareAnimation.j = j;
        fillSquareAnimation.alpha = 255;

        animationHandler.sendEmptyMessage(MSG_ANIMATE);
    }







    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animation.isFinish()) {
            int action = event.getAction();

            int i = (int) ((event.getX() - BOARD_MARGIN) / cellSizeX);
            int j = (int) ((event.getY() - BOARD_MARGIN) / cellSizeY);
            boolean bHelp = false,bStart=false,bPaused=false;
            if (event.getX()>=(boardWidth-BOARD_MARGIN)+(BOARD_MARGIN*6) && (event.getY() <=(boardHeight/3)))
            {
                // Toast.makeText(getContext(), "Help" ,5000).show();
                bHelp = true;
            }

            if (event.getX()>=(boardWidth+BOARD_MARGIN) && event.getX() <=(boardWidth +BOARD_MARGIN+cellSizeX) && (event.getY() >=(boardHeight-BOARD_MARGIN)-(BOARD_MARGIN*4)))
            {
                // Toast.makeText(getContext(), "Help" ,5000).show();
                bStart = true;
            }

            if (event.getX()>=(boardWidth+(BOARD_MARGIN*6)) && event.getX() <=(boardWidth+(BOARD_MARGIN*6)+cellSizeX) && (event.getY() >=(boardHeight-BOARD_MARGIN)-(BOARD_MARGIN*4)))
            {
                // Toast.makeText(getContext(), "Help" ,5000).show();
                bPaused = true;
            }


            if ((i >= 0 && i <= (BOARD_SIZE_X - 1) && j >= 0
                    && j <= (BOARD_SIZE_Y - 1))|| (bHelp==true) || (bStart==true) || (bPaused==true))
            {
                // If user just click, then we will show painted square.
                if (action == MotionEvent.ACTION_DOWN)
                {
                    if (bHelp==true)
                    {
                        i=-1;
                        j=-1;
                    }

                    if (bStart==true)
                    {
                        i=-2;
                        j=-2;
                    }

                    if (bPaused==true)
                    {
                        i=-3;
                        j=-3;
                    }


                    moveStageListener.userClick(i, j);
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("Tinggi", String.valueOf(h));
        Log.i("Lebar", String.valueOf(w));
        boardWidth = w - ((int)(w/4));// > h ? w : h;
        boardHeight = h;
        cellSizeX = (boardWidth - GRID_SIZE * BOARD_MARGIN) / BOARD_SIZE_X;
        cellSizeY = (boardHeight - GRID_SIZE * BOARD_MARGIN) / BOARD_SIZE_Y;

        initBitmap();
        maxRadius = cellSizeX * 0.68f / 2;
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        //int d = w == 0 ? h : h == 0 ? w : w > h ? w : h;

        setMeasuredDimension(w, h);
    }


    private class AnimationMessageHandler implements Callback {
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_ANIMATE) {
                BoardView.this.invalidate();
                if (!animationHandler.hasMessages(MSG_ANIMATE)) {
                    if (animation.isFinish()) {
                        animationHandler.removeMessages(MSG_ANIMATE);
                        moveStageListener.animationComplete();
                    } else {
                        animationHandler.sendEmptyMessageDelayed(MSG_ANIMATE,
                                animation.fps());
                    }
                }
                return true;
            }
            return false;
        }
    }


    private class NullAnimation implements Animation {
        public void animate(Canvas canvas) {
            // do nothing
        }

        public boolean isFinish() {
            return true;
        }

        public boolean skip(int i, int j) {
            return false;
        }

        public int fps() {
            return 1000 / 1;
        }
    }

    private class FillSquareAnimation implements Animation
    {
        public int i;
        public int j;
        public int alpha;

        public void animate(Canvas canvas)
        {
            drawWhiteSquare(canvas, i, j, alpha);
            alpha -= 75;
            if (alpha <= 0)
                alpha = 0;
        }

        public boolean isFinish() {
            return alpha <= 0;
        }

        public boolean skip(int i, int j) {
            return false;
        }

        public int fps() {
            return 1000 / 16;
        }
    }



}
