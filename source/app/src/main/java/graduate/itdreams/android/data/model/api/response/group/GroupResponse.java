package graduate.itdreams.android.data.model.api.response.group;

import java.time.LocalDateTime;
import java.util.List;

import graduate.itdreams.android.data.model.api.response.permisssion.PermissionResponse;
import lombok.Data;

@Data
public class GroupResponse {
    private String createdBy;
    private LocalDateTime createdDate;
    private String description;
    private Long id;
    private Boolean isSystemRole;
    private Integer kind;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
    private String name;
    private List<PermissionResponse> permissions;
    private Long reusedId;
    private Integer status;
}
