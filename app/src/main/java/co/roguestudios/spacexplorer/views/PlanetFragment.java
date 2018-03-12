package co.roguestudios.spacexplorer.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.roguestudios.spacexplorer.R;
import co.roguestudios.spacexplorer.datatypes.Solar;
import co.roguestudios.spacexplorer.utils.AssetsReader;
import co.roguestudios.spacexplorer.utils.Utils;
import co.roguestudios.spacexplorer.viewmodels.PlanetModel;

public class PlanetFragment extends Fragment {

    private PlanetModel planetModel;
    private int planetAmount;

    private ViewPager planetPager;
    private ViewPagerAdapter planetAdapter;

    private ImageButton prevButton;
    private ImageButton nextButton;

    private LinearLayout dotsLayout;
    private TextView[] dots;

    public PlanetFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        planetModel = ViewModelProviders.of(this).get(PlanetModel.class);
        planetModel.setSystemName("Solar");
        planetModel.getDatabase(this.getContext());

        final Observer<Solar> systemObserver = new Observer<Solar>() {
            @Override
            public void onChanged(@Nullable Solar system) {

                if (system != null) {
                    planetAmount = system.getPlanets().size();
                    if (planetAdapter == null) {
                        //Create pager adapter if it doesn't already exist
                        planetAdapter = new ViewPagerAdapter(getActivity(), system);
                        planetPager.setAdapter(planetAdapter);
                        planetPager.addOnPageChangeListener(planetPagerPageChangeListener);
                        planetPager.setOffscreenPageLimit(planetAmount);
                        planetPager.setCurrentItem(system.getStatingPlanet());
                        addBottomDots(planetPager.getCurrentItem());
                    } else {
                        //Update view of current page if pager adapter does exist
                        View planetPage = planetPager.findViewWithTag(system.getPlanets().get(planetPager.getCurrentItem()).getPlanetName());
                        planetAdapter.updatePage(getContext(), planetPage, system, planetPager.getCurrentItem());

                    }
                }
            }
        };
        planetModel.getSystemLive().observe(this, systemObserver);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planet, container, false);

        prevButton = view.findViewById(R.id.prevButton);
        nextButton = view.findViewById(R.id.nextButton);
        dotsLayout = view.findViewById(R.id.dotsLayout);
        planetPager = view.findViewById(R.id.planetPager);

        prevButton.setOnClickListener(clickPrev);
        nextButton.setOnClickListener(clickNext);

        return view;
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private Solar system;
        private ObjectAnimator[] progressAnimators;

        public ViewPagerAdapter(Context context, Solar system) {
            this.context = context;
            this.system = system;
            progressAnimators = new ObjectAnimator[system.getPlanets().size()];
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.page_planet, container, false);

            //Set tag for view so it can be found and updated later
            view.setTag(system.getPlanets().get(position).getPlanetName());

            //Set parts of view that don't change
            TextView planetNameText = view.findViewById(R.id.planetNameText);
            planetNameText.setText(system.getPlanets().get(position).getPlanetName());

            final ImageButton planetImageButton = view.findViewById(R.id.planetImageButton);
            planetImageButton.setImageDrawable(AssetsReader.getDrawableFromAssets(context, "Systems/" + planetModel.getSystemName() + "/" + system.getPlanets().get(position).getPlanetName() + ".png"));
            planetImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    planetModel.clickPlanet(position);
                    Animation animation = new ScaleAnimation(1.0f, 0.98f, 1.0f, 0.98f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(50);
                    planetImageButton.startAnimation(animation);
                }
            });

            final Button launchButton = view.findViewById(R.id.launchButton);
            launchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    planetModel.clickLaunch(position);
                    Animation animation = new ScaleAnimation(1.0f, 0.99f, 1.0f, 0.99f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(50);
                    launchButton.startAnimation(animation);
                }
            });


            updatePage(context, view, system, position);

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return system.getPlanets().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        private void updatePage(Context context, View page, final Solar system, final int planet) {

            this.system = system;

            ImageView typeImage = page.findViewById(R.id.launchTypeImage);
            //TODO update type image based on amount

            TextView amountText = page.findViewById(R.id.launchAmountText);
            amountText.setText(Integer.toString(system.getPlanets().get(planet).getAmount()));

            ProgressBar unlockProgress = page.findViewById(R.id.launchUnlockProgress);
            //TODO update unlock progress

            TextView nameText = page.findViewById(R.id.launchNameText);
            //TODO update name text based on amount

            TextView incomeText = page.findViewById(R.id.launchIncomeText);
            incomeText.setText(Utils.getStandardValue(system.getPlanets().get(planet).getLaunchIncome(), true, false, 1));

            if (system.getPlanets().get(planet).getAmount() > 0) {
                if (progressAnimators[planet] == null) {
                    final ProgressBar launchProgress = page.findViewById(R.id.launchProgress);
                    progressAnimators[planet] = ObjectAnimator.ofInt(launchProgress, "progress", system.getPlanets().get(planet).getTimePercentage(), 1000);
                    progressAnimators[planet].setDuration(system.getPlanets().get(planet).getTimeRemaining());
                    progressAnimators[planet].addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            progressAnimators[planet] = ObjectAnimator.ofInt(launchProgress, "progress", 0, 1000);
                            progressAnimators[planet].setDuration(system.getPlanets().get(planet).getLaunchTime());
                            progressAnimators[planet].setRepeatCount(ValueAnimator.INFINITE);
                            progressAnimators[planet].start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    progressAnimators[planet].start();
                }
            }


            TextView timerText = page.findViewById(R.id.launchTimerText);
            timerText.setText(Utils.getTimeString(system.getPlanets().get(planet).getTimeRemaining()));

            TextView costText = page.findViewById(R.id.launchCostText);
            costText.setText(Utils.getStandardValue(system.getPlanets().get(planet).getLaunchCost(), true, false, 1));




        }

    }

    @SuppressWarnings("deprecation")
    private void addBottomDots(int currentPage) {
        dots = new TextView[planetAmount];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                dots[i].setText(" " + Html.fromHtml("&#8226;",Html.FROM_HTML_MODE_LEGACY));
            } else {
                dots[i].setText(" " + Html.fromHtml("&#8226;"));
            }
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactiveDot));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.colorNumber));
    }

    ViewPager.OnPageChangeListener planetPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if(position == 0) prevButton.setVisibility(View.INVISIBLE);
            else prevButton.setVisibility(View.VISIBLE);

            if (position == planetAmount - 1) nextButton.setVisibility(View.INVISIBLE);
            else nextButton.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}
        @Override
        public void onPageScrollStateChanged(int arg0) {}
    };

    View.OnClickListener clickPrev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            planetPager.setCurrentItem(planetPager.getCurrentItem() - 1);
        }
    };
    View.OnClickListener clickNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            planetPager.setCurrentItem(planetPager.getCurrentItem() + 1);
        }
    };

}
