package com.example.bg.examplefirebaseui;

public class Shenimet
{
    private String titulli;
    private String pershkrimi;
    private int prioriteti;

    public Shenimet()
    {

    }

    public Shenimet(String titulli, String pershkrimi, int prioriteti) {
        this.titulli = titulli;
        this.pershkrimi = pershkrimi;
        this.prioriteti = prioriteti;
    }

    public String getTitulli() {
        return titulli;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public int getPrioriteti() {
        return prioriteti;
    }

}
