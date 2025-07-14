package graduate.itdreams.android.data.model.api.request.group;

import java.util.List;

import lombok.Data;

@Data
public class CreateGroupRequest {
    private String description;       // required
    private Integer kind;             // required
    private String name;              // required
    private List<Long> permissions;
}
