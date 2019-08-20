package com.leyou.common.config.rabbitmq;

public interface RabbitMqProperties {

    /**
     * 添加商品路由key
     */
    public static final String ADD_GOODS_ROUTER_KEY = "leyou.item.insert";

    /**
     * page服务修改商品队列
     */
    public static final String EDIT_GOODS_PAGE_QUEUE = "leyou.item.edit_page_queue";

    /**
     * search服务修改商品队列
     */
    public static final String EDIT_GOODS_SEARCH_QUEUE = "leyou.item.edit_search_queue";

    /**
     * page服务删除商品队列
     */
    public static final String DELETE_GOODS_PAGE_QUEUE = "leyou.item.delete_page_queue";

    /**
     * search服务删除商品队列
     */
    public static final String DELETE_GOODS_SEARCH_QUEUE = "leyou.item.delete_search_queue";
    /**
     * 更新商品路由key
     */
    public static final String UPDATE_ROUTER_KEY = "leyou.item.update";

    /**
     * 删除商品路由key
     */
    public static final String DELETE_GOODS_KEY = "leyou.item.delete";


    /**
     * 交换机名称
     */
    public static final String EXCHANGE_GOODS_NAME = "ly.item.exchange";


    /**
     * 短信服务监听队列
     */
    public static final String SMS_VERIFY_CODE_QUEUE = "sms.verify.code.queue";


    public static final String SMS_VERIFY_CODE_KEY = "sms.verify.code";
}
