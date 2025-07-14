package graduate.itdreams.android.data.model.api.request.account;

import lombok.Data;

@Data
public class CreateAccountAdminRequest {
    private String avatarPath;
    private String email;
    private String fullName;
    private Long groupId;
    private Integer kind;
    private String password;
    private String phone;
    private Integer status;
    private String username;
}
