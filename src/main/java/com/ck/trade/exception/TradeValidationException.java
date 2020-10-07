package com.ck.trade.exception; 

/**
 * 
 * @author ckarale
 *
 */
public class TradeValidationException extends Exception {
	public static final String ERR_OLD_TRADE_VERSION="Trade with higher version already executed";
	public static final String ERR_TRADE_WITH_LESS_MATURITYDATE ="Trade has less maturity date then today date";
	private ErrorCode errorCode;
	private String tradeId;
	public TradeValidationException(ErrorCode errorCode, String tradeId) {
		super();
		this.errorCode = errorCode;
		this.tradeId= tradeId;
	}

	@Override
	public String getMessage() {
		return errorCode.getMessage() + " TradeId"+tradeId;
	}
	

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getTradeId() {
		return tradeId;
	}
	

}
