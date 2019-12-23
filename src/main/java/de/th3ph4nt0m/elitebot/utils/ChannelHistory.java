package de.th3ph4nt0m.elitebot.utils;

public class ChannelHistory {

    int channels = 0;

    public ChannelHistory(){

    }

    public void addChannel(){
        channels++;
    }

    public void rmvChannel(){
        if( channels > 0)
        channels--;
    }

    public int isChannelHopping(){
        if(channels == 8){
            return  2;
        }if(channels >= 10){
            return  1;
        }else{
            return 0;
        }
    }
}
