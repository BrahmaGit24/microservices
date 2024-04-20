package com.staybook.accounts.service;

import com.staybook.accounts.dto.CustomersDto;

public interface IAccountsService {
	 /**
    *
    * @param customerDto - CustomerDto Object
    */
   void createAccount(CustomersDto customerDto);

   /**
    *
    * @param mobileNumber - Input Mobile Number
    * @return Accounts Details based on a given mobileNumber
    */
   CustomersDto fetchAccount(String mobileNumber);

   /**
    *
    * @param customerDto - CustomerDto Object
    * @return boolean indicating if the update of Account details is successful or not
    */
   boolean updateAccount(CustomersDto customerDto);

   /**
    *
    * @param mobileNumber - Input Mobile Number
    * @return boolean indicating if the delete of Account details is successful or not
    */
   boolean deleteAccount(String mobileNumber);

}
