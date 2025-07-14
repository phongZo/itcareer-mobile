package graduate.itdreams.android.data.model.api.request.category;

import lombok.Data;

@Data
public class UpdateCategoryRequest {
    private Long categoryId;
    private String description;
    private String image;
    private String name;
    private Integer ordering;
}
