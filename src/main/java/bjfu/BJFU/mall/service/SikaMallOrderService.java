
package bjfu.BJFU.mall.service;

import bjfu.BJFU.mall.controller.vo.SikaMallOrderDetailVO;
import bjfu.BJFU.mall.controller.vo.SikaMallOrderItemVO;
import bjfu.BJFU.mall.controller.vo.SikaMallShoppingCartItemVO;
import bjfu.BJFU.mall.controller.vo.SikaMallUserVO;
import bjfu.BJFU.mall.entity.SikaMallOrder;
import bjfu.BJFU.mall.util.PageQueryUtil;
import bjfu.BJFU.mall.util.PageResult;

import java.util.List;

public interface SikaMallOrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getSikaMallOrdersPage(PageQueryUtil pageUtil);

    /**
     * 订单信息修改
     *
     * @param sikaMallOrder
     * @return
     */
    String updateOrderInfo(SikaMallOrder sikaMallOrder);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    /**
     * 保存订单
     *
     * @param user
     * @param myShoppingCartItems
     * @return
     */
    String saveOrder(SikaMallUserVO user, List<SikaMallShoppingCartItemVO> myShoppingCartItems);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    SikaMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return
     */
    SikaMallOrder getSikaMallOrderByOrderNo(String orderNo);

    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);

    List<SikaMallOrderItemVO> getOrderItems(Long id);

    int save(SikaMallOrder sikaMallOrder);
}
