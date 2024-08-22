package com.shinhan.solsolhigh.user.domain;

import com.shinhan.solsolhigh.user.exception.UserNicknameSameException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?")
@DiscriminatorColumn(name = "type")
@Entity
@Table(name = "user")
public abstract class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String nickname;

    @Convert(converter = GenderConverter.class)
    @Column(name = "user_gender")
    private Gender gender;

    @Column
    private LocalDate birthday;

    @Column
    private Boolean isDeleted;

    public Integer getAge() {
        if (birthday == null)
            return null;
        return LocalDate.now().getYear() - birthday.getYear() + 1;
    }

    @PrePersist
    public void prePersist() {
        this.isDeleted = false;
    }

    public void changeNickname(String nickname) {
        if(this.nickname.equals(nickname))
            throw new UserNicknameSameException();
        this.nickname = nickname;
    }
}
