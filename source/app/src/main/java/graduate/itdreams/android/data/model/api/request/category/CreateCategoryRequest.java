package graduate.itdreams.android.data.model.api.request.category;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private String description;
    private String image;
    private Integer kind;
    private String name;
    private Integer ordering;
}
