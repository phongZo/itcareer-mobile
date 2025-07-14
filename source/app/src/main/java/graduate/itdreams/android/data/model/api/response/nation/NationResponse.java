package graduate.itdreams.android.data.model.api.response.nation;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NationResponse {
    private String createdBy;
    private LocalDateTime createdDate;
    private Long id;
    private Integer kind;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
    private String name;
    private Object parent; // Nếu có cấu trúc cụ thể hơn thì thay `Object` bằng kiểu thích hợp
    private String postCode;
    private Long reusedId;
    private Integer status;
}
