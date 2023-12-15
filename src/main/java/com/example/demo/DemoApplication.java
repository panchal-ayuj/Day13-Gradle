package com.example.demo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.cache.Cache;
import model.Pojo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.json.JSONArray;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


@SpringBootApplication
public class DemoApplication {
	private static final LoadingCache<String, Pojo> cache = CacheBuilder.newBuilder()
			.maximumSize(100)
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.build(new CacheLoader<String, Pojo>() {  // build the cacheloader

				@Override
				public Pojo load(String transId) throws Exception {
					//make the expensive call
					return pojoMap.get(transId);
				}
			});

	private static ObjectMapper objectMapper = new ObjectMapper();

	static Map<String,Pojo> pojoMap = new HashMap<>();

	public static void getPojosfromJson(String jsonFilePath) {
		try {

			File jsonFile  = new File(jsonFilePath);

			List<Pojo> pojoList = List.of(objectMapper.readValue(jsonFile, Pojo[].class));
			if(pojoList != null) {
				for(Pojo t: pojoList)
				{
					cache.put(t.getTransactionId(), t);
					pojoMap.put(t.getTransactionId(), t);
				}
			}

			return;
		} catch(IOException e) {
			e.printStackTrace();
		}

		return;
	}

	public static void main(String[] args) throws IOException, ExecutionException {
		SpringApplication.run(DemoApplication.class, args);
//		ObjectMapper om = new ObjectMapper();
//		om.registerModule(new JavaTimeModule());
//		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//		Pojo transaction = om.readValue(new File("C:\\Users\\panchal.kumar\\IdeaProjects\\demo\\src\\main\\resources\\transaction.json"),Pojo.class);
//		System.out.println("Transaction id:"+transaction.getTransactionId());
//		System.out.println("Account Number:"+transaction.getAccountNumber());
//		System.out.println("Credit:"+ transaction.getCredit());
//		System.out.println("Debit:"+ transaction.getDebit());
//		String dateTimeString = transaction.getDate().toGMTString();
//		System.out.println("Date:"+dateTimeString);
//		System.out.println("Merchant:"+transaction.getAdditionalDetails().getMerchant());
//		System.out.println("Location:"+transaction.getAdditionalDetails().getLocation());
		objectMapper.registerModule(new JavaTimeModule());
		String jsonFilePath = "C:\\Users\\panchal.kumar\\IdeaProjects\\demo\\src\\main\\resources\\transaction.json";
		getPojosfromJson(jsonFilePath);
		System.out.println(cache.get("1"));
	}

}
