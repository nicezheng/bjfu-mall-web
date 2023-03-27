
package bjfu.BJFU.mall.service;

import bjfu.BJFU.mall.entity.Address;

import java.util.List;

public interface AddressService {

   Address getDefaultAdd();
   List<Address> getAddressByUserId(Long userId);

   int deleteAddress(int id);
}
