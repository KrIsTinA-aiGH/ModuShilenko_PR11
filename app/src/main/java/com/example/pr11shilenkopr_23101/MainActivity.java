package com.example.pr11shilenkopr_23101;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.animation.TranslateAnimation;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Animation> cloudAnimations = new ArrayList<>();
    private Animation hourHandAnimation;
    private boolean animationsStopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Очищаем список анимаций
        cloudAnimations.clear();

        // Солнце
        ImageView sunImageView = findViewById(R.id.sun);
        Animation sunRiseAnimation = AnimationUtils.loadAnimation(this, R.anim.sun_rise);
        sunImageView.startAnimation(sunRiseAnimation);

        // Часы (минутная стрелка)
        ImageView clockImageView = findViewById(R.id.clock);
        Animation clockTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.clock_turn);
        clockImageView.startAnimation(clockTurnAnimation);

        // Часовая стрелка с слушателем окончания
        ImageView hourImageView = findViewById(R.id.hour_hand);
        hourHandAnimation = AnimationUtils.loadAnimation(this, R.anim.hour_turn);

        // Добавляем слушатель для часовой стрелки
        hourHandAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Анимация началась
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Когда часовая стрелка остановилась - останавливаем все облака
                stopAllCloudAnimations();
                animationsStopped = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Не используется
            }
        });

        hourImageView.startAnimation(hourHandAnimation);

        // Кот
        ImageView catImageView = findViewById(R.id.cat);
        Animation catBounceAnimation = AnimationUtils.loadAnimation(this, R.anim.cat_bounce);
        catImageView.startAnimation(catBounceAnimation);

        // Создаем и запускаем облака с возможностью остановки
        setupCloudAnimations();
    }

    private void setupCloudAnimations() {
        // Облако 1 - маленькое, быстрое
        ImageView cloud1 = findViewById(R.id.cloud1);
        Animation cloudAnim1 = createStoppableCloudAnimation(8000, 0, 3, 0);
        cloudAnimations.add(cloudAnim1);
        cloud1.startAnimation(cloudAnim1);

        // Облако 2 - среднее, нормальная скорость
        ImageView cloud2 = findViewById(R.id.cloud2);
        Animation cloudAnim2 = createStoppableCloudAnimation(12000, 1000, 5, 0.8f);
        cloudAnimations.add(cloudAnim2);
        cloud2.startAnimation(cloudAnim2);

        // Облако 3 - большое, медленное
        ImageView cloud3 = findViewById(R.id.cloud3);
        Animation cloudAnim3 = createStoppableCloudAnimation(20000, 2000, 8, 0.7f);
        cloudAnimations.add(cloudAnim3);
        cloud3.startAnimation(cloudAnim3);

        // Облако 4 - среднее, высоко летит
        ImageView cloud4 = findViewById(R.id.cloud4);
        Animation cloudAnim4 = createStoppableCloudAnimation(9000, 500, 3, 0.85f);
        cloudAnimations.add(cloudAnim4);
        cloud4.startAnimation(cloudAnim4);

        // Облако 5 - маленькое, низко летит с волной
        ImageView cloud5 = findViewById(R.id.cloud5);
        Animation cloudAnim5 = createStoppableWaveAnimation();
        cloudAnimations.add(cloudAnim5);
        cloud5.startAnimation(cloudAnim5);

        // Облако 6 - среднее, диагональное движение
        ImageView cloud6 = findViewById(R.id.cloud6);
        Animation cloudAnim6 = createStoppableDiagonalAnimation();
        cloudAnimations.add(cloudAnim6);
        cloud6.startAnimation(cloudAnim6);
    }

    private Animation createStoppableCloudAnimation(long duration, long startOffset,
                                                    float verticalMove, float startAlpha) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.RESTART);

        // Горизонтальное движение
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, -0.3f,
                TranslateAnimation.RELATIVE_TO_PARENT, 1.3f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, verticalMove / 100f
        );
        translate.setDuration(duration);
        translate.setStartOffset(startOffset);

        // Эффект появления/исчезновения
        AlphaAnimation alpha = new AlphaAnimation(startAlpha, 1.0f);
        alpha.setDuration(duration / 2);
        alpha.setStartOffset(startOffset + duration / 4);

        animationSet.addAnimation(translate);
        animationSet.addAnimation(alpha);

        return animationSet;
    }

    private Animation createStoppableWaveAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.RESTART);

        // Основное горизонтальное движение
        TranslateAnimation mainTranslate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, -0.25f,
                TranslateAnimation.RELATIVE_TO_PARENT, 1.25f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.1f
        );
        mainTranslate.setDuration(14000);
        mainTranslate.setStartOffset(1500);

        // Волнообразное движение вверх-вниз
        TranslateAnimation waveTranslate = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 30f
        );
        waveTranslate.setDuration(2000);
        waveTranslate.setRepeatCount(Animation.INFINITE);
        waveTranslate.setRepeatMode(Animation.REVERSE);

        animationSet.addAnimation(mainTranslate);
        animationSet.addAnimation(waveTranslate);

        return animationSet;
    }

    private Animation createStoppableDiagonalAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.setRepeatCount(Animation.INFINITE);
        animationSet.setRepeatMode(Animation.RESTART);

        // Диагональное движение
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, -0.4f,
                TranslateAnimation.RELATIVE_TO_PARENT, 1.4f,
                TranslateAnimation.RELATIVE_TO_PARENT, -0.05f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.15f
        );
        translate.setDuration(15000);
        translate.setStartOffset(3000);

        // Масштабирование
        android.view.animation.ScaleAnimation scale = new android.view.animation.ScaleAnimation(
                0.8f, 1.2f, 0.8f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(15000);

        animationSet.addAnimation(translate);
        animationSet.addAnimation(scale);

        return animationSet;
    }

    private void stopAllCloudAnimations() {
        // Останавливаем все облака
        int[] cloudIds = {R.id.cloud1, R.id.cloud2, R.id.cloud3,
                R.id.cloud4, R.id.cloud5, R.id.cloud6};

        for (int id : cloudIds) {
            ImageView cloud = findViewById(id);
            if (cloud != null) {
                // Получаем текущую позицию облака
                float currentX = cloud.getX();
                float currentY = cloud.getY();

                // Очищаем анимацию
                cloud.clearAnimation();

                // Устанавливаем облако в текущую позицию
                cloud.setX(currentX);
                cloud.setY(currentY);
            }
        }

        // Также очищаем все анимации из списка
        for (Animation anim : cloudAnimations) {
            anim.cancel();
        }
        cloudAnimations.clear();

        // Можно добавить визуальный эффект - облака медленно исчезают
        fadeOutClouds();
    }

    private void fadeOutClouds() {
        int[] cloudIds = {R.id.cloud1, R.id.cloud2, R.id.cloud3,
                R.id.cloud4, R.id.cloud5, R.id.cloud6};

        for (int id : cloudIds) {
            ImageView cloud = findViewById(id);
            if (cloud != null) {
                // Анимация исчезновения облаков
                AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.3f);
                fadeOut.setDuration(2000);
                fadeOut.setFillAfter(true);
                cloud.startAnimation(fadeOut);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Если анимации были остановлены, не перезапускаем их
        if (!animationsStopped) {
            restartAnimations();
        }
    }

    private void restartAnimations() {
        // Перезапускаем только если они не были остановлены
        // Здесь можно добавить логику перезапуска
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Очищаем анимации только если они не были остановлены
        if (!animationsStopped) {
            clearAllAnimations();
        }
    }

    private void clearAllAnimations() {
        int[] viewIds = {R.id.sun, R.id.cat, R.id.clock, R.id.hour_hand,
                R.id.cloud1, R.id.cloud2, R.id.cloud3,
                R.id.cloud4, R.id.cloud5, R.id.cloud6};

        for (int id : viewIds) {
            ImageView view = findViewById(id);
            if (view != null) {
                view.clearAnimation();
            }
        }

        // Очищаем список анимаций
        for (Animation anim : cloudAnimations) {
            anim.cancel();
        }
        cloudAnimations.clear();

        if (hourHandAnimation != null) {
            hourHandAnimation.cancel();
        }
    }
}