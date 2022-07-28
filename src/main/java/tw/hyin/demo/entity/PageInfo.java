package tw.hyin.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author H-yin on 2021.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
@Table(name = "PageInfo")
public class PageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PAGE_ID")
    private String pageId;

    @Column(name = "PAGE_NAME")
    private String pageName;

    @Column(name = "PAGE_ICON")
    private String pageIcon;

    @Column(name = "PAGE_URL")
    private String pageUrl;

    @Column(name = "PAGE_PARENT")
    private String pageParent;

}
