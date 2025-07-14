package graduate.itdreams.android.data.model.api.request.account;

import lombok.Data;

@Data
public class UpdateAccountAdminRequest {
    private String avatarPath;
    private String email;
    private String fullName;
    private Long groupId;
    private Long id;
    private String password;
    private String phone;
    private Integer status;
}
