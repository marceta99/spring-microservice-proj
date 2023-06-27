package com.orgmihailo.productservice;

import com.orgmihailo.productservice.dtos.ProductRequest;
import com.orgmihailo.productservice.repository.ProductRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	private static DockerImageName myImage = DockerImageName.parse("monogo:4.4.2").asCompatibleSubstituteFor("mongo");
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(myImage);

	@Autowired
	private MockMvc mockMvc; //this will mock our controller so we can send requests on that mocked controller
	@Autowired
	private ObjectMapper objectMapper; //this will convert pojo objects to json string, and json string to objects
										//because content method require json string as format not objects
	@Autowired
	private ProductRepository productRepository;
	@DynamicPropertySource
	static void setProperites(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	//here we will write integration test to test if post returns 201 created response
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated()); //test if status is 201 created
		Assertions.assertTrue(productRepository.findAll().size() == 1);
	}

	private ProductRequest getProductRequest(){
		return ProductRequest.builder()
				.name("Iphone")
				.description("best phone in the worold")
				.price(BigDecimal.valueOf(1400))
				.build();

	}
}
