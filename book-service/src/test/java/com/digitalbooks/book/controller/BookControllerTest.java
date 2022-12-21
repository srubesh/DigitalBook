//package com.digitalbooks.book.controller;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
//import java.time.LocalDate;
//
//import com.digitalbooks.book.BookApplication;
//import com.digitalbooks.book.entities.Book;
//import com.digitalbooks.book.service.BookService;
//
//@RunWith(SpringRunner.class)
//
//@SpringBootTest(webEnvironment=WebEnvironment.MOCK, classes={ BookApplication.class })
//public class BookControllerTest {
//	
//	private MockMvc mockMvc;
//	
//	@Autowired
//	private WebApplicationContext webApplicationContext;
//	
//	@MockBean 
//	private BookService bookServiceMock;
//
//	@Before
//	public void test() {
//		this.mockMvc = webAppContextSetup(webApplicationContext).build();
//	}
//	
//	@Test
//	public void getBookById_When_ValidRequest() throws Exception {
//		Book book = new Book(null,"title","category",10,1,"publisher",LocalDate.now(),true,"content");
//		when(bookServiceMock.getBookById(any(Long.class))).thenReturn(book);
//		
//		mockMvc.perform(get("/digitalbooks/test/1")
////				.contentType(MediaType.APPLICATION_JSON)
////				.content("{ \"title\": \"title\", \"category\": \"category\",\"price\":10,\"authorId\" : 1,\"publisher\" : \"publisher\",\"publishedDate\" : \"2022-12-12\",\"active\":\"true\",\"content\":\"content\"  }")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.title").value("title"));
//	}
//	
////	@Test
////	public void should_CreateAccount_When_ValidRequest() throws Exception {
////		
////		when(accountServiceMock.createAccount(any(Account.class))).thenReturn(12345L);
////		
////		mockMvc.perform(post("/api/account")
////			   .contentType(MediaType.APPLICATION_JSON)
////			   .content("{ \"accountType\": \"SAVINGS\", \"balance\": 5000.0 }")						
////			   .accept(MediaType.APPLICATION_JSON))
////			   .andExpect(status().isCreated())
////			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
////			   .andExpect(header().string("Location", "/api/account/12345"))
////			   .andExpect(jsonPath("$.accountId").value("12345"))			   
////			   .andExpect(jsonPath("$.accountType").value("SAVINGS"))
////			   .andExpect(jsonPath("$.balance").value(5000));		
////	}
//
//}
