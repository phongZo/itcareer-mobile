package graduate.itdreams.android.ui.main.taskdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bolts.Task;
import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.data.model.api.response.task.SubTaskResponse;
import graduate.itdreams.android.data.model.api.response.task.TaskResponse;
import graduate.itdreams.android.ui.base.activity.BaseViewModel;

public class TaskDetailViewModel extends BaseViewModel {
    private final MutableLiveData<List<TaskResponse>> tasksLiveData = new MutableLiveData<>();

    public TaskDetailViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
        loadMockData();
    }

    private void loadMockData() {
        List<TaskResponse> mockTasks = new ArrayList<>();

//        mockTasks.add(new TaskResponse("1", "Lập trình Android", Arrays.asList(
//                new SubTaskResponse("1.1", "Activity & Fragment"),
//                new SubTaskResponse("1.2", "RecyclerView"),
//                new SubTaskResponse("1.3", "Navigation Drawer")
//        )));
//
//        mockTasks.add(new TaskResponse("2", "Thiết kế UI", Arrays.asList(
//                new SubTaskResponse("2.1", "ConstraintLayout"),
//                new SubTaskResponse("2.2", "Data Binding")
//        )));
//
//        mockTasks.add(new TaskResponse("3", "Kết nối API", Collections.emptyList()));
//
//        tasksLiveData.setValue(mockTasks);
    }

    public LiveData<List<TaskResponse>> getTasks() {
        return tasksLiveData;
    }
}
