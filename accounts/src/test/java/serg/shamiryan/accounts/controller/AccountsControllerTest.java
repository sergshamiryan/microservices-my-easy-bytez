package serg.shamiryan.accounts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import serg.shamiryan.accounts.accountService.impl.AccountServiceImpl;
import serg.shamiryan.accounts.dto.CustomerDto;
import serg.shamiryan.accounts.entity.Customer;
import serg.shamiryan.accounts.mapper.CustomerMapper;

@WebMvcTest(AccountsController.class)
class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto customerDto;

    private Customer customer;

    @BeforeEach
    public void setup() {
        this.customerDto = new CustomerDto();
        customerDto.setMobileNumber("1234567891");
        customerDto.setName("Test Name");
        customerDto.setEmail("test@gmail.com");
        this.customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
    }

    @Test
    public void givenCustomerDto_whenCreateAccount_thenReturnSavedCustomerWithAccount()
            throws Exception {
        //given - precondition or setup
        BDDMockito.given(accountService.createAccount(ArgumentMatchers.any(CustomerDto.class)))
                .willReturn(customer);
        //when - action or behaviour we are going to test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)));
        //then - verify the output
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void givenCustomerDto_whenUpdateCustomer_thenReturnTrue() throws Exception {
        //given - precondition or setup
        BDDMockito.given(accountService.updateAccount(customerDto))
                .willReturn(true);
        //when - action or behaviour we are going to test
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)));
        //then - verify the output
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}