package com.game.combob;

import java.util.Date;


public final class PlayerScore
{

    private final String keyname;
    private final String nilaiscore;


    public PlayerScore(String keyname,String nilaiscore)
    {
        this.keyname = keyname;
        this.nilaiscore =nilaiscore;

    }

    public String getKeyName()
    {
        return keyname;
    }

    public String getNilaiScore() {
        return nilaiscore;
    }
}