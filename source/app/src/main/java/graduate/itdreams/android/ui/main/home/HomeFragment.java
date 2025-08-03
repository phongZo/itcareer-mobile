package graduate.itdreams.android.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import graduate.itdreams.android.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.response.simulation.SimulationResponse;
import graduate.itdreams.android.databinding.FragmentHomeBinding;
import graduate.itdreams.android.di.component.FragmentComponent;
import graduate.itdreams.android.ui.base.fragment.BaseFragment;
import graduate.itdreams.android.ui.main.simulation.SimulationOverviewActivity;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {
    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        performDataBinding();

        customBtnSearch();

        loadJobs();

        return binding.getRoot();
    }
    private void loadJobs() {
        viewModel.fetchSimulationList();
        viewModel.getPostList().observe(getViewLifecycleOwner(), postList -> {
            if (postList == null || postList.isEmpty()) return;

            // Phân trang mỗi trang 4 item
            List<List<SimulationResponse>> pages = new ArrayList<>();
            for (int i = 0; i < postList.size(); i += 4) {
                pages.add(postList.subList(i, Math.min(i + 4, postList.size())));
            }

            SimulationPagerAdapter adapter = new SimulationPagerAdapter(pages, viewModel, item -> {
                Intent intent = new Intent(getContext(), SimulationOverviewActivity.class);
                intent.putExtra("item_id", item);
                startActivity(intent);
            });

            binding.viewPager.setAdapter(adapter);
            setupIndicator(pages.size());
            setCurrentIndicator(0);

            binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setCurrentIndicator(position);
                }
            });
        });

    }

    private void customBtnSearch() {
        View searchPlate = binding.searchView.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackground(null);
        }
        ImageView searchIcon = binding.searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        searchIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.bg_btn));

    }

    private void setupIndicator(int count) {
        binding.indicatorLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            View dot = new View(requireContext());
            int size = (int) getResources().getDimension(R.dimen._8sdp);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dot.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.indicator_unactive));
            binding.indicatorLayout.addView(dot);
        }
    }


    private void setCurrentIndicator(int index) {
        int childCount = binding.indicatorLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View dot = binding.indicatorLayout.getChildAt(i);
            int drawableId = (i == index)
                    ? R.drawable.indicator_active
                    : R.drawable.indicator_unactive;
            dot.setBackground(ContextCompat.getDrawable(requireContext(), drawableId));
        }
    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
