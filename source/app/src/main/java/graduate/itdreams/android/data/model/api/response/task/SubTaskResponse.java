package graduate.itdreams.android.data.model.api.response.task;

import lombok.Data;

@Data
public class SubTaskResponse {
    private String name;
    private String content;

    public SubTaskResponse(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
