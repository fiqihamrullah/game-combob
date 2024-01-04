package com.game.combob;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ViewPilihanActivity extends Activity
{
    ImageButton imgVBtnSkor,imgVBtnHTP,imgVBtnSuara;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpilihan);

        imgVBtnSkor =(ImageButton) findViewById(R.id.imgBtnSkor);
        imgVBtnSkor.setOnClickListener(clickToSkor);

        imgVBtnHTP =(ImageButton) findViewById(R.id.imgVBtnBantuan);
        imgVBtnHTP.setOnClickListener(clickToHTP);

        imgVBtnSuara =(ImageButton) findViewById(R.id.imgVBtnSuara);
        imgVBtnSuara.setOnClickListener(clickToSetting);
    }

    public void showChoiceDialog()
    {
        AlertDialog dialog;
        final CharSequence[] items = {"Aktif"};
        GameDatabase gd = new GameDatabase(getApplicationContext());
        boolean[] check= {gd.isSuaraAktif()};


        final ArrayList seletedItems=new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atur Suara");
        builder.setMultiChoiceItems(items, check,
                        new DialogInterface.OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected,
                                                boolean isChecked) {
                                if (isChecked) {

                                    seletedItems.add(indexSelected);
                                } else if (seletedItems.contains(indexSelected))
                                {
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        GameDatabase gd = new GameDatabase(getApplicationContext());
                        if (seletedItems.size()>0) {
                            gd.updateSetting("1");
                        }else{
                            gd.updateSetting("0");
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });

        dialog = builder.create();
        dialog.show();
    }


    private OnClickListener clickToSkor = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            startActivity(new Intent(getApplicationContext(), ViewHighScoreActivity.class));
        }
    };

    private OnClickListener clickToSetting = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            showChoiceDialog();
        }
    };



    private OnClickListener clickToHTP= new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            startActivity(new Intent(getApplicationContext(), ViewHowToPlayActivity.class));
        }
    };

}