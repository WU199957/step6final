package service;

import entity.Position;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TradeCSVReader {

    public static List<Position.TradeInformation> readTradeInformationFromCSV(String filename)  {
        List<Position.TradeInformation> tradeInfoList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 5) {
                    LocalDateTime tradeDateTime = LocalDateTime.parse(parts[0], formatter);
                    String productName = parts[1];
                    String buyOrSell = parts[2];
                    BigDecimal quantity = new BigDecimal(parts[3]);
                    BigDecimal unitPrice = new BigDecimal(parts[4]);

                    Position.TradeInformation tradeInfo = new Position.TradeInformation(tradeDateTime, productName,
                            buyOrSell, quantity, unitPrice);
                    tradeInfoList.add(tradeInfo);
                }
            }
            tradeInfoList.sort(Comparator.comparing(
                    Position.TradeInformation::getTradeDateTime,Comparator.reverseOrder()));
        }catch (FileNotFoundException e){
            System.out.println("見つかりません");
        }catch (IOException e){
            System.out.println("error");
        }

        return tradeInfoList;
    }
}
