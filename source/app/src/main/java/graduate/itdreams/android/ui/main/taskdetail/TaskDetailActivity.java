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

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
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

        drawerLayout = viewBinding.drawerLayout;
        navigationView = viewBinding.navigationView;

        viewBinding.btnMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        RecyclerView recyclerView = viewBinding.recyclerTaskList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TaskDrawerAdapter((task, subTask) -> {
            if (task != null && subTask != null) {
                SubTaskFragment fragment = (SubTaskFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.tab_content_frame);
                if (fragment != null) {
                    fragment.loadSubTask(task, subTask);  // truyền cả TaskResponse
                }
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(TaskDetailViewModel.class);
        viewModel.getTasks().observe(this, taskItems -> adapter.setTaskItems(taskItems));
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
