
package bjfu.BJFU.mall.controller.mall;

import bjfu.BJFU.mall.common.Constants;
import bjfu.BJFU.mall.common.SikaMallException;
import bjfu.BJFU.mall.common.ServiceResultEnum;
import bjfu.BJFU.mall.controller.vo.SikaMallGoodsDetailVO;
import bjfu.BJFU.mall.controller.vo.SearchPageCategoryVO;
import bjfu.BJFU.mall.entity.GoodsCategory;
import bjfu.BJFU.mall.entity.SikaMallGoods;
import bjfu.BJFU.mall.service.SikaMallCategoryService;
import bjfu.BJFU.mall.service.SikaMallGoodsService;
import bjfu.BJFU.mall.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private SikaMallGoodsService sikaMallGoodsService;
    @Resource
    private SikaMallCategoryService sikaMallCategoryService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = sikaMallCategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", sikaMallGoodsService.searchSikaMallGoods(pageUtil));
        return "mall/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            return "error/error_5xx";
        }
        SikaMallGoods goods = sikaMallGoodsService.getSikaMallGoodsById(goodsId);
        if (goods == null) {
            SikaMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            SikaMallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        SikaMallGoodsDetailVO goodsDetailVO = new SikaMallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        return "mall/detail";
    }


    @GetMapping({"/goods/byCategoriesId"})
    @ResponseBody
    public Result<SikaMallGoods>selectGoodsByCategory(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        String goodsCategoryId = (String) params.get("goodsCategoryId");
        Long goodsCategoryIds = Long.valueOf(goodsCategoryId);

        List<Long>firstLevelparentIds =  new ArrayList<>();
        List<SikaMallGoods> sikaMallGoods = new ArrayList<>();

        firstLevelparentIds.add(goodsCategoryIds);
        List<GoodsCategory> secondGoodsCategories = sikaMallCategoryService.selectByLevelAndParentIdsAndNumber(firstLevelparentIds, 2);
        for(int i = 0;i<secondGoodsCategories.size();i++){
            List<Long>secondLevelparentIds =  new ArrayList<>();
            secondLevelparentIds.add(secondGoodsCategories.get(i).getCategoryId());
            List<GoodsCategory> thirdLevelCategories = sikaMallCategoryService.selectByLevelAndParentIdsAndNumber(secondLevelparentIds, 3);
            for(int j = 0;j<thirdLevelCategories.size();j++){
                List<SikaMallGoods> sikaMallGoodsByCategories = sikaMallGoodsService.getSikaMallGoodsByCategories(thirdLevelCategories.get(j).getCategoryId());
                sikaMallGoods.addAll(sikaMallGoodsByCategories);
            }
        }

        return ResultGenerator.genSuccessResult(sikaMallGoods);
    }
    @GetMapping("/product")
    @ResponseBody
    public Result productData(Long goodsId, HttpSession httpSession) {
        SikaMallGoods sikaMallGoodsById = sikaMallGoodsService.getSikaMallGoodsById(goodsId);

        return ResultGenerator.genSuccessResult(sikaMallGoodsById);

    }




}
