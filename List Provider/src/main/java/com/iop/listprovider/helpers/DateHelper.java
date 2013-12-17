package com.iop.listprovider.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rafael Iop
 *
 * Classe utilizada para manipular as datas
 *
 * Calculadora para testar/converter datas para milisegundos:
 * http://www.ruddwire.com/handy-code/date-to-millisecond-calculators
 */

public class DateHelper {

    // Retorna a data atual em ms (milisegundos)
    // Utilizado para salvar o horário em que a nota foi criada no banco de dados
    public long getHorarioAgora(){
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    // Converte uma data em ms para string
    public String toString(long data){
        SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");
        Date date = new Date(data);

        return ft.format(date);
    }
}
