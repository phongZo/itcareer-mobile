package graduate.itdreams.android.ui.main.simulation.overview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.response.ItemTitleContentResponse;
import graduate.itdreams.android.databinding.FragmentOverviewBinding;
import graduate.itdreams.android.di.component.FragmentComponent;
import graduate.itdreams.android.ui.base.fragment.BaseFragment;
import graduate.itdreams.android.ui.main.simulation.SimulationOverviewViewModel;


public class OverviewFragment extends BaseFragment<FragmentOverviewBinding, OverviewViewModel> {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SimulationOverviewViewModel activityViewModel = new ViewModelProvider(requireActivity()).get(SimulationOverviewViewModel.class);

        activityViewModel.getSimulationDetail().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                String overviewJson = response.getOverview();
                String descriptionJson = response.getDescription();
                Gson gson = new Gson();

                ItemTitleContentResponse itemDescription = gson.fromJson(descriptionJson, ItemTitleContentResponse.class);
                binding.tvTitleDescription.setText(itemDescription.getTitle());
                binding.description.setText(itemDescription.getContent());

                Type listType = new TypeToken<List<ItemTitleContentResponse>>(){}.getType();
                List<ItemTitleContentResponse> overviewList = gson.fromJson(overviewJson, listType);

                OverviewAdapter overviewAdapter = new OverviewAdapter(overviewList);
                binding.rcvOverview.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.rcvOverview.setAdapter(overviewAdapter);

            }
        });
    }


    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_overview;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}