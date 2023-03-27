
package bjfu.BJFU.mall.dao;

import bjfu.BJFU.mall.entity.SikaMallOrder;
import bjfu.BJFU.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SikaMallOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(SikaMallOrder record);

    int insertSelective(SikaMallOrder record);

    SikaMallOrder selectByPrimaryKey(Long orderId);

    SikaMallOrder selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(SikaMallOrder record);

    int updateByPrimaryKey(SikaMallOrder record);

    List<SikaMallOrder> findSikaMallOrderList(PageQueryUtil pageUtil);

    int getTotalSikaMallOrders(PageQueryUtil pageUtil);

    List<SikaMallOrder> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int checkOut(@Param("orderIds") List<Long> orderIds);

    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int checkDone(@Param("orderIds") List<Long> asList);
}