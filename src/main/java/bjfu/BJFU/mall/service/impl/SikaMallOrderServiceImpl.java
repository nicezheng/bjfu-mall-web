
package bjfu.BJFU.mall.service.impl;

import bjfu.BJFU.mall.common.*;
import bjfu.BJFU.mall.controller.vo.*;
import bjfu.BJFU.mall.dao.SikaMallGoodsMapper;
import bjfu.BJFU.mall.dao.SikaMallOrderItemMapper;
import bjfu.BJFU.mall.dao.SikaMallOrderMapper;
import bjfu.BJFU.mall.dao.SikaMallShoppingCartItemMapper;
import bjfu.BJFU.mall.entity.SikaMallOrder;
import bjfu.BJFU.mall.entity.SikaMallOrderItem;
import bjfu.BJFU.mall.entity.SikaMallGoods;
import bjfu.BJFU.mall.entity.StockNumDTO;
import bjfu.BJFU.mall.service.SikaMallOrderService;
import bjfu.BJFU.mall.util.BeanUtil;
import bjfu.BJFU.mall.util.NumberUtil;
import bjfu.BJFU.mall.util.PageQueryUtil;
import bjfu.BJFU.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class SikaMallOrderServiceImpl implements SikaMallOrderService {

    @Autowired
    private SikaMallOrderMapper sikaMallOrderMapper;
    @Autowired
    private SikaMallOrderItemMapper sikaMallOrderItemMapper;
    @Autowired
    private SikaMallShoppingCartItemMapper sikaMallShoppingCartItemMapper;
    @Autowired
    private SikaMallGoodsMapper sikaMallGoodsMapper;

    @Override
    public PageResult getSikaMallOrdersPage(PageQueryUtil pageUtil) {
        List<SikaMallOrder> sikaMallOrders = sikaMallOrderMapper.findSikaMallOrderList(pageUtil);
        int total = sikaMallOrderMapper.getTotalSikaMallOrders(pageUtil);
        PageResult pageResult = new PageResult(sikaMallOrders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String updateOrderInfo(SikaMallOrder sikaMallOrder) {
        SikaMallOrder temp = sikaMallOrderMapper.selectByPrimaryKey(sikaMallOrder.getOrderId());
        //不为空且orderStatus>=0且状态为出库之前可以修改部分信息
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(sikaMallOrder.getTotalPrice());
            temp.setUserAddress(sikaMallOrder.getUserAddress());
            temp.setUpdateTime(new Date());
            if (sikaMallOrderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<SikaMallOrder> orders = sikaMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (SikaMallOrder sikaMallOrder : orders) {
                if (sikaMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += sikaMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (sikaMallOrder.getOrderStatus() != 1) {
                    errorOrderNos += sikaMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行配货完成操作 修改订单状态和更新时间
                if (sikaMallOrderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功的订单，无法执行配货完成操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkOut(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<SikaMallOrder> orders = sikaMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (SikaMallOrder sikaMallOrder : orders) {
                if (sikaMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += sikaMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (sikaMallOrder.getOrderStatus() != 1 && sikaMallOrder.getOrderStatus() != 2) {
                    errorOrderNos += sikaMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行出库操作 修改订单状态和更新时间
                if (sikaMallOrderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功或配货完成无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功或配货完成的订单，无法执行出库操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String closeOrder(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<SikaMallOrder> orders = sikaMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (SikaMallOrder sikaMallOrder : orders) {
                // isDeleted=1 一定为已关闭订单
                if (sikaMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += sikaMallOrder.getOrderNo() + " ";
                    continue;
                }
                //已关闭或者已完成无法关闭订单
                if (sikaMallOrder.getOrderStatus() == 4 || sikaMallOrder.getOrderStatus() < 0) {
                    errorOrderNos += sikaMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行关闭操作 修改订单状态和更新时间
                if (sikaMallOrderMapper.closeOrder(Arrays.asList(ids), SikaMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行关闭操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单不能执行关闭操作";
                } else {
                    return "你选择的订单不能执行关闭操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String saveOrder(SikaMallUserVO user, List<SikaMallShoppingCartItemVO> myShoppingCartItems) {
        List<Long> itemIdList = myShoppingCartItems.stream().map(SikaMallShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = myShoppingCartItems.stream().map(SikaMallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
        List<SikaMallGoods> sikaMallGoods = sikaMallGoodsMapper.selectByPrimaryKeys(goodsIds);
        //检查是否包含已下架商品
        List<SikaMallGoods> goodsListNotSelling = sikaMallGoods.stream()
                .filter(newBeeMallGoodsTemp -> newBeeMallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            //goodsListNotSelling 对象非空则表示有下架商品
            SikaMallException.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");
        }
        Map<Long, SikaMallGoods> newBeeMallGoodsMap = sikaMallGoods.stream().collect(Collectors.toMap(bjfu.BJFU.mall.entity.SikaMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        //判断商品库存
        for (SikaMallShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!newBeeMallGoodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
                SikaMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //存在数量大于库存的情况，直接返回错误提醒
            if (shoppingCartItemVO.getGoodsCount() > newBeeMallGoodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
                SikaMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        //删除购物项
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(sikaMallGoods)) {
            if (sikaMallShoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
                int updateStockNumResult = sikaMallGoodsMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    SikaMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //生成订单号
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                //保存订单
                SikaMallOrder sikaMallOrder = new SikaMallOrder();
                sikaMallOrder.setOrderNo(orderNo);
                sikaMallOrder.setUserId(user.getUserId());
                sikaMallOrder.setUserAddress(user.getAddress());
                //总价
                for (SikaMallShoppingCartItemVO sikaMallShoppingCartItemVO : myShoppingCartItems) {
                    priceTotal += sikaMallShoppingCartItemVO.getGoodsCount() * sikaMallShoppingCartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    SikaMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                sikaMallOrder.setTotalPrice(priceTotal);
                String extraInfo = "";
                sikaMallOrder.setExtraInfo(extraInfo);
                //生成订单项并保存订单项纪录
                if (sikaMallOrderMapper.insertSelective(sikaMallOrder) > 0) {
                    //生成所有的订单项快照，并保存至数据库
                    List<SikaMallOrderItem> sikaMallOrderItems = new ArrayList<>();
                    for (SikaMallShoppingCartItemVO sikaMallShoppingCartItemVO : myShoppingCartItems) {
                        SikaMallOrderItem sikaMallOrderItem = new SikaMallOrderItem();
                        //使用BeanUtil工具类将newBeeMallShoppingCartItemVO中的属性复制到newBeeMallOrderItem对象中
                        BeanUtil.copyProperties(sikaMallShoppingCartItemVO, sikaMallOrderItem);
                        //NewBeeMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
                        sikaMallOrderItem.setOrderId(sikaMallOrder.getOrderId());
                        sikaMallOrderItems.add(sikaMallOrderItem);
                    }
                    //保存至数据库
                    if (sikaMallOrderItemMapper.insertBatch(sikaMallOrderItems) > 0) {
                        //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
                        return orderNo;
                    }
                    SikaMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                SikaMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            SikaMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        SikaMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }

    @Override
    public SikaMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        SikaMallOrder sikaMallOrder = sikaMallOrderMapper.selectByOrderNo(orderNo);
        if (sikaMallOrder != null) {
            //todo 验证是否是当前userId下的订单，否则报错
            List<SikaMallOrderItem> orderItems = sikaMallOrderItemMapper.selectByOrderId(sikaMallOrder.getOrderId());
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<SikaMallOrderItemVO> sikaMallOrderItemVOS = BeanUtil.copyList(orderItems, SikaMallOrderItemVO.class);
                SikaMallOrderDetailVO sikaMallOrderDetailVO = new SikaMallOrderDetailVO();
                BeanUtil.copyProperties(sikaMallOrder, sikaMallOrderDetailVO);
                sikaMallOrderDetailVO.setOrderStatusString(SikaMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(sikaMallOrderDetailVO.getOrderStatus()).getName());
                sikaMallOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(sikaMallOrderDetailVO.getPayType()).getName());
                sikaMallOrderDetailVO.setSikaMallOrderItemVOS(sikaMallOrderItemVOS);
                return sikaMallOrderDetailVO;
            }
        }
        return null;
    }

    @Override
    public SikaMallOrder getSikaMallOrderByOrderNo(String orderNo) {
        return sikaMallOrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = sikaMallOrderMapper.getTotalSikaMallOrders(pageUtil);
        List<SikaMallOrder> sikaMallOrders = sikaMallOrderMapper.findSikaMallOrderList(pageUtil);
        List<SikaMallOrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(sikaMallOrders, SikaMallOrderListVO.class);
            //设置订单状态中文显示值
            for (SikaMallOrderListVO sikaMallOrderListVO : orderListVOS) {
                sikaMallOrderListVO.setOrderStatusString(SikaMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(sikaMallOrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = sikaMallOrders.stream().map(SikaMallOrder::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<SikaMallOrderItem> orderItems = sikaMallOrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<SikaMallOrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(SikaMallOrderItem::getOrderId));
                for (SikaMallOrderListVO sikaMallOrderListVO : orderListVOS) {
                    //封装每个订单列表对象的订单项数据
                    if (itemByOrderIdMap.containsKey(sikaMallOrderListVO.getOrderId())) {
                        List<SikaMallOrderItem> orderItemListTemp = itemByOrderIdMap.get(sikaMallOrderListVO.getOrderId());
                        //将NewBeeMallOrderItem对象列表转换成NewBeeMallOrderItemVO对象列表
                        List<SikaMallOrderItemVO> sikaMallOrderItemVOS = BeanUtil.copyList(orderItemListTemp, SikaMallOrderItemVO.class);
                        sikaMallOrderListVO.setSikaMallOrderItemVOS(sikaMallOrderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String cancelOrder(String orderNo, Long userId) {
        SikaMallOrder sikaMallOrder = sikaMallOrderMapper.selectByOrderNo(orderNo);
        if (sikaMallOrder != null) {
            //todo 验证是否是当前userId下的订单，否则报错
            //todo 订单状态判断
            if (sikaMallOrderMapper.closeOrder(Collections.singletonList(sikaMallOrder.getOrderId()), SikaMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
        SikaMallOrder sikaMallOrder = sikaMallOrderMapper.selectByOrderNo(orderNo);
        if (sikaMallOrder != null) {
            //todo 验证是否是当前userId下的订单，否则报错
            //todo 订单状态判断
            sikaMallOrder.setOrderStatus((byte) SikaMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            sikaMallOrder.setUpdateTime(new Date());
            if (sikaMallOrderMapper.updateByPrimaryKeySelective(sikaMallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        SikaMallOrder sikaMallOrder = sikaMallOrderMapper.selectByOrderNo(orderNo);
        if (sikaMallOrder != null) {
            //todo 订单状态判断 非待支付状态下不进行修改操作
            sikaMallOrder.setOrderStatus((byte) SikaMallOrderStatusEnum.OREDER_PAID.getOrderStatus());
            sikaMallOrder.setPayType((byte) payType);
            sikaMallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            sikaMallOrder.setPayTime(new Date());
            sikaMallOrder.setUpdateTime(new Date());
            if (sikaMallOrderMapper.updateByPrimaryKeySelective(sikaMallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public List<SikaMallOrderItemVO> getOrderItems(Long id) {
        SikaMallOrder sikaMallOrder = sikaMallOrderMapper.selectByPrimaryKey(id);
        if (sikaMallOrder != null) {
            List<SikaMallOrderItem> orderItems = sikaMallOrderItemMapper.selectByOrderId(sikaMallOrder.getOrderId());
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<SikaMallOrderItemVO> sikaMallOrderItemVOS = BeanUtil.copyList(orderItems, SikaMallOrderItemVO.class);
                return sikaMallOrderItemVOS;
            }
        }
        return null;
    }

    @Override
    public int save(SikaMallOrder sikaMallOrder) {
        return sikaMallOrderMapper.insert(sikaMallOrder);
    }
}