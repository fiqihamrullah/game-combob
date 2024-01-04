package com.game.combob;


import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Node
{
    public static int SOAL=1;
    public static int BUKAN_SOAL=0;

    private int tipe;
    private int gambar;
    private boolean sudahdijawab;
    private String nama;
    private String fungsi;
    private Bitmap mBitmap;
    private Bitmap mBitmapScaled;

    public Node(int tipe)
    {
        // TODO Auto-generated constructor stub
        this.tipe = tipe;
        sudahdijawab = false;
        gambar=0;
        nama = "";
        mBitmap=null;
        mBitmapScaled=null;
    }

    public void SetGambar(int gambar)
    {
        this.gambar = gambar;
    }

    public int getGambar() {
        return gambar;
    }

    public void SetSudahDijawab (boolean bstatjawab)
    {
        this.sudahdijawab= bstatjawab;
    }

    public boolean IsDijawab()
    {
        return sudahdijawab;
    }

    public void SetNama(String nama)
    {
        this.nama = nama;
    }

    public String getNama()
    {
        return nama;
    }

    public boolean IsSoal() {
        return (tipe==Node.SOAL);
    }

    public String GetFungsi()
    {
        return fungsi;
    }

    public void SetFungsi(String nmfungsi)
    {
        this.fungsi = nmfungsi;
    }

    public void createBitmap(InputStream is,int cellSizeX,int cellSizeY )
    {
        mBitmap = BitmapFactory.decodeStream(is);
        mBitmapScaled = Bitmap.createScaledBitmap(mBitmap, cellSizeX, cellSizeY, false);
    }

    public Bitmap getBitmapScaled()
    {
        return mBitmapScaled;
    }

}