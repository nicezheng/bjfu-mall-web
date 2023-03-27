
package bjfu.BJFU.mall.service;

import bjfu.BJFU.mall.controller.vo.SikaMallShoppingCartItemVO;
import bjfu.BJFU.mall.entity.SikaMallShoppingCartItem;

import java.util.List;

public interface SikaMallShoppingCartService {

    /**
     * 保存商品至购物车中
     *
     * @param sikaMallShoppingCartItem
     * @return
     */
    String saveSikaMallCartItem(SikaMallShoppingCartItem sikaMallShoppingCartItem);

    /**
     * 修改购物车中的属性
     *
     * @param sikaMallShoppingCartItem
     * @return
     */
    String updateSikaMallCartItem(SikaMallShoppingCartItem sikaMallShoppingCartItem);

    /**
     * 获取购物项详情
     *
     * @param siKaMallShoppingCartItemId
     * @return
     */
    SikaMallShoppingCartItem getSikaMallCartItemById(Long siKaMallShoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     * @param siKaMallShoppingCartItemId
     * @return
     */
    Boolean deleteById(Long siKaMallShoppingCartItemId);


    /**
     * 获取我的购物车中的列表数据
     *
     * @param siKaMallUserId
     * @return
     */
    List<SikaMallShoppingCartItemVO> getMyShoppingCartItems(Long siKaMallUserId);
    Boolean deleteByGoodsId(Long goodsId);

    Boolean updateCountByGoodsId(Long goodsId, int count);
}
