package com.grupo7.GestionInventarioAPI.model.enums;

public enum NivelStock {
    SIN_STOCK, // cuando stock_actual = 0
    BAJO, //cuando stockActual <= stockMinimo
    NORMAL, // cuando stockActual > stockMinimo
    ALTO // cuando stockActual > stockMinimo*2
}
