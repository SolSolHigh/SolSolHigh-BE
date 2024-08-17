package com.shinhan.solsolhigh.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "parent")
@Entity
@DiscriminatorValue("p")
public class Parent extends User{

}
