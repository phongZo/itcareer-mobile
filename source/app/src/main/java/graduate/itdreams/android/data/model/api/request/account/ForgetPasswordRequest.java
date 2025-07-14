package graduate.itdreams.android.data.model.api.request.account;

import lombok.Data;

@Data
public class ForgetPasswordRequest {
    private String idHash;
    private String newPassword; // minLength: 6, maxLength: 2147483647
    private String otp;
}
