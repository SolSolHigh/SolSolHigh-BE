package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class EggSellBoardCanNotAccessException extends CustomException {
    public EggSellBoardCanNotAccessException() {
        super(EggExceptionConstants.EGG_SELL_BOARD_CAN_NOT_ACCESS);
    }
}
