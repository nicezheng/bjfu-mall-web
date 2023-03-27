
package bjfu.BJFU.mall.service.impl;

import bjfu.BJFU.mall.common.ServiceResultEnum;
import bjfu.BJFU.mall.controller.vo.SikaMallSearchGoodsVO;
import bjfu.BJFU.mall.dao.SikaMallGoodsMapper;
import bjfu.BJFU.mall.entity.SikaMallGoods;
import bjfu.BJFU.mall.service.SikaMallGoodsService;
import bjfu.BJFU.mall.util.BeanUtil;
import bjfu.BJFU.mall.util.PageQueryUtil;
import bjfu.BJFU.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SikaMallGoodsServiceImpl implements SikaMallGoodsService {

    @Autowired
    private SikaMallGoodsMapper goodsMapper;

    @Override
    public PageResult getSikaMallGoodsPage(PageQueryUtil pageUtil) {
        List<SikaMallGoods> goodsList = goodsMapper.findSikaMallGoodsList(pageUtil);
        int total = goodsMapper.getTotalSikaMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveSikaMallGoods(SikaMallGoods goods) {
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveSikaMallGoods(List<SikaMallGoods> sikaMallGoodsList) {
        if (!CollectionUtils.isEmpty(sikaMallGoodsList)) {
            goodsMapper.batchInsert(sikaMallGoodsList);
        }
    }

    @Override
    public String updateSikaMallGoods(SikaMallGoods goods) {
        SikaMallGoods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public SikaMallGoods getSikaMallGoodsById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchSikaMallGoods(PageQueryUtil pageUtil) {
        List<SikaMallGoods> goodsList = goodsMapper.findSikaMallGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalSikaMallGoodsBySearch(pageUtil);
        List<SikaMallSearchGoodsVO> sikaMallSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            sikaMallSearchGoodsVOS = BeanUtil.copyList(goodsList, SikaMallSearchGoodsVO.class);
            for (SikaMallSearchGoodsVO sikaMallSearchGoodsVO : sikaMallSearchGoodsVOS) {
                String goodsName = sikaMallSearchGoodsVO.getGoodsName();
                String goodsIntro = sikaMallSearchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    sikaMallSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    sikaMallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(sikaMallSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public List<SikaMallGoods> getSikaMallGoodsByCategories(Long goodsCategoryId) {
        return goodsMapper.selectByCategories(goodsCategoryId);
    }
}
