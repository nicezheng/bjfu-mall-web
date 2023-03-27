
package bjfu.BJFU.mall.dao;

import bjfu.BJFU.mall.entity.SikaMallGoods;
import bjfu.BJFU.mall.entity.StockNumDTO;
import bjfu.BJFU.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SikaMallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(SikaMallGoods record);

    int insertSelective(SikaMallGoods record);

    SikaMallGoods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(SikaMallGoods record);

    int updateByPrimaryKeyWithBLOBs(SikaMallGoods record);

    int updateByPrimaryKey(SikaMallGoods record);

    List<SikaMallGoods> findSikaMallGoodsList(PageQueryUtil pageUtil);

    int getTotalSikaMallGoods(PageQueryUtil pageUtil);

    List<SikaMallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<SikaMallGoods> selectByCategories(Long goodsCategoryId);

    List<SikaMallGoods> findSikaMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalSikaMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("newBeeMallGoodsList") List<SikaMallGoods> sikaMallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}