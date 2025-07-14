package graduate.itdreams.android.data.model.api.request.employee;

import lombok.Data;

@Data
public class CreateEmployeeRequest {
    private String avatarPath;
    private String code;
    private Long companyId;
    private String email;
    private String fullName; // Ví dụ: "Tam Nguyen"
    private Boolean manager;
    private String name;
    private String password;
    private String phone;
}
