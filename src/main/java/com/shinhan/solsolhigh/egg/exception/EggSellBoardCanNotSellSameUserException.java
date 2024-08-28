package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class EggSellBoardCanNotSellSameUserException extends CustomException {
    public EggSellBoardCanNotSellSameUserException() {
        super(EggExceptionConstants.EGG_SELL_BOARD_CAN_NOT_SELL_SAME_USER);
    }
}
