package graduate.itdreams.android.data.model.api.request.company;

import lombok.Data;

@Data
public class UpdateCompanyRequest {
    private String banner;
    private String description;
    private String hotline;
    private Long id;
    private String logo;
    private String name;
    private String shortDescription;
}
