package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class EggSellBoardNotSufficientException extends CustomException {
    public EggSellBoardNotSufficientException() {
        super(EggExceptionConstants.EGG_SELL_BOARD_NOT_SUFFICIENT);
    }
}
