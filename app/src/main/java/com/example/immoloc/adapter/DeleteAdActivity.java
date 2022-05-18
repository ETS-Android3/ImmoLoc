package com.example.immoloc.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.immoloc.R;

public class DeleteAdActivity extends AppCompatActivity {

        public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
        private EditText mEditWordView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_delete_ad);
            mEditWordView = findViewById(R.id.edit_word);

            final Button button = findViewById(R.id.button_del);
            button.setOnClickListener(view -> {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            });
        }
    }
