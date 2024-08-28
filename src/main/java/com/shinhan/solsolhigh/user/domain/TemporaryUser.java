package com.shinhan.solsolhigh.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temporary_user_id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
}
