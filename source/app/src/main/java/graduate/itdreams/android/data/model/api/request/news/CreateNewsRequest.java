package graduate.itdreams.android.data.model.api.request.news;

import lombok.Data;

@Data
public class CreateNewsRequest {
    private String avatar;
    private String banner;
    private Long categoryId;
    private String content;
    private String description;
    private Integer status;
    private String title;
}
