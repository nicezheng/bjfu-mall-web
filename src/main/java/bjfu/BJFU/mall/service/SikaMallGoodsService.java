
package bjfu.BJFU.mall.service;

import bjfu.BJFU.mall.entity.SikaMallGoods;
import bjfu.BJFU.mall.util.PageQueryUtil;
import bjfu.BJFU.mall.util.PageResult;

import java.util.List;

public interface SikaMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getSikaMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveSikaMallGoods(SikaMallGoods goods);

    /**
     * 批量新增商品数据
     *
     * @param sikaMallGoodsList
     * @return
     */
    void batchSaveSikaMallGoods(List<SikaMallGoods> sikaMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateSikaMallGoods(SikaMallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    SikaMallGoods getSikaMallGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */

    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchSikaMallGoods(PageQueryUtil pageUtil);

    List<SikaMallGoods> getSikaMallGoodsByCategories(Long goodsCategoryId);
}
