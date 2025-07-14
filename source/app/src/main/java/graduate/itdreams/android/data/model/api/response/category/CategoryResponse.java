package graduate.itdreams.android.data.model.api.response.category;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CategoryResponse {
    private LocalDateTime createdDate;
    private String description;
    private Long id;
    private String image;
    private Integer kind;
    private LocalDateTime modifiedDate;
    private String name;
    private Integer ordering;
    private Integer status;
}
