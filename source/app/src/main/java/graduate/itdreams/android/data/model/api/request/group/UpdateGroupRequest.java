package graduate.itdreams.android.data.model.api.request.group;

import java.util.List;

import lombok.Data;

@Data
public class UpdateGroupRequest {
    private String description;
    private Long id;                  // required
    private String name;              // required
    private List<Long> permissions;   // required - list of permission IDs
}
