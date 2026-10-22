package com.grupo7.GestionInventarioAPI.model.enums;

public enum NivelStock {
    SIN_STOCK, // cuando stock_actual = 0
    BAJO, //cuando stockActual <= stockMinimo
    NORMAL, // cuando stockActual > stockMinimo
    ALTO; // cuando stockActual > stockMinimo*2

    public static NivelStock calcularNivelStock(Integer stockActual, Integer stockMinimo){
        if(stockActual == null || stockActual ==0){
            return SIN_STOCK;
        }
        if(stockActual <= stockMinimo){
            return BAJO;
        }
        if(stockActual > stockMinimo * 2){
            return ALTO;
        }
        return NORMAL;
    }
}
