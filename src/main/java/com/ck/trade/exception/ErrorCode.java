package com.ck.trade.exception;

public enum ErrorCode {
	ERR_OLD_TRADE_VERSION("Trade with higher version already executed"),
	ERR_TRADE_WITH_LESS_MATURITYDATE("Trade has less maturity date then today date");

	private String message;

	ErrorCode( String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
