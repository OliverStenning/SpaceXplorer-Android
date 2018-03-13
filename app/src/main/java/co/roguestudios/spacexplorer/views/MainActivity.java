package co.roguestudios.spacexplorer.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import co.roguestudios.spacexplorer.R;
import co.roguestudios.spacexplorer.datatypes.Solar;
import co.roguestudios.spacexplorer.utils.Utils;
import co.roguestudios.spacexplorer.viewmodels.GameModel;

public class MainActivity extends AppCompatActivity {

    private GameModel gameModel;

    private FragmentManager fragmentManager;
    private LaunchFragment planetFragment;

    private TextView balanceText;
    private TextView incomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        deleteDatabase("systems");

        balanceText = findViewById(R.id.balanceText);
        incomeText = findViewById(R.id.launchIncomeText);

        gameModel = ViewModelProviders.of(this).get(GameModel.class);
        gameModel.setSystemName("Solar");
        gameModel.loadDatabase(this);

        fragmentManager = getSupportFragmentManager();
        planetFragment = new LaunchFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment, planetFragment).commit();

        final Observer<Solar> balanceObserver = new Observer<Solar>() {
            @Override
            public void onChanged(@Nullable Solar system) {
                if (system != null) {
                    if (system.getIncome() > 0) {

                        balanceText.setText(Utils.getStandardValue(system.getBalance(), true, false, 3));
                        Animation animation = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(100);
                        balanceText.startAnimation(animation);

                        incomeText.setText(Utils.getStandardValue(system.getIncome(), true, true, 1));
                        Animation fadeAnimation = new AlphaAnimation(1f, 0f);
                        fadeAnimation.setDuration(1000);
                        fadeAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                incomeText.setAlpha(1f);
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                incomeText.setAlpha(0f);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        incomeText.setAnimation(fadeAnimation);
                    } else {
                        incomeText.setAlpha(0f);
                    }

                }
            }
        };
        gameModel.getSystemLive().observe(this, balanceObserver);
        gameModel.startTick();
    }

    public void clickMenu(View view) {

    }

    public void onDestroy() {
        gameModel.stopTick();
        super.onDestroy();
    }

}
