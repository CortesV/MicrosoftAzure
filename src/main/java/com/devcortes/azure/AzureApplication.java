package com.devcortes.azure;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class AzureApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzureApplication.class, args);

		createKnowledgeBase();
		//deleteKnowledgeBase();
	}

	private static void deleteKnowledgeBase() {
		HttpClient httpclient = null;

		try	{
			httpclient = HttpClients.createDefault();
			String knowledgeBaseID = "34891e57-f66d-4b68-af84-d38ecc6787c9";
			URIBuilder builder = new URIBuilder(String.format("https://westus.api.cognitive.microsoft.com/qnamaker/v3.0/knowledgebases/%s", knowledgeBaseID));


			URI uri = builder.build();
			HttpDelete request = new HttpDelete(uri);
			request.setHeader("Ocp-Apim-Subscription-Key", "");

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null)	{
				System.out.println(EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (httpclient != null) {
				HttpClientUtils.closeQuietly(httpclient);
			}
		}
	}

	private static void createKnowledgeBase() {
		HttpClient httpclient = null;
		try {
			httpclient = HttpClients.createDefault();
			URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/qnamaker/v3.0/knowledgebases/create");


			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", "");


			// Request body
			StringEntity reqEntity = new StringEntity("{\n" +
					"\t\"name\" : \"QnA Maker FAQ Bot Second try\",\n" +
					"    \"qnaList\": [\n" +
					"        {\n" +
					"            \"answer\": \"You can change the default message if you use the QnAMakerDialog. See this for details: https://docs.botframework.com/en-us/azure-bot-service/templates/qnamaker/#navtitle\",\n" +
					"            \"questions\": [\"How can I change the default message from QnA Maker?\"],\n" +
					"        },\n" +
					"        {\n" +
					"            \"answer\": \"You can use our REST apis to manage your KB. See here for details: https://westus.dev.cognitive.microsoft.com/docs/services/58994a073d9e04097c7ba6fe/operations/58994a073d9e041ad42d9baa\",\n" +
					"            \"questions\": [\"How do I programmatically update my KB?\"],\n" +
					"        }\n" +
					"    ],\n" +
					"    \"urls\": [\n" +
					"        \"https://docs.microsoft.com/en-in/azure/cognitive-services/qnamaker/faqs\",\n" +
					"      \"https://docs.microsoft.com/en-us/bot-framework/resources-bot-framework-faq\"\n" +
					"    ]\n" +
					"}");
			request.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null)	{
				System.out.println(EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (httpclient != null) {
				HttpClientUtils.closeQuietly(httpclient);
			}
		}
	}
}
