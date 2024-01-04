package com.game.combob;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.res.Resources;
import android.location.GpsStatus.NmeaListener;

public class Game
{
    public static int MODE_SOAL_NAMA =0;
    public static int MODE_SOAL_FUNGSI =1;

    private int gambar[] = new int[]
            {
                    R.drawable.three3max,
                    R.drawable.android,
                    R.drawable.avira,
                    R.drawable.barcode,
                    R.drawable.barcodereader,
                    R.drawable.baterai,
                    R.drawable.bluetooth,
                    R.drawable.cardreader,
                    R.drawable.cctv,
                    R.drawable.cddrive,
                    R.drawable.cdrom,
                    R.drawable.cdrw,
                    R.drawable.chip,
                    R.drawable.cpu,
                    R.drawable.dvddrive,
                    R.drawable.dvdrom,
                    R.drawable.dvdrw,
                    R.drawable.earphone,
                    R.drawable.fingerscan,
                    R.drawable.firefox,
                    R.drawable.flash,
                    R.drawable.flashdisk,
                    R.drawable.floppydisk,
                    R.drawable.gamepad,
                    R.drawable.handsfree,
                    R.drawable.hardisk,
                    R.drawable.headphone,
                    R.drawable.headset,
                    R.drawable.jack,
                    R.drawable.kabeldata,
                    R.drawable.kabelethernet,
                    R.drawable.keyboard,
                    R.drawable.kipaslaptop,
                    R.drawable.kursor,
                    R.drawable.laptop,
                    R.drawable.laptopcharger,
                    R.drawable.lightpen,
                    R.drawable.linux,
                    R.drawable.mac,
                    R.drawable.mic,
                    R.drawable.microsd,
                    R.drawable.microsdadapter,
                    R.drawable.microsoftaccess,
                    R.drawable.microsoftexcel,
                    R.drawable.microsoftonenote,
                    R.drawable.microsoftoutlook,
                    R.drawable.microsoftpowerpoint,
                    R.drawable.microsoftsharepoint,
                    R.drawable.microsoftword,
                    R.drawable.modem,
                    R.drawable.monitortouchscreen,
                    R.drawable.monitor,
                    R.drawable.motherboard,
                    R.drawable.mouse,
                    R.drawable.msdos,
                    R.drawable.ms_visual_studio,
                    R.drawable.notepad,
                    R.drawable.operamini,
                    R.drawable.paint,
                    R.drawable.pdf,
                    R.drawable.photoshop,
                    R.drawable.phpdesigner,
                    R.drawable.powerbank,
                    R.drawable.powersupply,
                    R.drawable.printer,
                    R.drawable.processor,
                    R.drawable.projector,
                    R.drawable.ram,
                    R.drawable.recyclebin,
                    R.drawable.rom,
                    R.drawable.scanner,
                    R.drawable.snippingtool,
                    R.drawable.soundcard,
                    R.drawable.speaker,
                    R.drawable.sqlserver,
                    R.drawable.ubuntu,
                    R.drawable.vga,
                    R.drawable.virus,
                    R.drawable.webcam,
                    R.drawable.winamp,
                    R.drawable.windows,
                    R.drawable.wmp,  //Windows Media Player
                    R.drawable.winrar,
                    R.drawable.wireless};


    private int xpertama=-1,ypertama =-1;
    private int xkedua=-1,ykedua =-1;
    private int currentlevel;

    private int maxsoal;
    private int progressoal;
    private int targetsoal;

    private int currentTime;
    private int totTime;

    private String waktu;

    private String nmobjek[];
    private String nmfungsi[];
    private String jawabnamarandom[];
    private String jawabfungsirandom[];
    private Node[][] nodes;

    private int mode;

    private String nama;
    private String fungsi;

    private boolean bstop;

