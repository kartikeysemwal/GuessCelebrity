package com.example.guesscelebrityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    List<String> list_website_image = new ArrayList();
    List<String> list_website_name = new ArrayList();
    int i = 0, n = 0;
    int setIndex = 0;
    int choosebutton, min, max, mi, ma;
    ImageView downloadedImage;
    Button optionOne, optionTwo, optionThree, optionFour;


    public class DownloadContent extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            HttpURLConnection urlConnection = null;
            URL url = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data != -1)
                {
                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();
                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Failed";
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed";
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public void mainFunction() {
        DownloadImage taskImage = new DownloadImage();
        try {
            Bitmap myimage = taskImage.execute(list_website_image.get(setIndex)).get();
            downloadedImage.setImageBitmap(myimage);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        choosebutton = new Random().nextInt((ma-mi) + 1) + mi;

        if (choosebutton == 0)
            try {
                optionOne.setText(list_website_name.get(setIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }

        else {
            int ran = new Random().nextInt((max - min) + 1) + min;
            if (ran == setIndex)
                ran = new Random().nextInt((max - min) + 1) + min;
            try {
                optionOne.setText(list_website_name.get(ran));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (choosebutton == 1)
            try {
                optionTwo.setText(list_website_name.get(setIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }
        else {
            int ran = new Random().nextInt((max - min) + 1) + min;
            if (ran == setIndex)
                ran = new Random().nextInt((max - min) + 1) + min;
            try {
                optionTwo.setText(list_website_name.get(ran));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (choosebutton == 2)
            try {
                optionThree.setText(list_website_name.get(setIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }
        else {
            int ran = new Random().nextInt((max - min) + 1) + min;
            if (ran == setIndex)
                ran = new Random().nextInt((max - min) + 1) + min;
            try {
                optionThree.setText(list_website_name.get(ran));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (choosebutton == 3)
            try {
                optionFour.setText(list_website_name.get(setIndex));
            } catch (Exception e) {
                e.printStackTrace();
            }
        else {
            int ran = new Random().nextInt((max - min) + 1) + min;
            if (ran == setIndex)
                ran = new Random().nextInt((max - min) + 1) + min;
            try {
                optionFour.setText(list_website_name.get(ran));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String result = "";
        downloadedImage = findViewById(R.id.image);
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);
        min = 0;
        max = 50;
        mi = 0;
        ma = 3;

        DownloadContent task = new DownloadContent();
        try
        {
            result = task.execute("https://www.posh24.se/kandisar").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Pattern pwebsiteimage = Pattern.compile("img src=\"(.*?)\"");
        Matcher mpwebsiteimage = pwebsiteimage.matcher(result);

        Pattern pwebsitename = Pattern.compile("alt=\"(.*?)\"");
        Matcher mwebsitename = pwebsitename.matcher(result);

        //Log.i("Info", result);

        while (mpwebsiteimage.find()) {
            list_website_image.add(mpwebsiteimage.group(1));
            //Log.i("Website", mpwebsiteimage.group(1) + "  " + Integer.toString(++i));
        }

        while (mwebsitename.find()) {
            list_website_name.add(mwebsitename.group(1));
            //Log.i("Website", mwebsitename.group(1) + "  " + Integer.toString(++n));
        }

        //Log.i("Size", Integer.toString(list_website_image.size()) + "    " + Integer.toString(list_website_name.size()));
        mainFunction();

        optionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choosebutton==0)
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Wrong it was " + list_website_name.get(setIndex), Toast.LENGTH_SHORT).show();
                setIndex++;
                mainFunction();
            }
        });

        optionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choosebutton==1)
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Wrong it was " + list_website_name.get(setIndex), Toast.LENGTH_SHORT).show();
                setIndex++;
                mainFunction();
            }
        });

        optionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choosebutton==2)
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Wrong it was " + list_website_name.get(setIndex), Toast.LENGTH_SHORT).show();
                setIndex++;
                mainFunction();
            }
        });

        optionFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choosebutton==3)
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Wrong it was " + list_website_name.get(setIndex), Toast.LENGTH_SHORT).show();
                setIndex++;
                mainFunction();
            }
        });
    }
}
