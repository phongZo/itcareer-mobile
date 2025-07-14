package graduate.itdreams.android.data.model.api.request.employee;

import lombok.Data;

@Data
public class UpdateEmployeeRequest {
    private String avatarPath;
    private String code;
    private String email;
    private String fullName;
    private Long id;
    private Boolean isManager;
    private String name;
    private String oldPassword;
    private String password;
    private String phone;
    private Integer status;
}
