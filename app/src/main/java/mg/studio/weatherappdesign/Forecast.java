package mg.studio.weatherappdesign;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mg.studio.weatherappdesign.MainActivity.CLOUDY;
import static mg.studio.weatherappdesign.MainActivity.RAINY;
import static mg.studio.weatherappdesign.MainActivity.SUNNY;
import static mg.studio.weatherappdesign.MainActivity.WINDY;

/**
 * Created by lining on 2018/3/20.
 */

public class Forecast {
    private String mlowest_Temperature;
    private String mhighest_Temperature;
    private String mType;



    public Forecast(String mlowest_Temperature, String mhighest_Temperature, String mType) {
        this.mlowest_Temperature = mlowest_Temperature;
        this.mhighest_Temperature = mhighest_Temperature;
        this.mType = mType;
        Log.i("lt","------------>"+mlowest_Temperature);
        Log.i("ht","------------>"+mhighest_Temperature);
        Log.i("type","----------->"+mType);

    }

    public int getTemperature(){
        String lt = null,
                ht = null;
        int temprerature;
        Matcher matcher = Pattern.compile("\\d+.\\d+").matcher(mlowest_Temperature);
        while(matcher.find())
            lt = matcher.group().trim();

        matcher = Pattern.compile("\\d+.\\d+").matcher(mhighest_Temperature);
        while(matcher.find())
            ht = matcher.group().trim();
        temprerature = (int)(Float.valueOf(lt)+Float.valueOf(ht))/2;
        return temprerature;
    }

    public int getType(){
        Pattern pattern = Pattern.compile("[雨]|[雷]");
        Matcher matcher = pattern.matcher(mType);
        if (matcher.find())
            return RAINY;
        pattern = Pattern.compile("[风]|[云]");
        matcher = pattern.matcher(mType);
        if (matcher.find())
            return WINDY;
        pattern = Pattern.compile("[阴]");
        matcher = pattern.matcher(mType);
        if (matcher.find())
            return CLOUDY;
        return SUNNY;
    }
}