    public Game(int level)
    {
        // TODO Auto-generated constructor stub
        currentlevel = level;
        progressoal = 0;
        if (level==1)
        {
            maxsoal = 15;
            targetsoal =10;
            totTime = 300;
        }else if (level==2)
        {
            maxsoal = 15;
            targetsoal =15;
            totTime = 300;
        }else if (level==3)
        {
            maxsoal = 20;
            targetsoal =20;
            totTime = 300;
        }else if (level==4)
        {
            maxsoal = 25;
            targetsoal =25;
            totTime = 300;
        }else if (level==5)
        {
            maxsoal = 30;
            targetsoal =30;
            totTime = 300;
        }

        bstop = false;
        currentTime = totTime;
    }



    public void Stop(){
        bstop =true;
    }

    public boolean IsStop()
    {
        return bstop;
    }

    public int getCurrentlevel() {
        return currentlevel;
    }



    public int getTargetSoal()
    {
        return targetsoal;
    }

    public int getProgressSoal()
    {
        return 	progressoal;
    }

    public void decreaseTime()
    {
        currentTime--;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    private void resetIndex(int idxs[],int idxnodes[])
    {



        for(int i=0;i<idxs.length;i++)
        {
            idxs[i]=-1;
            idxnodes[i] = -1;
        }
    }

    private void resetNodes()
    {
        for (int i = 0; i < BoardView.BOARD_SIZE_X ; i++)
        {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y ; j++)
            {
                nodes[i][j] = null;
            }
        }
    }

    private boolean checkAda(int idxs[],int idxcheck)
    {
        boolean ada= false;
        for(int i=0;i<idxs.length;i++)
        {
            if (idxs[i]==idxcheck)
            {
                ada = true;
                break;
            }
        }
        return ada;
    }


    public void breadthFirstSearch(State[][] positions)
    {
        boolean b= false;
        xpertama=-1;ypertama =-1;
        xkedua=-1;ykedua =-1;
        boolean dapatpair=false;
        String nmapair="";
        for (int i = 0; i < BoardView.BOARD_SIZE_X ; i++)
        {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y ; j++)
            {
                if (nodes[i][j].IsSoal() && (nodes[i][j].IsDijawab()==false))
                {
                    if (xpertama==-1 && ypertama==-1)
                    {
                        nmapair = nodes[i][j].getNama();
                        xpertama = i;ypertama=j;
                        positions[i][j]=State.SELECTED;
                    }else
                    {
                        if (nodes[i][j].getNama().equals(nmapair))
                        {
                            xkedua = i;ykedua=j;
                            dapatpair = true;
                            positions[i][j]=State.SELECTED;
                            break;
                        }
                    }
                }
            }
            if (dapatpair) break;
        }

