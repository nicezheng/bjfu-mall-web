
package bjfu.BJFU.mall.controller.mall;

import bjfu.BJFU.mall.common.Constants;
import bjfu.BJFU.mall.common.ServiceResultEnum;
import bjfu.BJFU.mall.controller.vo.SikaMallShoppingCartItemVO;
import bjfu.BJFU.mall.controller.vo.SikaMallUserVO;
import bjfu.BJFU.mall.entity.SikaMallShoppingCartItem;
import bjfu.BJFU.mall.service.SikaMallShoppingCartService;
import bjfu.BJFU.mall.util.Result;
import bjfu.BJFU.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShoppingCartController {

    @Resource
    private SikaMallShoppingCartService sikaMallShoppingCartService;

    @GetMapping("/shop-cart")
    public String cartListPage(HttpServletRequest request,
                               HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        int itemsTotal = 0;
        int priceTotal = 0;
        List<SikaMallShoppingCartItemVO> myShoppingCartItems = sikaMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数
            itemsTotal = myShoppingCartItems.stream().mapToInt(SikaMallShoppingCartItemVO::getGoodsCount).sum();
            if (itemsTotal < 1) {
                return "error/error_5xx";
            }
            //总价
            for (SikaMallShoppingCartItemVO sikaMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += sikaMallShoppingCartItemVO.getGoodsCount() * sikaMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                return "error/error_5xx";
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/cart";
    }

    @PostMapping("/shop-cart")
    @ResponseBody
    public Result saveSikaMallShoppingCartItem(@RequestBody SikaMallShoppingCartItem sikaMallShoppingCartItem,
                                                 HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        sikaMallShoppingCartItem.setUserId(user.getUserId());
        //todo 判断数量
        String saveResult = sikaMallShoppingCartService.saveSikaMallCartItem(sikaMallShoppingCartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/shop-cart")
    @ResponseBody
    public Result updateSikaMallShoppingCartItem(@RequestBody SikaMallShoppingCartItem sikaMallShoppingCartItem,
                                                   HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        sikaMallShoppingCartItem.setUserId(user.getUserId());
        //todo 判断数量
        String updateResult = sikaMallShoppingCartService.updateSikaMallCartItem(sikaMallShoppingCartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/shop-cart/{SikaMallShoppingCartItemId}")
    @ResponseBody
    public Result updateSikaMallShoppingCartItem(@PathVariable("SikaMallShoppingCartItemId") Long SikaMallShoppingCartItemId,
                                                   HttpSession httpSession) {
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Boolean deleteResult = sikaMallShoppingCartService.deleteById(SikaMallShoppingCartItemId);
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    public String settlePage(HttpServletRequest request,
                             HttpSession httpSession) {
        int priceTotal = 0;
        SikaMallUserVO user = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<SikaMallShoppingCartItemVO> myShoppingCartItems = sikaMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //无数据则不跳转至结算页
            return "/shop-cart";
        } else {
            //总价
            for (SikaMallShoppingCartItemVO sikaMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += sikaMallShoppingCartItemVO.getGoodsCount() * sikaMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                return "error/error_5xx";
            }
        }
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/order-settle";
    }


    @GetMapping("/cartData")
    @ResponseBody
    public Result cartData(String userId, HttpSession httpSession) {

        List<SikaMallShoppingCartItemVO> myShoppingCartItems = sikaMallShoppingCartService.getMyShoppingCartItems(Long.valueOf(userId));
        return ResultGenerator.genSuccessResult(myShoppingCartItems);

    }


    @GetMapping("/deleteCartItem")
    @ResponseBody
    public Result deleteSikaMallShoppingCartItem(Long goodsId,
                                                 HttpSession httpSession) {
        Boolean deleteResult = sikaMallShoppingCartService.deleteByGoodsId(goodsId);
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/updateCartItem")
    @ResponseBody
    public Result updateSikaMallShoppingCartItem(Long goodsId,int count,
                                                 HttpSession httpSession) {
        Boolean updateResult = sikaMallShoppingCartService.updateCountByGoodsId(goodsId, count);
        //删除成功
        if (updateResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }


    @GetMapping("/addCartItem")
    @ResponseBody
    public Result addCartItem(String goodsId,String count,String userId, HttpSession httpSession) {
        SikaMallShoppingCartItem sikaMallShoppingCartItem = new SikaMallShoppingCartItem();
        System.out.println(sikaMallShoppingCartItem);
        sikaMallShoppingCartItem.setUserId(Long.valueOf(userId));
        sikaMallShoppingCartItem.setGoodsCount(Integer.valueOf(count));
        sikaMallShoppingCartItem.setGoodsId(Long.valueOf(goodsId));
        String s = sikaMallShoppingCartService.saveSikaMallCartItem(sikaMallShoppingCartItem);
        return ResultGenerator.genSuccessResult(s);
    }
}
