package com.example.authentication;

public class productos {
    private String codproducto;
    private String nomproducto;
    private String stockproducto;
    private String precioproducto;
    private String ventaproducto;

    public String getCodproducto() {
        return codproducto;
    }

    public void setCodproducto(String codproducto) {
        this.codproducto = codproducto;
    }

    public String getNomproducto() {
        return nomproducto;
    }

    public void setNomproducto(String nomproducto) {
        this.nomproducto = nomproducto;
    }

    public String getStockproducto() {
        return stockproducto;
    }

    public void setStockproducto(String stockproducto) {
        this.stockproducto = stockproducto;
    }

    public String getPrecioproducto() {
        return precioproducto;
    }

    public void setPrecioproducto(String precioproducto) {
        this.precioproducto = precioproducto;
    }

    public String getVentaproducto() {
        return ventaproducto;
    }

    public void setVentaproducto(String ventaproducto) {
        this.ventaproducto = ventaproducto;
    }

    public productos(String codproducto, String nomproducto, String stockproducto, String precioproducto, String ventaproducto) {
        this.codproducto = codproducto;
        this.nomproducto = nomproducto;
        this.stockproducto = stockproducto;
        this.precioproducto = precioproducto;
        this.ventaproducto = ventaproducto;
    }

    public productos() {
    }
}
