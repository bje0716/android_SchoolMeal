package com.grapefruit.schoolmeal.datas;

public class School {

    private String kraOrgNm;
    private String zipAdres;
    private String schulCode;
    private String schulCrseScCode;
    private String schulKndScCode;

    public School setKraOrgNm(String kraOrgNm) {
        this.kraOrgNm = kraOrgNm;
        return this;
    }

    public School setZipAdres(String zipAdres) {
        this.zipAdres = zipAdres;
        return this;
    }

    public School setSchulCode(String schulCode) {
        this.schulCode = schulCode;
        return this;
    }

    public School setSchulCrseScCode(String schulCrseScCode) {
        this.schulCrseScCode = schulCrseScCode;
        return this;
    }

    public School setSchulKndScCode(String schulKndScCode) {
        this.schulKndScCode = schulKndScCode;
        return this;
    }

    public String getKraOrgNm() {
        return kraOrgNm;
    }

    public String getZipAdres() {
        return zipAdres;
    }

    public String getSchulCode() {
        return schulCode;
    }

    public String getSchulCrseScCode() {
        return schulCrseScCode;
    }

    public String getSchulKndScCode() {
        return schulKndScCode;
    }
}
