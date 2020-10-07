package com.ck.trade.model;

import java.util.Date; 

/**
 * Trade Object
 * 
 * @author ckarale
 *
 */
public class Trade {
	private String id;
	private int version;
	private String counterPartyId;
	private String bookId;
	private Date maturityDate;
	private Date createdDate;
	private boolean isExpired;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	/**
	 * 
	 * { "id":"100", "version":"1", "counterPartyId":"cp1", "bookId":"bkid-01",
	 * "maturityDate":"04/10/2020", "isExpired":"false" }
	 */

	/**
	 * [ { "id": "100", "version": 1, "counterPartyId": "cp1", "bookId": "bkid-01",
	 * "maturityDate": "2020-10-05T00:00:00.000+00:00", "createdDate":
	 * "2020-10-04T11:51:37.784+00:00", "expired": false }, { "id": "101",
	 * "version": 1, "counterPartyId": "cp1", "bookId": "bkid-01", "maturityDate":
	 * "2020-10-05T00:00:00.000+00:00", "createdDate":
	 * "2020-10-04T11:52:33.608+00:00", "expired": false } ]
	 */

}
