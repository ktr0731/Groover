package com.syfm.groover.model.storage.databases;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2015/10/07.
 */
@RealmClass
public class StageData extends RealmObject {

    private int all;
    private int clear;
    private int fullchain;
    private int nomiss;
    private int s;
    private int ss;
    private int sss;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getClear() {
        return clear;
    }

    public void setClear(int clear) {
        this.clear = clear;
    }

    public int getFullchain() {
        return fullchain;
    }

    public void setFullchain(int fullchain) {
        this.fullchain = fullchain;
    }

    public int getNomiss() {
        return nomiss;
    }

    public void setNomiss(int nomiss) {
        this.nomiss = nomiss;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getSs() {
        return ss;
    }

    public void setSs(int ss) {
        this.ss = ss;
    }

    public int getSss() {
        return sss;
    }

    public void setSss(int sss) {
        this.sss = sss;
    }
}
