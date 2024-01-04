package com.game.combob;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.BoringLayout;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity
{
    private State[][] positions = new State[BoardView.BOARD_SIZE_X][BoardView.BOARD_SIZE_Y];
    private BoardView boardView;
    private Game game;
    private boolean isFinish = false;
    private int jumlahpilih=0;
    private int jawabanpilih=0;

    private Timer tmr;
    private boolean bHelp=false;

    private final int delayTime = 1000;
    private Handler myHandler = new Handler();

    private int helpCount;

    String nmobjek[];
    String nmfungsi[] ;

    MediaPlayer mMediaPlayer;
    boolean suaraaktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        helpCount = 5;
        setContentView(R.layout.board);

        GameDatabase gd = new GameDatabase(getApplicationContext());
        suaraaktif = gd.isSuaraAktif();

        if (suaraaktif) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.op_sanji2);
        }

        //game data
        String data[] = getResources().getStringArray(R.array.gamedata);
        nmobjek = new String[data.length];
        nmfungsi = new String[data.length];

        for(int i=0;i<data.length;i++)
        {
            String temp[] = data[i].split(":");
            nmobjek[i] = temp[0].trim();
            nmfungsi[i] = temp[1].trim();
        }


        nextLevel(1);

    }

    @Override
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        if (suaraaktif)
        {
            mMediaPlayer.stop();
        }
        super.onBackPressed();
    }

    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        if (suaraaktif)
        {
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        }
        super.onStart();
    }

    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        //	 mMediaPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (suaraaktif)
        {
            mMediaPlayer.start();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        if (suaraaktif)
        {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        super.onDestroy();
    }

    private Runnable closeSelf= new Runnable()
    {
        public void run()
        {
            finish();
        }
    };

    private Runnable gotoGameOver= new Runnable() {
        public void run() {
            startActivity(new Intent(getApplicationContext(), PopUpActivity.class).putExtra("state", "0"));
            //	GameDatabase gd = new GameDatabase(getApplicationContext());
            //	gd.SetScore(keyname, sgp.getGameScore().getTotalScore());
            myHandler.postDelayed(closeSelf, delayTime);
        }
    };

    public void nextLevel(int level)
    {
        boardView = (BoardView) findViewById(R.id.boardView);
        boardView.setBackgroundColor(Color.BLACK);

        boardView.setFocusable(true);
        boardView.setFocusableInTouchMode(true);
        boardView.setMoveStageListener(new CellSelected());

        // Initialize positions.
        game = new Game(level);

        //Game.setUnSelectedValues( positions);
        game.setEmptyValues(positions);
        game.CreateNodes(nmobjek,nmfungsi);

        boardView.setNTargetSoal(game.getTargetSoal());
        setStatus();
        boardView.setPositions(positions);
        boardView.setNodes(game.getNodes());

        if (level>1) {
            boardView.initBitmap();
        }

        tmr = new Timer();
        tmr.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (!boardView.IsPaused() && !(game.IsStop()))
                        {
                            game.decreaseTime();
                        }

                        if (game.getCurrentTime()==-1 && !(game.IsStop()))
                        {
                            game.Stop();
                            myHandler.postDelayed(gotoGameOver, delayTime);
                            tmr.cancel();
                            // Toast.makeText(getApplicationContext(), "Game Over", 5000).show();

                        }else {
                            boardView.SetWaktu(game.getCurrentTime());
                            boardView.invalidate();
                        }
                    }
                });

            }
        }, 0, 1000);

    }

    public void showDialogSingleChoice(String title,String jawaban[]){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //alert.setIconAttribute(android.R.attr.alertDialogIcon);
        alert.setTitle(title +  "::.");
        alert.setSingleChoiceItems(jawaban, 0, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                jawabanpilih = whichButton;
            }
        });
        alert.setPositiveButton("Jawab", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                //  Log.d("Selected",String.valueOf(whichButton));

                if (game.CheckJawaban(jawabanpilih))
                {
                    game.emptySelectedPair(positions);
                    setStatus();
                    boardView.invalidate();
                    jumlahpilih=0;
                    if (game.getProgressSoal()==game.getTargetSoal())
                    {
                        Bundle extras = new Bundle();
                        if (game.getCurrentlevel()<5)
                        {

                            extras.putString("state", "1");
                            startActivity(new Intent(getApplicationContext(), PopUpActivity.class).putExtras(extras));
                            GameDatabase gdb  = new GameDatabase(getApplicationContext());
                            gdb.CreateTableScore();
                            gdb.SetScore("Level " + String.valueOf(game.getCurrentlevel()), game.getCurrentTime());
                            int level =game.getCurrentlevel();
                            nextLevel(++level);
                        }else {
                            extras.putString("state", "2");
                            startActivity(new Intent(getApplicationContext(), PopUpActivity.class).putExtras(extras));
                            GameDatabase gdb  = new GameDatabase(getApplicationContext());
                            gdb.CreateTableScore();
                            gdb.SetScore("Level " + String.valueOf(game.getCurrentlevel()), game.getCurrentTime());
                        }
                    }
                    //Toast.makeText(getApplicationContext(), "Anda Menjawab Benar", 5000).show();
                }else{
                    //Acak Kembali
                    game.AcakPosisi(positions);
                    boardView.invalidate();
                    game.setUnSelectedValues(positions);
                    jumlahpilih=0;
                    Toast.makeText(getApplicationContext(), "Anda Menjawab Salah", 5000).show();
                }
                /* User clicked on a radio button do some stuff */

            }
        });

        alert.show();

    }

    private void setStatus()
    {
        boardView.SetCurLevel(game.getCurrentlevel());
        boardView.SetWaktu(game.getCurrentTime());
        boardView.SetProgressSoal(game.getProgressSoal());
        boardView.SetHelpCount(helpCount);
    }


    private void showDialogPertanyaan() {
        if( game.CheckPair(positions))
        {

            Random rand = new Random();
            int jj = rand.nextInt(2);
            if (jj==0) { //Nama Objek

                showDialogSingleChoice("Nama Objek ?",game.getRandomJawabanNama());
            }else {  //Fungsi
                showDialogSingleChoice("Fungsi Objek ?",game.getRandomJawabanFungsi());
            }

        }else{
            // Toast.makeText(getApplicationContext(), "Tidak Dapat", 5000).show();

            game.setUnSelectedValues(positions);
            jumlahpilih=0;
        }
    }


    private class CellSelected implements BoardView.MoveStageListener {

        public void userClick(int i, int j)
        {
            if (i==-1 && j==-1)
            {
                ///Toast.makeText(getApplicationContext(),"Help Sesungguhnya", 5000).show();

                if (helpCount!=0)
                {
                    game.breadthFirstSearch(positions);

                    bHelp = true;
                    helpCount--;
                    setStatus();
                    boardView.invalidate();
                }

                //	showDialogPertanyaan();
                return;
            }


            if (i==-2 && j==-2)
            {
                // Toast.makeText(getApplicationContext(),"Start Sesungguhnya", 5000).show();
                boardView.reStartGame();
                boardView.invalidate();


                return;
            }

            if (i==-3 && j==-3)
            {
                boardView.Paused();
                //  Toast.makeText(getApplicationContext(),"Paused Sesungguhnya", 5000).show();

                boardView.invalidate();


                return;
            }


            if (bHelp==true && positions[i][j]==State.SELECTED) {
                showDialogPertanyaan();
                bHelp = false;
                return ;
            }
            boardView.error(i, j);
            if (positions[i][j]==State.SELECTED)
            {
                jumlahpilih--;
                positions[i][j] = State.NOTSELECTED;

            }else{

                if (positions[i][j]!=State.EMPTY)
                {
                    jumlahpilih++;
                    positions[i][j] = State.SELECTED;
                    // Toast.makeText(getApplicationContext(),game.getNodes()[i][j].GetFungsi(), 5000).show();
                }
                if (jumlahpilih==2)
                {
                    showDialogPertanyaan();

                }
            }



        }


        public void animationComplete() {


        }





    }


}
