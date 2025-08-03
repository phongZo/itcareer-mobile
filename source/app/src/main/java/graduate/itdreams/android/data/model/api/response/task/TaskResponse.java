package graduate.itdreams.android.data.model.api.response.task;

import java.util.List;

import graduate.itdreams.android.data.model.api.response.simulation.SimulationDetailResponse;
import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String content;
    private String description;
    private String name;
    private SimulationDetailResponse simulation;


//    public TaskResponse(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
//
//    public TaskResponse(String title, String content, List<SubTaskResponse> subTasks) {
//        this.title = title;
//        this.content = content;
//        this.subTasks = subTasks;
//    }
}
