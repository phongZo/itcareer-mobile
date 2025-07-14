package graduate.itdreams.android.data.model.api.request.user;

import lombok.Data;

@Data
public class SignUpRequest {
    private String avatarPath;
    private String birthday; // Định dạng ISO-8601 (yyyy-MM-dd'T'HH:mm:ss)
    private String email;

    private String fullName; // Bắt buộc (*)
    private String password; // Bắt buộc (*)
    private String phone;    // Bắt buộc (*)
}
