package bjfu.BJFU.mall.service;

import bjfu.BJFU.mall.controller.vo.SikaMallIndexCarouselVO;
import bjfu.BJFU.mall.entity.Carousel;
import bjfu.BJFU.mall.util.PageQueryUtil;
import bjfu.BJFU.mall.util.PageResult;

import java.util.List;

public interface SikaMallCarouselService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<SikaMallIndexCarouselVO> getCarouselsForIndex(int number);
}
