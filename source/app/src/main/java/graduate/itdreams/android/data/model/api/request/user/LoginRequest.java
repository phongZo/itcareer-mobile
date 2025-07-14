package graduate.itdreams.android.data.model.api.request.user;

import lombok.Data;

@Data
public class LoginRequest {
    String password;
    String phone;
}
