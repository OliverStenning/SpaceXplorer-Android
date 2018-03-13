package co.roguestudios.spacexplorer.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.roguestudios.spacexplorer.R;
import co.roguestudios.spacexplorer.datatypes.Launch;
import co.roguestudios.spacexplorer.datatypes.Solar;
import co.roguestudios.spacexplorer.utils.AssetsReader;
import co.roguestudios.spacexplorer.utils.Utils;
import co.roguestudios.spacexplorer.viewmodels.LaunchModel;

public class LaunchAdapter extends RecyclerView.Adapter<LaunchAdapter.LaunchViewHolder> {

    private Context context;
    private Solar system;
    private LaunchModel launchModel;

    public static class LaunchViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout launchLayout;
        public LinearLayout lockedLayout;

        public ImageView planetImage;
        public ImageView typeImage;
        public ProgressBar unlockProgress;
        public TextView amountText;

        public TextView nameText;
        public TextView incomeText;
        public Button launchButton;
        public TextView costText;

        public ImageView lockedPlanetImage;
        public ImageView lockedTypeImage;

        public TextView lockedNameText;
        public TextView lockedUpgradeText;

        public LaunchViewHolder(View view) {
            super(view);

            launchLayout = view.findViewById(R.id.launchLayout);
            lockedLayout = view.findViewById(R.id.lockedLayout);

            planetImage = view.findViewById(R.id.launchPlanetImage);
            typeImage = view.findViewById(R.id.launchTypeImage);
            unlockProgress = view.findViewById(R.id.launchUnlockProgress);
            amountText = view.findViewById(R.id.launchAmountText);

            nameText = view.findViewById(R.id.launchNameText);
            incomeText = view.findViewById(R.id.launchIncomeText);
            launchButton = view.findViewById(R.id.launchButton);
            costText = view.findViewById(R.id.launchCostText);

            lockedPlanetImage = view.findViewById(R.id.lockedPlanetImage);
            lockedTypeImage = view.findViewById(R.id.lockedTypeImage);

            lockedNameText = view.findViewById(R.id.lockedNameText);
            lockedUpgradeText = view.findViewById(R.id.lockedUpgradeText);

        }
    }

    public LaunchAdapter(Context context, Solar system, LaunchModel launchModel) {
        this.context = context;
        this.system = system;
        this.launchModel = launchModel;
    }

    @Override
    public LaunchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_launch, parent, false);
        return new LaunchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LaunchViewHolder holder, final int position) {

        final Launch currentLaunch = system.getLaunches().get(position);

        if (currentLaunch.getRocket() <= system.getRocket()) {
            //Setup unlocked layout
            holder.launchLayout.setVisibility(View.VISIBLE);
            holder.lockedLayout.setVisibility(View.INVISIBLE);

            holder.planetImage.setImageDrawable(AssetsReader.getDrawableFromAssets(context, "Systems/" + system.getSystemName() + "/" + currentLaunch.getPlanet() + ".png"));
            //TODO type image
            //TODO unlock progress
            holder.amountText.setText(Integer.toString(currentLaunch.getAmount()));

            holder.nameText.setText(currentLaunch.getName());
            holder.incomeText.setText(Utils.getStandardValue(currentLaunch.getLaunchIncome(), true, true, 2));
            holder.launchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchModel.clickLaunch(position);
                }
            });
            holder.costText.setText(Utils.getStandardValue(currentLaunch.getLaunchCost(), true, false, 2));


        } else {
            //Setup locked layout


        }

    }

    @Override
    public int getItemCount() {
        return system.getLaunches().size();
    }

    public void updateDataset(Solar system) {
        this.system = system;
    }


}
