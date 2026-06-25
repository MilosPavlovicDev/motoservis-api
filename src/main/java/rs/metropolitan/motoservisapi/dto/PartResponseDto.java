package rs.metropolitan.motoservisapi.dto;

import lombok.Data;

@Data
public class PartResponseDto {
    private Long id;
    private String name;
    private Double price;
    private Long serviceRecordId;
    private String serviceRecordInfo;
}
