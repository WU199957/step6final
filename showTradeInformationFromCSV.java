package show;

import entity.Position;
import service.TradeCSVReader;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class showTradeInformationFromCSV {
    public static void printTradeInformation(String csvFilePath){
        List<Position.TradeInformation>tradeInfoList = TradeCSVReader.readTradeInformationFromCSV(csvFilePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        System.out.println("|日　　　　　　　　　　　\t|銘柄            \t|           売買\t|          数量\t|         取引単価｜");
        System.out.println("--------------------------------------------------------------------------------------------");
        tradeInfoList.forEach(s ->


                System.out.printf("|%-20s\t|%-15s\t|%13s\t|%13s\t|%15s| %n",
                        s.getTradeDateTime().format(formatter),
                        s.getProductName(),
                        s.getBuyOrSell(),
                        s.getQuantity(),
                        s.getUnitPrice()
                ));
    }
}
