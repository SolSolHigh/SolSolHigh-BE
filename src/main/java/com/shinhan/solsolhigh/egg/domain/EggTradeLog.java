package com.shinhan.solsolhigh.egg.domain;

import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "egg_trade_log")
@Entity
public class EggTradeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "egg_trade_log_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Child seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Child buyer;

    @Column(name = "traded_at")
    private LocalDateTime tradedAt;

    @ManyToOne
    @JoinColumn(name = "special_egg_id")
    private SpecialEgg specialEgg;

    @Column(name = "egg_stock_count")
    private Integer eggStockCount;

    @Column(name = "egg_price_per_once")
    private Integer eggPricePerOnce;
}
