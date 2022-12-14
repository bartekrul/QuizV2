package pl.edu.pb.quizv2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String QUIZ_TAG = "MainActivity";
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;
    private boolean answerWasShown;

    private int currentIndex = 0;
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final int REQUEST_CODE_PROMPT = 0;

    private Question[] questions = new Question[] {
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_version, false)
    };

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d("QUIZ_TAG", "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }


    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer  = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("QUIZ_TAG", "Wywołana została metoda: onCreate" );
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        promptButton = findViewById(R.id.prompt_button);




        ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() != RESULT_OK) {
                            return;
                        }
                        if (result.getResultCode() == REQUEST_CODE_PROMPT) {
                            Intent data = result.getData();
                            if (data == null) {
                                return;
                            }
                            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
                        }
                    }
                });

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult.launch(intent);
            }
        });;

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(true);

            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(false);

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1)&questions.length;
                answerWasShown = false;
                setNextQuestion();

            }
        });
        setNextQuestion();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tag", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag", "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tag", "Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "Destroy");
    }



}