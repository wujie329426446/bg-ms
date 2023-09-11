package com.bg.commons.pagination;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.constant.CommonConstant;
import io.jsonwebtoken.lang.Collections;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 可排序查询参数对象
 *
 * @author jiewus
 */
@Data
@Accessors(chain = true)
@Schema(description = "可排序查询参数对象")
public abstract class BasePageParam implements Serializable {

  @Serial
  private static final long serialVersionUID = 57714391204790143L;

  @Schema(description = "搜索字符串", example = "")
  private String keyword;

  @Schema(description = "分页参数")
  private Page page;

  @Schema(description = "排序参数")
  private List<OrderItem> pageSorts;

  @Schema(description = "排序字段映射", hidden = true)
  private OrderMapping orderMapping = new OrderMapping(true);

  public BasePageParam() {
    this.page = new Page(CommonConstant.DEFAULT_PAGE_INDEX, CommonConstant.DEFAULT_PAGE_SIZE);
    this.pageSorts = new ArrayList<>();
  }

  public void setPage(Page page) {
    if (Objects.isNull(page)) {
      this.page = new Page(CommonConstant.DEFAULT_PAGE_INDEX, CommonConstant.DEFAULT_PAGE_SIZE);
    }
  }

  public Page getPage() {
    if (Objects.isNull(page)) {
      this.page = new Page(CommonConstant.DEFAULT_PAGE_INDEX, CommonConstant.DEFAULT_PAGE_SIZE);
    }
    return this.page;
  }

  public void pageSortsHandle() {
    List<OrderItem> orderItems = new ArrayList<>();
    if (!Collections.isEmpty(pageSorts)) {
      orderItems.addAll(pageSorts);
    }
    orderMapping.filterOrderItems(orderItems);
    this.page.setOrders(orderItems);
  }

  public void pageSortsHandle(OrderItem orderItem) {
    this.pageSortsHandle(Arrays.asList(orderItem));
  }

  public void pageSortsHandle(List<OrderItem> pageSorts) {
    if (CollectionUtils.isNotEmpty(pageSorts)) {
      this.pageSorts.addAll(pageSorts);
    }
    this.page.setOrders(pageSorts);
  }

}
