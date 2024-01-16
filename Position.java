package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Position {
    private String ProductName;
    private String company;
    private BigDecimal quantity;
    private BigDecimal AveragePrice;
    private BigDecimal realizedProfitLoss;
    private BigDecimal valuation;
    private BigDecimal UnrealizedProfitAndLoss;

    public Position() {
    }

    public Position(String ProductName) {
        this.ProductName = ProductName;
        this.company=company;
        this.quantity = BigDecimal.ZERO;
        this.AveragePrice = BigDecimal.ZERO;
        this.realizedProfitLoss = BigDecimal.ZERO;
        this.valuation=BigDecimal.ZERO;
        this.UnrealizedProfitAndLoss=BigDecimal.ZERO;
    }

    public Position(String productName, String company,BigDecimal quantity, BigDecimal AveragePrice, BigDecimal valuation) {
        ProductName = productName;
        company=company;
        this.quantity = quantity;
        this.AveragePrice = AveragePrice;
        this.valuation=valuation;
        this.UnrealizedProfitAndLoss=UnrealizedProfitAndLoss;
    }

    public BigDecimal getRealizedProfitLoss() {
        return realizedProfitLoss;
    }

    public void setRealizedProfitLoss(BigDecimal realizedProfitLoss) {
        this.realizedProfitLoss = realizedProfitLoss;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAveragePrice() {
        return AveragePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.AveragePrice = averagePrice;
    }

    public BigDecimal getValuation() {
        return valuation;
    }

    public void setValuation(BigDecimal valuation) {
        this.valuation = valuation;
    }

    public BigDecimal getUnrealizedProfitAndLoss() {
        return UnrealizedProfitAndLoss;
    }

    public void setUnrealizedProfitAndLoss(BigDecimal unrealizedProfitAndLoss) {
        UnrealizedProfitAndLoss = unrealizedProfitAndLoss;
    }

    public static class TradeInformation {
        private LocalDateTime tradeDateTime;
        private String productName;
        private String buyOrSell;
        private BigDecimal quantity;
        private BigDecimal unitPrice;

        public TradeInformation() {
        }

        public TradeInformation(LocalDateTime tradeDateTime, String productName, String buyOrSell, BigDecimal quantity, BigDecimal unitPrice) {
            this.tradeDateTime = tradeDateTime;
            this.productName = productName;
            this.buyOrSell = buyOrSell;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public LocalDateTime getTradeDateTime() {
            return tradeDateTime;
        }

        public void setTradeDateTime(LocalDateTime tradeDateTime) {
            this.tradeDateTime = tradeDateTime;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getBuyOrSell() {
            return buyOrSell;
        }

        public void setBuyOrSell(String buyOrSell) {
            this.buyOrSell = buyOrSell;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }
    }

    public static class Stock {
        private String code;
        private String productName;
        private String market;
        private String sharesIssued;

        public Stock(String code, String productName, String market, String sharesIssued) {
            this.code = code;
            this.productName = productName;
            this.market = market;
            this.sharesIssued = sharesIssued;
        }

        public String getCode() {
            return code;
        }

        public String getProductName() {
            return productName;
        }

        public String getMarket() {
            return market;
        }

        public String getSharesIssued() {
            return sharesIssued;
        }
    }
}


