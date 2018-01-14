package entity;

import java.math.BigDecimal;

public class TradedGoods {

	private String orderNo;
	
	private String goodsNo;
	
	private BigDecimal costPrice;//成本价
	
	private BigDecimal marketPrice;//市场价
	
	private BigDecimal sellPrice;//销售价
	
	private Integer number;//数量
	
	private String status;//状态
	
	private Integer row;//行数
	
	private int destGoodsNoCellColumn = -1;
	private int destCostPriceCellColumn = -1;
	private int destMarketPriceCellColumn = -1;
	private int destSellPriceCellColumn = -1;
	private int destNumberCellColumn = -1;
	private int destStatusCellColumn = -1;
	private int destOrderNoCellColumn = -1;
	

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}
	
	

	public int getDestGoodsNoCellColumn() {
		return destGoodsNoCellColumn;
	}

	public void setDestGoodsNoCellColumn(int destGoodsNoCellColumn) {
		this.destGoodsNoCellColumn = destGoodsNoCellColumn;
	}

	public int getDestCostPriceCellColumn() {
		return destCostPriceCellColumn;
	}

	public void setDestCostPriceCellColumn(int destCostPriceCellColumn) {
		this.destCostPriceCellColumn = destCostPriceCellColumn;
	}

	public int getDestMarketPriceCellColumn() {
		return destMarketPriceCellColumn;
	}

	public void setDestMarketPriceCellColumn(int destMarketPriceCellColumn) {
		this.destMarketPriceCellColumn = destMarketPriceCellColumn;
	}

	public int getDestSellPriceCellColumn() {
		return destSellPriceCellColumn;
	}

	public void setDestSellPriceCellColumn(int destSellPriceCellColumn) {
		this.destSellPriceCellColumn = destSellPriceCellColumn;
	}

	public int getDestNumberCellColumn() {
		return destNumberCellColumn;
	}

	public void setDestNumberCellColumn(int destNumberCellColumn) {
		this.destNumberCellColumn = destNumberCellColumn;
	}

	public int getDestStatusCellColumn() {
		return destStatusCellColumn;
	}

	public void setDestStatusCellColumn(int destStatusCellColumn) {
		this.destStatusCellColumn = destStatusCellColumn;
	}

	public int getDestOrderNoCellColumn() {
		return destOrderNoCellColumn;
	}

	public void setDestOrderNoCellColumn(int destOrderNoCellColumn) {
		this.destOrderNoCellColumn = destOrderNoCellColumn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((costPrice == null) ? 0 : costPrice.hashCode());
		result = prime * result + ((goodsNo == null) ? 0 : goodsNo.hashCode());
		result = prime * result
				+ ((marketPrice == null) ? 0 : marketPrice.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result
				+ ((sellPrice == null) ? 0 : sellPrice.hashCode());
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
		TradedGoods other = (TradedGoods) obj;
		if (costPrice == null) {
			if (other.costPrice != null)
				return false;
		} else if (!costPrice.equals(other.costPrice))
			return false;
		if (goodsNo == null) {
			if (other.goodsNo != null)
				return false;
		} else if (!goodsNo.equals(other.goodsNo))
			return false;
		if (marketPrice == null) {
			if (other.marketPrice != null)
				return false;
		} else if (!marketPrice.equals(other.marketPrice))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (sellPrice == null) {
			if (other.sellPrice != null)
				return false;
		} else if (!sellPrice.equals(other.sellPrice))
			return false;
		return true;
	}

}
