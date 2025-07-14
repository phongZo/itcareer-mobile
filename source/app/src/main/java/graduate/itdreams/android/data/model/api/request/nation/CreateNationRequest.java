package graduate.itdreams.android.data.model.api.request.nation;

import lombok.Data;

@Data
public class CreateNationRequest {
    private Integer kind;
    private String name;
    private Long parentId;
    private String postCode;
    private Integer status;
}
