
package bjfu.BJFU.mall.service.impl;

import bjfu.BJFU.mall.common.Constants;
import bjfu.BJFU.mall.common.ServiceResultEnum;
import bjfu.BJFU.mall.controller.vo.SikaMallShoppingCartItemVO;
import bjfu.BJFU.mall.dao.SikaMallGoodsMapper;
import bjfu.BJFU.mall.dao.SikaMallShoppingCartItemMapper;
import bjfu.BJFU.mall.entity.SikaMallShoppingCartItem;
import bjfu.BJFU.mall.entity.SikaMallGoods;
import bjfu.BJFU.mall.service.SikaMallShoppingCartService;
import bjfu.BJFU.mall.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SikaShoppingCartServiceImpl implements SikaMallShoppingCartService {

    @Autowired
    private SikaMallShoppingCartItemMapper sikaMallShoppingCartItemMapper;

    @Autowired
    private SikaMallGoodsMapper sikaMallGoodsMapper;

    //todo 修改session中购物项数量

    @Override
    public String saveSikaMallCartItem(SikaMallShoppingCartItem sikaMallShoppingCartItem) {
        SikaMallShoppingCartItem temp = sikaMallShoppingCartItemMapper.selectByUserIdAndGoodsId(sikaMallShoppingCartItem.getUserId(), sikaMallShoppingCartItem.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            //todo count = tempCount + 1
            temp.setGoodsCount(sikaMallShoppingCartItem.getGoodsCount());
            return updateSikaMallCartItem(temp);
        }
        SikaMallGoods sikaMallGoods = sikaMallGoodsMapper.selectByPrimaryKey(sikaMallShoppingCartItem.getGoodsId());
        //商品为空
        if (sikaMallGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = sikaMallShoppingCartItemMapper.selectCountByUserId(sikaMallShoppingCartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (sikaMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        //保存记录
        if (sikaMallShoppingCartItemMapper.insertSelective(sikaMallShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateSikaMallCartItem(SikaMallShoppingCartItem sikaMallShoppingCartItem) {
        SikaMallShoppingCartItem sikaMallShoppingCartItemUpdate = sikaMallShoppingCartItemMapper.selectByPrimaryKey(sikaMallShoppingCartItem.getCartItemId());
        if (sikaMallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (sikaMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //todo 数量相同不会进行修改
        //todo userId不同不能修改
        sikaMallShoppingCartItemUpdate.setGoodsCount(sikaMallShoppingCartItem.getGoodsCount());
        sikaMallShoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (sikaMallShoppingCartItemMapper.updateByPrimaryKeySelective(sikaMallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public SikaMallShoppingCartItem getSikaMallCartItemById(Long SikaMallShoppingCartItemId) {
        return sikaMallShoppingCartItemMapper.selectByPrimaryKey(SikaMallShoppingCartItemId);
    }

    @Override
    public Boolean deleteById(Long SikaMallShoppingCartItemId) {
        return sikaMallShoppingCartItemMapper.deleteByPrimaryKey(SikaMallShoppingCartItemId) > 0;
    }

    @Override
    public List<SikaMallShoppingCartItemVO> getMyShoppingCartItems(Long SikaMallUserId) {
        List<SikaMallShoppingCartItemVO> sikaMallShoppingCartItemVOS = new ArrayList<>();
        List<SikaMallShoppingCartItem> sikaMallShoppingCartItems = sikaMallShoppingCartItemMapper.selectByUserId(SikaMallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(sikaMallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> SikaMallGoodsIds = sikaMallShoppingCartItems.stream().map(SikaMallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<SikaMallGoods> sikaMallGoods = sikaMallGoodsMapper.selectByPrimaryKeys(SikaMallGoodsIds);
            Map<Long, SikaMallGoods> SikaMallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(sikaMallGoods)) {
                SikaMallGoodsMap = sikaMallGoods.stream().collect(Collectors.toMap(bjfu.BJFU.mall.entity.SikaMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (SikaMallShoppingCartItem sikaMallShoppingCartItem : sikaMallShoppingCartItems) {
                SikaMallShoppingCartItemVO sikaMallShoppingCartItemVO = new SikaMallShoppingCartItemVO();
                BeanUtil.copyProperties(sikaMallShoppingCartItem, sikaMallShoppingCartItemVO);
                if (SikaMallGoodsMap.containsKey(sikaMallShoppingCartItem.getGoodsId())) {
                    SikaMallGoods sikaMallGoodsTemp = SikaMallGoodsMap.get(sikaMallShoppingCartItem.getGoodsId());
                    sikaMallShoppingCartItemVO.setGoodsCoverImg(sikaMallGoodsTemp.getGoodsCoverImg());
                    String goodsName = sikaMallGoodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    sikaMallShoppingCartItemVO.setGoodsName(goodsName);
                    sikaMallShoppingCartItemVO.setSellingPrice(sikaMallGoodsTemp.getSellingPrice());
                    sikaMallShoppingCartItemVOS.add(sikaMallShoppingCartItemVO);
                }
            }
        }
        return sikaMallShoppingCartItemVOS;
    }

    @Override
    public Boolean deleteByGoodsId(Long goodsId) {

        return sikaMallShoppingCartItemMapper.deleteByGoodsId(goodsId)>0;
    }

    @Override
    public Boolean updateCountByGoodsId(Long goodsId, int count) {
        return sikaMallShoppingCartItemMapper.updateCountByGoodsId(goodsId, count);
    }
}
