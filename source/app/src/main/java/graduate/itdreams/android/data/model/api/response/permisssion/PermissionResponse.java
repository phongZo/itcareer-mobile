package graduate.itdreams.android.data.model.api.response.permisssion;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PermissionResponse {
    private String action;
    private String createdBy;
    private LocalDateTime createdDate;
    private String description;
    private Long id;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
    private String name;
    private String nameGroup;
    private String pcode;
    private Long reusedId;
    private Boolean showMenu;
    private Integer status;
}
