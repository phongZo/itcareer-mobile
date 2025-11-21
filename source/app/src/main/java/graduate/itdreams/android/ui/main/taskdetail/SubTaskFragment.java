package graduate.itdreams.android.ui.main.taskdetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.response.ItemTitleContentResponse;
import graduate.itdreams.android.data.model.api.response.task.SubTaskResponse;
import graduate.itdreams.android.data.model.api.response.task.TaskResponse;
import graduate.itdreams.android.databinding.FragmentSubTaskBinding;
import graduate.itdreams.android.di.component.FragmentComponent;
import graduate.itdreams.android.ui.base.fragment.BaseFragment;
import graduate.itdreams.android.ui.main.simulation.SimulationOverviewActivity;
import graduate.itdreams.android.ui.main.simulation.overview.OverviewAdapter;

public class SubTaskFragment extends BaseFragment<FragmentSubTaskBinding,SubTaskViewModel> {
    SubTaskResponse subTask;

    public void loadSubTask(TaskResponse task,SubTaskResponse subTask) {
        viewModel.fetchSubtaskDetail(subTask.getId());
        viewModel.getSubTaskDetail().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                String introductionJson = response.getIntroduction();
                Gson gson = new Gson();

                Type listType = new TypeToken<List<ItemTitleContentResponse>>(){}.getType();
                List<ItemTitleContentResponse> overviewList = gson.fromJson(introductionJson, listType);

                OverviewAdapter overviewAdapter = new OverviewAdapter(overviewList);
                binding.rcvContent.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.rcvContent.setAdapter(overviewAdapter);
                if(response.getFilePath() == null){
                    binding.documentButton.setVisibility(View.GONE);
                }
            }
        });
    }
    public void viewFileClick(){
        String url = viewModel.getSubTaskDetail().getValue().getFilePath();
        Intent intent = new Intent(getContext(), PdfActivity.class);
        intent.putExtra("url_pdf", url );
        startActivity(intent);
    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_task;
    }

    @Override
    protected void performDataBinding() {
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setF(this);
        binding.setVm(viewModel);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}