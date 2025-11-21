package graduate.itdreams.android.ui.main.taskdetail;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.response.task.SubTaskResponse;
import graduate.itdreams.android.data.model.api.response.task.TaskResponse;
import graduate.itdreams.android.databinding.ActivityTaskDetailBinding;
import graduate.itdreams.android.di.component.ActivityComponent;
import graduate.itdreams.android.ui.base.activity.BaseActivity;

public class TaskDetailActivity extends BaseActivity<ActivityTaskDetailBinding,TaskDetailViewModel> {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout ;
    private NavigationView navigationView ;
    private TaskDrawerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tab_content_frame, new SubTaskFragment()) // hoặc Fragment bạn muốn hiển thị
                    .commit();
        }
        initDrawer();
        initRecyclerView();
        initViewModel();
    }
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TaskDetailViewModel.class);

        Long simulationId = getIntent().getLongExtra("simulation_id", -1L);
        viewModel.fetchListTask(simulationId);

        viewModel.getTasks().observe(this, taskItems -> {
            adapter.setTaskItems(taskItems);
            loadFirstSubTask(taskItems);
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = viewBinding.recyclerTaskList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TaskDrawerAdapter((task, subTask) -> onSubTaskSelected(task, subTask));
        recyclerView.setAdapter(adapter);
    }
    private void onSubTaskSelected(TaskResponse task, SubTaskResponse subTask) {
        SubTaskFragment fragment = (SubTaskFragment) getSupportFragmentManager()
                .findFragmentById(R.id.tab_content_frame);
        if (fragment != null) {
            fragment.loadSubTask(task, subTask);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void initDrawer() {
        drawerLayout = viewBinding.drawerLayout;
        navigationView = viewBinding.navigationView;

        viewBinding.btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Click bất kỳ đâu ngoài drawer cũng đóng drawer
        viewBinding.menuContainer.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void loadFirstSubTask(List<TaskResponse> taskItems) {
        if (taskItems.isEmpty()) return;

        TaskResponse firstTask = taskItems.get(0);
        SubTaskResponse firstSubTask = firstTask.getSubTasks().get(0);

        SubTaskFragment fragment = (SubTaskFragment) getSupportFragmentManager()
                .findFragmentById(R.id.tab_content_frame);
        if (fragment != null) {
            fragment.loadSubTask(firstTask, firstSubTask);
        }

        adapter.selectDefaultSubTask(firstTask, firstSubTask);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_detail;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}
