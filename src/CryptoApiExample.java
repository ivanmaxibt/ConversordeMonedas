import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CryptoApiExample {

    public static List<JsonObject> obtenerTop5Monedas() {
        List<JsonObject> top5Coins = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.coincap.io/v2/assets"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonArray data = jsonResponse.getAsJsonArray("data");

                List<JsonObject> coins = data.asList().stream()
                        .map(element -> element.getAsJsonObject())
                        .sorted(Comparator.comparingDouble(c -> -c.get("volumeUsd24Hr").getAsDouble()))
                        .collect(Collectors.toList());

                top5Coins = coins.subList(0, 5);
            } else {
                System.out.println("Error al obtener los datos de la API");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return top5Coins;
    }

    public static void main(String[] args) {
        List<JsonObject> top5Coins = obtenerTop5Monedas();
        if (top5Coins != null) {
            for (JsonObject coin : top5Coins) {
                System.out.println("Moneda: " + coin.get("id").getAsString() +
                        ", Volumen en USD (24h): " + coin.get("volumeUsd24Hr").getAsDouble());
            }
        }
    }
}