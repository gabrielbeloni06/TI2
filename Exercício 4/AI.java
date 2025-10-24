import com.azure.core.credential.AzureKeyCredential;
import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.CategorizedEntity;
public class AI {
    private static final String API_KEY = "SUA_CHAVE_AQUI";
    private static final String ENDPOINT = "SEU_ENDPOINT_AQUI";

    public static void main(String[] args) {
        AzureKeyCredential credential = new AzureKeyCredential(API_KEY);
        TextAnalyticsClient client = new TextAnalyticsClientBuilder()
                .endpoint(ENDPOINT)
                .credential(credential)
                .buildClient();
        String textoParaAnalisar = "O meu amigo João Silva vai se mudar para São Paulo "
                                 + "para trabalhar na Microsoft. Ele comprou um notebook "
                                 + "Dell novo para o trabalho.";

        System.out.println("Analisando o texto: \"" + textoParaAnalisar + "\"\n");
        System.out.println("--- ENTIDADES RECONHECIDAS ---");
        for (CategorizedEntity entity : client.recognizeEntities(textoParaAnalisar)) {
            System.out.printf("Entidade encontrada: '%s'%n", entity.getText());
            System.out.println("\tCategoria: " + entity.getCategory());
            System.out.println("\tSubcategoria (se houver): " + entity.getSubcategory());
            System.out.printf("\tPontuação (Confiança): %.2f%n%n", entity.getConfidenceScore());
        }
    }
}