package com.hfad.workout;

import android.view.View;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StopWatchFragment# factory method to
 * create an instance of this fragment.
 */
public class StopWatchFragment extends Fragment implements View.OnClickListener {
    private static class MyData implements Serializable
    {

        private int seconds = 0;
        //Is the stopwatch running?
        private boolean running=false;
        private boolean wasRunning=false;
    }
    private MyData data=new MyData();

    public StopWatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
           data= (MyData) savedInstanceState.getSerializable("myData");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("create view");

        return inflater.inflate(R.layout.fragment_stopwatch, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindClickEvent();
        runTimer();
    }


    @Override
    public void onResume() {
        super.onResume();
        data.running=data.wasRunning;

    }

    @Override
    public void onPause() {
        super.onPause();

        data.wasRunning=data.running;
        data.running=false;
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putSerializable("myData",data);
        }

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        //!@?
        switch(v.getId()){
            case R.id.stop_button: onClickStop(v); break;
            case R.id.reset_button: onClickReset(v); break;
            case R.id.start_button: onClickStart(v); break;
            // Just like you were doing
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindClickEvent()
    {
            getView().getTouchables().forEach(v -> {
                if (v instanceof Button) {
                    v.setOnClickListener(this);
                }
            });

    }

    //Start the stopwatch running when the Start button is clicked.
    private void onClickStart(View view) {
        data.running = true;
    }

    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        data.running = false;
    }

    //Reset the stopwatch when the Reset button is clicked.
    private void onClickReset(View view) {
        data.running = false;
        data.seconds = 0;

    }

    //Sets the number of seconds on the timer.
    private void runTimer() {
        final TextView timeView = (TextView) getView().findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours =  data.seconds/3600;
                int minutes = ( data.seconds%3600)/60;
                int secs =  data.seconds%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if ( data.running) {
                    data.seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
