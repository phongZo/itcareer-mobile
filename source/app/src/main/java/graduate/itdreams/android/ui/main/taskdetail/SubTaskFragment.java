package graduate.itdreams.android.ui.main.taskdetail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.response.task.SubTaskResponse;
import graduate.itdreams.android.data.model.api.response.task.TaskResponse;
import graduate.itdreams.android.databinding.FragmentSubTaskBinding;
import graduate.itdreams.android.di.component.FragmentComponent;
import graduate.itdreams.android.ui.base.fragment.BaseFragment;

public class SubTaskFragment extends BaseFragment<FragmentSubTaskBinding,SubTaskViewModel> {

    public void loadSubTask(TaskResponse task,SubTaskResponse subTask) {
        // Ví dụ đơn giản: show lên UI, bạn có thể dùng LiveData để binding
        binding.content.setText(subTask.getIntroduction());

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
        binding.setF(this);
        binding.setVm(viewModel);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}