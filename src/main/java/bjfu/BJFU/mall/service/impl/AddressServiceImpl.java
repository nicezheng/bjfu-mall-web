
package bjfu.BJFU.mall.service.impl;

import bjfu.BJFU.mall.dao.AddressMapper;
import bjfu.BJFU.mall.entity.Address;
import bjfu.BJFU.mall.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;


    @Override
    public Address getDefaultAdd() {
        return addressMapper.getDefaultAddress();
    }

    @Override
    public List<Address> getAddressByUserId(Long userId) {

        return addressMapper.findAddressByUserId(userId);
    }

    @Override
    public int deleteAddress(int id) {
        return addressMapper.deleteAddress(id);
    }
}
