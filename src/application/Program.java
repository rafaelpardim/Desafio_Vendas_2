package application;

import entities.Sale;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Locale.setDefault(Locale.US);
        
        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();
        
        List<Sale> sales = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); 
            
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                int month = Integer.parseInt(fields[0]);
                int year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                int items = Integer.parseInt(fields[3]);
                double total = Double.parseDouble(fields[4]);
                
                sales.add(new Sale(month, year, seller, items, total));
            }
            
            Set<String> sellers = sales.stream().map(Sale::getSeller).collect(Collectors.toSet());
            
            System.out.println("Total de vendas por vendedor:");
            for (String seller : sellers) {
                double totalSales = sales.stream()
                        .filter(s -> s.getSeller().equals(seller))
                        .mapToDouble(Sale::getTotal)
                        .sum();
                System.out.printf("%s - R$ %.2f%n", seller, totalSales);
            }
            
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
        sc.close();
    }
}
