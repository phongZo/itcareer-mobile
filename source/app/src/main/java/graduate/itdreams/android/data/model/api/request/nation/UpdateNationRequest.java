package graduate.itdreams.android.data.model.api.request.nation;

import lombok.Data;

@Data
public class UpdateNationRequest {
    private Long id;
    private String name;
    private Long parentId;
    private String postCode;
    private Integer status;
}
