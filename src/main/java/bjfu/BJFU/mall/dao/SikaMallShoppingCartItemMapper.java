
package bjfu.BJFU.mall.dao;

import bjfu.BJFU.mall.entity.SikaMallShoppingCartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SikaMallShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);
    int deleteByGoodsId(Long goodsId);

    int insert(SikaMallShoppingCartItem record);

    int insertSelective(SikaMallShoppingCartItem record);

    SikaMallShoppingCartItem selectByPrimaryKey(Long cartItemId);

    SikaMallShoppingCartItem selectByUserIdAndGoodsId(@Param("sikaMallUserId") Long newBeeMallUserId, @Param("goodsId") Long goodsId);

    List<SikaMallShoppingCartItem> selectByUserId(@Param("sikaMallUserId") Long newBeeMallUserId, @Param("number") int number);

    int selectCountByUserId(Long newBeeMallUserId);

    int updateByPrimaryKeySelective(SikaMallShoppingCartItem record);

    int updateByPrimaryKey(SikaMallShoppingCartItem record);

    int deleteBatch(List<Long> ids);

    Boolean updateCountByGoodsId(Long goodsId, int count);
}