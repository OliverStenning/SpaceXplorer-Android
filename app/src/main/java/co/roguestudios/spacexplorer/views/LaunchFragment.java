package co.roguestudios.spacexplorer.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.roguestudios.spacexplorer.R;
import co.roguestudios.spacexplorer.datatypes.Solar;
import co.roguestudios.spacexplorer.viewmodels.LaunchModel;

public class LaunchFragment extends Fragment {

    private LaunchModel planetModel;
    private RecyclerView launchList;
    private LaunchAdapter launchAdapter;

    public LaunchFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        planetModel = ViewModelProviders.of(this).get(LaunchModel.class);
        planetModel.setSystemName("Solar");
        planetModel.getDatabase(this.getContext());

        final Observer<Solar> systemObserver = new Observer<Solar>() {
            @Override
            public void onChanged(@Nullable Solar system) {
                if (system != null) {
                    if (launchAdapter == null) {
                        launchAdapter = new LaunchAdapter(getContext(), system, planetModel);
                        launchList.setHasFixedSize(true);
                        launchList.setLayoutManager(new LinearLayoutManager(getContext()));
                        launchList.setAdapter(launchAdapter);
                    } else {
                        //launchAdapter.updateDataset(system);
                        //launchAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
        planetModel.getSystemLive().observe(this, systemObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planet, container, false);
        launchList = view.findViewById(R.id.launchList);
        return view;
    }


}
