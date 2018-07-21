/*
 * Copyright (C) 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sample.cast.refplayer.browser;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.sample.cast.refplayer.CastOptionsProvider;
import com.google.sample.cast.refplayer.ElonMessage;
import com.google.sample.cast.refplayer.R;
import com.google.sample.cast.refplayer.SolutionMessage;
import com.google.sample.cast.refplayer.SolutionsMessage;
import com.google.sample.cast.refplayer.mediaplayer.LocalPlayerActivity;
import com.google.sample.cast.refplayer.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

/**
 * A fragment to host a list view of the video catalog.
 */
public class VideoBrowserFragment extends Fragment {

    private static final String TAG = "VideoBrowserFragment";
    private static final String CATALOG_URL =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/f.json";
    private final SessionManagerListener<CastSession> mSessionManagerListener =
            new MySessionManagerListener();

    private Button button;
    private Button startButton;
    private View problemCreator;
    private EditText problemPrompt;
    private Button problemSubmit;
    private TextView elonText;
    private CastSession session;
    private Gson gson;
    private View solutionCreator;
    private LinearLayout solutions;
    private EditText solutionPrompt;
    private EditText namePrompt;
    private Button solutionSubmit;

    public VideoBrowserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_browser_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        button = (Button) view.findViewById(R.id.name_submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendName(randomString(10));
            }
        });

        startButton = (Button) view.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendStart();
            }
        });

        problemCreator = view.findViewById(R.id.problem_creator);
        problemPrompt = (EditText) view.findViewById(R.id.problem_prompt);
        problemSubmit = (Button) view.findViewById(R.id.problem_submit);
        problemSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendProblem();
            }
        });

        elonText = (TextView) view.findViewById(R.id.elon_text);
        gson = new Gson();

        solutionCreator = view.findViewById(R.id.elon_holder);
        solutionPrompt = (EditText) view.findViewById(R.id.solution_prompt);
        solutionSubmit = (Button) view.findViewById(R.id.solution_submit);
        solutionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSolution();
            }
        });

        solutions = (LinearLayout) view.findViewById(R.id.solutions);

        namePrompt = (EditText) view.findViewById(R.id.name_prompt);
    }

    private String randomString(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }

    private void sendName(String name) {
        PendingResult<Status> result = session.sendMessage(CastOptionsProvider.CUSTOM_NAMESPACE, "{\"type\":\"NAME_SUBMITTED\", \"data\":\"" + namePrompt.getText().toString() + "\"}");
        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.d("ELON", "STATUS");
            }
        });
    }

    private void sendStart() {
        PendingResult<Status> result = session.sendMessage(CastOptionsProvider.CUSTOM_NAMESPACE, "{\"type\":\"START_GAME\"}");
        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.d("ELON", "STATUS");
            }
        });
    }

    private void sendProblem() {
        PendingResult<Status> result = session.sendMessage(CastOptionsProvider.CUSTOM_NAMESPACE, "{\"type\":\"PROBLEM_SUBMITTED\", \"data\":\"" + problemPrompt.getText().toString() + "\"}");
        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.d("ELON", "STATUS");
            }
        });
    }

    private void sendSolution() {
        PendingResult<Status> result = session.sendMessage(CastOptionsProvider.CUSTOM_NAMESPACE, "{\"type\":\"SOLUTION_SUBMITTED\", \"data\":\"" + solutionPrompt.getText().toString() + "\"}");
        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.d("ELON", "STATUS");
            }
        });
    }

    private void sendVote(String senderId) {
        PendingResult<Status> result = session.sendMessage(CastOptionsProvider.CUSTOM_NAMESPACE, "{\"type\":\"VOTE_SUBMITTED\", \"data\":\"" + senderId + "\"}");
        result.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.d("ELON", "STATUS");
            }
        });
    }

    private void goToProblemCreator() {
        button.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        namePrompt.setVisibility(View.GONE);
        problemCreator.setVisibility(View.VISIBLE);
    }

    private void goToStartedState() {
        button.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        namePrompt.setVisibility(View.GONE);
        problemCreator.setVisibility(View.GONE);
    }

    private void goToStartState() {
        button.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);
        problemCreator.setVisibility(View.GONE);
    }

    private void goToSelectingElon() {
        solutionCreator.setVisibility(View.VISIBLE);
        problemCreator.setVisibility(View.GONE);
    }

    private void goToSolutionVote(SolutionsMessage solutionsMessage) {
        solutionCreator.setVisibility(View.GONE);
        solutions.setVisibility(View.VISIBLE);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (SolutionMessage solution : solutionsMessage.data) {
            View inflated = inflater.inflate(R.layout.solution, solutions);
            final TextView text = (TextView) inflated.findViewById(R.id.solution_text);
            text.setText(solution.solution);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendVote(text.getHint().toString());
                }
            });
            solutions.addView(text);
        }
    }

    @Override
    public void onStart() {
        CastContext.getSharedInstance(getContext()).getSessionManager()
                .addSessionManagerListener(mSessionManagerListener, CastSession.class);
        super.onStart();
    }

    @Override
    public void onStop() {
        CastContext.getSharedInstance(getContext()).getSessionManager()
                .removeSessionManagerListener(mSessionManagerListener, CastSession.class);
        super.onStop();
    }

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {

        @Override
        public void onSessionEnded(CastSession session, int error) {
        }

        @Override
        public void onSessionResumed(CastSession session, boolean wasSuspended) {
        }

        @Override
        public void onSessionStarted(CastSession session, String sessionId) {
            VideoBrowserFragment.this.session = session;
            try {
                session.setMessageReceivedCallbacks(CastOptionsProvider.CUSTOM_NAMESPACE, new Cast.MessageReceivedCallback() {
                    @Override
                    public void onMessageReceived(CastDevice castDevice, String s, String s1) {
                        Log.d("ELON", s + " " + s1);
                        if (s1.contains("\"type\":\"CAN_START_GAME\"")) {
                            goToStartState();
                        } else if (s1.contains("\"type\":\"START_GAME\"")) {
                            goToStartedState();
                        } else if (s1.contains("\"type\":\"CHOSEN_PROBLEM_CREATOR\"")) {
                            goToProblemCreator();
                        } else if (s1.contains("\"type\":\"SELECTING_ELON\"")) {
                            goToSelectingElon();
                        } else if (s1.contains("\"type\":\"SELECTED_ELON\"")) {
                            elonText.setText("YOU ARE ELON, GOOD LUCK");
                        } else if (s1.contains("\"type\":\"SELECTED_NORMAL\"")) {
                            ElonMessage message = gson.fromJson(s1, ElonMessage.class);
                            elonText.setText(message.data);
                        } else if (s1.contains("\"type\":\"ALL_SOLUTIONS_SUBMITTED\"")) {
                            SolutionsMessage solutions = gson.fromJson(s1, SolutionsMessage.class);
                            goToSolutionVote(solutions);
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSessionStarting(CastSession session) {
        }

        @Override
        public void onSessionStartFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionEnding(CastSession session) {
        }

        @Override
        public void onSessionResuming(CastSession session, String sessionId) {
        }

        @Override
        public void onSessionResumeFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionSuspended(CastSession session, int reason) {
        }
    }
}
