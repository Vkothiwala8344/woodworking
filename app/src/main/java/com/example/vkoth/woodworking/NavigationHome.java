package com.example.vkoth.woodworking;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class NavigationHome extends AppCompatActivity {

    Button navigation;
    ImageButton mic;
    TextView tv;

    String ans;

    private ProximityObserver proximityObserver;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;

    TextToSpeech tt;
    String res;
    private final int SPEECH_RECOGNITION_CODE = 1;

    ProximityObserver.Handler proximityHandler;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      //     proximityHandler.stop();

        tv = (TextView)findViewById(R.id.textView);

        EstimoteCloudCredentials cloudCredentials =
                new EstimoteCloudCredentials("varun-kothiwala-s-proximit-8kf", "94291148934a38a96e7f46a0fdf9d9f6");




        proximityObserver =
                new ProximityObserverBuilder(getApplicationContext(), cloudCredentials)
                        .withOnErrorAction(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("proximity app", "proximity observer error: " + throwable);
                                return null;
                            }
                        })
                        .withBalancedPowerMode()
                        .build();


        // add this below:


        ProximityZone zone1 = proximityObserver.zoneBuilder()
                .forAttachmentKeyAndValue("floor", "8th")
                .inCustomRange(2.5)
                .withOnChangeAction(new Function1<List<? extends ProximityAttachment>, Unit>() {
                    @Override
                    public Unit invoke(List<? extends ProximityAttachment> attachments) {
                        List<String> desks = new ArrayList<>();
                        for (ProximityAttachment attachment : attachments) {
                            desks.add(attachment.getPayload().get("location"));
                        }
                        Log.d("app", "Nearby location: " + desks);
                        int count = desks.size();
                        String data = "";
                        for (int i = 0; i < count; i++) {
                            data = data + desks.get(i) + ",";
                            }

                        tv.setText(data);
                        if(data.equals("woodworking entrance,"))
                        {
                         ans = "woodworking entrance";
                        }
                        if(data.equals("student lounge start,"))
                        {
                            ans = "student lounge entrance";
                        }
                        if(data.equals("student lounge mid,"))
                        {
                            ans = "student lounge";
                        }
                        if(data.equals("student lounge end,"))
                        {
                            ans = "student lounge end";
                        }
                        return null;
                    }
                })
                .create();
         proximityObserver.addProximityZone(zone1);


        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        // onRequirementsFulfilled
                        new Function0<Unit>() {
                            @Override public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                proximityHandler =  proximityObserver.start();
                                return null;
                            }
                        },
                        // onRequirementsMissing
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        // onError
                        new Function1<Throwable, Unit>() {
                            @Override public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        });

        navigation = (Button)findViewById(R.id.navigation);
        mic= (ImageButton)findViewById(R.id.btn_mic);
        tv = (TextView)findViewById(R.id.textView);





        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent i = new Intent(NavigationHome.this,MainActivity.class);
                Bundle b = new Bundle();
                b.putString("startLocation", ans);
                i.putExtras(b);
                startActivity(i);
                startActivity(i);
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechToText();
            }
        });

    }

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    // txtOutput.setText(text + "?");
                    res = text;

                }
                break;
            }
        }
        tv.setText(res);
        if (res.equals("where am I")) {



            tv.setText(ans);

            tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {


                public void onInit(int s) {
                    // Toast.makeText(getApplicationContext(), Integer.toString(s), Toast.LENGTH_SHORT).show();

                    if (s != TextToSpeech.ERROR) {
                        tt.setLanguage(Locale.CANADA);
                        //  Toast.makeText(getApplicationContext(), "Language is set", Toast.LENGTH_SHORT).show();

                        tt.speak(ans, TextToSpeech.QUEUE_FLUSH, null, null);
                        try {
                            Thread.sleep(8000);
                        }
                        catch (Exception e)
                        {}
                    } else {
                        Toast.makeText(getApplicationContext(), "Google speech is not working", Toast.LENGTH_SHORT).show();
                    }
                }
            });






        } else {
            // ansOutput.setText("Sorry! I did not get you");

            tt = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {


                public void onInit(int s) {
                    // Toast.makeText(getApplicationContext(), Integer.toString(s), Toast.LENGTH_SHORT).show();

                    if (s != TextToSpeech.ERROR) {
                        tt.setLanguage(Locale.CANADA);
                        //  Toast.makeText(getApplicationContext(), "Language is set", Toast.LENGTH_SHORT).show();

                        tt.speak("Sorry! I did not get you", TextToSpeech.QUEUE_FLUSH, null, null);
                        try {
                            Thread.sleep(8000);
                        }
                        catch (Exception e)
                        {}
                    } else {
                        Toast.makeText(getApplicationContext(), "Google speech is not working", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
    @Override
    protected void onDestroy() {
        proximityHandler.stop();
        super.onDestroy();
        }

    @Override
    protected void onStop() {
        proximityHandler.stop();
        super.onStop();

    }

}
