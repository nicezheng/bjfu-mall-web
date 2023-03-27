
package bjfu.BJFU.mall.dao;

import bjfu.BJFU.mall.entity.SikaMallOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SikaMallOrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(SikaMallOrderItem record);

    int insertSelective(SikaMallOrderItem record);

    SikaMallOrderItem selectByPrimaryKey(Long orderItemId);

    /**
     * 根据订单id获取订单项列表
     *
     * @param orderId
     * @return
     */
    List<SikaMallOrderItem> selectByOrderId(Long orderId);

    /**
     * 根据订单ids获取订单项列表
     *
     * @param orderIds
     * @return
     */
    List<SikaMallOrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 批量insert订单项数据
     *
     * @param orderItems
     * @return
     */
    int insertBatch(@Param("orderItems") List<SikaMallOrderItem> orderItems);

    int updateByPrimaryKeySelective(SikaMallOrderItem record);

    int updateByPrimaryKey(SikaMallOrderItem record);
}