package graduate.itdreams.android.data.model.api.request.news;

import lombok.Data;

@Data
public class UpdateNewsRequest {
    private String avatar;
    private String banner;
    private Long categoryId;
    private String content;
    private String description;
    private Long id;
    private Integer pinTop;
    private Integer status;
    private String title;
}
