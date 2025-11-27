package graduate.itdreams.android.ui.main.taskdetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
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
import graduate.itdreams.android.ui.main.simulation.task.TaskContentItemAdapter;
import graduate.itdreams.android.ui.main.taskdetail.quiz.QuestionQuizAdapter;

public class SubTaskFragment extends BaseFragment<FragmentSubTaskBinding,SubTaskViewModel> {

    public void loadSubTask(Long simulationId,SubTaskResponse subTask) {
        viewModel.fetchSubtaskDetail(subTask.getId());
        viewModel.getSubTaskDetail().observe(getViewLifecycleOwner(), responseSubtaskDetail -> {
            if (responseSubtaskDetail != null) {
                String introductionJson = responseSubtaskDetail.getIntroduction();
                Gson gson = new Gson();

                Type listType = new TypeToken<List<ItemTitleContentResponse>>(){}.getType();
                List<ItemTitleContentResponse> overviewList = gson.fromJson(introductionJson, listType);

                OverviewAdapter overviewAdapter = new OverviewAdapter(overviewList);
                binding.rcvContent.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.rcvContent.setAdapter(overviewAdapter);
                if(responseSubtaskDetail.getFilePath() == null){
                    binding.documentButton.setVisibility(View.GONE);
                }else {
                    binding.documentButton.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.createSubtaskProgress(subTask.getId());
        viewModel.getSubTaskProgress().observe(getViewLifecycleOwner(), responseSubtaskProgress -> {

        });
        viewModel.fetchListTaskQuestion(simulationId, subTask.getId());
        viewModel.getTaskQuestion().observe(getViewLifecycleOwner(), responseTaskQuestion -> {
            if(responseTaskQuestion != null){
                if(responseTaskQuestion.get(0).getQuestionType() != 3){
                    QuestionItemAdapter questionItemAdapter = new QuestionItemAdapter(responseTaskQuestion);
                    binding.rcvQuestionFileAndText.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.rcvQuestionFileAndText.setAdapter(questionItemAdapter);
                }else {
                    QuestionQuizAdapter adapter = new QuestionQuizAdapter(responseTaskQuestion);
                    binding.rcvQuestionFileAndText.setAdapter(adapter);
                    binding.rcvQuestionFileAndText.setLayoutManager(new LinearLayoutManager(requireContext()));
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