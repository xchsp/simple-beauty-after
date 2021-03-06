package ncu.soft.blog.controller.article;

import io.swagger.annotations.ApiOperation;
import ncu.soft.blog.entity.Message;
import ncu.soft.blog.selfannotation.LoginToken;
import ncu.soft.blog.service.ArticlesService;
import ncu.soft.blog.service.MessageService;
import ncu.soft.blog.utils.JsonResult;
import ncu.soft.blog.utils.ResultCode;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author www.xyjz123.xyz
 * @description
 * @date 2019/8/31 11:33
 */
@RestController
public class ManageController {

    @Resource
    ArticlesService articlesService;

    @Resource
    MessageService messageService;

    private final static String LIKE = "like";

    @ApiOperation("删除文章")
    @DeleteMapping("/articles/{aid}/{uid}")
    @LoginToken
    public JsonResult deleteArticle(@PathVariable("aid") int aid,@PathVariable("uid") String uid){
        articlesService.delete(aid,uid);
        return JsonResult.success();
    }

    @ApiOperation("文章点赞并推送消息")
    @PostMapping("/article/like/{aid}/{uid}")
    @LoginToken
    public JsonResult likeArticle(@Valid @PathVariable("aid") int aid,@PathVariable("uid") String uid){
        int likes = messageService.likeArticle(aid, uid);
        return JsonResult.success(likes);
    }

    @ApiOperation("获取某用户是否对文章点赞")
    @GetMapping("/article/like/{aid}/{uid}")
    public JsonResult isLike(@Valid @PathVariable("aid") int aid,@PathVariable("uid") String uid){
        Message message = messageService.getMessageByType(aid,uid,LIKE);
        if (message == null){
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }else {
            return JsonResult.success();
        }
    }

    @ApiOperation("获取用户未读消息数")
    @GetMapping("/message/{uid}")
    public JsonResult getNoReadMessage(@Valid @PathVariable("uid") String uid){
        long num = messageService.getMessageWithoutRead(uid);
        if (num >= 0) {
            return JsonResult.success(num);
        }else {
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }
    }

    @ApiOperation("获取某类型的用户未读消息数")
    @GetMapping("/message/{type}/{uid}")
    public JsonResult getNoReadMessage(@Valid @PathVariable("type") String type,@PathVariable("uid") String uid){
        long num = messageService.getMessageWithoutReadByType(type,uid);
        if (num >= 0) {
            return JsonResult.success(num);
        }else {
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }
    }

    @ApiOperation("改变消息未读为已读")
    @PutMapping("/message/{id}/{uid}")
    public JsonResult changeState(@Valid @PathVariable("id") int id,@PathVariable("uid") String uid){
        Boolean message = messageService.changeMessageState(id, uid);
        if (message){
            return JsonResult.success();
        }else {
            return JsonResult.failure(ResultCode.UPDATE_ERROR);
        }
    }

    @ApiOperation("分页分类获取消息列表")
    @GetMapping("/message/{uid}/{type}/{index}/{size}")
    public JsonResult getMessageByPage(@Valid @PathVariable("uid") String uid,@PathVariable("type") String type,
                                       @PathVariable("index") int index,@PathVariable("size") int size){
        PageImpl<Message> messages = messageService.getMessageByUidByPage(uid, type, index, size);
        if (messages.isEmpty()) {
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }else {
            return JsonResult.success(messages);
        }
    }

    @ApiOperation("删除消息")
    @DeleteMapping("/message/{id}")
    public JsonResult deleteMessageById(@Valid @PathVariable("id")int id){
        messageService.delete(id);
        return JsonResult.success();
    }
}