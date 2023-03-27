
package bjfu.BJFU.mall.controller.mall;

import bjfu.BJFU.mall.common.Constants;
import bjfu.BJFU.mall.common.IndexConfigTypeEnum;
import bjfu.BJFU.mall.controller.vo.SikaMallIndexCarouselVO;
import bjfu.BJFU.mall.controller.vo.SikaMallIndexCategoryVO;
import bjfu.BJFU.mall.controller.vo.SikaMallIndexConfigGoodsVO;
import bjfu.BJFU.mall.entity.GoodsCategory;
import bjfu.BJFU.mall.service.SikaMallCarouselService;
import bjfu.BJFU.mall.service.SikaMallCategoryService;
import bjfu.BJFU.mall.service.SikaMallIndexConfigService;
import bjfu.BJFU.mall.util.Result;
import bjfu.BJFU.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private SikaMallCarouselService sikaMallCarouselService;

    @Resource
    private SikaMallIndexConfigService sikaMallIndexConfigService;

    @Resource
    private SikaMallCategoryService sikaMallCategoryService;



    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<SikaMallIndexCategoryVO> categories = sikaMallCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            return "error/error_5xx";
        }
        List<SikaMallIndexCarouselVO> carousels = sikaMallCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<SikaMallIndexConfigGoodsVO> hotGoodses = sikaMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<SikaMallIndexConfigGoodsVO> newGoodses = sikaMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<SikaMallIndexConfigGoodsVO> recommendGoodses = sikaMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);
        request.setAttribute("categories", categories);//分类数据
        request.setAttribute("carousels", carousels);//轮播图
        request.setAttribute("hotGoodses", hotGoodses);//热销商品
        request.setAttribute("newGoodses", newGoodses);//新品
        request.setAttribute("recommendGoodses", recommendGoodses);//推荐商品
        return "mall/index";
    }

    @GetMapping("/category")
    @ResponseBody
    public Result getSikaMallOneCategory() {
        List<GoodsCategory> goodsCategories = sikaMallCategoryService.selectByLevel(1);
        return ResultGenerator.genSuccessResult(goodsCategories);
    }

    @GetMapping("/hotGoods")
    @ResponseBody
    public Result getSikaMallHotGoods() {
        List<SikaMallIndexConfigGoodsVO> hotGoods = sikaMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(),8);
        return ResultGenerator.genSuccessResult(hotGoods);
    }



}
