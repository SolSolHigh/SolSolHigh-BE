package com.shinhan.solsolhigh.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Convert(converter = GenderConverter.class)
    @Column(name = "user_gender")
    private Gender gender;

    @Column
    private LocalDate birthday;


    public Integer getAge() {
        if(birthday == null){
            return null;
        }
        return LocalDate.now().getYear() - birthday.getYear() + 1;
    }
}