        if (nodes[xpertama][ypertama].getNama().equals(nodes[xkedua][ykedua].getNama()))
        {
            b=true;
            nama = nodes[xpertama][ypertama].getNama();
            fungsi = nodes[xpertama][ypertama].GetFungsi();
            jawabfungsirandom = new String[4];
            jawabnamarandom = new String[4];
            Random rand = new Random();
            //Jawaban Nama Objek
            for(int i=0;i<jawabnamarandom.length;i++)
            {
                int idxg = rand.nextInt(nmobjek.length);
                String nm = nmobjek[idxg];
                while (nm.equals(nama))
                {
                    idxg = rand.nextInt(nmobjek.length);
                    nm = nmobjek[idxg];
                }
                jawabnamarandom[i]=nm;
            }
            int idxj = rand.nextInt(4);
            jawabnamarandom[idxj]=nama;
            //Jawaban Nama Fungsi
            for(int i=0;i<jawabfungsirandom.length;i++)
            {
                int idxg = rand.nextInt(nmfungsi.length);
                String nm = nmfungsi[idxg];
                while (nm.equals(fungsi))
                {
                    idxg = rand.nextInt(nmfungsi.length);
                    nm = nmfungsi[idxg];
                }
                jawabfungsirandom[i]=nm;
            }
            idxj = rand.nextInt(4);
            jawabfungsirandom[idxj]=fungsi;

        }


    }



    public void CreateNodes(String[] namaobjek,String[] fungsiobjek)
    {
        int idxgambar[] = new int[BoardView.BOARD_SIZE_X*BoardView.BOARD_SIZE_Y];
        int idxnodes[] = new int[BoardView.BOARD_SIZE_X*BoardView.BOARD_SIZE_Y];

        this.nmobjek = namaobjek;
        this.nmfungsi = fungsiobjek;

        resetIndex(idxgambar,idxnodes);
        nodes= new Node[BoardView.BOARD_SIZE_X][BoardView.BOARD_SIZE_Y];
        resetNodes();
        int byksoal = 15;
        Random rand = new Random();
        int maxrandom = gambar.length;

        Node[] nodetemps = new Node[BoardView.BOARD_SIZE_X*BoardView.BOARD_SIZE_Y];
        //Membuat Soal
        for(int i=0;i<byksoal;i++)
        {
            Node node = new Node(Node.SOAL);
            int idxg  = rand.nextInt(maxrandom);
            while (checkAda(idxgambar, idxg) ==true)
            {
                idxg  = rand.nextInt(maxrandom);
            }
            idxgambar[i] = idxg;
            idxgambar[i+byksoal] = idxg;
            node.SetGambar(gambar[idxg]);
            node.SetNama(namaobjek[idxg]);
            node.SetFungsi(fungsiobjek[idxg]);
            nodetemps[i] = node;
            nodetemps[i+byksoal] = node;
        }

        for(int i=byksoal*2;i<nodetemps.length;i++)
        {
            Node node = new Node(Node.BUKAN_SOAL);
            int idxg  = rand.nextInt(maxrandom);
            while (checkAda(idxgambar, idxg) ==true)
            {
                idxg  = rand.nextInt(maxrandom);
            }
            idxgambar[i] = idxg;
            node.SetGambar(gambar[idxg]);
            node.SetNama(namaobjek[idxg]);
            node.SetFungsi(fungsiobjek[idxg]);
            nodetemps[i] = node;
        }

        int idxn=0;
        for (int i = 0; i < BoardView.BOARD_SIZE_X ; i++)
        {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y ; j++)
            {
                int idxnode  = rand.nextInt(nodetemps.length);
                while (checkAda(idxnodes, idxnode) ==true)
                {
                    idxnode  = rand.nextInt(nodetemps.length);
                }
                idxnodes[idxn] = idxnode;
                nodes[i][j] = nodetemps[idxnode];
                idxn++;
            }
        }
    }

    public void AcakPosisi(State[][] posStates)
    {
        Random rand = new Random();
        int idxgambar[] = new int[BoardView.BOARD_SIZE_X*BoardView.BOARD_SIZE_Y];
        int idxnodes[] = new int[BoardView.BOARD_SIZE_X*BoardView.BOARD_SIZE_Y];

        resetIndex(idxgambar,idxnodes);

        Node[] nodetemps = new Node[BoardView.BOARD_SIZE_X*BoardView.BOARD_SIZE_Y];
        Node[][] nodefinaltemp= new Node[BoardView.BOARD_SIZE_X][BoardView.BOARD_SIZE_Y];
        int idx=0;
        for (int i = 0; i < BoardView.BOARD_SIZE_X ; i++)
        {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y ; j++)
            {
                if (posStates[i][j]!=State.EMPTY)
                {
                    nodetemps[idx] = nodes[i][j];

                }else {
                    nodetemps[idx] = null;
                }
                idx++;
            }
        }


        int idxn=0;
        for (int i = 0; i < BoardView.BOARD_SIZE_X ; i++)
        {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y ; j++)
            {

                if (posStates[i][j]!=State.EMPTY)
                {
                    int idxnode  = rand.nextInt(nodetemps.length);
                    while ((checkAda(idxnodes, idxnode) ==true) || (nodetemps[idxnode]==null))
                    {
                        idxnode  = rand.nextInt(nodetemps.length);
                    }
                    idxnodes[idxn] = idxnode;
                    nodefinaltemp[i][j] = nodetemps[idxnode];
                    idxn++;
                }else  {
                    nodefinaltemp[i][j] = nodes[i][j];
                }
            }
        }

        for (int i = 0; i < BoardView.BOARD_SIZE_X ; i++)
        {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y ; j++)
            {
                nodes[i][j] = nodefinaltemp[i][j];
            }
        }


    }


    public void SetModeSoal(int mode)
    {
        this.mode = mode;
    }

    public void emptySelectedPair(State[][] posStates)
    {
        posStates[xpertama][ypertama] = State.EMPTY;
        posStates[xkedua][ykedua] = State.EMPTY;

        nodes[xpertama][ypertama].SetSudahDijawab(true);
        nodes[xkedua][ykedua].SetSudahDijawab(true);

        xpertama=-1;ypertama =-1;
        xkedua=-1;ykedua =-1;

        progressoal++;
    }

    public boolean CheckJawaban(int selected)
    {
        boolean benar =false;
        if (mode==MODE_SOAL_NAMA)
        {
            if (nama.equals(jawabnamarandom[selected]))
            {
                benar = true;
            }
        }else if (mode==MODE_SOAL_FUNGSI)
        {
            if (fungsi.equals(jawabfungsirandom[selected]))
            {
                benar = true;
            }
        }
        return benar;

    }

    public boolean CheckPair(State[][] positionstate)
    {
        boolean b= false;
        xpertama=-1;ypertama =-1;
        xkedua=-1;ykedua =-1;
        boolean dapatpair=false;
        for (int i = 0; i < BoardView.BOARD_SIZE_X ; i++)
        {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y ; j++)
            {
                if (positionstate[i][j]==State.SELECTED)
                {
                    if (xpertama==-1 && ypertama==-1)
                    {
                        xpertama = i;ypertama=j;
                    }else {
                        xkedua = i;ykedua=j;
                        dapatpair = true;
                        break;
                    }
                }
            }
            if (dapatpair) break;
        }

        if (nodes[xpertama][ypertama].getNama().equals(nodes[xkedua][ykedua].getNama()))
        {
            b=true;
            nama = nodes[xpertama][ypertama].getNama();
            fungsi = nodes[xpertama][ypertama].GetFungsi();
            jawabfungsirandom = new String[4];
            jawabnamarandom = new String[4];
            Random rand = new Random();
            //Jawaban Nama Objek
            for(int i=0;i<jawabnamarandom.length;i++)
            {
                int idxg = rand.nextInt(nmobjek.length);
                String nm = nmobjek[idxg];
                while (nm.equals(nama))
                {
                    idxg = rand.nextInt(nmobjek.length);
                    nm = nmobjek[idxg];
                }
                jawabnamarandom[i]=nm;
            }
            int idxj = rand.nextInt(4);
            jawabnamarandom[idxj]=nama;
            //Jawaban Nama Fungsi
            for(int i=0;i<jawabfungsirandom.length;i++)
            {
                int idxg = rand.nextInt(nmfungsi.length);
                String nm = nmfungsi[idxg];
                while (nm.equals(fungsi))
                {
                    idxg = rand.nextInt(nmfungsi.length);
                    nm = nmfungsi[idxg];
                }
                jawabfungsirandom[i]=nm;
            }
            idxj = rand.nextInt(4);
            jawabfungsirandom[idxj]=fungsi;

        }

        return b;
    }

    public Node[][] getNodes()
    {
        return nodes;
    }


    public String[] getRandomJawabanNama()
    {
        mode= MODE_SOAL_NAMA;
        return jawabnamarandom;
    }

    public String[] getRandomJawabanFungsi()
    {
        mode= MODE_SOAL_FUNGSI;
        return jawabfungsirandom;
    }




    public static void setUnSelectedValues(State[][] positions) {
        for (int i = 0; i < BoardView.BOARD_SIZE_X; i++) {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y; j++) {
                if (positions[i][j]!=State.EMPTY)
                {
                    positions[i][j] = State.NOTSELECTED;
                }
            }
        }
    }

    public static void setEmptyValues(State[][] positions)
    {
        for (int i = 0; i < BoardView.BOARD_SIZE_X; i++) {
            for (int j = 0; j < BoardView.BOARD_SIZE_Y; j++) {
                positions[i][j] = State.NOTSELECTED;

            }
        }
    }


    private State[][] copy(State[][] positions) {
        State[][] positionsCopy = new State[positions.length][positions.length];

        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions.length; j++) {
                positionsCopy[i][j] = positions[i][j];
            }
        }

        return positionsCopy;
    }


}