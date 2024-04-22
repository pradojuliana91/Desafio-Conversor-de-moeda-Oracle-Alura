import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;


public class Main {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        char choice;

        do {
            System.out.println("*********************************");
            System.out.println("Bem-vindo ao Conversor de Moedas!");
            System.out.println("*********************************");


            System.out.println("\nEscolha uma opção de conversão:");
            System.out.println("1. USD para EUR");
            System.out.println("2. EUR para USD");
            System.out.println("3. USD para JPY");
            System.out.println("4. JPY para USD");
            System.out.println("5. EUR para JPY");
            System.out.println("6. JPY para EUR");
            System.out.println("\n*********************************");


            System.out.print("Digite o número da opção desejada: ");
            int option = scanner.nextInt();


            String baseCurrency = "";
            String targetCurrency = "";
            switch (option) {
                case 1:
                    baseCurrency = "USD";
                    targetCurrency = "EUR";
                    break;
                case 2:
                    baseCurrency = "EUR";
                    targetCurrency = "USD";
                    break;
                case 3:
                    baseCurrency = "USD";
                    targetCurrency = "JPY";
                    break;
                case 4:
                    baseCurrency = "JPY";
                    targetCurrency = "USD";
                    break;
                case 5:
                    baseCurrency = "EUR";
                    targetCurrency = "JPY";
                    break;
                case 6:
                    baseCurrency = "JPY";
                    targetCurrency = "EUR";
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }

            if (!baseCurrency.isEmpty() && !targetCurrency.isEmpty()) {

                System.out.print("Digite o valor a ser convertido: ");

                double amount = scanner.nextDouble();


                double convertedAmount = realizarConversao(baseCurrency, targetCurrency, amount);


                System.out.println(amount + " " + baseCurrency + " equivale a " + convertedAmount + " " + targetCurrency);
            }


            System.out.print("Deseja fazer outra conversão? (S/N): ");
            choice = scanner.next().toUpperCase().charAt(0);

        } while (choice == 'S');

        System.out.println("Obrigado por usar o Conversor de Moedas!");
        scanner.close();
    }


    private static double realizarConversao(String baseCurrency, String targetCurrency, double amount) {
        String completeURL = API_URL + baseCurrency;
        double exchangeRate = 0.0;

        try {

            URL url = new URL(completeURL);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();


            JsonObject rates = jsonObject.getAsJsonObject("rates");


            exchangeRate = rates.get(targetCurrency).getAsDouble();

        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }

        return amount * exchangeRate;

    }
}