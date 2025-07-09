package com.springFramework.mm.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

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
    @JoinColumn(name = "Factilty_id")
    private Facility facility;   // 저장소가 있는 시설
}
