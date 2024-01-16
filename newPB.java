package service;

import entity.Position;
import show.NewStockSystemUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


public class newPB {

    public final static String ouptput = "/Users/wuyingtao/Desktop/step6/output.csv";
    private ArrayList<Position> allPositions;
    private List<Position.TradeInformation> tradeRecordList;
    private Map<String, BigDecimal> marketPriceMap;
    private List<Position.Stock> stocks;


    public newPB(String csvFilePath)  {
        this.tradeRecordList = TradeCSVReader.readTradeInformationFromCSV(csvFilePath); // 正确初始化类成员变量
        this.allPositions = new ArrayList<>();
        this.marketPriceMap = new HashMap<>();
        Portfolio(tradeRecordList,null);
    }
    public void update(String csvFilePath,Map<String, BigDecimal> newMarketPrices) {
        this.tradeRecordList = TradeCSVReader.readTradeInformationFromCSV(csvFilePath); // 正确初始化类成员变量
        this.allPositions = new ArrayList<>();
        this.marketPriceMap = new HashMap<>();
        Portfolio(tradeRecordList,newMarketPrices);
    }


    // 从trade csv文件中读取所有的trade: tradeRecordList <- 假设你已经读取好了
    public void Portfolio(List<Position.TradeInformation> tradeRecordList, Map<String, BigDecimal> newMarketPrices) {
        // 提取出所有的trade name 然后用set去重
        HashSet<String> allName = new HashSet<>();
        for (Position.TradeInformation tradeInformation : tradeRecordList) {
            allName.add(tradeInformation.getProductName());
        }

        // 遍历set<trade name>
        for (String tradeName : allName) {
            // 提取交易记录中名字是遍历中name的所有交易记录 -> 按照时间排序(sorted中自己写)
            List<Position.TradeInformation> neededTradeList = tradeRecordList.stream()
                    .filter(a -> a.getProductName().equals(tradeName))
                    .sorted(Comparator.comparing(Position.TradeInformation::getTradeDateTime))
                    .toList();

            // 遍历交易记录
            Position position = new Position(tradeName);

            for (Position.TradeInformation tradeInformation : neededTradeList) {
                String type = tradeInformation.getBuyOrSell();
                BigDecimal quantity = tradeInformation.getQuantity();
                BigDecimal unitPrice = tradeInformation.getUnitPrice();
                calculate(type,position,quantity,unitPrice);
            }

            position.setValuation(BigDecimal.ZERO);
            position.setUnrealizedProfitAndLoss(BigDecimal.ZERO);
            String company = codeToName(tradeName);
            position.setCompany(company);
            allPositions.add(position);
        }
        if(newMarketPrices != null){
            updateMarketPrice(newMarketPrices);
        }
    }
    //计算数量，单价，损益

    public void calculate (String type,Position position,BigDecimal quantity,BigDecimal price){
        if (type.equals("买")) {
            BigDecimal temp1 = position.getQuantity().multiply(position.getAveragePrice());
            BigDecimal temp2 = quantity.multiply(price);
            BigDecimal temp3 = temp1.add(temp2);
            BigDecimal temp4 = quantity.add(position.getQuantity());
            position.setAveragePrice(temp3.divide(temp4, 2, RoundingMode.HALF_UP));
            position.setQuantity(position.getQuantity().add(quantity));

        } else {
            position.setRealizedProfitLoss(position.getRealizedProfitLoss().
                    add(quantity.multiply(price.subtract(position.getAveragePrice()))));
            position.setQuantity(position.getQuantity().subtract(quantity));
            if (position.getQuantity().intValue() == 0) {
                position.setAveragePrice(BigDecimal.ZERO);
            }
        }

    }

    public HashSet<String> rightTradeName() {
        HashSet<String> allName = new HashSet<>();

        for (Position.TradeInformation tradeInformation : tradeRecordList) {
            allName.add(tradeInformation.getProductName());
        }

        return allName;
    }
    public Map<String, BigDecimal> getMarketPrice() {
        Scanner sc = new Scanner(System.in);
        Map<String, BigDecimal> marketPriceMap = new HashMap<>();
        HashSet<String> validTradeNames = rightTradeName();

        for (String tradeName : validTradeNames) {
            System.out.println("请输入 " + tradeName + " 的市场价格:");

            BigDecimal marketPrice;
            while (true) {
                try {
                    marketPrice = sc.nextBigDecimal();
                    break;  // 如果输入有效，退出循环
                } catch (InputMismatchException e) {
                    System.out.println("无效的输入，请输入一个数值:");
                    sc.next();  // 清除无效输入
                }
            }
            marketPriceMap.put(tradeName, marketPrice);
        }

        return marketPriceMap;
    }
    public String codeToName(String code){
        this.stocks= NewStockSystemUtil.readStocksFromCSV(ouptput);
        Map<String,String>codeAndName=new HashMap<>();
        for (Position.Stock stock : stocks) {
            String stockCode = stock.getCode(); // 使用局部变量stockCode
            String name = stock.getProductName();
            codeAndName.put(stockCode, name);
        }
        return codeAndName.get(code);
    }

    //如果有市场价格的话，计算评价额与评价损益
    public void updateMarketPrice(Map<String, BigDecimal> newMarketPriceMap) {
        for (Position position : allPositions) {
            BigDecimal bigDecimal = newMarketPriceMap.get(position.getProductName());
            if (bigDecimal != null) {
                BigDecimal multiply = bigDecimal.multiply(position.getQuantity());
                position.setValuation(multiply);
                BigDecimal acquisitionCost = position.getQuantity().multiply(position.getAveragePrice());
                BigDecimal UnrealizedProfitAndLoss = position.getValuation().subtract(acquisitionCost);
                position.setUnrealizedProfitAndLoss(UnrealizedProfitAndLoss);
            } else {
                // 处理 bigDecimal 为 null 的情况
                position.setValuation(BigDecimal.ZERO);
                position.setUnrealizedProfitAndLoss(BigDecimal.ZERO);
            }
        }
    }

    public static void print(newPB portfolio){
        System.out.println("code---公司名字-----持有数量----平均取得单价-----实现损益----评价额----评价损益");


        for (Position position : portfolio.allPositions) {

            System.out.println(
                    position.getProductName() +"--|"+
                            position.getCompany()+"----|"+
                            position.getQuantity() +"---|"+
                            position.getAveragePrice() +"---|"+ position.getRealizedProfitLoss()+"---|"+
                            position.getValuation()+ "---|"+ position.getUnrealizedProfitAndLoss());
        }
    }
}

