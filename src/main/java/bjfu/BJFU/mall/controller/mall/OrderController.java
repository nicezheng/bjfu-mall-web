
package bjfu.BJFU.mall.controller.mall;

import bjfu.BJFU.mall.common.Constants;
import bjfu.BJFU.mall.common.SikaMallException;
import bjfu.BJFU.mall.common.ServiceResultEnum;
import bjfu.BJFU.mall.controller.vo.SikaMallOrderDetailVO;
import bjfu.BJFU.mall.controller.vo.SikaMallShoppingCartItemVO;
import bjfu.BJFU.mall.controller.vo.SikaMallUserVO;
import bjfu.BJFU.mall.entity.Address;
import bjfu.BJFU.mall.entity.SikaMallOrder;
import bjfu.BJFU.mall.service.AddressService;
import bjfu.BJFU.mall.service.SikaMallOrderService;
import bjfu.BJFU.mall.service.SikaMallShoppingCartService;
import bjfu.BJFU.mall.service.SikaMallUserService;
import bjfu.BJFU.mall.util.PageQueryUtil;
import bjfu.BJFU.mall.util.Result;
import bjfu.BJFU.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Resource
    private SikaMallShoppingCartService sikaMallShoppingCartService;
    @Resource
    private SikaMallOrderService sikaMallOrderService;
    @Resource
    private AddressService addressService;
    @Resource
    private SikaMallUserService sikaMallUserService;
    @GetMapping("/orders/{orderNo}")
    public String orderDetailPage(HttpServletRequest request, @PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        SikaMallOrderDetailVO orderDetailVO = sikaMallOrderService.getOrderDetailByOrderNo(orderNo, user.getUserId());
        if (orderDetailVO == null) {
            return "error/error_5xx";
        }
        request.setAttribute("orderDetailVO", orderDetailVO);
        return "mall/order-detail";
    }

    @GetMapping("/orders")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("userId", user.getUserId());
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("orderPageResult", sikaMallOrderService.getMyOrders(pageUtil));
        request.setAttribute("path", "orders");
        return "mall/my-orders";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<SikaMallShoppingCartItemVO> myShoppingCartItems = sikaMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (StringUtils.isEmpty(user.getAddress().trim())) {
            //无收货地址
            SikaMallException.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物车中无数据则跳转至错误页
            SikaMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        //保存订单并返回订单号
        String saveOrderResult = sikaMallOrderService.saveOrder(user, myShoppingCartItems);
        //跳转到订单详情页
        return "redirect:/orders/" + saveOrderResult;
    }

    @PutMapping("/orders/{orderNo}/cancel")
    @ResponseBody
    public Result cancelOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String cancelOrderResult = sikaMallOrderService.cancelOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/orders/{orderNo}/finish")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String finishOrderResult = sikaMallOrderService.finishOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/selectPayType")
    public String selectPayType(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        SikaMallOrder sikaMallOrder = sikaMallOrderService.getSikaMallOrderByOrderNo(orderNo);
        //todo 判断订单userId
        //todo 判断订单状态
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", sikaMallOrder.getTotalPrice());
        return "mall/pay-select";
    }

    @GetMapping("/payPage")
    public String payOrder(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession, @RequestParam("payType") int payType) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        SikaMallOrder sikaMallOrder = sikaMallOrderService.getSikaMallOrderByOrderNo(orderNo);
        //todo 判断订单userId
        //todo 判断订单状态
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", sikaMallOrder.getTotalPrice());
        if (payType == 1) {
            return "mall/alipay";
        } else {
            return "mall/wxpay";
        }
    }

    @GetMapping("/paySuccess")
    @ResponseBody
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        String payResult = sikaMallOrderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }
    @GetMapping("/defaultAddress")
    @ResponseBody
    public Result getDefaultAddress( HttpSession httpSession) {
        return ResultGenerator.genSuccessResult(addressService.getDefaultAdd());
    }

    @GetMapping("/handOrder")
    @ResponseBody
    public Result handOrder(Long userId, HttpSession httpSession) {

        List<SikaMallShoppingCartItemVO> myShoppingCartItems = sikaMallShoppingCartService.getMyShoppingCartItems(userId);
        SikaMallUserVO sikaMallUserVO = new SikaMallUserVO();
        sikaMallUserVO.setUserId(userId);
        sikaMallOrderService.saveOrder(sikaMallUserVO,myShoppingCartItems);
        return ResultGenerator.genSuccessResult(new SikaMallOrder());
    }

    @GetMapping("/addressList")
    @ResponseBody
    public Result getAddress(Long userId, HttpSession httpSession) {
        List<Address> addressByUserId = addressService.getAddressByUserId(userId);
        return ResultGenerator.genSuccessResult(addressByUserId);
    }

    @GetMapping("/deleteAddress")
    @ResponseBody
    public Result deleteAddress(int id, HttpSession httpSession) {
        int i = addressService.deleteAddress(id);
        return ResultGenerator.genSuccessResult(i);
    }


}
