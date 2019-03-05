package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.Double;
import java.lang.String;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view) {
        new DownloadUpdate().execute();
        //Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();


    }


    private class DownloadUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://t.weather.sojson.com/api/weather/city/101040100";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    Log.d("TAG", line);
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String temperature) {
            //Update the temperature displayed
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowtime=df.format(new Date());
            String nowyear=nowtime.substring(0, 4);
            String nowmonth=nowtime.substring(5,7);
            String nowday=nowtime.substring(8,10);
            int ne1=Integer.valueOf(nowday)+1;

            int ne2=ne1+1;
            int ne3=ne1+2;
            int ne4=ne1+3;
            String next1=String.valueOf(ne1);
            String next2=String.valueOf(ne2);
            String next3=String.valueOf(ne3);
            String next4=String.valueOf(ne4);
            String nowtime1=nowtime.substring(0,10);

            ((TextView) findViewById(R.id.tv_date)).setText(nowyear+"/"+nowmonth+"/"+nowday);

            int time=temperature.indexOf(nowtime1);
            int temp=temperature.indexOf("wendu",time);//实际温度
            int today=temperature.indexOf("date\":\""+nowday);//今天
            int next1day=temperature.indexOf("date\":\""+"0"+next1);
            int next2day=temperature.indexOf("date\":\""+"0"+next2);
            int next3day=temperature.indexOf("date\":\""+"0"+next3);
            int next4day=temperature.indexOf("date\":\""+"0"+next4);


            int tempmin=temperature.indexOf("低温",today);//最小温度
            int tempmax=temperature.indexOf("高温",today);//最大温度
            //int pressure=temperature.indexOf("pressure",time);
            String nowt=temperature.substring(temp+8,temp+10);

            String mint=temperature.substring(tempmin+3,tempmin+9);
            String maxt=temperature.substring(tempmax+3,tempmax+9);

            //Double nowte=Double.valueOf(nowt)-273.15;
            //Double minte=Double.valueOf(mint);
            //Double maxte=Double.valueOf(maxt);

            //设定输出温度小数位
           // DecimalFormat ddf = new DecimalFormat( "0.0");


            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(nowt);
            ((TextView) findViewById(R.id.textView2)).setText(mint+"~"+maxt);

            //设置星期几
            int mon=temperature.indexOf("week",today);
            String month=temperature.substring(mon+7,mon+10);
            switch (month){
                case "星期一":month="Monday";
                    ((TextView) findViewById(R.id.nextone)).setText("Tuesday");
                    ((TextView) findViewById(R.id.nexttwo)).setText("Wednesday");
                    ((TextView) findViewById(R.id.nextthree)).setText("Thursday");
                    ((TextView) findViewById(R.id.nextfoure)).setText("Friday");
                    break;
                case "星期二":month="Tuesday";
                    ((TextView) findViewById(R.id.nextone)).setText("Wednesday");
                    ((TextView) findViewById(R.id.nexttwo)).setText("Thursday");
                    ((TextView) findViewById(R.id.nextthree)).setText("Friday");
                    ((TextView) findViewById(R.id.nextfoure)).setText("Saturday");
                    break;
                case "星期三":month="Wednesday";
                    ((TextView) findViewById(R.id.nextone)).setText("Thursday");
                    ((TextView) findViewById(R.id.nexttwo)).setText("Friday");
                    ((TextView) findViewById(R.id.nextthree)).setText("Saturday");
                    ((TextView) findViewById(R.id.nextfoure)).setText("Sunday");
                    break;
                case "星期四":month="Thursday";
                    ((TextView) findViewById(R.id.nextone)).setText("Friday");
                    ((TextView) findViewById(R.id.nexttwo)).setText("Saturday");
                    ((TextView) findViewById(R.id.nextthree)).setText("Sunday");
                    ((TextView) findViewById(R.id.nextfoure)).setText("Monday");
                    break;
                case "星期五":month="Friday";
                    ((TextView) findViewById(R.id.nextone)).setText("Saturday");
                    ((TextView) findViewById(R.id.nexttwo)).setText("Sunday");
                    ((TextView) findViewById(R.id.nextthree)).setText("Monday");
                    ((TextView) findViewById(R.id.nextfoure)).setText("Tuesday");
                    break;
                case "星期六":month="Saturday";
                    ((TextView) findViewById(R.id.nextone)).setText("Sunday");
                    ((TextView) findViewById(R.id.nexttwo)).setText("Monday");
                    ((TextView) findViewById(R.id.nextthree)).setText("Tuesday");
                    ((TextView) findViewById(R.id.nextfoure)).setText("Wednesday");
                    break;
                case "星期日":month="Sunday";
                    ((TextView) findViewById(R.id.nextone)).setText("Monday");
                    ((TextView) findViewById(R.id.nexttwo)).setText("Tuesday");
                    ((TextView) findViewById(R.id.nextthree)).setText("Wednesday");
                    ((TextView) findViewById(R.id.nextfoure)).setText("Thursday");
                    break;
                    default:
                        break;
            }
            ((TextView) findViewById(R.id.todayly)).setText(month);

            //设置图片
            int typ=temperature.indexOf("type",today);
            int typ1=temperature.indexOf("notice",today);
            String type=temperature.substring(typ+7,typ1-3);
            //设置提示语
            int tempmin0=temperature.indexOf("notice",today);
            int tempmax0=temperature.indexOf("\"}",today);
            String mint0=temperature.substring(tempmin0+9,tempmax0);
            ((TextView) findViewById(R.id.tv_news)).setText(mint0);

            switch(type){
                case "多云":((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.partly_sunny_small);

                case "小雨":((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.rainy_small);

                break;
                case "阴":((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.windy_small);
                break;
                case "晴":((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.sunny_small);
                break;
                case "大雨":((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.rainy_up);
                break;
                default:
                    break;
            }
            int ty1=temperature.indexOf("type",next1day);
            int typ11=temperature.indexOf("notice",next1day);
            String type1=temperature.substring(ty1+7,typ11-3);
            int tempmin1=temperature.indexOf("低温",next1day);//最小温度
            int tempmax1=temperature.indexOf("高温",next1day);//最大温度
            String mint1=temperature.substring(tempmin1+3,tempmin1+9);
            String maxt1=temperature.substring(tempmax1+3,tempmax1+9);
            ((TextView) findViewById(R.id.next1)).setText(mint1+"~"+maxt1);
            switch(type1){
                case "多云":((ImageView) findViewById(R.id.p1)).setImageResource(R.drawable.partly_sunny_small);
                    break;
                case "小雨":((ImageView) findViewById(R.id.p1)).setImageResource(R.drawable.rainy_small);
                    break;
                case "阴":((ImageView) findViewById(R.id.p1)).setImageResource(R.drawable.windy_small);
                    break;
                case "晴":((ImageView) findViewById(R.id.p1)).setImageResource(R.drawable.sunny_small);
                    break;
                case "大雨":((ImageView) findViewById(R.id.p1)).setImageResource(R.drawable.rainy_up);
                    break;
                default:
                    break;
            }

            int ty2=temperature.indexOf("type",next2day);
            int typ22=temperature.indexOf("notice",next2day);
            String type2=temperature.substring(ty2+7,typ22-3);
            int tempmin2=temperature.indexOf("低温",next2day);//最小温度
            int tempmax2=temperature.indexOf("高温",next2day);//最大温度
            String mint2=temperature.substring(tempmin2+3,tempmin2+9);
            String maxt2=temperature.substring(tempmax2+3,tempmax2+9);
            ((TextView) findViewById(R.id.next2)).setText(mint2+"~"+maxt2);
            switch(type2){
                case "多云":((ImageView) findViewById(R.id.p2)).setImageResource(R.drawable.partly_sunny_small);
                    break;
                case "小雨":((ImageView) findViewById(R.id.p2)).setImageResource(R.drawable.rainy_small);
                    break;
                case "阴":((ImageView) findViewById(R.id.p2)).setImageResource(R.drawable.windy_small);
                    break;
                case "晴":((ImageView) findViewById(R.id.p2)).setImageResource(R.drawable.sunny_small);
                    break;
                case "大雨":((ImageView) findViewById(R.id.p2)).setImageResource(R.drawable.rainy_up);
                    break;
                default:
                    break;
            }

            int ty3=temperature.indexOf("type",next3day);
            int typ33=temperature.indexOf("notice",next3day);
            String type3=temperature.substring(ty3+7,typ33-3);
            int tempmin3=temperature.indexOf("低温",next3day);//最小温度
            int tempmax3=temperature.indexOf("高温",next3day);//最大温度
            String mint3=temperature.substring(tempmin3+3,tempmin3+9);
            String maxt3=temperature.substring(tempmax3+3,tempmax3+9);
            ((TextView) findViewById(R.id.next3)).setText(mint3+"~"+maxt3);
            switch(type3){
                case "多云":((ImageView) findViewById(R.id.p3)).setImageResource(R.drawable.partly_sunny_small);
                    break;
                case "小雨":((ImageView) findViewById(R.id.p3)).setImageResource(R.drawable.rainy_small);
                    break;
                case "阴":((ImageView) findViewById(R.id.p3)).setImageResource(R.drawable.windy_small);
                    break;
                case "晴":((ImageView) findViewById(R.id.p3)).setImageResource(R.drawable.sunny_small);
                    break;
                case "大雨":((ImageView) findViewById(R.id.p3)).setImageResource(R.drawable.rainy_up);
                    break;
                default:
                    break;
            }

            int ty4=temperature.indexOf("type",next4day);
            int typ44=temperature.indexOf("notice",next4day);
            String type4=temperature.substring(ty4+7,typ44-3);
            int tempmin4=temperature.indexOf("低温",next4day);//最小温度
            int tempmax4=temperature.indexOf("高温",next4day);//最大温度
            String mint4=temperature.substring(tempmin4+3,tempmin4+9);
            String maxt4=temperature.substring(tempmax4+3,tempmax4+9);
            ((TextView) findViewById(R.id.next4)).setText(mint4+"~"+maxt4);
            switch(type4){
                case "多云":((ImageView) findViewById(R.id.p4)).setImageResource(R.drawable.partly_sunny_small);
                    break;
                case "小雨":((ImageView) findViewById(R.id.p4)).setImageResource(R.drawable.rainy_small);
                    break;
                case "阴":((ImageView) findViewById(R.id.p4)).setImageResource(R.drawable.windy_small);
                    break;
                case "晴":((ImageView) findViewById(R.id.p4)).setImageResource(R.drawable.sunny_small);
                    break;
                case "大雨":((ImageView) findViewById(R.id.p4)).setImageResource(R.drawable.rainy_up);
                    break;
                default:
                    break;
            }
            Toast.makeText(getApplicationContext(),"刷新成功！",Toast.LENGTH_LONG).show();
        }
    }
}
