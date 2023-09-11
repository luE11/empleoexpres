package pra.lue11.empleoexpres.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * @author luE11 on 4/09/23
 */
public class LocalDateUtils {

    public static String localDateTimeAsRecentTime(LocalDateTime date){
        Period p = Period.between(date.toLocalDate(), LocalDate.now());
        Duration d = Duration.between(date, LocalDateTime.now());
        StringBuilder dateDiff = new StringBuilder();
        dateDiff.append("Hace ");
        if(p.getYears()>0)
            dateDiff.append(p.getYears()).append(" años");
        else if(p.getMonths()>0)
            dateDiff.append(p.getMonths()).append(" meses");
        else if(p.getDays()>0)
            dateDiff.append(p.getDays()).append(" días");
        else if(d.getSeconds()>=60)
            if(d.getSeconds()>=(60*60))
                dateDiff.append(Math.round((float) d.getSeconds() / (60*60))).append(" horas");
            else
                dateDiff.append(Math.round((float) d.getSeconds() / 60)).append(" minutos");
        else
            dateDiff.append(d.getSeconds()).append(" segundos");
        return dateDiff.toString();
    }
}
