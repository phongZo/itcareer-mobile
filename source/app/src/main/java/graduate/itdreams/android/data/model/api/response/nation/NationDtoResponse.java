package graduate.itdreams.android.data.model.api.response.nation;

import lombok.Data;

@Data
public class NationDtoResponse {
    private Long id;
    private Integer kind;
    private String name;
    private NationResponse parent;
    private String postCode;
}
