package graduate.itdreams.android.data.model.api.response.nation;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NationAdminDtoResponse {
    private LocalDateTime createdDate;
    private Long id;
    private Integer kind;
    private LocalDateTime modifiedDate;
    private String name;
    private NationDtoResponse parent;
    private String postCode;
    private Integer status;
}
