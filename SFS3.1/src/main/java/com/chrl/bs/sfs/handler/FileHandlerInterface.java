package com.chrl.bs.sfs.handler;


/**
 * 读取一个文件的所有接口
 * User: chenruilin
 * Date: 13-10-10
 * To change this template use File | Settings | File Templates.
 */
public interface FileHandlerInterface {

    /**
     * 检查是否uri是否需要处理
     * @param uri
     * @return
     */
    boolean check(String uri);

    /**
     * 检查是否需要验证权限
     * @return
     */
    boolean security();

    boolean cacheable();

    String path(String uri);

    String defaultPath(String uri);

    String filename(String uri);

}
