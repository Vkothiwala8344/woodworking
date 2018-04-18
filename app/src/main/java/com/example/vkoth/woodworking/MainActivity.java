package com.example.vkoth.woodworking;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity{

    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint,cpaint;
    Path path;

    float woodworkingEnterance[] = {716,2490};
    float studentloungeStart[]={716,1990};
    float stdentloungeEnd[] = {716,1190};
    float studentloungeMid[] = {716,1380};
    float w1w3exit[]={1420,1990};
    float w1[] = {1365,1990};
    float w2[] = {1290,1990};
    float w3[] = {900,1990};
    float w9we[] = {900,1990};
    float w10we[] = {1380,1990};
    float w11[] = {1490,1990};
    float w4w5exit[] = {15,1990};
    float w52[] = {100,1990};
    float w51[] = {140,1990};
    float w4[] = {530,1990};
    float w81[] = {570,1990};
    float w82[] = {450,1990};
    float w6we[] = {10,1990};
    float w7we[] ={75,1990};

    float w9[] = {900,1190};
    float w10[]={1380,1190};
    float w12[] = {1490,1190};
    float w13[] ={1490,1190};
    float w1213exit[] = {1420,1190};

    float w678[] = {15,1190};
    float w8[] = {340,1190};
    float w6[] = {10,1190};
    float w7[] = {280,1190};

    ProximityObserver.Handler proximityHandlerw12;



    EditText startPoint;
    EditText endPoint;
    Button showPath;

    String ans = null;

    private ProximityObserver proximityObserverw12;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // proximityHandler.stop();


        EstimoteCloudCredentials cloudCredentials =
                new EstimoteCloudCredentials("varun-kothiwala-s-proximit-8kf", "94291148934a38a96e7f46a0fdf9d9f6");


        proximityObserverw12 =
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



        imageView = (ImageView) this.findViewById(R.id.imageView);
        startPoint = (EditText)findViewById(R.id.startPoint);
        endPoint = (EditText)findViewById(R.id.endPoint);
        showPath = (Button)findViewById(R.id.showPath);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int dh = displayMetrics.heightPixels;
        int dw = displayMetrics.widthPixels;


       Bundle bundle = getIntent().getExtras();
        String text= bundle.getString("startLocation");
      //  Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();


       startPoint.setText(text);
     //   endPoint.setText("w9");

        bitmap = Bitmap.createBitmap((int) dw, (int) dh,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        cpaint = new Paint();
        path = new Path();

        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20f);

        cpaint.setAntiAlias(true);
        cpaint.setColor(Color.WHITE);
        cpaint.setStrokeJoin(Paint.Join.ROUND);
        cpaint.setStyle(Paint.Style.STROKE);
        cpaint.setStrokeWidth(20f);

        imageView.setImageBitmap(bitmap);

        //canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w1[0], w1[1], paint); // line to w1
       // canvas.drawLine(w1[0],w1[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
       // canvas.drawLine(studentloungeStart[0], studentloungeStart[1], woodworkingEnterance[0], woodworkingEnterance[1], paint); // line to w1

        showPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String start = startPoint.getText().toString();
                String end = endPoint.getText().toString();

                canvas.drawLine(woodworkingEnterance[0],woodworkingEnterance[1],stdentloungeEnd[0],stdentloungeEnd[1],cpaint); //woodworking entrance to end of student lounge
                canvas.drawLine(studentloungeStart[0],studentloungeStart[1], w6we[0],w6we[1],cpaint); //w1-w3 exit
                canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w11[0], w11[1] ,cpaint); // w12-13 exit
                canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1], w13[0],w13[1],cpaint); //w4-w5 exit
                canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1], w6[0],w6[1],cpaint); //w6,7,8 exit


                if(start.equals("woodworking entrance"))
                {
                    if(end.equals("w1")) {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w1[0], w1[1], paint); // line to w1


                        ProximityZone zone1 = proximityObserverw12.zoneBuilder()
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

                                      //  tv.setText(data);
                                        if(data.equals("woodworking entrance,"))
                                        {
                                            Toast.makeText(getApplicationContext(),"You are at woodworking entrance. Walk straight around 4 meters.",Toast.LENGTH_LONG).show();
                                        }
                                        if (data.equals("woodworking entrance,student lounge start,")  || data.equals("student lounge start,")) {
                                            Toast.makeText(getApplicationContext(), "Take left and walk for around 8 meters", Toast.LENGTH_SHORT).show();
                                        }

                                        // Toast.makeText(getApplicationContext(),"devices"+desks,Toast.LENGTH_SHORT).show();
                                        return null;
                                    }
                                })
                                .create();
                        proximityObserverw12.addProximityZone(zone1);



                        ProximityZone zone3 =
                                proximityObserverw12.zoneBuilder()
                                        .forAttachmentKeyAndValue("location", "w12")
                                        .inNearRange()
                                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                                            @Override
                                            public Unit invoke(ProximityAttachment proximityAttachment) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                                                builder.setTitle("Destination Arrived");
                                                builder.setMessage("You have reached your destination. It is on your right side");

                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent i = new Intent(MainActivity.this,NavigationHome.class);
                                                        startActivity(i);

                                                        dialog.dismiss();
                                                    }
                                                });



                                                AlertDialog alert = builder.create();
                                                alert.show();

                                                //Toast.makeText(getApplicationContext(), "Your destination is on your right side", Toast.LENGTH_LONG).show();
                                                return null;
                                            }
                                        })
                                        .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                                            @Override
                                            public Unit invoke(ProximityAttachment proximityAttachment) {
                                                // Toast.makeText(getApplicationContext(),"bye to student lounge end",Toast.LENGTH_LONG).show();
                                                return null;
                                            }
                                        })
                                        .withOnChangeAction(new Function1<List<? extends ProximityAttachment>, Unit>() {
                                            @Override
                                            public Unit invoke(List<? extends ProximityAttachment> proximityAttachments) {

                                                return null;
                                            }
                                        })
                                        .create();


                        proximityObserverw12.addProximityZone(zone3);


                        RequirementsWizardFactory
                                .createEstimoteRequirementsWizard()
                                .fulfillRequirements(MainActivity.this,
                                        // onRequirementsFulfilled
                                        new Function0<Unit>() {
                                            @Override public Unit invoke() {
                                                Log.d("app", "requirements fulfilled");
                                                proximityHandlerw12 = proximityObserverw12.start();
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
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w2[0], w2[1], paint); // line to w2
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w3[0], w3[1], paint); // line to w3
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w9we[0], w9we[1], paint); // line to w9
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w10we[0], w10we[1], paint); // line to w10
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w11[0], w11[1], paint); // line to w11
                    }
                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w4[0], w4[1], paint); // line to w4
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w51[0], w51[1], paint); // line to w5
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w6we[0], w6we[1], paint); // line to w6
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w7we[0], w7we[1], paint); // line to w7
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], studentloungeStart[0],studentloungeStart[1], paint); // line to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w81[0], w81[1], paint); // line to w8
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], stdentloungeEnd[0],stdentloungeEnd[1], paint); // line to studentlounge end
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12



                        ProximityZone zone1 = proximityObserverw12.zoneBuilder()
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

                                       // tv.setText(data);
                                        if(data.equals("woodworking entrance,"))
                                        {
                                            Toast.makeText(getApplicationContext(),"You are at woodworking entrance. Walk straight around 18 meters.",Toast.LENGTH_LONG).show();
                                        }
                                        if (data.equals("student lounge start,student lounge mid,") || data.equals("student lounge start,")) {
                                            Toast.makeText(getApplicationContext(), "You are passing through student lounge", Toast.LENGTH_SHORT).show();
                                        }

                                        // Toast.makeText(getApplicationContext(),"devices"+desks,Toast.LENGTH_SHORT).show();
                                        return null;
                                    }
                                })
                                .create();
                        proximityObserverw12.addProximityZone(zone1);



                        ProximityZone zone2 =
                                proximityObserverw12.zoneBuilder()
                                        .forAttachmentKeyAndValue("location", "student lounge end")
                                        .inNearRange()
                                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                                            @Override
                                            public Unit invoke(ProximityAttachment proximityAttachment) {
                                                Toast.makeText(getApplicationContext(), "Take right and walk straight for around 17 meters ", Toast.LENGTH_LONG).show();
                                                return null;
                                            }
                                        })
                                        .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                                            @Override
                                            public Unit invoke(ProximityAttachment proximityAttachment) {
                                                //Toast.makeText(getApplicationContext(),"bye to student lounge mid",Toast.LENGTH_LONG).show();
                                                return null;
                                            }
                                        })
                                        .withOnChangeAction(new Function1<List<? extends ProximityAttachment>, Unit>() {
                                            @Override
                                            public Unit invoke(List<? extends ProximityAttachment> proximityAttachments) {

                                                return null;
                                            }
                                        })
                                        .create();


                        proximityObserverw12.addProximityZone(zone2);

                        ProximityZone zone3 =
                                proximityObserverw12.zoneBuilder()
                                        .forAttachmentKeyAndValue("location", "w12")
                                        .inNearRange()
                                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                                            @Override
                                            public Unit invoke(ProximityAttachment proximityAttachment) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                                                builder.setTitle("Destination Arrived");
                                                builder.setMessage("You have reached your destination. It is on your right side");

                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent i = new Intent(MainActivity.this,NavigationHome.class);
                                                        startActivity(i);

                                                        dialog.dismiss();
                                                    }
                                                });



                                                AlertDialog alert = builder.create();
                                                alert.show();
                                                //Toast.makeText(getApplicationContext(), "Your destination is on your right side", Toast.LENGTH_LONG).show();
                                                return null;
                                            }
                                        })
                                        .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                                            @Override
                                            public Unit invoke(ProximityAttachment proximityAttachment) {
                                                // Toast.makeText(getApplicationContext(),"bye to student lounge end",Toast.LENGTH_LONG).show();
                                                return null;
                                            }
                                        })
                                        .withOnChangeAction(new Function1<List<? extends ProximityAttachment>, Unit>() {
                                            @Override
                                            public Unit invoke(List<? extends ProximityAttachment> proximityAttachments) {

                                                return null;
                                            }
                                        })
                                        .create();


                        proximityObserverw12.addProximityZone(zone3);


                        RequirementsWizardFactory
                                .createEstimoteRequirementsWizard()
                                .fulfillRequirements(MainActivity.this,
                                        // onRequirementsFulfilled
                                        new Function0<Unit>() {
                                            @Override public Unit invoke() {
                                                Log.d("app", "requirements fulfilled");
                                                proximityHandlerw12 = proximityObserverw12.start();
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

                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(woodworkingEnterance[0], woodworkingEnterance[1], stdentloungeEnd[0],stdentloungeEnd[1], paint); // line to studentlounge end
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w13
                    }

                }
                else if(start.equals("student lounge entrance"))
                {
                    if(end.equals("w1")) {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w1[0], w1[1], paint); // line to w1
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w2[0], w2[1], paint); // line to w2
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w3[0], w3[1], paint); // line to w3
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w9we[0], w9we[1], paint); // line to w9
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w10we[0], w10we[1], paint); // line to w10
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w11[0], w11[1], paint); // line to w11
                    }
                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w4[0], w4[1], paint); // line to w4
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w51[0], w51[1], paint); // line to w5
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w6we[0], w6we[1], paint); // line to w6
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w7we[0], w7we[1], paint); // line to w7
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w81[0], w81[1], paint); // line to w8
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], stdentloungeEnd[0],stdentloungeEnd[1], paint); // line student lounge start to studentlounge end
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], stdentloungeEnd[0],stdentloungeEnd[1], paint); // line to studentlounge end
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w13
                    }

                }
                else if(start.equals("student lounge"))
                {
                    if(end.equals("w1")) {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w1[0], w1[1], paint); // line to w1
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w2[0], w2[1], paint); // line to w2
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w3[0], w3[1], paint); // line to w3
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w9we[0], w9we[1], paint); // line to w9
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w10we[0], w10we[1], paint); // line to w10
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w11[0], w11[1], paint); // line to w11
                    }
                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w4[0], w4[1], paint); // line to w4
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w51[0], w51[1], paint); // line to w5
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w6we[0], w6we[1], paint); // line to w6
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w7we[0], w7we[1], paint); // line to w7
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(studentloungeMid[0],studentloungeMid[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w81[0], w81[1], paint); // line to w8
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(studentloungeMid[0], studentloungeMid[1], stdentloungeEnd[0],stdentloungeEnd[1], paint); // line student lounge start to studentlounge end
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(studentloungeMid[0], studentloungeMid[1], stdentloungeEnd[0],stdentloungeEnd[1], paint); // line to studentlounge end
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w13
                    }

                }
                else if(start.equals("student lounge end"))
                {
                    if(end.equals("w1")) {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w1[0], w1[1], paint); // line to w1
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w2[0], w2[1], paint); // line to w2
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w3[0], w3[1], paint); // line to w3
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w9we[0], w9we[1], paint); // line to w9
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w10we[0], w10we[1], paint); // line to w10
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w11[0], w11[1], paint); // line to w11
                    }
                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w4[0], w4[1], paint); // line to w4
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w51[0], w51[1], paint); // line to w5
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w6we[0], w6we[1], paint); // line to w6
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w7we[0], w7we[1], paint); // line to w7
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);// student lounge mid to studentlounge start
                        canvas.drawLine(studentloungeStart[0], studentloungeStart[1], w81[0], w81[1], paint); // line to w8
                    }
                    else if(end.equals("w12"))
                    {

                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w13
                    }

                }
                else if(start.equals("w1"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w1[0],w1[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w1[0],w1[1],w2[0],w2[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(w1[0],w1[1],w3[0],w3[1],paint);
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                       // Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w1[0],w1[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w1[0],w1[1],w10we[0],w10we[1],paint);
                        }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w1[0],w1[1],w11[0],w11[1],paint);
                    }
                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w1[0],w1[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w1[0],w1[1],w51[0],w51[1],paint);
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w1[0],w1[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w1[0],w1[1],w7we[0],w7we[1],paint);
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w1[0],w1[1],w81[0],w81[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w1[0],w1[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w1[0],w1[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w12
                    }

                }
                else if(start.equals("w2"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w2[0],w2[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w1[0],w1[1],w2[0],w2[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(w2[0],w2[1],w3[0],w3[1],paint);
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        // Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w2[0],w2[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w2[0],w2[1],w10we[0],w10we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w2[0],w2[1],w11[0],w11[1],paint);
                    }
                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w2[0],w2[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w2[0],w2[1],w51[0],w51[1],paint);
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w2[0],w2[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w2[0],w2[1],w7we[0],w7we[1],paint);
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w2[0],w2[1],w81[0],w81[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w2[0],w2[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w2[0],w2[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w12
                    }

                }
                else if(start.equals("w3"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w3[0],w3[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w1[0],w1[1],w3[0],w3[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w2[0],w2[1],w3[0],w3[1],paint);
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        // Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w3[0],w3[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w3[0],w3[1],w10we[0],w10we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w3[0],w3[1],w11[0],w11[1],paint);
                    }
                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w3[0],w3[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w3[0],w3[1],w51[0],w51[1],paint);
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w3[0],w3[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w3[0],w3[1],w7we[0],w7we[1],paint);
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w3[0],w3[1],w81[0],w81[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w3[0],w3[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w3[0],w3[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w12
                    }

                }
                else if(start.equals("w3"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w3[0],w3[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w1[0],w1[1],w3[0],w3[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w2[0],w2[1],w3[0],w3[1],paint);
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                         Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w3[0],w3[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w3[0],w3[1],w10we[0],w10we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w3[0],w3[1],w11[0],w11[1],paint);
                    }
                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w3[0],w3[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w3[0],w3[1],w51[0],w51[1],paint);
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w3[0],w3[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w3[0],w3[1],w7we[0],w7we[1],paint);
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w3[0],w3[1],w81[0],w81[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w3[0],w3[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w3[0],w3[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w12
                    }

                }
                else if(start.equals("w4"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w3[0],w3[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w1[0],w1[1],w4[0],w4[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w2[0],w2[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(w3[0],w3[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        // Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w4[0],w4[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w4[0],w4[1],w10we[0],w10we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w4[0],w4[1],w11[0],w11[1],paint);
                    }

                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w4[0],w4[1],w51[0],w51[1],paint);
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w4[0],w4[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w4[0],w4[1],w7we[0],w7we[1],paint);
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w4[0],w4[1],w81[0],w81[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w4[0],w4[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w4[0],w4[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w12
                    }

                }
                else if(start.equals("w5"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w51[0],w51[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w51[0],w51[1],w1[0],w1[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w51[0],w51[1],w2[0],w2[1],paint);
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(w51[0],w51[1],w3[0],w3[1],paint);
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        // Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w51[0],w51[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w51[0],w51[1],w10we[0],w10we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w51[0],w51[1],w11[0],w11[1],paint);
                    }

                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w51[0],w51[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w51[0],w51[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w51[0],w51[1],w7we[0],w7we[1],paint);
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w51[0],w51[1],w81[0],w81[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w51[0],w51[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w51[0],w51[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w12
                    }

                }
                else if(start.equals("w6"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w6we[0],w6we[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w6we[0],w6we[1],w1[0],w1[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w6we[0],w6we[1],w2[0],w2[1],paint);
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(w6we[0],w6we[1],w3[0],w3[1],paint);
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        // Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w6we[0],w6we[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w6we[0],w6we[1],w10we[0],w10we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w6we[0],w6we[1],w11[0],w11[1],paint);
                    }

                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w6we[0],w6we[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w6we[0],w6we[1],w51[0],w51[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w6we[0],w6we[1],w7we[0],w7we[1],paint);
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w6we[0],w6we[1],w81[0],w81[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w6[0],w6[1],w12[0],w12[1],paint);

                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w6[0],w6[1],w12[0],w12[1],paint);
                    }

                }
                else if(start.equals("w7"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w7we[0],w7we[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w7we[0],w7we[1],w1[0],w1[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w7we[0],w7we[1],w2[0],w2[1],paint);
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(w7we[0],w7we[1],w3[0],w3[1],paint);
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        // Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w7we[0],w7we[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w7we[0],w7we[1],w10we[0],w10we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w7we[0],w7we[1],w11[0],w11[1],paint);
                    }

                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w7we[0],w7we[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w7we[0],w7we[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w51[0],w51[1],w7we[0],w7we[1],paint);
                    }
                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w7we[0],w7we[1],w81[0],w81[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w7[0],w7[1],w12[0],w12[1],paint);
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w7[0],w7[1],w12[0],w12[1],paint);
                    }

                }
                else if(start.equals("w8"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w81[0],w81[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w81[0],w81[1],w1[0],w1[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w81[0],w81[1],w2[0],w2[1],paint);
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(w81[0],w81[1],w3[0],w3[1],paint);
                    }
                    else if(end.equals("w9") || end.equals("w9/10"))
                    {
                        // Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w81[0],w81[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w81[0],w81[1],w10we[0],w10we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w81[0],w81[1],w11[0],w11[1],paint);
                    }

                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w81[0],w81[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w81[0],w81[1],w7we[0],w7we[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w81[0],w81[1],w51[0],w51[1],paint);
                    }
                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w81[0],w81[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w8[0],w8[1],w12[0],w12[1],paint);
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w8[0],w8[1],w12[0],w12[1],paint);
                    }

                }
                else if(start.equals("w9") || start.equals("w9/10"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w9we[0],w9we[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w9we[0],w9we[1],w1[0],w1[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w9we[0],w9we[1],w2[0],w2[1],paint);
                    }
                    else if(end.equals("w3")) {
                        Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w9we[0],w9we[1],w3[0],w3[1],paint);
                    }

                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w9we[0],w81[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w9we[0],w9we[1],w51[0],w51[1],paint);
                    }

                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w9we[0],w9we[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w9we[0],w9we[1],w7we[0],w7we[1],paint);
                    }

                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w9we[0],w9we[1],w81[0],w81[1],paint);
                    }

                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w9we[0],w9we[1],w10we[0],w10we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w9we[0],w9we[1],w11[0],w11[1],paint);
                    }

                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w9[0],w9[1],w12[0],w12[1],paint);
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w9[0],w9[1],w12[0],w12[1],paint);
                    }

                }
                else if(start.equals("w10"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w10we[0],w10we[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w10we[0],w10we[1],w1[0],w1[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w10we[0],w10we[1],w2[0],w2[1],paint);
                    }
                    else if(end.equals("w3")) {
                        //Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w10we[0],w10we[1],w3[0],w3[1],paint);
                    }

                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w10we[0],w10we[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w10we[0],w10we[1],w51[0],w51[1],paint);
                    }

                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w10we[0],w10we[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w10we[0],w10we[1],w7we[0],w7we[1],paint);
                    }

                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w10we[0],w10we[1],w81[0],w81[1],paint);
                    }

                    else if(end.equals("w9"))
                    {
                        canvas.drawLine(w10we[0],w10we[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w10we[0],w10we[1],w11[0],w11[1],paint);
                    }

                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w10[0],w10[1],w12[0],w12[1],paint);
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w10[0],w10[1],w12[0],w12[1],paint);
                    }

                }
                else if(start.equals("w11"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w11[0],w11[1],studentloungeStart[0],studentloungeStart[1],paint); // student lounge end to studentlounge start
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],woodworkingEnterance[0],woodworkingEnterance[1],paint);
                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w11[0],w11[1],w1[0],w1[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w11[0],w11[1],w2[0],w2[1],paint);
                    }
                    else if(end.equals("w3")) {
                        //Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                        canvas.drawLine(w11[0],w11[1],w3[0],w3[1],paint);
                    }

                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w11[0],w11[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w11[0],w11[1],w51[0],w51[1],paint);
                    }

                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w11[0],w11[1],w6we[0],w6we[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w11[0],w11[1],w7we[0],w7we[1],paint);
                    }

                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w11[0],w11[1],w81[0],w81[1],paint);
                    }

                    else if(end.equals("w9"))
                    {
                        canvas.drawLine(w11[0],w11[1],w9we[0],w9we[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w11[0],w11[1],w10we[0],w10we[1],paint);
                    }

                    else if(end.equals("w12"))
                    {
                        canvas.drawLine(w11[0],w11[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w12[0], w12[1], paint); // line to w12
                    }
                    else if(end.equals("w13"))
                    {
                        canvas.drawLine(w11[0],w11[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0], stdentloungeEnd[1], w13[0], w13[1], paint); // line to w12
                    }

                }
                else if(start.equals("w12"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w12[0],w12[1],w1213exit[0],w1213exit[1],paint); // student lounge end to studentlounge start

                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w1[0],w1[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w2[0],w2[1],paint);
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w3[0],w3[1],paint);
                    }

                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w51[0],w51[1],paint);
                    }

                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w12[0],w12[1],w6[0],w6[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w12[0],w12[1],w7[0],w7[1],paint);
                    }

                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w12[0],w12[1],w8[0],w8[1],paint);
                    }

                    else if(end.equals("w9"))
                    {
                        canvas.drawLine(w12[0],w12[1],w9[0],w9[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w12[0],w12[1],w10[0],w10[1],paint);
                    }

                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w11[0],w11[1],paint);
                    }
                    else if(end.equals("w13"))
                    {
                        Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                    }

                }
                else if(start.equals("w13"))
                {
                    if(end.equals("woodworking exit") || end.equals("woodworking entrance")) {
                        canvas.drawLine(w12[0],w12[1],w1213exit[0],w1213exit[1],paint); // student lounge end to studentlounge start

                    }
                    else if(end.equals("w1")) {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w1[0],w1[1],paint);// student lounge end to studentlounge start
                    }
                    else if(end.equals("w2")) {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w2[0],w2[1],paint);
                    }
                    else if(end.equals("w3")) {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w3[0],w3[1],paint);
                    }

                    else if(end.equals("w4"))
                    {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w4[0],w4[1],paint);
                    }
                    else if(end.equals("w5"))
                    {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w51[0],w51[1],paint);
                    }

                    else if(end.equals("w6"))
                    {
                        canvas.drawLine(w12[0],w12[1],w6[0],w6[1],paint);
                    }
                    else if(end.equals("w7"))
                    {
                        canvas.drawLine(w12[0],w12[1],w7[0],w7[1],paint);
                    }

                    else if(end.equals("w8"))
                    {
                        canvas.drawLine(w12[0],w12[1],w8[0],w8[1],paint);
                    }

                    else if(end.equals("w9"))
                    {
                        canvas.drawLine(w12[0],w12[1],w9[0],w9[1],paint);
                    }
                    else if(end.equals("w10"))
                    {
                        canvas.drawLine(w12[0],w12[1],w10[0],w10[1],paint);
                    }

                    else if(end.equals("w11"))
                    {
                        canvas.drawLine(w12[0],w12[1],stdentloungeEnd[0],stdentloungeEnd[1],paint);
                        canvas.drawLine(stdentloungeEnd[0],stdentloungeEnd[1],studentloungeStart[0],studentloungeStart[1],paint);
                        canvas.drawLine(studentloungeStart[0],studentloungeStart[1],w11[0],w11[1],paint);
                    }
                    else if(end.equals("w12"))
                    {
                        Toast.makeText(getApplicationContext(),"Room is in front of you",Toast.LENGTH_SHORT).show();
                    }

                }
                else if(start.equals("")&&end.equals("") || start.equals("") || end.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter start point and end point",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter valid start point or end point",Toast.LENGTH_SHORT).show();
                }



            }
        });

        //halfway to w1 to w1 to w8
       // canvas.drawLine(716,2120,716,1710,paint); //mid line

        //w1 to w11
       // canvas.drawLine(716,1710, 1420,1710,paint); //w1-w3 exit
       // canvas.drawLine(716,1710, 1185,1710,paint); //w1 entry
       // canvas.drawLine(716,1710, 1135,1710,paint); //w2 entry
       // canvas.drawLine(716,1710, 850,1710,paint); //w3 entry
       // canvas.drawLine(716,1710, 850,1710,paint); //w9 entry
       // canvas.drawLine(716,1710, 1200,1710,paint); //w10 entry
        //canvas.drawLine(716,1710, 1250,1710,paint); //w11 entry

        //w4 to w8
       //  canvas.drawLine(716,1710, 15,1710,paint); //w4-w5 exit
      //  canvas.drawLine(716,1710, 160,1710,paint); //w5 entry-1
      //  canvas.drawLine(716,1710, 300,1710,paint); //w5 entry-2
        //canvas.drawLine(716,1710, 590,1710,paint); //w4 entry
        //  canvas.drawLine(716,1710, 605,1710,paint); //w8 entry-1
        //  canvas.drawLine(716,1710, 450,1710,paint); //w8 entry-2
        //  canvas.drawLine(716,1710, 202,1710,paint); //w6 entry
        //  canvas.drawLine(716,1710, 252,1710,paint); //w7 entry


        // canvas.drawLine(716,2120,716,1380,paint); //mid of student lounge

       // canvas.drawLine(716,2120,716,1020,paint); //woodworking entrance to end of student lounge

        //w9 to w13
       // canvas.drawLine(716, 1020,850, 1020 ,paint); // w9
       // canvas.drawLine(716, 1020,1200, 1020 ,paint); // w10
       // canvas.drawLine(716, 1020,1250, 1020 ,paint); // w12
      //  canvas.drawLine(716, 1020,1250, 1020 ,paint); // w13
      //  canvas.drawLine(716, 1020,1420, 1020 ,paint); // w12-13 exit


        //w6,w7,w8
        //canvas.drawLine(716,1020, 15,1020,paint); //w6,7,8 exit
         // canvas.drawLine(716,1020, 450,1020,paint); //w8 entry
        //  canvas.drawLine(716,1020, 202,1020,paint); //w6 entry
        //  canvas.drawLine(716,1020, 400,1020,paint); //w7 entry


       // imageView.setOnTouchListener(this);



    }



  /*  public boolean onTouch(View v, MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
               // path.moveTo(x,y);
                float x = event.getX();
                float y = event.getY();
                Log.d("down X-Y",x+","+y);
                break;
            case MotionEvent.ACTION_MOVE:
               // path.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                float x1 = event.getX();
                float y1 = event.getY();
                Log.d("up X-Y",x1+","+y1);
                break;
            default:
                return false;
        }
       // canvas.drawPath(path,paint);
        imageView.invalidate();
        return true;
    } */
  @Override
  protected void onDestroy() {
      proximityHandlerw12.stop();
      super.onDestroy();

  }

    @Override
    protected void onStop() {
        proximityHandlerw12.stop();
        super.onStop();

    }


}
