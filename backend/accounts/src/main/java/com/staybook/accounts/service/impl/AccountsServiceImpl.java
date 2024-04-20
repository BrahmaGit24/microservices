package com.staybook.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.staybook.accounts.constants.AccountConstants;
import com.staybook.accounts.dto.AccountsDto;
import com.staybook.accounts.dto.CustomersDto;
import com.staybook.accounts.entity.Accounts;
import com.staybook.accounts.entity.Customer;
import com.staybook.accounts.exception.CustomerAlreadyExistsException;
import com.staybook.accounts.exception.ResourceNotFoundException;
import com.staybook.accounts.mapper.AccountsMapper;
import com.staybook.accounts.mapper.CustomersMapper;
import com.staybook.accounts.repository.AccountsRepository;
import com.staybook.accounts.repository.CustomerRepository;
import com.staybook.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	/**
	 * @param customerDto - CustomerDto Object
	 */
	@Override
	public void createAccount(CustomersDto customerDto) {
		Customer customer = CustomersMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if (optionalCustomer.isPresent()) {
			throw new CustomerAlreadyExistsException(
					"Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
		}
		Customer savedCustomer = customerRepository.save(customer);
		accountsRepository.save(createNewAccount(savedCustomer));
	}

	/**
	 * @param customer - Customer Object
	 * @return the new account details
	 */
	private Accounts createNewAccount(Customer customer) {
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

		newAccount.setAccountNumber(randomAccNumber);
		newAccount.setAccountType(AccountConstants.SAVINGS);
		newAccount.setBranchAddress(AccountConstants.ADDRESS);
		return newAccount;
	}

	/**
	 * @param mobileNumber - Input Mobile Number
	 * @return Accounts Details based on a given mobileNumber
	 */
	@Override
	public CustomersDto fetchAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
		CustomersDto customerDto = CustomersMapper.mapToCustomerDto(customer, new CustomersDto());
		customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
		return customerDto;
	}

	/**
	 * @param customerDto - CustomerDto Object
	 * @return boolean indicating if the update of Account details is successful or
	 *         not
	 */
	@Override
	public boolean updateAccount(CustomersDto customerDto) {
		boolean isUpdated = false;
		AccountsDto accountsDto = customerDto.getAccountsDto();
		if (accountsDto != null) {
			Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
					.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber",
							accountsDto.getAccountNumber().toString()));
			AccountsMapper.mapToAccounts(accountsDto, accounts);
			accounts = accountsRepository.save(accounts);

			Long customerId = accounts.getCustomerId();
			Customer customer = customerRepository.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));
			CustomersMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);
			isUpdated = true;
		}
		return isUpdated;
	}

	/**
	 * @param mobileNumber - Input Mobile Number
	 * @return boolean indicating if the delete of Account details is successful or
	 *         not
	 */
	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		accountsRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}

}
