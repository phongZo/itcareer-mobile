package graduate.itdreams.android.data.model.api.request.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String email;
    private String fullname;
    private String username;
    private String birthday;
}
