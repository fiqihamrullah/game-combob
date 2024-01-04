package com.game.combob;


import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ViewHighScoreActivity extends Activity
{
    ScoreListAdapter sclistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewhighscore);
        initComponent();
        initScore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        finish();
        return super.onTouchEvent(event);
    }

    public void initComponent()
    {
        final ListView entryListView = (ListView) findViewById(R.id.lvScore);
        sclistAdapter = new ScoreListAdapter(this, R.layout.scorelist);
        entryListView.setAdapter(sclistAdapter);
    }

    public void initScore()
    {
        GameDatabase gd = new GameDatabase(getApplicationContext());
        List<PlayerScore> listPs =  gd.GetHighScores();
        for(final PlayerScore entry : listPs)
        {
            sclistAdapter.add(entry);
        }
    }

}
