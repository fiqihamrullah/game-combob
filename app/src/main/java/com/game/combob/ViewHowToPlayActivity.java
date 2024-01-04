package com.game.combob;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHowToPlayActivity extends Activity
{
    private ImageView imgVGambar;
    private TextView tvPetunjuk;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewhtplay);


        tvPetunjuk = (TextView) findViewById(R.id.tvPetunjuk);
        String pet = "Pilih 2 icon yang sama atau cocok, lalu muncul soal ";
        pet +="\n" + "Pilih jawaban dengan benar ";
        pet +="\n" + "Level 1 harus menyelesaikan 10 soal dalam waktu 5 menit";
        pet +="\n" + "Level 2 harus menyelesaikan 15 soal dalam waktu 5 menit";
        pet +="\n" + "Level 3 harus menyelesaikan 20 soal dalam waktu 5 menit";
        pet +="\n" + "Level 4 harus menyelesaikan 25 soal dalam waktu 5 menit";
        pet +="\n" + "Level 5 harus menyelesaikan 30 soal dalam waktu 5 menit";
        pet +="\n" + "Jika mengalami kesulitan, pilih bantuan dengan klik tombol tanda tanya";
        tvPetunjuk.setText(pet);
		/*
		imgVGambar =(ImageView) findViewById(R.id.imgVPetunjuk);
		Bundle var = getIntent().getExtras();
		if (var.getString("state").equals("1"))
		{
			imgVGambar.setImageResource(R.drawable.gameinformation);

		}else	if (var.getString("state").equals("2"))  {

			imgVGambar.setImageResource(R.drawable.petunjukutama);

		} */

    }




}
