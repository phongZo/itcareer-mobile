package graduate.itdreams.android.ui.main.simulation;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.response.simulation.SimulationResponse;
import graduate.itdreams.android.databinding.ActivitySimulationOverviewBinding;
import graduate.itdreams.android.di.component.ActivityComponent;
import graduate.itdreams.android.ui.base.activity.BaseActivity;
import graduate.itdreams.android.ui.main.home.SimulationPagerAdapter;


public class SimulationOverviewActivity extends BaseActivity<ActivitySimulationOverviewBinding, SimulationOverviewViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        viewBinding.setLifecycleOwner(this);
        viewBinding.btnBack.setOnClickListener(v -> finish());

        customTabLayout();

        long itemId = getIntent().getLongExtra("item_id", -1L);
        viewModel.fetchSimulationDetail(itemId);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_simulation_overview;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void customTabLayout() {
        TabLayout tabLayout = viewBinding.tabLayout;
        ViewPager2 viewPager = viewBinding.viewPager;

        SimulationOverviewViewPagerAdapter simulationOverviewViewPagerAdapter = new SimulationOverviewViewPagerAdapter(this);
        viewPager.setAdapter(simulationOverviewViewPagerAdapter);
        viewPager.setUserInputEnabled(false);
        tabLayout.addTab(tabLayout.newTab().setText("Tổng quan"));
        tabLayout.addTab(tabLayout.newTab().setText("Nhiệm vụ"));
        tabLayout.addTab(tabLayout.newTab().setText("Đánh giá"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }
}