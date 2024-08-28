package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class EggSellBoardNotFoundException extends CustomException {
    public EggSellBoardNotFoundException() {
        super(EggExceptionConstants.EGG_SELL_BOARD_NOT_FOUND);
    }
}
