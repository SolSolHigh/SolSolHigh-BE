package com.shinhan.solsolhigh.egg.domain;

import com.shinhan.solsolhigh.egg.application.dto.EggSellBoardRegisterRequest;
import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "egg_sell_board")
@Entity
public class EggSellBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "egg_sell_board_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hold_special_egg_id")
    private HoldSpecialEgg holdSpecialEgg;

    @Column(name = "wrote_at")
    private LocalDateTime wroteAt;

    @Column(name = "egg_price_per_once")
    private Integer eggPricePerOnce;

    @Column(name = "sell_count")
    private Integer sellCount;

    public static EggSellBoard create(Child child, EggSellBoardRegisterRequest request, HoldSpecialEgg holdSpecialEgg , LocalDateTime wroteAt) {
        return EggSellBoard.builder()
                .child(child)
                .holdSpecialEgg(holdSpecialEgg)
                .wroteAt(wroteAt)
                .sellCount(request.getSellCount())
                .eggPricePerOnce(request.getPricePerOnce())
                .build();
    }
}
