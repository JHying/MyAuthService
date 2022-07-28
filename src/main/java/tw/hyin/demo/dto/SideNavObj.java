package tw.hyin.demo.dto;

import lombok.Data;
import tw.hyin.demo.entity.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author B00359 on 2022.
 * @description 側導覽列物件
 */
@Data
public class SideNavObj implements Serializable {

    String pageIcon;
    String pageName;
    String pageUrl;
    List<PageInfo> childPages;

}
