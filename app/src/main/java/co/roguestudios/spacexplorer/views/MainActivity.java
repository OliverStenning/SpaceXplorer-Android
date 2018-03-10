package co.roguestudios.spacexplorer.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import co.roguestudios.spacexplorer.R;
import co.roguestudios.spacexplorer.datatypes.Solar;
import co.roguestudios.spacexplorer.utils.Utils;
import co.roguestudios.spacexplorer.viewmodels.GameModel;

public class MainActivity extends AppCompatActivity {

    private GameModel gameModel;

    private FragmentManager fragmentManager;
    private PlanetFragment planetFragment;

    private TextView balanceText;
    private TextView incomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        deleteDatabase("systems");

        balanceText = findViewById(R.id.balanceText);
        incomeText = findViewById(R.id.incomeText);

        gameModel = ViewModelProviders.of(this).get(GameModel.class);
        gameModel.setSystemName("Solar");
        gameModel.loadDatabase(this);

        fragmentManager = getSupportFragmentManager();
        planetFragment = new PlanetFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment, planetFragment).commit();

        final Observer<Solar> balanceObserver = new Observer<Solar>() {
            @Override
            public void onChanged(@Nullable Solar system) {
                if (system != null) {
                    balanceText.setText(Utils.getStandardValue(system.getBalance(), true, false, 1));
                    //incomeText.setText(Utils.getStandardValue(system.getIncome(), true, true, 1));
                }
            }
        };
        gameModel.getSystemLive().observe(this, balanceObserver);

    }

    public void clickMenu(View view) {

    }

}
