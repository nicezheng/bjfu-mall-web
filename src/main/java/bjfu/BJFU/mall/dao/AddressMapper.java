
package bjfu.BJFU.mall.dao;

import bjfu.BJFU.mall.entity.Address;

import java.util.List;

public interface AddressMapper {
   Address getDefaultAddress();

    List<Address> findAddressByUserId(Long userId);

    int deleteAddress(int id);
}