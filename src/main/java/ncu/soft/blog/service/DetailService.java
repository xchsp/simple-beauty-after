package ncu.soft.blog.service;

import ncu.soft.blog.entity.ArticleDetail;

/**
 * @author www.xyjz123.xyz
 * @description
 * @date 2019/8/26 8:10
 */
public interface DetailService {

    /**
     * 保存文章详情内容
     * @param articleDetail 文章内容
     */
    ArticleDetail save(ArticleDetail articleDetail);

    /**
     * 更新文章详情
     * @param articleDetail 文章详情
     * @return ArticleDetail
     */
    ArticleDetail update(ArticleDetail articleDetail);

    /**
     * 删除文章内容
     * @param aid 文章id
     */
    void delete(int aid);

    /**
     * 根据文章id获取文章内容
     * @param aid 文章id
     * @return 文章内容
     */
    ArticleDetail getArticleByAid(int aid);
}