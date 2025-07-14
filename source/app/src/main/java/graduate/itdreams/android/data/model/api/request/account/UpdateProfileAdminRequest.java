package graduate.itdreams.android.data.model.api.request.account;

import lombok.Data;
@Data
public class UpdateProfileAdminRequest {
    private String avatarPath;
    private String fullName;     // required
    private String oldPassword;  // required
    private String password;
    private String phone;
}
