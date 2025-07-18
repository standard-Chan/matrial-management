package com.springframework.mm.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)  // 데이터를 조회할 때, 항상 시설과 같이 가져올 필요가 있음
    @JoinColumn(name = "facility_id")
    private Facility facility;   // 저장소가 있는 시설

    @Version
    private Long version;
}
