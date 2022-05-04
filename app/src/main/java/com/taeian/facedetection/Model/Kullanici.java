package com.taeian.facedetection.Model;

public class Kullanici {

    private String kullaniciIsmi,kullaniciSoyisim,kullaniciMail,kullaniciId,kullaniciSifre;

    public Kullanici(String kullaniciIsmi, String kullaniciSoyisim, String kullaniciMail,String kullaniciSifre,String kullaniciId){
        this.kullaniciIsmi=kullaniciIsmi;
        this.kullaniciSoyisim=kullaniciSoyisim;
        this.kullaniciMail=kullaniciMail;
        this.kullaniciId=kullaniciId;

        this.kullaniciSifre=kullaniciSifre;
    }

    public Kullanici(){

    }

    public String getKullaniciIsmi(){
        return kullaniciIsmi;
    }
    public void setKullaniciIsmi(String kullaniciIsmi){
        this.kullaniciIsmi=kullaniciIsmi;
    }
    public String getKullaniciSoyisim(){
        return kullaniciSoyisim;
    }
    public void setKullaniciSoyisim(String kullaniciSoyisim){
        this.kullaniciSoyisim=kullaniciSoyisim;
    }
    public String getKullaniciMail(){
        return kullaniciMail;
    }
    public void setKullaniciMail(String kullaniciMail){
        this.kullaniciMail=kullaniciMail;
    }
    public String getKullaniciId(){
        return kullaniciId;
    }
    public void setKullaniciId(String kullaniciId){
        this.kullaniciId=kullaniciId;
    }
    public String getKullaniciSifre(){
        return kullaniciSifre;
    }
    public void setKullaniciSifre(String kullaniciSifre){
        this.kullaniciId=kullaniciSifre;
    }

}
