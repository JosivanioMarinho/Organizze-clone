package com.app.organizze.activity.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual(){

        //retorna a data ataual
        Long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;

    }

    public static String mesAnoDataEscolhida(String data){

        //Método spllic para separar a string comm base em um caractere
        String retornoData[] = data.split("/");
        String dia = retornoData[0]; // retorna o dia
        String mes = retornoData[1]; //retonar o mês
        String ano = retornoData[2]; // retorna o ano

        String mesAno = mes + ano;
        return mesAno;

    }

}
