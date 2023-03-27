
package bjfu.BJFU.mall.service.impl;

import bjfu.BJFU.mall.common.ServiceResultEnum;
import bjfu.BJFU.mall.controller.vo.SikaMallIndexConfigGoodsVO;
import bjfu.BJFU.mall.dao.IndexConfigMapper;
import bjfu.BJFU.mall.dao.SikaMallGoodsMapper;
import bjfu.BJFU.mall.entity.IndexConfig;
import bjfu.BJFU.mall.entity.SikaMallGoods;
import bjfu.BJFU.mall.service.SikaMallIndexConfigService;
import bjfu.BJFU.mall.util.BeanUtil;
import bjfu.BJFU.mall.util.PageQueryUtil;
import bjfu.BJFU.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SikaMallIndexConfigServiceImpl implements SikaMallIndexConfigService {

    @Autowired
    private IndexConfigMapper indexConfigMapper;

    @Autowired
    private SikaMallGoodsMapper goodsMapper;

    @Override
    public PageResult getConfigsPage(PageQueryUtil pageUtil) {
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigList(pageUtil);
        int total = indexConfigMapper.getTotalIndexConfigs(pageUtil);
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveIndexConfig(IndexConfig indexConfig) {
        //todo 判断是否存在该商品
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        //todo 判断是否存在该商品
        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public IndexConfig getIndexConfigById(Long id) {
        return null;
    }

    @Override
    public List<SikaMallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number) {
        List<SikaMallIndexConfigGoodsVO> sikaMallIndexConfigGoodsVOS = new ArrayList<>(number);
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType, number);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            //取出所有的goodsId
            List<Long> goodsIds = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());
            List<SikaMallGoods> sikaMallGoods = goodsMapper.selectByPrimaryKeys(goodsIds);
            sikaMallIndexConfigGoodsVOS = BeanUtil.copyList(sikaMallGoods, SikaMallIndexConfigGoodsVO.class);
            for (SikaMallIndexConfigGoodsVO sikaMallIndexConfigGoodsVO : sikaMallIndexConfigGoodsVOS) {
                String goodsName = sikaMallIndexConfigGoodsVO.getGoodsName();
                String goodsIntro = sikaMallIndexConfigGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 30) {
                    goodsName = goodsName.substring(0, 30) + "...";
                    sikaMallIndexConfigGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 22) {
                    goodsIntro = goodsIntro.substring(0, 22) + "...";
                    sikaMallIndexConfigGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        return sikaMallIndexConfigGoodsVOS;
    }

    @Override
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return indexConfigMapper.deleteBatch(ids) > 0;
    }
}
