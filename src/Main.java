import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static List<String> conversionHistory = new ArrayList<>();

    public static void addConversionToHistory(String fromCurrency, String toCurrency, double amount, double convertedAmount) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        String conversion = timestamp + " - " + amount + " " + fromCurrency + " -> " + convertedAmount + " " + toCurrency;
        conversionHistory.add(conversion);
    }

    public static void showConversionHistory(boolean useColor) {
        if (conversionHistory.isEmpty()) {
            printLine(useColor, ConsoleColors.WHITE, "No hay conversiones en el historial.");
        } else {
            printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "Historial de Conversiones:");
            for (String conversion : conversionHistory) {
                printLine(useColor, ConsoleColors.WHITE, conversion);
            }
        }
    }
    public static void printLine(boolean useColor, String colorCode, String text) { 
        if (useColor) {
            System.out.println(colorCode + text + ConsoleColors.RESET); 
        } else { 
            System.out.println(text);
        }
    }    
        public static void addConversionToHistory(String conversion) {
            conversionHistory.add(conversion);
        }
        public static void mostrarTop5Monedas(boolean useColor) {
            List<JsonObject> top5Coins = CryptoApiExample.obtenerTop5Monedas();
            if (top5Coins != null) {
                printLine(useColor, ConsoleColors.GREEN, "Datos obtenidos correctamente. Mostrando las monedas:");
                for (JsonObject coin : top5Coins) {
                    printLine(useColor, ConsoleColors.GREEN, 
                              "Moneda: " + coin.get("id").getAsString() +
                              ", Volumen en USD (24h): " + coin.get("volumeUsd24Hr").getAsDouble());
                }
            } else {
                printLine(useColor, ConsoleColors.RED, "Error al obtener los datos de la API");
            }
        }        
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Deseas ver la pantalla en colores (c) o en blanco y negro (bn)?"); 
        String choice = scanner.nextLine(); 
        boolean useColor = choice.equalsIgnoreCase("c");
        
        try {
            while (true) {
                printLine(useColor, "", "                                                                                ");
                printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                printLine(useColor, "", "                                                                                ");
                printLine(useColor, ConsoleColors.BLUE, "Sea bienvenido/a al Conversor de Monedas");
                printLine(useColor, "", "                                                                                ");
                printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.UNDERLINE, "Elija una opción de conversión:");
                printLine(useColor, "", "                                                                                ");
                printLine(useColor, ConsoleColors.RED, "1. De USD a moneda sudamericana (ARS, BOB, BRL, CLP, COP)");
                printLine(useColor, "", "                                                                                ");
                printLine(useColor, ConsoleColors.YELLOW, "2. De moneda sudamericana (ARS, BOB, BRL, CLP, COP) a USD");
                printLine(useColor, "", "                                                                                ");
                printLine(useColor, ConsoleColors.CYAN, "3. De otras monedas (HRK, ISK, JPY, KRW, LSL, NOK, NZD, PLN, XAF, ZWL) a BTN");
                printLine(useColor, "", "                                                                                ");
                printLine(useColor, ConsoleColors.GREEN, "4. Mostrar las cinco criptomonedas con mayor volumen de comercio en las últimas 24 horas");
                printLine(useColor, "", "                                                                                ");
                printLine(useColor, ConsoleColors.PURPLE, "5. Salir");
                printLine(useColor, "", "                                                                                ");
                printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");


                int option = scanner.nextInt();
                if (option == 5) {
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.CYAN + ConsoleStyles.BOLD, "Gracias por usar el Conversor de Monedas. ¡Hasta luego!");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    break;
                }

                String fromCurrency = "";
                String toCurrency = "";

                if (option == 1) {
                    fromCurrency = "USD";
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "Seleccione la moneda de destino (1. ARS, 2. BOB, 3. BRL, 4. CLP, 5. COP):");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                } else if (option == 2) {
                    toCurrency = "USD";
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "Seleccione la moneda de origen (1. ARS, 2. BOB, 3. BRL, 4. CLP, 5. COP):");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                } else if (option == 3) {
                        toCurrency = "BTN";
                        printLine(useColor, "", "                                                                                ");
                        printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                        printLine(useColor, "", "                                                                                ");
                        printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "Seleccione la moneda de origen (6.HRK, 7.ISK, 8.JPY, 9.KRW, 10.LSL, 11.NOK, 12.NZD, 13.PLN, 14.XAF, 15.ZWL):");
                        printLine(useColor, "", "                                                                                ");
                        printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                } else if (option == 4) {
                        mostrarTop5Monedas(useColor);
                        continue;
                } else if (option == 5) {
                    showConversionHistory(useColor);
                    continue;
                } else {
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "Opción no válida. Por favor, reinicie el programa e intente de nuevo.");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    continue;
                }

                int currencyOption = scanner.nextInt();
                switch (currencyOption) {
                    case 1:
                        if (option == 1) toCurrency = "ARS"; else fromCurrency = "ARS";
                        break;
                    case 2:
                        if (option == 1) toCurrency = "BOB"; else fromCurrency = "BOB";
                        break;
                    case 3:
                        if (option == 1) toCurrency = "BRL"; else fromCurrency = "BRL";
                        break;
                    case 4:
                        if (option == 1) toCurrency = "CLP"; else fromCurrency = "CLP";
                        break;
                    case 5:
                        if (option == 1) toCurrency = "COP"; else fromCurrency = "COP";
                        break;
                    case 6:
                        if (option == 1) toCurrency = "HRK"; else fromCurrency = "HRK"; 
                        break;
                    case 7:
                        if (option == 1) toCurrency = "ISK"; else fromCurrency = "ISK";
                        break;
                    case 8:
                        if (option == 1) toCurrency = "JPY"; else fromCurrency = "JPY";
                        break;
                    case 9:
                        if (option == 1) toCurrency = "KRW"; else fromCurrency = "KRW";
                        break;
                    case 10:
                        if (option == 1) toCurrency = "LSL"; else fromCurrency = "LSL";
                        break;
                    case 11:
                        if (option == 1) toCurrency = "NOK"; else fromCurrency = "NOK";
                        break;
                    case 12:
                        if (option == 1) toCurrency = "NZD"; else fromCurrency = "NZD";
                        break;
                    case 13:
                        if (option == 1) toCurrency = "PLN"; else fromCurrency = "PLN";
                        break;
                    case 14:
                        if (option == 1) toCurrency = "XAF"; else fromCurrency = "XAF";
                        break;
                    case 15:
                        if (option == 1) toCurrency = "ZWL"; else fromCurrency = "ZWL";
                        break;
                    default:
                        printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                        printLine(useColor, "", "                                                                                ");
                        printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "Opción no válida. Por favor, reinicie el programa e intente de nuevo.");
                        printLine(useColor, "", "                                                                                ");
                        printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                        continue;
                        }
                        printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                        printLine(useColor, "", "                                                                                ");
                        printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "Introduce la cantidad en " + fromCurrency + ":");
                        printLine(useColor, "", "                                                                                ");
                        printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                double amount = scanner.nextDouble();
                double conversionRate = getConversionRate(fromCurrency, toCurrency);
                double convertedAmount = amount * conversionRate;        
                if (conversionRate == 0.0) {
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "No se pudo obtener la tasa de conversión. Inténtalo más tarde.");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                } else {
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "La cantidad en " + toCurrency + " es: " + convertedAmount);
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    addConversionToHistory(fromCurrency, toCurrency, amount, convertedAmount);
                }
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "¿Desea hacer otra conversión? (s/n)");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                String anotherConversion = scanner.next().toLowerCase();
                if (!anotherConversion.equals("s")) {
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.WHITE + ConsoleStyles.BOLD, "Gracias por usar el Conversor de Monedas. ¡Hasta luego!");
                    printLine(useColor, "", "                                                                                ");
                    printLine(useColor, ConsoleColors.GREEN + ConsoleStyles.BOLD, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                    break;
                }
            }
        } finally {
            scanner.close();
        }
    }

    private static double getConversionRate(String fromCurrency, String toCurrency) {
        String apiKey = "228b85ad6d681ea6205790b4";
        String urlStr = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s", apiKey, fromCurrency, toCurrency);

        try {
            URL url = URI.create(urlStr).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JsonObject jsonResponse = JsonParser.parseReader(br).getAsJsonObject();
            br.close();

            if (jsonResponse.get("result").getAsString().equals("success")) {
                return jsonResponse.get("conversion_rate").getAsDouble();
            } else {
                System.out.println("Error en la respuesta de la API: " + jsonResponse.get("error-type").getAsString());
                return 0.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
